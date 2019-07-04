package tp

import org.junit.Test
import org.junit.Assert._

class Parser_anyChar_Test {
  @Test
  def anychar_test_successful() = {
    var resultadoParseo = anyChar("hola")
    assertEquals(Success('h', "ola"), resultadoParseo)
  }

  @Test
  def anychar_test_failure_empty() = {
    var resultadoParseo = anyChar("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
}

class Parser_char_Test {
  @Test
  def char_test_successful_samechar() = {
    var resultadoParseo = char('h')("hola")
    assertEquals(Success('h', "ola"), resultadoParseo)
  }

  @Test
  def char_test_failure_notAChar() = {
    var resultadoParseo = char(9)("9123") //ESTO NO DEBERIA PODER DEJARTE COMPILAR!!!... WHAT? EL 9 ES UN CHAR?
    assertEquals(Success(9, "123"), resultadoParseo)
    assertEquals(Success('9', "123"), resultadoParseo)
  }

  @Test
  def char_test_failure_wrongchar() = {
    var resultadoParseo = char('c')("hola")
    assertEquals(Failure("Not the same char"), resultadoParseo)
  }

  @Test
  def char_test_failure_empty() = {
    var resultadoParseo = char('c')("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
}

class Parser_void_Test {
  @Test
  def void_test_successful() = {
    var resultadoParseo = void("hola")
    assertEquals(Success((), "ola"), resultadoParseo)
  }

  @Test
  def void_test_failure_empty() = {
    var resultadoParseo = void("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
}

class Parser_letter_Test {
  @Test
  def letter_test_successful() = {
    var resultadoParseo = letter("hola")
    assertEquals(Success('h', "ola"), resultadoParseo)
  }

  @Test
  def letter_test_failure_nonletter() = {
    var resultadoParseo = letter("1234")
    assertEquals(Failure("Not a letter"), resultadoParseo)
  }

  @Test
  def letter_test_failure_empty() = {
    var resultadoParseo = letter("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
}

class Parser_digit_Test {
  @Test
  def digit_test_successful() = {
    var resultadoParseo = digit("1234")
    assertEquals(Success('1', "234"), resultadoParseo)
  }

  @Test
  def digit_test_failure_nondigit() = {
    var resultadoParseo = digit("hola")
    assertEquals(Failure("Not a digit"), resultadoParseo)
  }

  @Test
  def digit_test_failure_empty() = {
    var resultadoParseo = digit("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
}

class Parser_alphanum_Test {
  @Test
  def alphanum_test_successful_digit() = {
    var resultadoParseo = alphaNum("1234")
    assertEquals(Success('1', "234"), resultadoParseo)
  }

  @Test
  def alphanum_test_successful_letter() = {
    var resultadoParseo = alphaNum("hola")
    assertEquals(Success('h', "ola"), resultadoParseo)
  }

  @Test
  def alphanum_test_failure_notalphanum() = {
    var resultadoParseo = alphaNum("***")
    assertEquals(Failure("Not an alphanum"), resultadoParseo)
  }

  @Test
  def alphanum_test_failure_empty() = {
    var resultadoParseo = alphaNum("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
}

class Parser_string_Test {
  @Test
  def string_test_successful_containsstring() = {
    var resultadoParseo = string("hola")("hola mundo")
    assertEquals(Success("hola", " mundo"), resultadoParseo)
  }

  @Test
  def string_test_failure_wrongstring() = {
    var resultadoParseo = string("hola")("holgado")
    assertEquals(Failure("Not the same string"), resultadoParseo)
  }

  @Test
  def string_test_failure_empty_empty() = {
    var resultadoParseo = string("")("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def string_test_failure_empty_string() = {
    var resultadoParseo = string("hola")("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def string_test_failure_string_empty() = {
    var resultadoParseo = string("")("hola")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
}

class ParserCombinators_Test {
  val alphaNumCombined = digit <|> letter
  val concatLetterLetter = letter <> letter
  val rightMost = letter ~> digit
  val leftMost = letter <~ digit
  val concatCharString = char('a') <> string(" veces")
  val combinedCharString = char('a') <|> string(" veces")
  val combinedCharVoid = char('a') <|> void
  val combinedStringVoid = string("hola") <|> void

  @Test
  def parser_combinator_alphaNum_number() = {
    var resultadoParseo = alphaNumCombined("1234")
    assertEquals(Success('1', "234"), resultadoParseo)
  }

  @Test
  def parser_combinator_alphaNum_letter() = {
    var resultadoParseo = alphaNumCombined("hola")
    assertEquals(Success('h', "ola"), resultadoParseo)
  }

  @Test
  def parser_combinator_alphaNum_empty() = {
    var resultadoParseo = alphaNumCombined("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def parser_combinator_letterLetter_number_letter() = {
    var resultadoParseo = concatLetterLetter("9z")
    assertEquals(Failure("Not a letter"), resultadoParseo)
  }

  @Test
  def parser_combinator_letterLetter_letter_letter() = {
    var resultadoParseo = concatLetterLetter("az")
    assertEquals(Success(('a', 'z'), ""), resultadoParseo)
  }

  @Test
  def parser_combinator_letterLetter_letter_number() = {
    var resultadoParseo = concatLetterLetter("z9")
    assertEquals(Failure("Not a letter"), resultadoParseo)
  }

  @Test
  def parser_combinator_letterLetter_empty() = {
    var resultadoParseo = concatLetterLetter("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def parser_combinator_rightmost_successful() = {
    var resultadoParseo = rightMost("z9")
    assertEquals(Success('9', ""), resultadoParseo)
  }

  @Test
  def parser_combinator_rightmost_failure() = {
    var resultadoParseo = rightMost("99")
    assertEquals(Failure("Not a letter"), resultadoParseo)
  }

  @Test
  def parser_combinator_rightmost_failure_empty() = {
    var resultadoParseo = rightMost("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def parser_combinator_leftmost_successful() = {
    var resultadoParseo = leftMost("z9")
    assertEquals(Success('z', ""), resultadoParseo)
  }

  @Test
  def parser_combinator_leftmost_failure() = {
    var resultadoParseo = leftMost("99")
    assertEquals(Failure("Not a letter"), resultadoParseo)
  }

  @Test
  def parser_combinator_leftmost_failure_empty() = {
    var resultadoParseo = leftMost("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def parser_combinator_concat_char_string_success() = {
    var resultadoParseo = concatCharString("a veces gano")
    assertEquals(Success(('a', " veces"), " gano"), resultadoParseo)
  }

  @Test
  def parser_combinator_concat_char_string_failure_char() = {
    var resultadoParseo = concatCharString("dance dance dance")
    assertEquals(Failure("Not the same char"), resultadoParseo)
  }

  @Test
  def parser_combinator_concat_char_string_failure_string() = {
    var resultadoParseo = concatCharString("a beces gano")
    assertEquals(Failure("Not the same string"), resultadoParseo)
  }

  @Test
  def parser_combinator_concat_char_string_failure_empty() = {
    var resultadoParseo = concatCharString("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
  
  @Test
  def parser_combinator_combined_char_string_success_char() = {
    var resultadoParseo = combinedCharString("a345")
    assertEquals(Success('a', "345"), resultadoParseo)
  }
  
  @Test
  def parser_combinator_combined_char_string_success_string() = {
    var resultadoParseo = combinedCharString(" veces gano")
    assertEquals(Success(" veces", " gano"), resultadoParseo)
  }
  
  @Test
  def parser_combinator_combined_char_string_failure_empty() = {
    var resultadoParseo = combinedCharString("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
  
  @Test
  def parser_combinator_combinedCharVoid_char() = {
    var resultadoParseo = combinedCharVoid("a234")
    assertEquals(Success('a', "234"), resultadoParseo)
  }

  @Test
  def parser_combinator_combinedCharVoid_void() = {
    var resultadoParseo = combinedCharVoid("hola")
    assertEquals(Success((), "ola"), resultadoParseo)
  }

  @Test
  def parser_combinator_combinedCharVoid_empty() = {
    var resultadoParseo = combinedCharVoid("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
  
  @Test
  def parser_combinator_combinedStringVoid_void() = {
    var resultadoParseo = combinedStringVoid("1234")
    assertEquals(Success((), "234"), resultadoParseo)
  }

  @Test
  def parser_combinator_combinedStringVoid_string() = {
    var resultadoParseo = combinedStringVoid("hola")
    assertEquals(Success("hola", ""), resultadoParseo)
  }

  @Test
  def parser_combinator_combinedStringVoid_empty() = {
    var resultadoParseo = combinedStringVoid("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
}

class ParsersII_Satisfies_Tests {
  val alphaNumCombined = digit <|> letter
  
  val alphaNumCombinedSatisfiesIsAOr9 = alphaNumCombined.satisfies((x: Char) => x == 'a' || x == 'A' || x == '9')

  @Test
  def parserII_satisfies_alphaNumCombinedSatisfiesIsAOr9_success_A() = {
    var resultadoParseo = alphaNumCombinedSatisfiesIsAOr9("a234")
    assertEquals(Success('a', "234"), resultadoParseo)
  }
  
  @Test
  def parserII_satisfies_alphaNumCombinedSatisfiesIsAOr9_success_9() = {
    var resultadoParseo = alphaNumCombinedSatisfiesIsAOr9("9234")
    assertEquals(Success('9', "234"), resultadoParseo)
  }

  @Test
  def parserII_satisfies_alphaNumCombinedSatisfiesIsAOr9_failure_notA() = {
    var resultadoParseo = alphaNumCombinedSatisfiesIsAOr9("1234")
    assertEquals(Failure("Doesn't satisfy the condition"), resultadoParseo)
  }
  
  @Test
  def parserII_satisfies_alphaNumCombinedSatisfiesIsAOr9_failure_empty() = {
    var resultadoParseo = alphaNumCombinedSatisfiesIsAOr9("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
}