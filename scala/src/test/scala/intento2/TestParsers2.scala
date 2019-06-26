package intento2

import org.junit.Before
import org.junit.Test
import org.junit.Assert._

class Parser2_anyChar2_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def anychar_test_successful() = {
    var parseo = anyChar ("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals('h', parseo.getValor())
  }
  
  @Test
  def anychar_test_failure_empty() = {
    var parseo = anyChar ("")
    assertEquals(false, parseo.isSuccess)
  }
}

class Parser2_char2_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def char_test_successful_samechar() = {
    var parseo = char('h') ("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals("ola", parseo.getResto())
    assertEquals('h', parseo.getValor())
  }
  
  @Test
  def char_test_failure_wrongchar() = {
    var parseo = char('c') ("hola")
    assertEquals(false, parseo.isSuccess)
  }
  
  @Test
  def char_test_failure_empty() = {
    var parseo = char('c') ("")
    assertEquals(false, parseo.isSuccess)
  }
}

class Parser2_void2_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def void_test_successful() = {
    var parseo = void ("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals("ola", parseo.getResto())
    assertEquals((), parseo.getValor())
  }
  
  @Test
  def void_test_failure_empty() = {
    var parseo = void ("")
    assertEquals(false, parseo.isSuccess)
  }
}

class Parser2_letter2_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def void_test_successful_letter() = {
    var parseo = letter ("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals('h', parseo.getValor())
    assertEquals("ola", parseo.getResto())
  }
  
  @Test
  def void_test_failure_nonletter() = {
    var parseo = letter ("1234")
    assertEquals(false, parseo.isSuccess)
  }
  
  @Test
  def void_test_failure_empty() = {
    var parseo = letter ("")
    assertEquals(false, parseo.isSuccess)
  }
}

class Parser2_digit2_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def void_test_successful_digit() = {
    var parseo = digit ("1234")
    assertEquals(true, parseo.isSuccess)
    assertEquals("234", parseo.getResto())
    assertEquals('1', parseo.getValor())
  }
  
  @Test
  def void_test_failure_nondigit() = {
    var parseo = digit ("hola")
    assertEquals(false, parseo.isSuccess)
  }
  
  @Test
  def void_test_failure_empty() = {
    var parseo = digit ("")
    assertEquals(false, parseo.isSuccess)
  }
}

class Parser2_alphanum2_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def void_test_successful_digit() = {
    var parseo = alphaNum ("1234")
    assertEquals(true, parseo.isSuccess)
    assertEquals("234", parseo.getResto())
    assertEquals('1', parseo.getValor())
  }
  
  @Test
  def void_test_successful_letter() = {
    var parseo = alphaNum ("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals("ola", parseo.getResto())
    assertEquals('h', parseo.getValor())
  }
  
  @Test
  def void_test_failure_nondigit() = {
    var parseo = alphaNum ("***")
    assertEquals(false, parseo.isSuccess)
  }
  
  @Test
  def void_test_failure_empty() = {
    var parseo = alphaNum ("")
    assertEquals(false, parseo.isSuccess)
  }
}

class Parser2_string2_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def void_test_successful_containsstring() = {
    var parseo = string ("hola mundo", "hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals(" mundo", parseo.getResto())
    assertEquals("hola", parseo.getValor())
  }
  
  @Test
  def void_test_failure_wrongstring() = {
    var parseo = string ("holgado", "hola")
    assertEquals(false, parseo.isSuccess)
  }
  
  @Test
  def void_test_failure_empty_empty() = {
    var parseo = string ("", "")
    assertEquals(false, parseo.isSuccess)
  }
  
  @Test
  def void_test_failure_empty_() = {
    var parseo = string ("", "hola")
    assertEquals(false, parseo.isSuccess)
  }
}
