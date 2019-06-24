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
*/