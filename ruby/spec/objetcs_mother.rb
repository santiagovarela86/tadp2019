require_relative '../lib/framework'

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

  invariant {@gato == 1}

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

  def test_method
    3
  end

  def test_method_2
    @gato = 2
  end

end


