package domain

import org.junit.Before
import org.junit.Test
import org.junit.Assert._

class anyChar_Parser_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def anychar_test_successful() = {
    var resultado = anyChar.apply("hola".toList)
    assertEquals(true, resultado.isSuccess)
    assertEquals('h', resultado.get.getCaracter())
  }
  
  @Test
  def anychar_test_failure() = {
    var resultado = anyChar.apply("".toList)
    assertEquals(false, resultado.isSuccess) //ESTO FALLA
  }
}

class char_Parser_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def char_test_successful() = {
    var resultado = char.apply("hola", 'h')
    assertEquals(true, resultado.isSuccess)
    assertEquals('h', resultado.get.getCaracter())
  }
  
  @Test
  def char_test_failure_char() = {
    var resultado = char.apply("hola", 'c')
    assertEquals(false, resultado.isSuccess)
  }
  
  @Test
  def char_test_failure_empty() = {
    var resultado = char.apply("", 'c') //NOSUCH ELEMENTEXCEPTION
    assertEquals(false, resultado.isSuccess)
  }
}