package domain

import domain.parsers._

import org.junit.Before
import org.junit.Test
import org.junit.Assert._

class Parser_char4_Test {  
  @Before
  def setup() = {
  }
  
  @Test
  def char4_test_successful() = {
    var parseo = success ('h')
    //assertEquals(true, parseo.)
    assertEquals('h', parseo("hola").head._1)
  }
  
  @Test
  def char4_test_failure_empty() = {
    var parseo = success ('h')
    assertEquals(, parseo)
  }
}