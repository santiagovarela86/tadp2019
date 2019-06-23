package domain

import scala.util.{ Failure, Success, Try }

abstract class ResultadoParseo3 {
  def getValor(): Any
  def getResto(): String
}

case class ParseoChar(valor: Char, resto: String) extends ResultadoParseo3 {
  def getValor() = valor
  def getResto() = resto
}
case class ParseoVoid(valor: Unit, resto: String) extends ResultadoParseo3 {
  def getValor() = valor
  def getResto() = resto
}
case class ParseoString(valor: String, resto: String) extends ResultadoParseo3 {
  def getValor() = valor
  def getResto() = resto
}

trait Parser3

case object anyChar3 extends Parser3 {
  def apply(string: String): Try[ResultadoParseo3] = string match {
    case string if (string.isEmpty()) => Failure(new Throwable)
    case _                            => Success(new ParseoChar(string.head, string.tail))
  }

  def apply(resultadoParseo: Try[ResultadoParseo3]): Try[ResultadoParseo3] = resultadoParseo match {
    case resultadoParseo if (!resultadoParseo.isSuccess) => Failure(new Throwable)
    case _ => apply(resultadoParseo.get.getResto())
  }
}

case object char3 extends Parser3 {

  def apply(caracter: Char)(string: String): Try[ResultadoParseo3] = string match {
    case (string) if (string.isEmpty())     => Failure(new Throwable)
    case (caracter) if (caracter.isEmpty()) => Failure(new Throwable)
    case _ => if (string.head == caracter) Success(new ParseoChar(string.head, string.tail)) else Failure(new Throwable)
  }

  def apply(caracter: Char)(resultadoParseo: Try[ResultadoParseo3]): Try[ResultadoParseo3] = resultadoParseo match {
    case resultadoParseo if (!resultadoParseo.isSuccess) => Failure(new Throwable)
    case _ => if (resultadoParseo.get.getResto().head == caracter) Success(new ParseoChar(resultadoParseo.get.getResto().head, resultadoParseo.get.getResto().tail)) else Failure(new Throwable)
    //case _ => apply(caracter)(resultadoParseo.get.getResto()) //CURRYING & OVERLOADING !!! NOT
  }

}