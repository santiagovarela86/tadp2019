package domain

import scala.util.{Failure, Success, Try}

class Parseo(val string: Seq[Char]) {
  def getCaracter(): Char = string.head
  def getString(): Seq[Char] = string.tail
}

trait Parser

case object anyChar extends Parser {
  def apply(string: Seq[Char]): Try[Parseo] = Try(new Parseo(string))
}

case object char extends Parser {
  def apply(string: Seq[Char], caracter: Char): Try[Parseo] = if (string.head == caracter) Success(new Parseo(string)) else Failure(new Throwable)
}

case object void extends Parser {
  def apply(string: Seq[Char]): Try[Parseo] = Try(new Parseo(string.tail))
}

case object letter extends Parser {
  def apply(string: Seq[Char]): Try[Parseo] = if (string.head.isLetter) Success(new Parseo(string)) else Failure(new Throwable)
}

case object digit extends Parser {
  def apply(string: Seq[Char]): Try[Parseo] = if (string.head.isDigit) Success(new Parseo(string)) else Failure(new Throwable)
}

case object alphaNum extends Parser {
  def apply(string: Seq[Char]): Try[Parseo] = if (string.head.isLetter || string.head.isDigit) Success(new Parseo(string)) else Failure(new Throwable)
}

case object string extends Parser {
  def apply(string: String,substring: String): Try[Parseo] = if (string.startsWith(substring)) Success(new Parseo(string)) else Failure(new Throwable)
}



// Imprimiendo valores
object Algo {
  def show(x: Try[Parseo]) = x match {
    case Success(s) => print(s.getCaracter())
    case Failure(a) => print(a.getCause)
  }
}

object main extends App {
  val unOption = string("Hello World", "Hello")
  Algo.show(unOption)
}
