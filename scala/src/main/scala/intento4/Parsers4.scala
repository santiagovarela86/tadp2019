package intento4

import scala.util.{ Try, Success, Failure }

class ParseResult[A](result: A, resto: String) {
  def getValor: A = result
  def getResto: String = resto
}

//SOLO PARSER DE CHARS!!! AMPLIAR PARA INCLUIR PARSERS DE STRINGS Y UNIT
trait Parser {

  type Parser[A] = String => Try[ParseResult[A]]

  //PRIMITIVAS
  def result[A](t: A): Parser[A] = {
    (x: String) => Success(new ParseResult(t, x))
  }

  def zero(s: String): Try[ParseResult[Char]] = {
    Failure(new Throwable)
  }

  def item(s: String): Try[ParseResult[Char]] = s match {
    case s if (s.isEmpty()) => Failure(new Throwable)
    case _                  => Success(new ParseResult(s.head, s.tail))
  }

  def bind(m: Parser[Char], k: Char => Parser[Char]): Parser[Char] = { //"bind :: Parser a -> (a -> Parser b) -> Parser b"
    (x: String) =>
      {
        val res = m(x)
        if (res.isSuccess) k(res.get.getValor)(res.get.getResto) else zero(x)
      }
  }

  def satisfies(p: Char => Boolean): Parser[Char] = {
    bind(item, (x: Char) => { if (p(x)) result(x) else zero })
  }

  //PARSERS
  //ANYCHAR
  def anyChar: Parser[Char] = {
    satisfies((x: Char) => { true })
  }

  //CHAR
  def char(x: Char): Parser[Char] = {
    satisfies((y: Char) => { x.equals(y) })
  }
  
  //LETTER
  def letter: Parser[Char] = {
    satisfies((x: Char) => { x.isLetter })
  }
  
  //DIGIT
  def digit: Parser[Char] = {
    satisfies((x: Char) => { x.isDigit })
  }
  
  //ALPHANUM
  def alphaNum: Parser[Char] = {
    satisfies((x: Char) => { x.isLetter || x.isDigit })
  }
}

case object Parser extends Parser
