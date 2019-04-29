module MyMixin

  def self.included(observed)


    observed.instance_variable_set :@before_procs, []
    observed.instance_variable_set :@after_procs, []
    observed.instance_variable_set :@invariant_procs, []

    def observed.invariant_procs
      @invariant_procs
    end

    def observed.after_procs
      @after_procs
    end

    def observed.before_procs
      @before_procs
    end

    observed.instance_methods(false).each do |func|
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

    def observed.before_and_after_each_call(proc1, proc2)
      @before_procs << proc1
      @after_procs << proc2
    end

    def observed.invariant(&invariant_proc)
      @invariant_procs << invariant_proc
    end
  end

  def self.inject(target, meth)

    target.instance_eval do
      method_object = instance_method(meth)

      define_method(meth) do |*args, &block|
        procs_to_call_before = self.class.before_procs
        procs_to_call_after = self.class.after_procs
        procs_to_call_after_invariants = self.class.invariant_procs

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
