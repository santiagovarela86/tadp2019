#######################################
require_relative "antes_despues.rb"
#######################################
class InvariantException < Exception
end
#######################################
class Class
  def invariant(&block)

    @new_block = proc {
      unless self.instance_eval(&block)
        raise InvariantException
      end
    }

    before_and_after_each_call(proc {}, @new_block)
  end
end
#######################################
class Guerrero
  attr_accessor :vida, :fuerza

  invariant { vida >= 0 }
  invariant { fuerza > 0 && fuerza < 100 }

  def initialize(vida=100, fuerza=100)
    @vida = vida
    @fuerza = fuerza
  end

  def atacar(otro)
    otro.vida -= fuerza
  end
end
#######################################
puts "Antes de crear el guerrero 1"
guerrero1 = Guerrero.new(100,99)
puts "Despues de crear el guerrero 1"
puts "Antes de crear el guerrero 1"
guerrero2 = Guerrero.new(100,99)
puts "Despues de crear el guerrero 2"
puts "Antes del primer ataque"
guerrero1.atacar(guerrero2)
puts "Despues del primer ataque"
puts "Antes del segundo ataque"
guerrero1.atacar(guerrero2)
puts "Despues del segundo ataque"
