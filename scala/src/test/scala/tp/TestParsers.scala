package tp

import Musica._
import org.junit.Assert._
import org.junit.Test

class Parser_anyChar_Test {
  @Test
  def anychar_test_successful() = {
    val resultadoParseo = anyChar("hola")
    assertEquals(Success('h', "ola"), resultadoParseo)
  }

  @Test
  def anychar_test_failure_empty() = {
    val resultadoParseo = anyChar("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
}

class Parser_char_Test {
  @Test
  def char_test_successful_samechar() = {
    val resultadoParseo = char('h')("hola")
    assertEquals(Success('h', "ola"), resultadoParseo)
  }

  /*
  @Test
  def char_test_failure_notAChar() = {
    val resultadoParseo = char(9)("9123") //ESTO NO DEBERIA PODER DEJARTE COMPILAR!!!... WHAT? EL 9 ES UN CHAR?
    assertEquals(Success(9, "123"), resultadoParseo)
    assertEquals(Success('9', "123"), resultadoParseo)
  }
  */

  @Test
  def char_test_failure_wrongchar() = {
    val resultadoParseo = char('c')("hola")
    assertEquals(Failure("Not the same char"), resultadoParseo)
  }

  @Test
  def char_test_failure_empty() = {
    val resultadoParseo = char('c')("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
}

class Parser_void_Test {
  @Test
  def void_test_successful() = {
    val resultadoParseo = void("hola")
    assertEquals(Success((), "ola"), resultadoParseo)
  }

  @Test
  def void_test_failure_empty() = {
    val resultadoParseo = void("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
}

class Parser_letter_Test {
  @Test
  def letter_test_successful() = {
    val resultadoParseo = letter("hola")
    assertEquals(Success('h', "ola"), resultadoParseo)
  }

  @Test
  def letter_test_failure_nonletter() = {
    val resultadoParseo = letter("1234")
    assertEquals(Failure("Not a letter"), resultadoParseo)
  }

  @Test
  def letter_test_failure_empty() = {
    val resultadoParseo = letter("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
}

class Parser_digit_Test {
  @Test
  def digit_test_successful() = {
    val resultadoParseo = digit("1234")
    assertEquals(Success('1', "234"), resultadoParseo)
  }

  @Test
  def digit_test_failure_nondigit() = {
    val resultadoParseo = digit("hola")
    assertEquals(Failure("Not a digit"), resultadoParseo)
  }

  @Test
  def digit_test_failure_empty() = {
    val resultadoParseo = digit("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
}

class Parser_alphanum_Test {
  @Test
  def alphanum_test_successful_digit() = {
    val resultadoParseo = alphaNum("1234")
    assertEquals(Success('1', "234"), resultadoParseo)
  }

  @Test
  def alphanum_test_successful_letter() = {
    val resultadoParseo = alphaNum("hola")
    assertEquals(Success('h', "ola"), resultadoParseo)
  }

  @Test
  def alphanum_test_failure_notalphanum() = {
    val resultadoParseo = alphaNum("***")
    assertEquals(Failure("Not an alphanum"), resultadoParseo)
  }

  @Test
  def alphanum_test_failure_empty() = {
    val resultadoParseo = alphaNum("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
}

class Parser_string_Test {
  @Test
  def string_test_successful_containsstring() = {
    val resultadoParseo = string("hola")("hola mundo")
    assertEquals(Success("hola", " mundo"), resultadoParseo)
  }

  @Test
  def string_test_failure_wrongstring() = {
    val resultadoParseo = string("hola")("holgado")
    assertEquals(Failure("Not the same string"), resultadoParseo)
  }

  @Test
  def string_test_failure_empty_empty() = {
    val resultadoParseo = string("")("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def string_test_failure_empty_string() = {
    val resultadoParseo = string("hola")("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def string_test_failure_string_empty() = {
    val resultadoParseo = string("")("hola")
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
    val resultadoParseo = alphaNumCombined("1234")
    assertEquals(Success('1', "234"), resultadoParseo)
  }

  @Test
  def parser_combinator_alphaNum_letter() = {
    val resultadoParseo = alphaNumCombined("hola")
    assertEquals(Success('h', "ola"), resultadoParseo)
  }

  @Test
  def parser_combinator_alphaNum_empty() = {
    val resultadoParseo = alphaNumCombined("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def parser_combinator_letterLetter_number_letter() = {
    val resultadoParseo = concatLetterLetter("9z")
    assertEquals(Failure("Not a letter"), resultadoParseo)
  }

  @Test
  def parser_combinator_letterLetter_letter_letter() = {
    val resultadoParseo = concatLetterLetter("az")
    assertEquals(Success(('a', 'z'), ""), resultadoParseo)
  }

  @Test
  def parser_combinator_letterLetter_letter_number() = {
    val resultadoParseo = concatLetterLetter("z9")
    assertEquals(Failure("Not a letter"), resultadoParseo)
  }

  @Test
  def parser_combinator_letterLetter_empty() = {
    val resultadoParseo = concatLetterLetter("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def parser_combinator_rightmost_successful() = {
    val resultadoParseo = rightMost("z9")
    assertEquals(Success('9', ""), resultadoParseo)
  }

  @Test
  def parser_combinator_rightmost_failure() = {
    val resultadoParseo = rightMost("99")
    assertEquals(Failure("Not a letter"), resultadoParseo)
  }

  @Test
  def parser_combinator_rightmost_failure_empty() = {
    val resultadoParseo = rightMost("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def parser_combinator_leftmost_successful() = {
    val resultadoParseo = leftMost("z9")
    assertEquals(Success('z', ""), resultadoParseo)
  }

  @Test
  def parser_combinator_leftmost_failure() = {
    val resultadoParseo = leftMost("99")
    assertEquals(Failure("Not a letter"), resultadoParseo)
  }

  @Test
  def parser_combinator_leftmost_failure_empty() = {
    val resultadoParseo = leftMost("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def parser_combinator_concat_char_string_success() = {
    val resultadoParseo = concatCharString("a veces gano")
    assertEquals(Success(('a', " veces"), " gano"), resultadoParseo)
  }

  @Test
  def parser_combinator_concat_char_string_failure_char() = {
    val resultadoParseo = concatCharString("dance dance dance")
    assertEquals(Failure("Not the same char"), resultadoParseo)
  }

  @Test
  def parser_combinator_concat_char_string_failure_string() = {
    val resultadoParseo = concatCharString("a beces gano")
    assertEquals(Failure("Not the same string"), resultadoParseo)
  }

  @Test
  def parser_combinator_concat_char_string_failure_empty() = {
    val resultadoParseo = concatCharString("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def parser_combinator_combined_char_string_success_char() = {
    val resultadoParseo = combinedCharString("a345")
    assertEquals(Success('a', "345"), resultadoParseo)
  }

  @Test
  def parser_combinator_combined_char_string_success_string() = {
    val resultadoParseo = combinedCharString(" veces gano")
    assertEquals(Success(" veces", " gano"), resultadoParseo)
  }

  @Test
  def parser_combinator_combined_char_string_failure_empty() = {
    val resultadoParseo = combinedCharString("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def parser_combinator_combinedCharVoid_char() = {
    val resultadoParseo = combinedCharVoid("a234")
    assertEquals(Success('a', "234"), resultadoParseo)
  }

  @Test
  def parser_combinator_combinedCharVoid_void() = {
    val resultadoParseo = combinedCharVoid("hola")
    assertEquals(Success((), "ola"), resultadoParseo)
  }

  @Test
  def parser_combinator_combinedCharVoid_empty() = {
    val resultadoParseo = combinedCharVoid("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def parser_combinator_combinedStringVoid_void() = {
    val resultadoParseo = combinedStringVoid("1234")
    assertEquals(Success((), "234"), resultadoParseo)
  }

  @Test
  def parser_combinator_combinedStringVoid_string() = {
    val resultadoParseo = combinedStringVoid("hola")
    assertEquals(Success("hola", ""), resultadoParseo)
  }

  @Test
  def parser_combinator_combinedStringVoid_empty() = {
    val resultadoParseo = combinedStringVoid("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
}

class ParsersII_Tests {
  val alphaNumCombined = digit <|> letter
  val alphaNumCombinedSatisfiesIsAOr9 = alphaNumCombined.satisfies((x: Char) => x == 'a' || x == 'A' || x == '9')
  val trueParser = string("true").const(true)
  val talVezIn = string("in").opt
  val precedencia = talVezIn <> string("fija")
  val talVezChar = char('a').opt
  val charAKleene = char('a').*
  val charAOperadorMas = char('a').+
  val mapCharADigit = digit.map({
    case '1' => 1
    case '2' => 2
    case '3' => 3
    case '4' => 4
    case '5' => 5
    case '6' => 6
    case '7' => 7
    case '8' => 8
    case '9' => 9
  })
  val phoneNumber = digit.sepBy(char(' '))

  @Test
  def parserII_satisfies_alphaNumCombinedSatisfiesIsAOr9_success_A() = {
    val resultadoParseo = alphaNumCombinedSatisfiesIsAOr9("a234")
    assertEquals(Success('a', "234"), resultadoParseo)
  }

  @Test
  def parserII_satisfies_alphaNumCombinedSatisfiesIsAOr9_success_9() = {
    val resultadoParseo = alphaNumCombinedSatisfiesIsAOr9("9234")
    assertEquals(Success('9', "234"), resultadoParseo)
  }

  @Test
  def parserII_satisfies_alphaNumCombinedSatisfiesIsAOr9_failure_notA() = {
    val resultadoParseo = alphaNumCombinedSatisfiesIsAOr9("1234")
    assertEquals(Failure("Doesn't satisfy the condition"), resultadoParseo)
  }

  @Test
  def parserII_satisfies_alphaNumCombinedSatisfiesIsAOr9_failure_empty() = {
    val resultadoParseo = alphaNumCombinedSatisfiesIsAOr9("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def parserII_const_trueParser_success() = {
    val resultadoParseo = trueParser("true")
    assertEquals(Success(true, ""), resultadoParseo)
  }

  @Test
  def parserII_const_trueParser_failure_notTrue() = {
    val resultadoParseo = trueParser("9234")
    assertEquals(Failure("Not the same string"), resultadoParseo)
  }

  @Test
  def parserII_const_trueParser_failure_empty() = {
    val resultadoParseo = trueParser("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def parserII_opt_precedencia_success_infija_string() = {
    val resultadoParseo = precedencia("infija")
    assertEquals(Success((Some("in"), "fija"), ""), resultadoParseo)
  }

  @Test
  def parserII_opt_precedencia_success_fija_string() = {
    val resultadoParseo = precedencia("fija")
    assertEquals(Success((None, "fija"), ""), resultadoParseo)
  }

  @Test
  def parserII_opt_precedencia_string_failure_empty() = {
    val resultadoParseo = precedencia("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def parserII_opt_talVezChar_success_infija_string() = {
    val resultadoParseo = talVezChar("a veces")
    assertEquals(Success(Some('a'), " veces"), resultadoParseo)
  }

  @Test
  def parserII_opt_talVezChar_success_fija_string() = {
    val resultadoParseo = talVezChar("gano")
    assertEquals(Success(None, "gano"), resultadoParseo)
  }

  @Test
  def parserII_opt_talVezChar_string_success_empty() = {
    val resultadoParseo = talVezChar("")
    //assertEquals(Failure("Empty Input String"), resultadoParseo) //NO FALLA CON EMPTY STRING
    assertEquals(Success(None, ""), resultadoParseo)
  }

  @Test
  def parserII_charAKleene_success() = {
    val resultadoParseo = charAKleene("aaaaa veces")
    assertEquals(Success(List('a', 'a', 'a', 'a', 'a'), " veces"), resultadoParseo)
  }

  @Test
  def parserII_charAKleene_success_notSameChar() = {
    val resultadoParseo = charAKleene("bbba veces")
    assertEquals(Success(List(), "bbba veces"), resultadoParseo)
  }

  @Test
  def parserII_charAKleene_success_empty() = {
    val resultadoParseo = charAKleene("")
    //assertEquals(Failure("Empty Input String"), resultadoParseo) //NO FALLA CON EMPTY STRING, PUEDE APLICARSE 0 VECES
    assertEquals(Success(List(), ""), resultadoParseo)
  }

  @Test
  def parserII_charAOperadorMas_success() = {
    val resultadoParseo = charAOperadorMas("aaaaa veces")
    assertEquals(Success(List('a', 'a', 'a', 'a', 'a'), " veces"), resultadoParseo)
  }

  @Test
  def parserII_charAOperadorMas_failure_notSameChar() = {
    val resultadoParseo = charAOperadorMas("bbba veces")
    assertEquals(Failure("Not the same char"), resultadoParseo)
  }

  @Test
  def parserII_charAOperadorMas_failure_empty() = {
    val resultadoParseo = charAOperadorMas("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def parserII_map_mapCharADigit_success_1() = {
    val resultadoParseo = mapCharADigit("1234")
    assertEquals(Success(1, "234"), resultadoParseo)
  }

  @Test
  def parserII_map_mapCharADigit_failure() = {
    val resultadoParseo = mapCharADigit("a234")
    assertEquals(Failure("Not a digit"), resultadoParseo)
  }

  @Test
  def parserII_map_mapCharADigit_failure_empty() = {
    val resultadoParseo = mapCharADigit("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }

  @Test
  def parserII_sepBy_phoneNumber_success() = {
    val resultadoParseo = phoneNumber("1 2 3 4 5 6 7 8")
    assertEquals(Success(List('1', '2', '3', '4', '5', '6', '7', '8'), ""), resultadoParseo)
  }

  @Test
  def parserII_sepBy_phoneNumber_failure() = {
    val resultadoParseo = phoneNumber("1234 5678")
    assertEquals(Success(List('1'), "234 5678"), resultadoParseo)
  }

  @Test
  def parserII_sepBy_phoneNumber_failure_notADigit() = {
    val resultadoParseo = phoneNumber("saraza")
    assertEquals(Failure("Not a digit"), resultadoParseo)
  }

  @Test
  def parserII_sepBy_phoneNumber_failure_empty() = {
    val resultadoParseo = phoneNumber("")
    assertEquals(Failure("Empty Input String"), resultadoParseo)
  }
}

class ParsersIII_Tests {

  @Test
  def silencioBlancaParser() = {
    val resultadoParseo = silencio("_A")
    assertEquals(Success(Silencio(Blanca), "A"), resultadoParseo)
  }

  @Test
  def silencioCorcheaParser(): Unit = {
    val resultadoParseo = silencio("~A")
    assertEquals(Success(Silencio(Corchea), "A"), resultadoParseo)
  }

  @Test
  def notaParserSuccessA(): Unit = {
    val resultadoParseo = nota("ABCD")
    assertEquals(Success(A, "BCD"), resultadoParseo)
  }

  @Test
  def notaParserSuccesAs: Unit = {
    val resultadoParseo = nota("A#B")
    assertEquals(Success(As, "B"), resultadoParseo)
  }

  @Test
  def notaParserFailure(): Unit = {
    val resultadoParseo = nota("XABCD")
    assertEquals(Failure("Doesn't satisfy the condition"), resultadoParseo) //Estaria copado que sea otro error
  }

  @Test
  def figuraParserCorchea(): Unit = {
    val resultadoParseo = figura("1/8A")
    assertEquals(Success(Corchea, "A"), resultadoParseo)
  }

  @Test
  def figuraParserFailure(): Unit = {
    val resultadoParseo = figura("1/7B")
    assertEquals(Failure("Not the same string"), resultadoParseo) //Estaria copado que sea otro error
  }

  @Test
  def tonoParser6A(): Unit = {
    val resultadoParseo = tono("6A")
    assertEquals(Success(Tono(6, A), ""), resultadoParseo)
  }

  @Test
  def tonoParser1024D(): Unit = {
    val resultadoParseo = tono("1024D#J")
    assertEquals(Success(Tono(1024, Ds), "J"), resultadoParseo)
  }

  @Test
  def tonoParserFailure(): Unit = {
    val resultadoParseo = tono("6,5A")
    assertEquals(Failure("Doesn't satisfy the condition"), resultadoParseo)
  }

  @Test
  def sonidoParser6AsNegra(): Unit = {
    val resultadoParseo = sonido("6A#1/4")
    assertEquals(Success(Sonido(Tono(6, As), Negra), ""), resultadoParseo)
  }

  @Test
  def acordeExplicitoSuccess(): Unit = {
    val resultadorParseo = acordeExplicito("6A+6C#+6G1/8")
    assertEquals(Success(Acorde(List(Tono(6, A), Tono(6, Cs), Tono(6, G)), Corchea), ""), resultadorParseo)
  }

  @Test
  def acordeMayorSuccess(): Unit = {
    val resultadoParseo = acordeMayor("6AM1/2")
    assertEquals(Success(Acorde(List(Tono(6, A), Tono(6, Cs), Tono(6, E)), Blanca), ""), resultadoParseo)
  }

  @Test
  def melodiaFCSucces(): Unit = {
    val resultadoParseo = melodia("4C1/4 4C1/4 4D1/2 4C1/4 4F1/2 4E1/2 4C1/8 4C1/4 4D1/2 4C1/2 4G1/2 4F1/2 4C1/8 4C1/4 5C1/2 4A1/2 4F1/8 4F1/4 4E1/2 4D1/2")
    assertTrue(resultadoParseo.isInstanceOf[Success[Melodia]])
  }

  @Test
  def melodiaBonusSuccess(): Unit = {
    val resultadoParseo = melodia("4AM1/8 5C1/8 5C#1/8 5C#1/8 5D#1/8 5C1/8 4A#1/8 4G#1/2 - 4A#1/8 4A#1/8 5C1/4 5C#1/8 4A#1/4 4G#1/2 5G#1/4 5G#1/4 5D#1/2")
    assertTrue(resultadoParseo.isInstanceOf[Success[Melodia]])
  }
}