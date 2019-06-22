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
    var resultado = anyChar ("hola")
    assertEquals(true, resultado.isSuccess)
    assertEquals('h', resultado.get.getValor())
  }
  
  @Test
  def anychar_test_failure() = {
    var resultado = anyChar ("")
    assertEquals(false, resultado.isSuccess)
  }
}

class Parser_char_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def char_test_successful() = {
    var resultado = char ("hola", 'h')
    assertEquals(true, resultado.isSuccess)
    assertEquals('h', resultado.get.getValor())
  }
  
  @Test
  def char_test_failure_char() = {
    var resultado = char ("hola", 'c')
    assertEquals(false, resultado.isSuccess)
  }
  
  @Test
  def char_test_failure_empty() = {
    var resultado = char ("", 'c')
    assertEquals(false, resultado.isSuccess)
  }
}
