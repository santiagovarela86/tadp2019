#######################################
class PrecondicionException < Exception
end
#######################################
class PostcondicionException < Exception
end
#######################################
module PrePostCondiciones

  def self.included(othermod)

    attr_accessor :pre, :post

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

    def othermod.method_added(original_method_name)
      unless @already_replacing_method
        @already_replacing_method = true
        PrePostCondiciones.reemplazar(self, original_method_name)
        @already_replacing_method = false
      end
    end

    def self.reemplazar(othermod, original_method_name)
      othermod.instance_eval do
        original_method_object = instance_method(original_method_name)
        define_method(original_method_name) do |*args, &block|
          pre = othermod.__pre
          post = othermod.__post
          othermod.__pre = nil
          othermod.__post = nil

          original_method_object.parameters.each{|param|
            instance_variable_set("@" + param[1].to_s, args[original_method_object.parameters.find_index(param)])

            othermod.define_method(param[1].to_s) do
              instance_variable_get("@" + param[1].to_s)
            end
          }

          unless @esteLoopeando
            @esteLoopeando = true
            unless self.instance_eval(&pre)
              raise PrecondicionException
            end
            result = original_method_object.bind(self).call(*args, &block)
            unless self.instance_eval(&post)
              raise PostcondicionException
            end
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
objeto1.dividir(10,1) #tira excepcion
puts "Despues de probar el post"
