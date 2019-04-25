class Class
  def before_and_after_each_call(bef, aft)
    @befs ||= []
    @afts ||= []
    @befs << bef
    @afts << aft
  end

  def method_added(original_method_name)
    unless @already_replacing_method or original_method_name.equal?(:method_added)
        @already_replacing_method = true
        original_method_object = instance_method(original_method_name)
        bef_procs = @befs
        aft_procs = @afts
        define_method(original_method_name) do |*args, &block|
          bef_procs.each {|x| self.instance_eval(&x)}
          result = original_method_object.bind(self).call(*args, &block)
          aft_procs.each {|x| self.instance_eval(&x)}
          result
        end
        @already_replacing_method = false
    end
  end
end

class MyClass
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

class AnotherClass
  before_and_after_each_call(proc{ puts "Into another message1" },
                             proc{ puts "Out of another message1" })
  before_and_after_each_call(proc{ puts "Into another message2" },
                             proc{ puts "Out of another message2" })

  def another_method
    puts "Another message"
  end
end

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
