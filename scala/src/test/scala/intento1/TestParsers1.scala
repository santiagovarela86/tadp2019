package intento1

import org.junit.Before
import org.junit.Test
import org.junit.Assert._

class Parser_anyChar_Test {
  val parserAnyChar = new anyChar

  @Before
  def setup() = {
  }

  @Test
  def anychar_test_successful() = {
    var resultadoParseo = parserAnyChar("hola")
    assertEquals(true, resultadoParseo.isSuccessful)
    assertEquals('h', resultadoParseo.getValor)
    assertEquals("ola", resultadoParseo.getResto)
  }

  @Test
  def anychar_test_failure_empty() = {
    var resultadoParseo = parserAnyChar("")
    assertEquals(false, resultadoParseo.isSuccessful)
    //VALIDAR LAS EXCEPCIONES SI HAGO UN GET VALOR Y GET RESTO???
  }
}

class Parser_char_Test {
  val parserChar = new char

  @Before
  def setup() = {
  }

  @Test
  def char_test_successful_samechar() = {
    var resultadoParseo = parserChar('h')("hola")
    assertEquals(true, resultadoParseo.isSuccessful)
    assertEquals('h', resultadoParseo.getValor)
    assertEquals("ola", resultadoParseo.getResto)
  }

  @Test
  def char_test_failure_wrongchar() = {
    var resultadoParseo = parserChar('c')("hola")
    assertEquals(false, resultadoParseo.isSuccessful)
    //VALIDAR LAS EXCEPCIONES SI HAGO UN GET VALOR Y GET RESTO???
  }

  @Test
  def char_test_failure_empty() = {
    var resultadoParseo = parserChar('c')("")
    assertEquals(false, resultadoParseo.isSuccessful)
    //VALIDAR LAS EXCEPCIONES SI HAGO UN GET VALOR Y GET RESTO???
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
    assertEquals(true, resultadoParseo.isSuccessful)
    assertEquals((), resultadoParseo.getValor)
    assertEquals("ola", resultadoParseo.getResto)
  }

  @Test
  def void_test_failure_empty() = {
    var resultadoParseo = parserVoid("")
    assertEquals(false, resultadoParseo.isSuccessful)
    //VALIDAR LAS EXCEPCIONES SI HAGO UN GET VALOR Y GET RESTO???
  }
}

class Parser_letter_Test {
  val parserLetter = new letter

  @Before
  def setup() = {
  }

  @Test
  def void_test_successful_letter() = {
    var parseo = parserLetter("hola")
    assertEquals(true, parseo.isSuccessful)
    assertEquals('h', parseo.getValor)
    assertEquals("ola", parseo.getResto)
  }

  @Test
  def void_test_failure_nonletter() = {
    var parseo = parserLetter("1234")
    assertEquals(false, parseo.isSuccessful)
    //VALIDAR LAS EXCEPCIONES SI HAGO UN GET VALOR Y GET RESTO???
  }

  @Test
  def void_test_failure_empty() = {
    var parseo = parserLetter("")
    assertEquals(false, parseo.isSuccessful)
    //VALIDAR LAS EXCEPCIONES SI HAGO UN GET VALOR Y GET RESTO???
  }
}

class Parser_digit_Test {
  val parserDigit = new digit

  @Before
  def setup() = {
  }

  @Test
  def void_test_successful_digit() = {
    var resultadoParseo = parserDigit("1234")
    assertEquals(true, resultadoParseo.isSuccessful)
    assertEquals("234", resultadoParseo.getResto)
    assertEquals('1', resultadoParseo.getValor)
  }

  @Test
  def void_test_failure_nondigit() = {
    var resultadoParseo = parserDigit("hola")
    assertEquals(false, resultadoParseo.isSuccessful)
    //VALIDAR LAS EXCEPCIONES SI HAGO UN GET VALOR Y GET RESTO???
  }

  @Test
  def void_test_failure_empty() = {
    var resultadoParseo = parserDigit("")
    assertEquals(false, resultadoParseo.isSuccessful)
    //VALIDAR LAS EXCEPCIONES SI HAGO UN GET VALOR Y GET RESTO???
  }
}

class Parser_alphanum_Test {
  val parserAlphaNum = new alphaNum

  @Before
  def setup() = {
  }

  @Test
  def void_test_successful_digit() = {
    var parseo = parserAlphaNum("1234")
    assertEquals(true, parseo.isSuccessful)
    assertEquals('1', parseo.getValor)
    assertEquals("234", parseo.getResto)
  }

  @Test
  def void_test_successful_letter() = {
    var parseo = parserAlphaNum("hola")
    assertEquals(true, parseo.isSuccessful)
    assertEquals('h', parseo.getValor)
    assertEquals("ola", parseo.getResto)
  }

  @Test
  def void_test_failure_nondigit() = {
    var parseo = parserAlphaNum("***")
    assertEquals(false, parseo.isSuccessful)
  }

  @Test
  def void_test_failure_empty() = {
    var parseo = parserAlphaNum("")
    assertEquals(false, parseo.isSuccessful)
  }
}

class Parser_string_Test {
  val parserString = new string

  @Before
  def setup() = {
  }

  @Test
  def void_test_successful_containsstring() = {
    var parseo = parserString("hola")("hola mundo")
    assertEquals(true, parseo.isSuccessful)
    assertEquals(" mundo", parseo.getResto)
    assertEquals("hola", parseo.getValor)
  }

  @Test
  def void_test_failure_wrongstring() = {
    var parseo = parserString("hola")("holgado")
    assertEquals(false, parseo.isSuccessful)
  }

  @Test
  def void_test_failure_empty_empty() = {
    var parseo = parserString("")("")
    assertEquals(false, parseo.isSuccessful)
  }

  @Test
  def void_test_failure_empty_string() = {
    var parseo = parserString("hola")("")
    assertEquals(false, parseo.isSuccessful)
  }

  @Test
  def void_test_failure_string_empty() = {
    var parseo = parserString("")("hola")
    assertEquals(false, parseo.isSuccessful)
  }
}
