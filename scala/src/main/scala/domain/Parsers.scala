package domain

import scala.util.{Failure, Success, Try}

class Parseo(val string: String) {
  def getValor(): Char = string.head
  def getResto(): String = string.tail
}

trait Parser

//VER COMO SACAR LAS VERIFICACIONES POR EMPTY EN TODOS LOS PARSERS
//ASI COMO ESTA FUNCIONAN LOS TESTS SEGUN LAS ESPECIFICACIONES
//(debe dar failure el string de entrada vacio en todos los parsers)
case object anyChar extends Parser {
  def apply(string: String): Try[Parseo] = 
    {
      if (string.isEmpty()) Failure(new Throwable) else
        Success(new Parseo(string))
    
      //Try(new Parseo(string)) //POR QUE FALLA EL TEST DE ESTO??? NO PINCHA SI ENTRA UN STRING VACIO
    }
}

case object char extends Parser {
  def apply(caracter : Char)(string: String): Try[Parseo] = 
  {
    if (string.isEmpty()) Failure(new Throwable) else
        if (string.head == caracter) Success(new Parseo(string)) else 
          Failure(new Throwable)
  }
}

case object void extends Parser {
  def apply(string: String): Try[Parseo] = 
    {
      Try(new Parseo(string.tail)) //Aca no hace falta preguntar si esta vacio, por que en anychar si hace falta?
    }
}

case object letter extends Parser {
  def apply(string: String): Try[Parseo] = 
    {
      if (string.isEmpty()) Failure(new Throwable) else
        if (string.head.isLetter) Success(new Parseo(string)) else Failure(new Throwable)  
    }
}

case object digit extends Parser {
  def apply(string: String): Try[Parseo] = 
    {
      if (string.isEmpty()) Failure(new Throwable) else
        if (string.head.isDigit) Success(new Parseo(string)) else Failure(new Throwable)
    }
}

case object alphaNum extends Parser {
  def apply(string: String): Try[Parseo] = 
    {
      if (string.isEmpty()) Failure(new Throwable) else
        if (string.head.isLetter || string.head.isDigit) Success(new Parseo(string)) else Failure(new Throwable)
    }
}

case object string extends Parser {
  def apply(string: String, substring: String): Try[Parseo] = 
    {
      if (string.isEmpty()) Failure(new Throwable) else
        if (string.startsWith(substring)) Success(new Parseo(string)) else Failure(new Throwable)
    }
}

// Imprimiendo valores
object Algo {
  def show(x: Try[Parseo]) = x match {
    case Success(s) => print(s.getValor())
    case Failure(a) => print(a.getCause)
  }
}

object main extends App {
  val unOption = string("Hello World", "Hello")
  Algo.show(unOption)
}
