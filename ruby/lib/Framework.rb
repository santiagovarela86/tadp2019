module MyMixin

  def self.included(observed)


    observed.instance_variable_set :@beforeProcs, []
    observed.instance_variable_set :@afterProcs, []
    observed.instance_variable_set :@invariantProcs, []

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
      @beforeProcs << proc1
      @afterProcs << proc2
    end

    def observed.invariant(&invariant_proc)
      @invariantProcs << invariant_proc
    end
  end

  def self.inject(target, meth)

    target.instance_eval do
      method_object = instance_method(meth)
      procs_to_call_before = @beforeProcs
      procs_to_call_after = @afterProcs
      procs_to_call_after_invariants = @invariantProcs

      define_method(meth) do |*args, &block|
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

module SomeModule

  def hola
    puts 'Hola'
  end

end

module AnotherModule
  def hola
    puts 'Holitas'
  end
end


class SomeTestClass
  include MyMixin
  include AnotherModule
  include SomeModule

  invariant {@gato == 10}

  def initialize
    @perro = 10
    @gato = 1
  end

  before_and_after_each_call(
      proc {puts "Entré a un mensaje"},
      proc {puts "Salí de un mensaje"}
  )

  before_and_after_each_call(
      proc {puts "Entré a un mensaje2"},
      proc {@perro += 1}
  )

  def gilada
    puts 'gilada'
    puts @perro
    3
  end

  def gilada2
    @gato = 2
  end

end

=begin

someInstance = SomeTestClass.new
someInstance.gilada
someInstance.gilada
anotherInstance = SomeTestClass.new
algo = anotherInstance.gilada
puts "algo: #{algo}"
=end


someInstance = SomeTestClass.new
someInstance.gilada
someInstance.gilada
someInstance.gilada2


