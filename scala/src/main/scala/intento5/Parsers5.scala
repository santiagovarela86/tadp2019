package intento5
/*
trait SimpleResult {
  type Input

  trait Result[+T] {
    def next: Input
  }

  case class Success[+T](result: T, next: Input) extends Result[T]
  case class Failure(msg: String, next: Input) extends Result[Nothing]
}

trait SimpleParser extends SimpleResult {
  trait Parser[+T] extends (Input => Result[T]) {
    def apply(in: Input): Result[T]

    def <|>[U >: T](p: => Parser[U]): Parser[U] = {
      new Parser[U] {
        def apply(in: Input) =
          Parser.this(in) match {
            case Failure(_, _) => p(in)
            case Success(x, n) => Success(x, n)
          }
      }
    }

    def <>[U](p: Parser[U]): Parser[Tuple2[T, U]] = {
      new Parser[Tuple2[T, U]] {
        def apply(in: Input) =
          Parser.this(in) match {
            case Success(x, next) => p(next) match {
              case Success(x2, next2) => Success((x, x2), next2)
              case Failure(m, n)      => Failure(m, n)
            }
            case Failure(m, n) => Failure(m, n)
          }
      }
    }
  }
}

object Result extends SimpleResult {
  type Input = String
}

object anyChar extends SimpleResult {
  type Input = String

  def apply(in: Input): Result[Char] = {
    if (in.isEmpty()) Failure("Empty String", in) else Success(in.head, in.tail)
  }
}
/*
object char extends SimpleResult {
  type Input = Char => String

  def apply(in: Input) : Result[Char] = in match {
    case Input(a, b) 
  }
}*/
*/