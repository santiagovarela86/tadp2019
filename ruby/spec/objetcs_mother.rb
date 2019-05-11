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

  attr_accessor :vida, :fuerza

  invariant {vida == 1}
  invariant {fuerza > 10}

  def initialize(vida, fuerza)
    self.vida = vida
    self.fuerza = fuerza
  end

  before_and_after_each_call(
      proc {puts "Entré a un mensaje"},
      proc {puts "Salí de un mensaje"}
  )

  before_and_after_each_call(
      proc {puts "Entré a un mensaje2"},
      proc {self.fuerza = 12} #Not working if use only fuerza without self
  )

  def test_method
    3
  end

  def test_method_2
    @vida = 2
  end

  pre {vida > 0 && amount > 5}

  def add_vida(amount)
    @vida = @vida + amount
  end

end


