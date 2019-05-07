#######################################
class InvariantException < Exception
end
#######################################
class PrecondicionException < Exception
end
#######################################
class PostcondicionException < Exception
end
#######################################
module Framework
  def self.included(othermod)
    othermod.instance_variable_set :@befs, []
    othermod.instance_variable_set :@afts, []

    def othermod.before_and_after_each_call(bef, aft)
      __before(bef)
      __after(aft)
    end

    def othermod.__before(bef)
      @befs ||= []
      @befs << bef
    end

    def othermod.__after(aft)
      @afts ||= []
      @afts << aft
    end

    def othermod.__befs
      @befs.nil? ? [] : @befs
    end

    def othermod.__afts
      @afts.nil? ? [] : @afts
    end

    def othermod.invariant(&block)
      @new_block = proc {
        unless self.instance_eval(&block)
          raise InvariantException
        end
      }
      __after(@new_block)
    end

    def othermod.pre(&pre)
      @pre = pre
    end

    def othermod.post(&post)
      @post = post
    end

    def othermod.__pre
      @pre
    end

    def othermod.__post
      @post
    end

    def othermod.__pre=(pre)
      @pre = pre
    end

    def othermod.__post=(post)
      @post = post
    end

    othermod.instance_methods(false).each do |instance_method|
      reemplazar(othermod, instance_method)
    end

    def othermod.method_added(original_method_name)
      unless @already_replacing_method
        @already_replacing_method = true
        Framework.reemplazar(self, original_method_name)
        @already_replacing_method = false
      end
    end

    def self.reemplazar(othermod, original_method_name)
      othermod.instance_eval do
        original_method_object = instance_method(original_method_name)
        define_method(original_method_name) do |*args, &block|
          bef_procs = othermod.__befs
          aft_procs = othermod.__afts
          pre = othermod.__pre
          post = othermod.__post
          othermod.__pre = nil
          othermod.__post = nil

          #aca defino las variables y metodos a obtener
          #tendria que buscar la forma de luego sacarlos
          #para no ensuciar la interfaz de la clase que implemente esto
          original_method_object.parameters.each{|param|
            instance_variable_set("@" + param[1].to_s, args[original_method_object.parameters.find_index(param)])
            othermod.define_method(param[1].to_s) do
              instance_variable_get("@" + param[1].to_s)
            end
          }

          unless @esteLoopeando
            @esteLoopeando = true

            if !pre.equal?(nil)
              unless self.instance_eval(&pre)
                raise PrecondicionException
              end
            end

            bef_procs.each {|x| self.instance_eval(&x)}
            result = original_method_object.bind(self).call(*args, &block)

            if !post.equal?(nil)
              unless self.instance_exec(result, &post)
                raise PostcondicionException
              end
            end

            aft_procs.each {|x| self.instance_eval(&x)}
            @esteLoopeando = false
            result
          else
            result = original_method_object.bind(self).call(*args, &block)
            result
          end
        end
      end
    end
  end
end
#######################################
class MyClass
  include Framework

  before_and_after_each_call(proc{ puts "Entré a un mensaje1" },
                             proc{ puts "Salí de un mensaje1" })
  before_and_after_each_call(proc{ puts "Entré a un mensaje2" },
                             proc{ puts "Salí de un mensaje2" })

  def hola(un_parametro)
    puts "Hola: " + un_parametro
  end

  def chau
    puts "Chau: sin parametro"
  end

  def chau2(uno, dos)
    puts "Chau2: " + uno + " " + dos
  end

  def pruebo_bloque(&block)
    block.call("funciona OK")
  end

  def test_return
    puts 'TestReturn'
    10
  end
end
#######################################
class AnotherClass
  include Framework

  before_and_after_each_call(proc{ puts "Into another message1" },
                             proc{ puts "Out of another message1" })
  before_and_after_each_call(proc{ puts "Into another message2" },
                             proc{ puts "Out of another message2" })

  def another_method
    puts "Another message"
  end
end
#######################################
class NoBefore
  def no_before
    puts "No before OK."
  end
end
#######################################
class WithAccessors
  include Framework

  before_and_after_each_call(proc{ puts "Before with accessors" },
                             proc{ puts "After with accessors" })

  attr_accessor :atributo1, :atributo2

  def initialize(atr1, atr2)
    @atributo1 = atr1
    @atributo2 = atr2
    puts "Inicializando el objeto"
  end
end
#######################################
obj = MyClass.new
obj.hola("un parametro")
obj.chau
obj.chau2("dos","parametros")
obj.pruebo_bloque{ |n| puts "El bloque " + n }

obj2 = MyClass.new
a = obj2.test_return
puts a

obj3 = AnotherClass.new
obj3.another_method

obj4 = NoBefore.new
obj4.no_before

obj5 = WithAccessors.new(1,2)
puts obj5.atributo1
puts obj5.atributo2
obj5.atributo1 = 500
obj5.atributo2 = 1000
#######################################
class Guerrero
  include Framework

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
class Operaciones
  include PrePostCondiciones

  #precondición de dividir
  pre { divisor != 0 }
  #postcondición de dividir
  post { |result| result * divisor == dividendo }

  def dividir(dividendo, divisor)
    dividendo / divisor
  end

  # este método no se ve afectado por ninguna pre/post condición
  def restar(minuendo, sustraendo)
    minuendo - sustraendo
  end

end
#######################################
puts "Antes de dividir por cero"
objeto1 = Operaciones.new
#objeto1.dividir(10,0) #tira excepcion
puts "Despues de dividir por cero"
puts "Antes de probar el post"
#objeto1.dividir(10,1) #tira excepcion si pongo un post falso (ej: post { |result| result * divisor == dividendo + 1}
puts "Despues de probar el post"
#######################################
