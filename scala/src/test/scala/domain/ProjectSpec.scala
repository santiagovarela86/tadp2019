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
    var parseo = anyChar2 ("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals('h', parseo.getValor())
  }
  
  @Test
  def anychar_test_failure() = {
    var parseo = anyChar2 ("")
    assertEquals(false, parseo.isSuccess)
  }
}

class Parser_char_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def char_test_successful() = {
    var parseo = char2('h') ("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals('h', parseo.getValor())
  }
  
  @Test
  def char_test_failure_char() = {
    var parseo = char2('c') ("hola")
    assertEquals(false, parseo.isSuccess)
  }
  
  @Test
  def char_test_failure_empty() = {
    var parseo = char2('c') ("")
    assertEquals(false, parseo.isSuccess)
  }
}

class Parser_void_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def void_test_successful() = {
    var parseo = void2 ("hola")
    assertEquals(true, parseo.isSuccess)
    assertEquals(Unit, parseo.getValor())
    assertEquals("ola", parseo.getResto())
  }
  
  @Test
  def void_test_failure_empty() = {
    var parseo = void2 ("")
    assertEquals(false, parseo.isSuccess)
  }
}

