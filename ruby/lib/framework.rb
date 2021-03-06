class PreAndPosContext

  def initialize(alternative_self)
    @alternative_self = alternative_self
  end

  def register(parameter_name, parameter_value)
    singleton_class.define_method(:"#{parameter_name}") {parameter_value}
  end

  def method_missing(method_name, *args, &block)
    @alternative_self.send(method_name, *args)
  end
end

module InvariantProcs

  def invariant_procs(observed)
    observed.instance_variable_set :@invariant_procs, []

    def observed.invariant(&invariant_proc)
      @invariant_procs << invariant_proc
    end

    def observed.invariant_procs
      @invariant_procs
    end

    def observed.execute_invariants(instance)
      if @invariant_procs.any? {|x| !instance.instance_exec &x}
        raise InvariantError
      end
    end

  end

end

module PreAndPost
  def pre_and_post(observed)

    observed.instance_variable_set :@pre_proc, nil
    observed.instance_variable_set :@post_roc, nil

    def observed.pre(&pre_condition)
      @pre_proc = pre_condition
    end

    def observed.post(&post_condition)
      @post_proc = post_condition
    end

    def observed.get_pre
      pre_tu_return = @pre_proc
      @pre_proc = nil
      pre_tu_return
    end

    def observed.get_post
      post_tu_return = @post_proc
      @post_proc = nil
      post_tu_return
    end

    def observed.execute_pre(instance, actual_pre, method_object, *args)
      unless actual_pre.nil?
        context = generate_pre_and_post_context(args, instance, method_object)
        pre_ok = context.instance_exec &actual_pre
        unless pre_ok
          raise PreError
        end
      end
    end

    def observed.execute_post(instance, actual_post, method_object, *args, result)
      unless actual_post.nil?
        context = generate_pre_and_post_context(args, instance, method_object)
        post_ok = context.instance_exec(result, &actual_post)
        unless post_ok
          raise PostError
        end
      end
    end

    def observed.generate_pre_and_post_context(args, instance, method_object)
      context = PreAndPosContext.new(instance)
      parameter_list = method_object.parameters.map {|param| param[1].to_s}.zip(args)
      parameter_list.each {|parameter| context.register parameter[0], parameter[1]}
      context
    end

  end


end

module GeneralExclusions

  def method_exclusions(observed)

    observed.instance_variable_set :@exclusions, [] #, ['initialize']

    def observed.exclude(method_name)
      @exclusions << method_name
    end

    def observed.is_excluded?(method)
      @exclusions.include? method.to_s
    end

  end

end


module TADPFramework
  extend InvariantProcs
  extend PreAndPost
  extend GeneralExclusions

  def self.included(observed)

    invariant_procs(observed)
    pre_and_post(observed)
    method_exclusions(observed)
    @evaluating = false

    observed.instance_methods(false).select {|method| !is_excluded?(method)}.each do |func|
      inject(observed, func)
    end

    #Override the singletons method_added to ensure all future methods are injected.
    def observed.method_added(meth)
      unless @called_internal
        @called_internal = true
        TADPFramework.inject(self, meth) #This will call method_added itself, the condition prevents infinite recursion.
        @called_internal = false
      end
    end
  end

  def self.inject(target, meth)

    target.instance_eval do

      method_object = instance_method(meth)
      actual_pre = get_pre
      actual_post = get_post

      define_method(meth) do |*args, &block|

        my_class = self.class

        if @evaluating.nil?
          @evaluating = false
        end

        first_evaluation = !@evaluating
        should_not_execute = my_class.is_excluded?(meth) || @evaluating
        @evaluating = true

        my_class.execute_pre(self, actual_pre, method_object, *args)

        result = method_object.bind(self).call(*args, &block)

        my_class.execute_post(self, actual_post, method_object, *args, result)
        my_class.execute_invariants(self) unless should_not_execute

        if first_evaluation
          @evaluating = false
        end

        result
      end

    end
  end
end

class InvariantError < StandardError

end

class PreError < StandardError

end

class PostError < StandardError

end
