package domain

import org.junit.Before
import org.junit.Test
import org.junit.Assert._

class Parser_anyChar3_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def anychar3_test_successful() = {
    var parseo = anyChar3 ("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals('h', parseo.get.getValor())
  }
  
  @Test
  def anychar3_test_failure_empty() = {
    var parseo = anyChar3 ("")
    assertEquals(false, parseo.isSuccess)
  }
}
/* PINCHA ESTE MODELO
class Parser_char3_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def char3_test_successful_samechar() = {
    var parseo = char3('h') ("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals("ola", parseo.getResto())
    assertEquals('h', parseo.getValor())
  }
  
  @Test
  def char3_test_failure_wrongchar() = {
    var parseo = char3('c') ("hola")
    assertEquals(false, parseo.isSuccess)
  }
  
  @Test
  def char3_test_failure_empty() = {
    var parseo = char3('c') ("")
    assertEquals(false, parseo.isSuccess)
  }
}

class Parser_void_Test {  
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

class Parser_letter_Test {  
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

class Parser_digit_Test {  
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

class Parser_alphanum_Test {  
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

class Parser_string_Test {  
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
*/