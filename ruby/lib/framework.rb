class PreAndPosContext

  def initialize(alternative_self)
    @alternative_self = alternative_self
  end

  def register(parameter_name, parameter_value)
    singleton_class.send(:attr_accessor, parameter_name)
    self.send("#{parameter_name}=", parameter_value)
  end

  def method_missing(method_name, *args, &block)
    @alternative_self.send(method_name, *args)
  end
end

module BeforeAndAfter

  def before_and_after(observed)


    observed.instance_variable_set :@before_procs, []
    observed.instance_variable_set :@after_procs, []

    def observed.after_procs
      @after_procs
    end

    def observed.before_procs
      @before_procs
    end

    def observed.before_and_after_each_call(proc1, proc2)
      @before_procs << proc1
      @after_procs << proc2
    end

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

  end
end

module GeneralExclusions

  def method_exclusions(observed)

    observed.instance_variable_set :@exclusions, ['initialize']

    def observed.exclude(method_name)
      @exclusions << method_name
    end

    def observed.is_excluded?(method)
      @exclusions.include? method.to_s
    end

  end

end


module MyMixin
  extend BeforeAndAfter
  extend InvariantProcs
  extend PreAndPost
  extend GeneralExclusions

  def self.included(observed)

    before_and_after(observed)
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
        MyMixin.inject(self, meth) #This will call method_added itself, the condition prevents infinite recursion.
        @called_internal = false
      end
    end
  end

  def self.inject(target, meth)

    target.instance_eval do

      method_object = instance_method(meth)
      actual_pre = get_pre

      define_method(meth) do |*args, &block|


        my_class = self.class
        procs_to_call_before = my_class.before_procs
        procs_to_call_after = my_class.after_procs
        procs_to_call_after_invariants = my_class.invariant_procs

        if @evaluating.nil?
          @evaluating = false
        end

        first_evaluation = !@evaluating
        should_not_execute = my_class.is_excluded?(meth) || @evaluating
        @evaluating = true

        procs_to_call_before.each {|x| self.instance_exec &x} unless should_not_execute

        unless actual_pre.nil?
          context = PreAndPosContext.new(self)
          parameter_list = method_object.parameters.map {|param| param[1].to_s}.zip(args)
          parameter_list.each {|parameter| context.register parameter[0], parameter[1]}
          pre_ok = context.instance_exec &actual_pre

          if !pre_ok
            raise PreError
          end
        end

        result = method_object.bind(self).call(*args, &block)
        procs_to_call_after.each {|x| self.instance_exec &x} unless should_not_execute

        if !should_not_execute && procs_to_call_after_invariants.any? {|x| !self.instance_exec &x}
          raise InvariantError
        end

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
