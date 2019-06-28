package intento6

import org.junit.Before
import org.junit.Test
import org.junit.Assert._

class Parser_anyChar_Test {
  @Before
  def setup() = {
  }

  @Test
  def anychar_test_successful() = {
    var resultadoParseo = anyChar ("hola")    
    assertEquals(Success('h',"ola"), resultadoParseo)
  }

  @Test
  def anychar_test_failure_empty() = {
    var resultadoParseo = anyChar ("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
  
  @Test
  def anychar_test_failure_wrong_parameters() = {
    var resultadoParseo = anyChar (-1)
    assertEquals(Failure("Input Parameter Error"), resultadoParseo)
  }
}

class Parser_char_Test {
  @Before
  def setup() = {
  }

  @Test
  def char_test_successful_samechar() = {
    var resultadoParseo = char('h', "hola")
    assertEquals(Success('h',"ola"), resultadoParseo)
  }

  @Test
  def char_test_failure_wrongchar() = {
    var resultadoParseo = char('c', "hola")
    assertEquals(Failure("Not the same char"), resultadoParseo)
  }

  @Test
  def char_test_failure_empty() = {
    var resultadoParseo = char('c', "")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
  
  @Test
  def char_test_failure_wrong_parameters() = {
    var resultadoParseo = char (-1, "pepe")
    assertEquals(Failure("Input Parameter Error"), resultadoParseo)
  }
}

class Parser_void_Test {
  @Before
  def setup() = {
  }

  @Test
  def void_test_successful() = {
    var resultadoParseo = void ("hola")
    assertEquals(Success((),"ola"), resultadoParseo)
  }

  @Test
  def void_test_failure_empty() = {
    var resultadoParseo = void ("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
  
  @Test
  def void_test_failure_wrong_parameters() = {
    var resultadoParseo = void (-1)
    assertEquals(Failure("Input Parameter Error"), resultadoParseo)
  }
}

class Parser_letter_Test {
  @Before
  def setup() = {
  }

  @Test
  def letter_test_successful() = {
    var resultadoParseo = letter ("hola")
    assertEquals(Success('h',"ola"), resultadoParseo)
  }

  @Test
  def letter_test_failure_nonletter() = {
    var resultadoParseo = letter ("1234")
    assertEquals(Failure("Not a letter"), resultadoParseo)
  }

  @Test
  def letter_test_failure_empty() = {
    var resultadoParseo = letter ("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
  
  @Test
  def letter_test_failure_wrong_parameters() = {
    var resultadoParseo = letter (9999)
    assertEquals(Failure("Input Parameter Error"), resultadoParseo)
  }
}

class Parser_digit_Test {
  @Before
  def setup() = {
  }

  @Test
  def digit_test_successful() = {
    var resultadoParseo = digit ("1234")
    assertEquals(Success('1',"234"), resultadoParseo)
  }

  @Test
  def digit_test_failure_nondigit() = {
    var resultadoParseo = digit ("hola")
    assertEquals(Failure("Not a digit"), resultadoParseo)    
  }

  @Test
  def digit_test_failure_empty() = {
    var resultadoParseo = digit ("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)      
  }
  
  @Test
  def digit_test_failure_wrong_parameters() = {
    var resultadoParseo = digit (9999)
    assertEquals(Failure("Input Parameter Error"), resultadoParseo)
  }
}

class Parser_alphanum_Test {
  @Before
  def setup() = {
  }

  @Test
  def alphanum_test_successful_digit() = {
    var resultadoParseo = alphaNum ("1234")
    assertEquals(Success('1',"234"), resultadoParseo)    
  }

  @Test
  def alphanum_test_successful_letter() = {
    var resultadoParseo = alphaNum ("hola")
    assertEquals(Success('h',"ola"), resultadoParseo)    
  }

  @Test
  def alphanum_test_failure_notalphanum() = {
    var resultadoParseo = alphaNum ("***")
    assertEquals(Failure("Not an alphanum"), resultadoParseo)
  }

  @Test
  def alphanum_test_failure_empty() = {
    var resultadoParseo = alphaNum ("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)    
  }
  
  @Test
  def alphanum_test_failure_wrong_parameters() = {
    var resultadoParseo = alphaNum (9999)
    assertEquals(Failure("Input Parameter Error"), resultadoParseo)
  }
}

class Parser_string_Test {
  @Before
  def setup() = {
  }

  @Test
  def string_test_successful_containsstring() = {
    var resultadoParseo = string ("hola", "hola mundo")
    assertEquals(Success("hola"," mundo"), resultadoParseo)  
  }

  @Test
  def string_test_failure_wrongstring() = {
    var resultadoParseo = string ("hola", "holgado")
    assertEquals(Failure("Not same string"), resultadoParseo)    
  }

  @Test
  def string_test_failure_empty_empty() = {
    var resultadoParseo = string ("", "")
    assertEquals(Failure("Empty Input String"), resultadoParseo)  
  }

  @Test
  def string_test_failure_empty_string() = {
    var resultadoParseo = string ("hola", "")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def string_test_failure_string_empty() = {
    var resultadoParseo = string ("", "hola")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
  
  @Test
  def string_test_failure_wrong_parameters() = {
    var resultadoParseo = string (9999)
    assertEquals(Failure("Input Parameter Error"), resultadoParseo)
  }
}
