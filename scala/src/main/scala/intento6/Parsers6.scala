package intento6

abstract class Result[+T] //SI NO LE PONGO EL +, NO FUNCIONA DEVOLVER UN FAILURE QUE ES UN RESULT DE NOTHING

case class Success[T](result: T, next: String) extends Result[T]
case class Failure(msg: String) extends Result[Nothing]

trait Parser[T] {
  //type Input //INPUT DEBERIA PODER SER UN STRING... O UN (CHAR => STRING) O UN (STRING => STRING)... (???)
  //COMENTE EL INPUT... PORQUE AL QUERER USAR LOS TESTS Y PASARLE UN STRING A UN PARSER; ME DICE QUE TENGO QUE PASARLE UN INPUT Y NO UN STRING... (?)
  
  def apply(input: Any): Result[T] //ACA EL INPUT ERA DE TIPO INPUT, Y EN CADA PARSER TAMBIEN ERA INPUT. CON EL ANY PIERDO EL CHEQUEO DE TIPOS 
  //Y TENGO QUE TIRAR UN FAILURE NUEVO SI LE PASO MAL LOS PARAMETROS AL PARSER (SI LE PASO UN INT POR EJEMPLO)
}

case object anyChar extends Parser[Char] {
  def apply(input: Any): Result[Char] = input match {
    case (input: String) => if (input.isEmpty()) Failure("Empty Input String") else 
      Success(input.head, input.tail)
    case _ => Failure("Input Parameter Error") //????
  }
}

case object char extends Parser[Char] {
  def apply(input: Any): Result[Char] = input match {
    case (caracter: Char, inputString: String) => if (inputString.isEmpty()) Failure("Empty Input String") else //PUEDO CURRIFICAR ESTO?
      if (inputString.head == caracter) Success(inputString.head, inputString.tail) else
        Failure ("Not the same char")
    case _ => Failure("Input Parameter Error") //????
  }
}

case object void extends Parser[Unit] {
  def apply(input: Any): Result[Unit] = input match {
    case (input: String) => if (input.isEmpty()) Failure("Empty Input String") else
      Success((), input.tail)
    case _ => Failure("Input Parameter Error") //????
  }
}

case object letter extends Parser[Char] {
  def apply(input: Any): Result[Char] = input match {
    case (input: String) => if (input.isEmpty()) Failure("Empty Input String") else
      if (input.head.isLetter) Success(input.head, input.tail) else
        Failure("Not a letter")
    case _ => Failure("Input Parameter Error") //????
  }
}

case object digit extends Parser[Char] {
  def apply(input: Any): Result[Char] = input match {
    case (input: String) => if (input.isEmpty()) Failure("Empty Input String") else 
      if (input.head.isDigit) Success(input.head, input.tail) else 
        Failure("Not a digit")
    case _ => Failure("Input Parameter Error") //????
  }
}

case object alphaNum extends Parser[Char] {
  def apply(input: Any): Result[Char] = input match {
     case (input: String) => if (input.isEmpty()) Failure("Empty Input String") else
       if (input.head.isDigit || input.head.isLetter) Success(input.head, input.tail) else 
         Failure("Not an alphanum")
     case _ => Failure("Input Parameter Error") //????
  }
}

case object string extends Parser[String] {
  def apply(input: Any): Result[String] = input match {
    case (subString: String, inputString: String) => if (inputString.isEmpty() || subString.isEmpty()) Failure("Empty Input String") else  //PUEDO CURRIFICAR ESTO?
      if (inputString.startsWith(subString)) Success(subString, inputString.stripPrefix(subString)) else
        Failure("Not same string")
    case _ => Failure("Input Parameter Error") //????
  }
}