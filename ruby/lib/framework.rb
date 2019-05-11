module BeforeAndAfter

  def before_and_after(observed)

    observed.instance_variable_set :@before_procs, []
    observed.instance_variable_set :@after_procs, []

    def observed.invariant_procs
      @invariant_procs
    end

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

  end

end

module PreAndPost
  def pre_and_post(observed)

    observed.instance_variable_set :@pre_proc, nil
    observed.instance_variable_set :@post_roc, nil

    def observed.pre(&preCondition)
      @pre_proc = preCondition
    end

    def observed.post(&preCondition)
      @pre_proc = preCondition
    end

    def observed.pre
      pre_tu_return = @pre_proc
      @pre_proc = nil
      pre_tu_return
    end

    def observed.post
      post_tu_return = @post_proc
      @post_proc = nil
      post_tu_return
    end

  end
end

module Exclusions

  def method_exclusions(observed)

    observed.instance_variable_set :@exclusions, ['initialize']

    def observed.exclude(method_name)
      @exclusions = method_name
    end

    def observed.is_excluded?(method_name)
      @exclusions.include?method_name.to_s
    end

  end

end


module MyMixin
  extend BeforeAndAfter
  extend InvariantProcs
  extend PreAndPost
  extend Exclusions

  def self.included(observed)

    before_and_after(observed)
    invariant_procs(observed)
    pre_and_post(observed)
    method_exclusions(observed)

    observed.instance_methods(false).select{ |method_name| !is_excluded?(method_name) }.each do |func|
      inject(observed, func)
    end

    #Override the singletons method_added to ensure all future methods are injected.
    def observed.method_added(meth)
      unless @called_internal || is_excluded?(meth)
        @called_internal = true
        MyMixin.inject(self, meth) #This will call method_added itself, the condition prevents infinite recursion.
        @called_internal = false
      end
    end
  end

  def self.inject(target, meth)

    target.instance_eval do
      method_object = instance_method(meth)

      define_method(meth) do |*args, &block|
        my_class = self.class
        procs_to_call_before = my_class.before_procs
        procs_to_call_after = my_class.after_procs
        procs_to_call_after_invariants = my_class.invariant_procs
        procs_to_call_before.each {|x| self.instance_eval(&x)}
        result = method_object.bind(self).call(*args, &block)
        procs_to_call_after.each {|x| self.instance_eval(&x)}
        if procs_to_call_after_invariants.any? {|x| !self.instance_eval(&x)}
          raise InvariantError
        end
        result
      end

    end
  end
end

class InvariantError < StandardError

end
