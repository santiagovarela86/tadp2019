package domain

import org.junit.Before
import org.junit.Test
import org.junit.Assert._

class Parser_anyChar_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def anychar_test_successful() = {
    var parseo = anyChar ("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals('h', parseo.get.getValor())
  }
  
  @Test
  def anychar_test_failure() = {
    var parseo = anyChar ("")
    assertEquals(false, parseo.isSuccess)
  }
}

class Parser_char_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def char_test_successful() = {
    var parseo = char('h') ("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals('h', parseo.get.getValor())
  }
  
  @Test
  def char_test_failure_char() = {
    var parseo = char('c') ("hola")
    assertEquals(false, parseo.isSuccess)
  }
  
  @Test
  def char_test_failure_empty() = {
    var parseo = char('c') ("")
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
    assertEquals(Unit, parseo.get.getValor()) //FALLA, COMO HAGO PARA QUE EL PARSEO TENGA O UNIT O UN CHAR? MAYBE?
    assertEquals("ola", parseo.get.getResto())
  }
  
  @Test
  def void_test_failure_empty() = {
    var parseo = void ("")
    assertEquals(false, parseo.isSuccess)
  }
}

