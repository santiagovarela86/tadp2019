#######################################
require_relative "antes_despues.rb"
#######################################
class InvariantException < Exception
end
#######################################
module Invariant
  def self.included(othermod)
    othermod.include(BeforeAndAfter)

    def othermod.invariant(&block)
      @new_block = proc {
        unless self.instance_eval(&block)
          raise InvariantException
        end
      }

      __after(@new_block)
    end
  end
end
#######################################
class Guerrero
  include Invariant

  attr_accessor :vida, :fuerza

  invariant { vida >= 0 }
  invariant { fuerza > 0 && fuerza < 100 }

  def initialize(v=100, f=99)
    @vida = v
    @fuerza = f
  end

  def atacar(otro)
    otro.vida -= fuerza
  end
end
#######################################
puts "Antes de crear el guerrero 1"
guerrero1 = Guerrero.new(100,99)
puts "Despues de crear el guerrero 1"
puts "Antes de crear el guerrero 2"
guerrero2 = Guerrero.new(100,99)
puts "Despues de crear el guerrero 2"
puts "Antes del primer ataque"
guerrero1.atacar(guerrero2)
puts "Despues del primer ataque"
puts "Antes del segundo ataque"
#guerrero1.atacar(guerrero2) #tira excepcion
puts "Despues del segundo ataque"

#guerrero3 = Guerrero.new(-10,1) #tira excepcion
#guerrero4 = Guerrero.new(1,0) #tira excepcion
#######################################
