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
    assertEquals(false, resultado.isSuccess)
  }
}

