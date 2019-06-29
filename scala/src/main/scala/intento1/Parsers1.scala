package intento1

//trait Parsers
/*
//type Parser[T] = String => ParseResult[T]

////////////////////////////////
//PARSE RESULTS
////////////////////////////////
abstract class ParseResult[+T] {
  def getValor: T
  def getResto: String
  def isSuccessful: Boolean
}

case class Success[+T](result: T, resto: String) extends ParseResult[T] {
  def getValor: T = result
  def getResto: String = resto
  def isSuccessful = true
}

case class Failure() extends ParseResult[Nothing] {
  def getValor: Nothing = scala.sys.error("Failure") //OK?
  def getResto: Nothing = scala.sys.error("Failure") //OK?
  def isSuccessful = false
}

////////////////////////////////
//ABSTRACT PARSERS
////////////////////////////////
abstract class Parser[T] { // (String => ParseResult[T]) {
  //def apply(inputString: String): ParseResult[T]
  //def <|>(otroParser: Parser[T]): Parser[T]
  //def <>(otroParser: Parser[T]): Parser[T]
  //def ~>(otroParser: Parser[T]): Parser[T]
  //def <~(otroParser: Parser[T]): Parser[T]
}

abstract class SimpleParser[T] extends Parser[T] {
  def apply(inputString: String): ParseResult[T]
}

abstract class ComplexStringParser[T] extends Parser[T] {
  def apply(inputParse: String)(inputString: String): ParseResult[T]
}

abstract class ComplexCharParser[T] extends Parser[T] {
  def apply(inputParse: Char)(inputString: String): ParseResult[T]
}

////////////////////////////////
//CONCRETE PARSERS
////////////////////////////////
class anyChar extends SimpleParser[Char] {
  def apply(inputString: String): ParseResult[Char] = inputString match {
    case inputString if (inputString.isEmpty()) => Failure()
    case _                                      => Success(inputString.head, inputString.tail)
  }
}

class char extends ComplexCharParser[Char] {
  def apply(caracter: Char)(inputString: String): ParseResult[Char] = inputString match {
    case inputString if (inputString.isEmpty()) => Failure()
    case _                                      => if (inputString.head == caracter) Success(inputString.head, inputString.tail) else Failure()
  }
}

class void extends SimpleParser[Unit] {
  def apply(inputString: String): ParseResult[Unit] = inputString match {
    case inputString if (inputString.isEmpty()) => Failure()
    case _                                      => Success((), inputString.tail)
  }
}

class letter extends SimpleParser[Char] {
  def apply(inputString: String): ParseResult[Char] = inputString match {
    case inputString if (inputString.isEmpty()) => Failure()
    case _                                      => if (inputString.head.isLetter) Success(inputString.head, inputString.tail) else Failure()
  }
}

class digit extends SimpleParser[Char] {
  def apply(inputString: String): ParseResult[Char] = inputString match {
    case inputString if (inputString.isEmpty()) => Failure()
    case _                                      => if (inputString.head.isDigit) Success(inputString.head, inputString.tail) else Failure()
  }
}

class alphaNum extends SimpleParser[Char] {
  def apply(inputString: String): ParseResult[Char] = inputString match {
    case inputString if (inputString.isEmpty()) => Failure()
    case _                                      => if (inputString.head.isDigit || inputString.head.isLetter) Success(inputString.head, inputString.tail) else Failure()
  }
}

class string extends ComplexStringParser[String] {
  def apply(subString: String)(inputString: String): ParseResult[String] = inputString match {
    case inputString if (inputString.isEmpty() || subString.isEmpty()) => Failure()
    case _ => if (inputString.startsWith(subString)) Success(subString, inputString.stripPrefix(subString)) else Failure()
  }
}
*/