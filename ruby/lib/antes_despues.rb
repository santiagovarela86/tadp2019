#######################################
module BeforeAndAfter

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

    othermod.instance_methods(false).each do |instance_method|
      reemplazar(othermod, instance_method)
    end

    def othermod.method_added(original_method_name)
      unless @already_replacing_method
        @already_replacing_method = true
        BeforeAndAfter.reemplazar(self, original_method_name)
        @already_replacing_method = false;
      end
    end

    def self.reemplazar(othermod, original_method_name)
      othermod.instance_eval do
        original_method_object = instance_method(original_method_name)
        define_method(original_method_name) do |*args, &block|
          bef_procs = othermod.__befs
          aft_procs = othermod.__afts
          unless @esteLoopeando
            @esteLoopeando = true;
            bef_procs.each {|x| self.instance_eval(&x)}
            result = original_method_object.bind(self).call(*args, &block)
            aft_procs.each {|x| self.instance_eval(&x)}
            @esteLoopeando = false;
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
  include BeforeAndAfter

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
  include BeforeAndAfter

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
  include BeforeAndAfter

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
