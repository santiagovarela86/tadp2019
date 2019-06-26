package intento2

//RESULTADOS DE PARSEO
abstract class ResultadoParseo { 
  def getValor(): Any
  def getResto(): Any
  def isSuccess(): Boolean
}

class ParseoExitoso(val str: String) extends ResultadoParseo {
  def getValor(): Char = str.head
  def getResto(): String = str.tail
  def isSuccess(): Boolean = true
}

class ParseoExitosoVoid(val str: String) extends ResultadoParseo {
  def getValor(): Unit = ()
  def getResto(): String = str.tail
  def isSuccess(): Boolean = true
}

class ParseoExitosoString(val str: String, val rest: String) extends ResultadoParseo {
  def getValor(): String = str
  def getResto(): String = rest
  def isSuccess(): Boolean = true
}
  
class ErrorDeParseo extends ResultadoParseo {
  def getValor(): Unit = ()
  def getResto(): Unit = ()
  def isSuccess(): Boolean = false  
}

//PARSERS
trait Parser
{
   
}

case object anyChar extends Parser {
  def apply(string: String): ResultadoParseo = 
    if (string.isEmpty()) new ErrorDeParseo else new ParseoExitoso(string)
}

case object char extends Parser {
  def apply(caracter : Char)(string: String): ResultadoParseo = 
    if (string.isEmpty() || string.head != caracter) new ErrorDeParseo else new ParseoExitoso(string)
}

case object void extends Parser {
  def apply(string: String): ResultadoParseo = 
    if (string.isEmpty()) new ErrorDeParseo else new ParseoExitosoVoid(string)
}

case object letter extends Parser {
  def apply(string: String): ResultadoParseo = 
    if (string.isEmpty() || !string.head.isLetter) new ErrorDeParseo else new ParseoExitoso(string)
}

case object digit extends Parser {
  def apply(string: String): ResultadoParseo = 
    if (string.isEmpty() || !string.head.isDigit) new ErrorDeParseo else new ParseoExitoso(string)
}

case object alphaNum extends Parser {
  def apply(string: String): ResultadoParseo = 
    if (string.isEmpty() || (!string.head.isLetter && !string.head.isDigit)) new ErrorDeParseo else new ParseoExitoso(string)
  //ESTO PODRIA REEMPLAZARSE POR UN COMBINATOR DE DIGIT Y LETTER
}

case object string extends Parser {
  def apply(string: String, substring: String): ResultadoParseo = 
    if (string.isEmpty() || !string.startsWith(substring)) new ErrorDeParseo else new ParseoExitosoString(substring, string.stripPrefix(substring))
}
