package intento3

import org.junit.Before
import org.junit.Test
import org.junit.Assert._
/*
class Parser3_anyChar3_Test {
  val parserAnyChar = new anyChar

  @Before
  def setup() = {
  }

  @Test
  def anychar_test_successful() = {
    var resultadoParseo = parserAnyChar("hola")
    assertEquals(true, resultadoParseo.isSuccess)
    assertEquals('h', resultadoParseo.get.getValor)
    assertEquals("ola", resultadoParseo.get.getResto)
  }

  @Test
  def anychar_test_failure_empty() = {
    var resultadoParseo = parserAnyChar("")
    assertEquals(true, resultadoParseo.isFailure)
    assertEquals(None, resultadoParseo.toOption)
    assertEquals(None, resultadoParseo.toOption)
  }
}

class Parser3_char3_Test {
  val parserChar = new char

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

class Parser3_void3_Test {
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

class Parser3_letter3_Test {
  val parserLetter = new letter

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

class Parser3_digit3_Test {
  val parserDigit = new digit

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

class Parser3_alphanum3_Test {
  val parserAlphaNum = new alphaNum

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

class Parser3_string3_Test {
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