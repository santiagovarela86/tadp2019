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
      proc {self.fuerza = 12} #Doesnt worki if use only fuerza because creates local variable
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

class Operaciones
  include MyMixin
  #precondición de dividir
  pre {divisor != 0}
  #postcondición de dividir
  post {|result| result * divisor == dividendo}

  def dividir(dividendo, divisor)
    dividendo / divisor
  end

  post {|result| result == num1 + num2}

  def suma(num1, num2)
    num1 - num2
  end


end

#######################################
class Pila

  include MyMixin

  attr_accessor :current_node, :capacity

  invariant { capacity >= 0 }

  post { empty? }
  def initialize(capacity)
    @capacity = capacity
    @current_node = nil
  end

  pre { !full? }
  post { height > 0 }
  def push(element)
    @current_node = Node.new(element, current_node)
  end

  pre { !empty? }
  def pop
    element = top
    @current_node = @current_node.next_node
    element
  end

  pre { !empty? }
  def top
    current_node.element
  end

  def height
    empty? ? 0 : current_node.size
  end

  def empty?
    current_node.nil?
  end

  def full?
    height == capacity
  end

  Node = Struct.new(:element, :next_node) do
    def size
      next_node.nil? ? 1 : 1 + next_node.size
    end
  end
end
#######################################
class ClaseParaTest

  include MyMixin

  attr_accessor :atributo1, :atributo2, :atributo3, :atributo4

  invariant { atributo1 > 0 }
  invariant { atributo3 < 0 }

  def initialize(atributo1, atributo2, atributo3, atributo4)
    @atributo1 = atributo1
    @atributo2 = atributo2
    @atributo3 = atributo3
    @atributo4 = atributo4
  end

  pre { 1.equal?(2) }
  def metodo1
    @atributo1
  end

  post { 1.equal?(2) }
  def metodo2
    @atributo2
  end

  pre { 1.equal?(2) }
  post { 1.equal?(1) }
  def metodo3
    @atributo1
  end

  pre { 1.equal?(1) }
  post { 1.equal?(2) }
  def metodo4
    @atributo2
  end

  post { |result| result * divisor == dividendo + 1}
  def prueboResultEnPostCondicion(dividendo, divisor)
    dividendo / divisor
  end

end
#######################################
#