package tp

abstract class Result[+T] {
  def map[U](f: T => U): Result[U]

  def filter(f: T => Boolean): Result[T]
}

case class Success[+T](result: T, resto: String) extends Result[T] {
  def map[U](f: T => U) = Success(f(result), resto)

  def filter(f: T => Boolean): Result[T] = if (f(result)) Success(result, resto) else Failure("Doesn't satisfy the condition")
}

case class Failure(msg: String) extends Result[Nothing] {
  def map[U](f: Nothing => U) = this

  def filter(f: Nothing => Boolean): Result[Nothing] = this
}

trait Parser[+T] {

  def apply(input: String): Result[T]

  def map[U](f: T => U): Parser[U] = new Parser[U] {
    def apply(input: String) = Parser.this (input) map f
  }

  def flatMap[U](f: T => Parser[U]): Parser[U] = new Parser[U] {
    def apply(input: String) = Parser.this (input) match {
      case Success(result, resto) => f(result)(resto)
      case failure@Failure(_) => failure
    }

    //def apply(input: String) = Parser.this (input) flatMap (f)
  }

  def <|>[U >: T](p: Parser[U]): Parser[U] = {
    new Parser[U] {
      def apply(input: String) =
        Parser.this (input) match {
          case Success(result, resto) => Success(result, resto)
          case Failure(_) => p(input)
        }
    }
  }

  def <>[U](p: Parser[U]): Parser[(T, U)] = {
    new Parser[(T, U)] {
      def apply(input: String) =
        Parser.this (input) match {
          case Success(result, resto) => p(resto) match {
            case Success(result2, resto2) => Success((result, result2), resto2)
            case Failure(m) => Failure(m)
          }
          case Failure(m) => Failure(m)
        }
    }
  }

  def ~>[U](p: Parser[U]): Parser[U] = {
    for (
      a <- this;
      b <- p
    ) yield b

    //this.flatMap { (a: T) => p.map { (b: U) => { b } } }
  }

  def <~[U](p: Parser[U]): Parser[T] = {
    for (
      a <- this;
      b <- p
    ) yield a

    //this.flatMap { (a: T) => p.map { (b: U) => { a } } }
  }

  def satisfies(condicion: T => Boolean): Parser[T] = {
    new Parser[T] {
      def apply(input: String) = Parser.this (input).filter(condicion)
    }
  }

  //ANY?
  def opt: Parser[Any] = {
    new Parser[Any] {
      def apply(input: String) = {
        Parser.this (input) match {
          case Success(result, resto) => Success(result, resto)
          case _ => Success((), input)
        }
      }
    }
  }

  def const[T](valor: T): Parser[T] = {
    new Parser[T] {
      def apply(input: String) = {
        Parser.this (input) match {
          case Success(result, resto) => Success(valor, resto)
          case Failure(m) => Failure(m)
        }
      }
    }
  }

  //ANY?
  def * : Parser[List[T]] = {
    new Parser[List[T]] {
      def apply(input: String) = {
        Parser.this (input) match {
          case Success(result, resto) => apply(resto) match {
            case Success(result2: List[T], resto2) => Success(result :: result2, resto2)
          }
          case Failure(_) => Success(List(), input)
        }
      }
    }
  }

  def + : Parser[List[T]] = {
    var huboAlMenosUnSuccess: Boolean = false
    new Parser[List[T]] {
      def apply(input: String) = {
        Parser.this (input) match {
          case Success(result: T, resto) => {
            huboAlMenosUnSuccess = true
            apply(resto) match {
              case Success(result2: List[T], resto2) => Success(result :: result2, resto2)
            }
          }
          case Failure(m) => if (huboAlMenosUnSuccess) Success(List(), input) else Failure(m)
        }
      }
    }
  }

  //ANY?
  //SE PUEDE REEMPLAZAR ESTO POR ALGO QUE YA TENGO? o en su defecto meterlo dentro del operador *
  private def flatten(ls: List[Any]): List[Any] = ls flatMap {
    case Failure(_) => List()
    case () => List()
    case i: List[_] => flatten(i)
    case e => List(e)
  }
}

case object anyChar extends Parser[Char] {
  def apply(input: String): Result[Char] = if (input.isEmpty) Failure("Empty Input String") else Success(input.head, input.tail)
}

case object void extends Parser[Unit] {
  def apply(input: String): Result[Unit] = if (input.isEmpty) Failure("Empty Input String") else Success((), input.tail)
}

case object letter extends Parser[Char] {
  def apply(input: String): Result[Char] = if (input.isEmpty) Failure("Empty Input String") else if (input.head.isLetter) Success(input.head, input.tail) else
    Failure("Not a letter")
}

case object digit extends Parser[Char] {
  def apply(input: String): Result[Char] = if (input.isEmpty) Failure("Empty Input String") else if (input.head.isDigit) Success(input.head, input.tail) else
    Failure("Not a digit")
}

case object alphaNum extends Parser[Char] {
  def apply(input: String): Result[Char] = if (input.isEmpty) Failure("Empty Input String") else if (input.head.isDigit || input.head.isLetter) Success(input.head, input.tail) else
    Failure("Not an alphanum")
}

//ESTA BIEN QUE NO EXTIENDA A PARSER[CHAR]???
case object char {
  def apply(inputChar: Char): Parser[Char] = {
    new Parser[Char] {
      def apply(inputString: String) = {
        if (inputString.isEmpty) Failure("Empty Input String") else if (inputString.head == inputChar) Success(inputString.head, inputString.tail) else Failure("Not the same char")
      }
    }
  }
}

//ESTA BIEN QUE NO EXTIENDA A PARSER[STRING]???
case object string {
  def apply(inputSubString: String): Parser[String] = new Parser[String] {
    def apply(inputString: String) = {
      if (inputString.isEmpty || inputSubString.isEmpty) Failure("Empty Input String") else if (inputString.startsWith(inputSubString)) Success(inputSubString, inputString.stripPrefix(inputSubString)) else
        Failure("Not the same string")
    }
  }
}
