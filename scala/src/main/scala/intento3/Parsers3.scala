package intento3

import scala.util.{Try, Success, Failure}
/*
////////////////////////////////
//PARSE RESULTS
////////////////////////////////
class ParseResult[+T](result: T, resto: String) {
  def getValor: T = result
  def getResto: String = resto
}

////////////////////////////////
//ABSTRACT PARSERS
////////////////////////////////
abstract class Parser[+T] { // (String => ParseResult[T]) {
  //def apply(inputString: String): ParseResult[T]
  //def <|>(otroParser: Parser[Any]): Parser[Any]
  //def <>(otroParser: Parser[T]): Parser[T]
  //def ~>(otroParser: Parser[T]): Parser[T]
  //def <~(otroParser: Parser[T]): Parser[T]
}

abstract class SimpleParser[T] extends Parser[T] {
  def apply(inputString: String): Try[ParseResult[T]]
  
  /*
  def apply(inputParseResult: Try[ParseResult[Any]]): Try[ParseResult[Any]] = inputParseResult match {
    case Success(parseResult) => apply(parseResult.getResto)
    case Failure(exception) => Failure(exception)
  }
  */
}

abstract class ComplexStringParser[T] extends Parser[T] {
  def apply(inputParse: String)(inputString: String): Try[ParseResult[T]]
}

abstract class ComplexCharParser[T] extends Parser[T] {
  def apply(inputParse: Char)(inputString: String): Try[ParseResult[T]]
}

////////////////////////////////
//SIMPLE CONCRETE PARSERS
////////////////////////////////
class anyChar extends SimpleParser[Char] { primerParser =>
  def apply(inputString: String): Try[ParseResult[Char]] = inputString match {
    case inputString if (inputString.isEmpty()) => Failure(new Throwable)
    case _                                      => Success(new ParseResult(inputString.head, inputString.tail))
  }
  /*
  def <|>(segundoParser: Parser[Char]): Parser[Char] = {
    
  }
  
  def <|>(segundoParser: Parser[String]): Parser[Either[String, Char]] = {
    
  }
  */
}

class void extends SimpleParser[Unit] {
  def apply(inputString: String): Try[ParseResult[Unit]] = inputString match {
    case inputString if (inputString.isEmpty()) => Failure(new Throwable)
    case _                                      => Success(new ParseResult((), inputString.tail))
  }
}

class letter extends SimpleParser[Char] {
  def apply(inputString: String): Try[ParseResult[Char]] = inputString match {
    case inputString if (inputString.isEmpty()) => Failure(new Throwable)
    case _                                      => if (inputString.head.isLetter) Success(new ParseResult(inputString.head, inputString.tail)) else Failure(new Throwable)
  }
}

class digit extends SimpleParser[Char] {
  def apply(inputString: String): Try[ParseResult[Char]] = inputString match {
    case inputString if (inputString.isEmpty()) => Failure(new Throwable)
    case _                                      => if (inputString.head.isDigit) Success(new ParseResult(inputString.head, inputString.tail)) else Failure(new Throwable)
  }
}

class alphaNum extends SimpleParser[Char] {
  def apply(inputString: String): Try[ParseResult[Char]] = inputString match {
    case inputString if (inputString.isEmpty()) => Failure(new Throwable)
    case _                                      => if (inputString.head.isDigit || inputString.head.isLetter) Success(new ParseResult(inputString.head, inputString.tail)) else Failure(new Throwable)
  }
}

////////////////////////////////
//COMPLEX CONCRETE PARSERS
////////////////////////////////
class char extends ComplexCharParser[Char] {
  def apply(caracter: Char)(inputString: String): Try[ParseResult[Char]] = inputString match {
    case inputString if (inputString.isEmpty()) => Failure(new Throwable)
    case _                                      => if (inputString.head == caracter) Success(new ParseResult(inputString.head, inputString.tail)) else Failure(new Throwable)
  }
  
  /*
  def apply(caracter: Char)(inputParseResult: Try[ParseResult[Any]]): Try[ParseResult[Any]] = inputParseResult match {
    //case Success(parseResult) => apply(caracter)(parseResult.getResto) //HAY FORMA DE HACER QUE ESTO FUNCIONE? POR LO QUE LEI, NO.
    case Success(parseResult) => if (parseResult.getResto.head == caracter) Success(new ParseResult(parseResult.getResto.head, parseResult.getResto.tail)) else Failure(new Throwable)
    case Failure(exception) => Failure(exception)
  }
  */
}

class string extends ComplexStringParser[String] {
  def apply(subString: String)(inputString: String): Try[ParseResult[String]] = inputString match {
    case inputString if (inputString.isEmpty() || subString.isEmpty()) => Failure(new Throwable)
    case _ => if (inputString.startsWith(subString)) Success(new ParseResult(subString, inputString.stripPrefix(subString))) else Failure(new Throwable)
  }
  
  /*
  def apply(subString: String)(inputParseResult: Try[ParseResult[Any]]): Try[ParseResult[Any]] = inputParseResult match {
    //case Success(parseResult) => apply(subString)(parseResult.getResto) //HAY FORMA DE HACER QUE ESTO FUNCIONE? POR LO QUE LEI, NO.
    case Success(parseResult) => if (parseResult.getResto.startsWith(subString)) Success(new ParseResult(subString, parseResult.getResto.stripPrefix(subString))) else Failure(new Throwable)
    case Failure(exception) => Failure(exception)
  }
  */
}*/