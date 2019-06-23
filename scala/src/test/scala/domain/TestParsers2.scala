package domain

import org.junit.Before
import org.junit.Test
import org.junit.Assert._

class Parser_anyChar2_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def anychar_test_successful() = {
    var parseo = anyChar2 ("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals('h', parseo.getValor())
  }
  
  @Test
  def anychar_test_failure_empty() = {
    var parseo = anyChar2 ("")
    assertEquals(false, parseo.isSuccess)
  }
}

class Parser_char2_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def char_test_successful_samechar() = {
    var parseo = char2('h') ("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals("ola", parseo.getResto())
    assertEquals('h', parseo.getValor())
  }
  
  @Test
  def char_test_failure_wrongchar() = {
    var parseo = char2('c') ("hola")
    assertEquals(false, parseo.isSuccess)
  }
  
  @Test
  def char_test_failure_empty() = {
    var parseo = char2('c') ("")
    assertEquals(false, parseo.isSuccess)
  }
}

class Parser_void2_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def void_test_successful() = {
    var parseo = void2 ("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals("ola", parseo.getResto())
    assertEquals((), parseo.getValor())
  }
  
  @Test
  def void_test_failure_empty() = {
    var parseo = void2 ("")
    assertEquals(false, parseo.isSuccess)
  }
}

class Parser_letter2_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def void_test_successful_letter() = {
    var parseo = letter2 ("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals('h', parseo.getValor())
    assertEquals("ola", parseo.getResto())
  }
  
  @Test
  def void_test_failure_nonletter() = {
    var parseo = letter2 ("1234")
    assertEquals(false, parseo.isSuccess)
  }
  
  @Test
  def void_test_failure_empty() = {
    var parseo = letter2 ("")
    assertEquals(false, parseo.isSuccess)
  }
}

class Parser_digit2_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def void_test_successful_digit() = {
    var parseo = digit2 ("1234")
    assertEquals(true, parseo.isSuccess)
    assertEquals("234", parseo.getResto())
    assertEquals('1', parseo.getValor())
  }
  
  @Test
  def void_test_failure_nondigit() = {
    var parseo = digit2 ("hola")
    assertEquals(false, parseo.isSuccess)
  }
  
  @Test
  def void_test_failure_empty() = {
    var parseo = digit2 ("")
    assertEquals(false, parseo.isSuccess)
  }
}

class Parser_alphanum2_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def void_test_successful_digit() = {
    var parseo = alphaNum2 ("1234")
    assertEquals(true, parseo.isSuccess)
    assertEquals("234", parseo.getResto())
    assertEquals('1', parseo.getValor())
  }
  
  @Test
  def void_test_successful_letter() = {
    var parseo = alphaNum2 ("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals("ola", parseo.getResto())
    assertEquals('h', parseo.getValor())
  }
  
  @Test
  def void_test_failure_nondigit() = {
    var parseo = alphaNum2 ("***")
    assertEquals(false, parseo.isSuccess)
  }
  
  @Test
  def void_test_failure_empty() = {
    var parseo = alphaNum2 ("")
    assertEquals(false, parseo.isSuccess)
  }
}

class Parser_string2_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def void_test_successful_containsstring() = {
    var parseo = string2 ("hola mundo", "hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals(" mundo", parseo.getResto())
    assertEquals("hola", parseo.getValor())
  }
  
  @Test
  def void_test_failure_wrongstring() = {
    var parseo = string2 ("holgado", "hola")
    assertEquals(false, parseo.isSuccess)
  }
  
  @Test
  def void_test_failure_empty_empty() = {
    var parseo = string2 ("", "")
    assertEquals(false, parseo.isSuccess)
  }
  
  @Test
  def void_test_failure_empty_() = {
    var parseo = string2 ("", "hola")
    assertEquals(false, parseo.isSuccess)
  }
}
