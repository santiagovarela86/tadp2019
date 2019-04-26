#######################################
class Class
  def invariant(&block)
    puts block.to_s
  end
end
#######################################
class TestInvariant
  attr_accessor :vida, :fuerza

  def initialize(vida=100, fuerza=100)
    @vida = vida
    @fuerza = fuerza
  end

  invariant { vida >= 0 }
  invariant { fuerza > 0 && fuerza < 100 }
end
#######################################
