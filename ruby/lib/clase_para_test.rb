#######################################
require_relative 'framework.rb'
#######################################
class ClaseParaTest

  include Framework

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