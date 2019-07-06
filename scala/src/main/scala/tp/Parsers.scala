package tp

import Musica._

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
      _ <- this;
      b <- p
    ) yield b

    //this.flatMap { (a: T) => p.map { (b: U) => { b } } }
  }

  def <~[U](p: Parser[U]): Parser[T] = {
    for (
      a <- this;
      _ <- p
    ) yield a

    //this.flatMap { (a: T) => p.map { (b: U) => { a } } }
  }

  def satisfies(condicion: T => Boolean): Parser[T] = {
    new Parser[T] {
      def apply(input: String) = Parser.this (input).filter(condicion)
    }
  }

  def opt: Parser[Option[T]] = {
    new Parser[Option[T]] {
      def apply(input: String) = {
        Parser.this (input) match {
          case Success(result, resto) => Success(Option(result), resto)
          case _ => Success(Option.empty, input)
        }
      }
    }
  }

  def const[T](valor: T): Parser[T] = {
    new Parser[T] {
      def apply(input: String) = {
        Parser.this (input) match {
          case Success(_, resto) => Success(valor, resto)
          case Failure(m) => Failure(m)
        }
      }
    }
  }

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
    def insideParser = Parser.this <> *

    new Parser[List[T]] {
      def apply(input: String): Result[List[T]] = insideParser.apply(input) match {
        case Success((first, second), resto) => Success(first :: second, resto)
        case failure: Failure => failure
      }
    }
  }

  def sepBy(separatorParser: Parser[Any]): Parser[List[T]] = {
    def insideParser = (separatorParser ~> Parser.this) *

    new Parser[List[T]] {
      def apply(input: String): Result[List[T]] = Parser.this apply input match {
        case Success(result, resto) => insideParser.apply(resto) match {
          case Success(result2, resto2) => Success(result :: result2, resto2)
        }
        case failure: Failure => failure
      }
    }
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


/*
case object melodia extends Parser[Melodia] {

  sepBy(char(' '))

  def apply(input: String): Result[Melodia] = super.apply(input)
}
*/

case object silencio extends Parser[Tocable] {
  def apply(inputString: String): Result[Tocable] = (char('_').const(Blanca) <|> char('-').const(Negra) <|> char('~').const(Corchea)).map(figura => Silencio(figura))(inputString)
}

case object basicNota extends Parser[Nota] {
  def apply(inputString: String) = anyChar.satisfies(char => Nota.notas.map(_.toString).contains(char.toString)).map(c => Nota.notas.find(_.toString == c.toString).get)(inputString)
}

case object nota extends Parser[Nota] {
  def apply(inputString: String) = ((basicNota <~ char('#')).map(_.sostenido) <|> (basicNota <~ char('b')).map(_.bemol) <|> basicNota) (inputString)
}

case object figura extends Parser[Figura] {
  val denominadorParser = char('1').const(Redonda) <|> char('2').const(Blanca) <|> char('4').const(Negra) <|> char('8').const(Corchea) <|> string("16").const(SemiCorchea)

  def apply(input: String) = ((char('1') ~> char('/')) ~> denominadorParser) (input)
}

case object tono extends Parser[Tono] {
  def apply(input: String) = (digit.*.map(_.mkString.toInt) <> nota).map(result => Tono(result._1, result._2))(input)
}

case object sonido extends Parser[Sonido] {
  def apply(input: String) = (tono <> figura).map(result => Sonido(result._1, result._2))(input)
}


case object acorde extends Parser[Acorde] {
  def apply(input: String) = (acordeExplicito <|> acordeMayor <|> acordeMayor) (input)
}

case object acordeExplicito extends Parser[Acorde] {
  def apply(input: String) = (tono.sepBy(char('+')) <> figura).map(result => Acorde(result._1, result._2))(input)
}

case object acordeMenor extends Parser[Acorde] {
  def apply(input: String) = (tono <~ char('m') <> figura).map(result => result._1.nota.acordeMenor(result._1.octava, result._2))(input)
}

case object acordeMayor extends Parser[Acorde] {
  def apply(input: String) = (tono <~ char('M') <> figura).map(result => result._1.nota.acordeMayor(result._1.octava, result._2))(input)
}

