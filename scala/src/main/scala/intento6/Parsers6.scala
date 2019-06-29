package intento6

//SI NO LE PONGO EL +T, NO FUNCIONA DEVOLVER UN FAILURE QUE ES UN RESULT DE NOTHING
abstract class Result[+T] { 
  def map[U](f: T => U): Result[U] //convierte de Result[T] a Result[U]
  def flatMapNext[U](f: T => Parser[U]): Result[U]
}

case class Success[T](result: T, next: String) extends Result[T] {
  def map[U](f: T => U) = Success(f(result), next) //convierte de Success[T] a Success[U]
  def flatMapNext[U](f: T => Parser[U]) = f(result)(next)
}

case class Failure(msg: String) extends Result[Nothing] {
  def map[U](f: Nothing => U) = this //Convierte de Failure a Failure
  def flatMapNext[U](f: Nothing => Parser[U]) = this
}

trait Parser[T] {
  //type Input //INPUT DEBERIA PODER SER UN STRING... O UN (CHAR => STRING) O UN (STRING => STRING)... (???)
  //COMENTE EL INPUT... PORQUE AL QUERER USAR LOS TESTS Y PASARLE UN STRING A UN PARSER; ME DICE QUE TENGO QUE PASARLE UN INPUT Y NO UN STRING... (?)

  def apply(input: Any): Result[T] //ACA EL INPUT ERA DE TIPO INPUT, Y EN CADA PARSER TAMBIEN ERA INPUT. CON EL ANY PIERDO EL CHEQUEO DE TIPOS
  //Y TENGO QUE TIRAR UN FAILURE NUEVO SI LE PASO MAL LOS PARAMETROS AL PARSER (SI LE PASO UN INT POR EJEMPLO)
  
  def map[U](f: T => U): Parser[U] = new Parser[U] {
    def apply(in: Any) = Parser.this(in) map (f) //Any? //para hacer esto necesito el map en Result[T] //Convierto de Parser[T] a Parser[U]
  }

  def flatMap[U](f: T => Parser[U]): Parser[U] = new Parser[U] {
    def apply(in: Any) = Parser.this(in) flatMapNext (f) //ANY? //para hacer esto necesito el flatmapnext en el result[T]
  }

  def <|>[U >: T](p: => Parser[U]): Parser[U] = {
    new Parser[U] {
      def apply(in: Any) =
        Parser.this(in) match {
          case Failure(_)    => p(in)
          case Success(x, n) => Success(x, n)
        }
    }
  }

  def <>[U](p: Parser[U]): Parser[Tuple2[T, U]] = {
    new Parser[Tuple2[T, U]] {
      def apply(in: Any) =
        Parser.this(in) match {
          case Success(x, next) => p(next) match {
            case Success(x2, next2) => Success((x, x2), next2)
            case Failure(m)         => Failure(m)
          }
          case Failure(m) => Failure(m)
        }
    }
  }

  def ~>[U](p: => Parser[U]): Parser[U] = {
    for (
      a <- this; //para hacer esto necesito el flatmap en Parser[T]
      b <- p //para hacer esto necesito el map en Parser[U]
    ) yield b
  }

  def <~[U](p: => Parser[U]): Parser[T] = {
    for (
      a <- this; //para hacer esto necesito el flatmap en Parser[T]
      b <- p //para hacer esto necesito el map en Parser[U]
    ) yield a
  }
}

case object anyChar extends Parser[Char] {
  def apply(input: Any): Result[Char] = input match {
    case (input: String) => if (input.isEmpty()) Failure("Empty Input String") else
      Success(input.head, input.tail)
    case _ => Failure("Input Parameter Error") //????
  }
}

case object char extends Parser[Char] {
  def apply(input: Any): Result[Char] = input match {
    case (caracter: Char, inputString: String) => if (inputString.isEmpty()) Failure("Empty Input String") else
    if (inputString.head == caracter) Success(inputString.head, inputString.tail) else
      Failure("Not the same char")
    case _ => Failure("Input Parameter Error") //????
  }
}

case object void extends Parser[Unit] {
  def apply(input: Any): Result[Unit] = input match {
    case (input: String) => if (input.isEmpty()) Failure("Empty Input String") else
      Success((), input.tail)
    case _ => Failure("Input Parameter Error") //????
  }
}

case object letter extends Parser[Char] {
  def apply(input: Any): Result[Char] = input match {
    case (input: String) => if (input.isEmpty()) Failure("Empty Input String") else if (input.head.isLetter) Success(input.head, input.tail) else
      Failure("Not a letter")
    case _ => Failure("Input Parameter Error") //????
  }
}

case object digit extends Parser[Char] {
  def apply(input: Any): Result[Char] = input match {
    case (input: String) => if (input.isEmpty()) Failure("Empty Input String") else if (input.head.isDigit) Success(input.head, input.tail) else
      Failure("Not a digit")
    case _ => Failure("Input Parameter Error") //????
  }
}

case object alphaNum extends Parser[Char] {
  def apply(input: Any): Result[Char] = input match {
    case (input: String) => if (input.isEmpty()) Failure("Empty Input String") else if (input.head.isDigit || input.head.isLetter) Success(input.head, input.tail) else
      Failure("Not an alphanum")
    case _ => Failure("Input Parameter Error") //????
  }
}

case object string extends Parser[String] {
  def apply(input: Any): Result[String] = input match {
    case (subString: String, inputString: String) => if (inputString.isEmpty() || subString.isEmpty()) Failure("Empty Input String") else
    if (inputString.startsWith(subString)) Success(subString, inputString.stripPrefix(subString)) else
      Failure("Not same string")
    case _ => Failure("Input Parameter Error") //????
  }
}
