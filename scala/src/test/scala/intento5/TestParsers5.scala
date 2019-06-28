package intento5

import org.junit.Before
import org.junit.Test
import org.junit.Assert._

class Parser_anyChar_Test {
  val parserAnyChar = anyChar

  @Before
  def setup() = {
  }

  @Test
  def anychar_test_successful() = {
    var resultadoParseo = parserAnyChar("hola")    
    assertEquals(Result.Success('h',"ola"), resultadoParseo)
  }

  @Test
  def anychar_test_failure_empty() = {
    var resultadoParseo = parserAnyChar("")
    assertEquals(Result.Failure("Empty string",""), resultadoParseo)
  }
  
  @Test
  def anychar_test_combinator() = {
    var resultadoParseo = parserAnyChar("")
    assertEquals(Result.Failure("Empty string",""), resultadoParseo)
  }
}
/*
class Parser_char_Test {
  val parserChar = Parser.char _

  @Before
  def setup() = {
  }

  @Test
  def char_test_successful_samechar() = {
    var resultadoParseo = parserChar('h')("hola")
    assertEquals(true, resultadoParseo.isSuccess)
    assertEquals('h', resultadoParseo.get.getValor)
    assertEquals("ola", resultadoParseo.get.getResto)
  }

  @Test
  def char_test_failure_wrongchar() = {
    var resultadoParseo = parserChar('c')("hola")
    assertEquals(true, resultadoParseo.isFailure)
    assertEquals(None, resultadoParseo.toOption)
    assertEquals(None, resultadoParseo.toOption)
  }

  @Test
  def char_test_failure_empty() = {
    var resultadoParseo = parserChar('c')("")
    assertEquals(true, resultadoParseo.isFailure)
    assertEquals(None, resultadoParseo.toOption)
    assertEquals(None, resultadoParseo.toOption)
  }
}

class Parser_void_Test {
  val parserVoid = new void

  @Before
  def setup() = {
  }

  @Test
  def void_test_successful() = {
    var resultadoParseo = parserVoid("hola")
    assertEquals(true, resultadoParseo.isSuccess)
    assertEquals((), resultadoParseo.get.getValor)
    assertEquals("ola", resultadoParseo.get.getResto)
  }

  @Test
  def void_test_failure_empty() = {
    var resultadoParseo = parserVoid("")
    assertEquals(true, resultadoParseo.isFailure)
    assertEquals(None, resultadoParseo.toOption)
    assertEquals(None, resultadoParseo.toOption)
  }
}

class Parser_letter_Test {
  val parserLetter = Parser.letter

  @Before
  def setup() = {
  }

  @Test
  def void_test_successful_letter() = {
    var resultadoParseo = parserLetter("hola")
    assertEquals(true, resultadoParseo.isSuccess)
    assertEquals('h', resultadoParseo.get.getValor)
    assertEquals("ola", resultadoParseo.get.getResto)
  }

  @Test
  def void_test_failure_nonletter() = {
    var resultadoParseo = parserLetter("1234")
    assertEquals(true, resultadoParseo.isFailure)
    assertEquals(None, resultadoParseo.toOption)
    assertEquals(None, resultadoParseo.toOption)
  }

  @Test
  def void_test_failure_empty() = {
    var resultadoParseo = parserLetter("")
    assertEquals(true, resultadoParseo.isFailure)
    assertEquals(None, resultadoParseo.toOption)
    assertEquals(None, resultadoParseo.toOption)
  }
}

class Parser_digit_Test {
  val parserDigit = Parser.digit

  @Before
  def setup() = {
  }

  @Test
  def void_test_successful_digit() = {
    var resultadoParseo = parserDigit("1234")
    assertEquals(true, resultadoParseo.isSuccess)
    assertEquals("234", resultadoParseo.get.getResto)
    assertEquals('1', resultadoParseo.get.getValor)
  }

  @Test
  def void_test_failure_nondigit() = {
    var resultadoParseo = parserDigit("hola")
    assertEquals(true, resultadoParseo.isFailure)
    assertEquals(None, resultadoParseo.toOption)
    assertEquals(None, resultadoParseo.toOption)
  }

  @Test
  def void_test_failure_empty() = {
    var resultadoParseo = parserDigit("")
    assertEquals(true, resultadoParseo.isFailure)
    assertEquals(None, resultadoParseo.toOption)
    assertEquals(None, resultadoParseo.toOption)
  }
}

class Parser_alphanum_Test {
  val parserAlphaNum = Parser.alphaNum

  @Before
  def setup() = {
  }

  @Test
  def void_test_successful_digit() = {
    var parseo = parserAlphaNum("1234")
    assertEquals(true, parseo.isSuccess)
    assertEquals('1', parseo.get.getValor)
    assertEquals("234", parseo.get.getResto)
  }

  @Test
  def void_test_successful_letter() = {
    var parseo = parserAlphaNum("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals('h', parseo.get.getValor)
    assertEquals("ola", parseo.get.getResto)
  }

  @Test
  def void_test_failure_nondigit() = {
    var resultadoParseo = parserAlphaNum("***")
    assertEquals(true, resultadoParseo.isFailure)
    assertEquals(None, resultadoParseo.toOption)
    assertEquals(None, resultadoParseo.toOption)
  }

  @Test
  def void_test_failure_empty() = {
    var resultadoParseo = parserAlphaNum("")
    assertEquals(true, resultadoParseo.isFailure)
    assertEquals(None, resultadoParseo.toOption)
    assertEquals(None, resultadoParseo.toOption)
  }
}

class Parser_string_Test {
  val parserString = new string

  @Before
  def setup() = {
  }

  @Test
  def void_test_successful_containsstring() = {
    var resultadoParseo = parserString("hola")("hola mundo")
    assertEquals(true, resultadoParseo.isSuccess)
    assertEquals(" mundo", resultadoParseo.get.getResto)
    assertEquals("hola", resultadoParseo.get.getValor)
  }

  @Test
  def void_test_failure_wrongstring() = {
    var resultadoParseo = parserString("hola")("holgado")
    assertEquals(true, resultadoParseo.isFailure)
    assertEquals(None, resultadoParseo.toOption)
    assertEquals(None, resultadoParseo.toOption)
  }

  @Test
  def void_test_failure_empty_empty() = {
    var resultadoParseo = parserString("")("")
    assertEquals(true, resultadoParseo.isFailure)
    assertEquals(None, resultadoParseo.toOption)
    assertEquals(None, resultadoParseo.toOption)
  }

  @Test
  def void_test_failure_empty_string() = {
    var resultadoParseo = parserString("hola")("")
    assertEquals(true, resultadoParseo.isFailure)
    assertEquals(None, resultadoParseo.toOption)
    assertEquals(None, resultadoParseo.toOption)
  }

  @Test
  def void_test_failure_string_empty() = {
    var resultadoParseo = parserString("")("hola")
    assertEquals(true, resultadoParseo.isFailure)
    assertEquals(None, resultadoParseo.toOption)
    assertEquals(None, resultadoParseo.toOption)
  }
}
*/