/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Parser Test                +
project   icecore-json                    +
file      JsonParserTest.java             +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-29 18:06 UTC+0200       +
modified  2016-05-29 18:07 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Tests the JSON parser class "JsonParser".

[Copyright]
Copyright (C) 2016 Arctic Ice Studio <development@arcticicestudio.com>

[References]
JSON
  (http://json.org)
ECMA-404 1st Edition (October 2013)
  (http://www.ecma-international.org/publications/files/ECMA-ST/ECMA-404.pdf)
Java 8 API Documentation
  (https://docs.oracle.com/javase/8/docs/api/)
JUnit
  (http://junit.org)
Mockito
  (http://mockito.org)
Arctic Versioning Specification (ArcVer)
  (http://specs.arcticicestudio.com/arcver)
*/

package com.arcticicestudio.icecore.json;

import org.hamcrest.core.StringStartsWith;
import org.junit.Test;

import static com.arcticicestudio.icecore.json.TestUtil.assertException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import com.arcticicestudio.icecore.json.TestUtil.RunnableEx;

/**
 * Tests the JSON parser class {@link JsonParser}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class JsonParserTest {

  @Test
  public void parseRejectsEmptyString() {
    assertParseException(0, "Unexpected end of input", "");
  }

  @Test
  public void parseRejectsEmptyReader() {
    ParseException exception = assertException(ParseException.class, new RunnableEx() {
      public void run() throws IOException {
        new JsonParser(new StringReader("")).parse();
      }
    });
    assertEquals(0, exception.getOffset());
    assertThat(exception.getMessage(), StringStartsWith.startsWith("Unexpected end of input at"));
  }

  @Test
  public void parseAcceptsArrays() {
    assertEquals(new JsonArray(), parse("[]"));
  }

  @Test
  public void parseAcceptsObjects() {
    assertEquals(new JsonObject(), parse("{}"));
  }

  @Test
  public void parseAcceptsStrings() {
    assertEquals(new JsonString(""), parse("\"\""));
  }

  @Test
  public void parseAcceptsLiterals() {
    assertSame(Json.NULL, parse("null"));
  }

  @Test
  public void parseStripsPadding() {
    assertEquals(new JsonArray(), parse(" [ ] "));
  }

  @Test
  public void parseIgnoresAllWhiteSpace() {
    assertEquals(new JsonArray(), parse("\t\r\n [\t\r\n ]\t\r\n "));
  }

  @Test
  public void parseFailsWithUnterminatedString() {
    assertParseException(5, "Unexpected end of input", "[\"foo");
  }

  @Test
  public void parseHandlesLineBreaksAndColumnsCorrectly() {
    assertParseException(0, 1, 0, "!");
    assertParseException(2, 2, 0, "[\n!");
    assertParseException(3, 2, 0, "[\r\n!");
    assertParseException(6, 3, 1, "[ \n \n !");
    assertParseException(7, 2, 3, "[ \r\n \r !");
  }

  @Test
  public void parseHandlesInputsThatExceedBufferSize() throws IOException {
    String input = "[ 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47 ]";
    JsonValue value = new JsonParser(new StringReader(input), 3).parse();
    assertEquals("[2,3,5,7,11,13,17,19,23,29,31,37,41,43,47]", value.toString());
  }

  @Test
  public void parseHandlesStringsThatExceedBufferSize() throws IOException {
    String input = "[ \"lorem ipsum dolor sit amet\" ]";
    JsonValue value = new JsonParser(new StringReader(input), 3).parse();
    assertEquals("[\"lorem ipsum dolor sit amet\"]", value.toString());
  }

  @Test
  public void parseHandlesNumbersThatExceedBufferSize() throws IOException {
    String input = "[ 3.141592653589 ]";
    JsonValue value = new JsonParser(new StringReader(input), 3).parse();
    assertEquals("[3.141592653589]", value.toString());
  }

  @Test
  public void parseHandlesPositionsCorrectlyWhenInputExceedsBufferSize() {
    final String input = "{\n  \"a\": 23,\n  \"b\": 42,\n}";
    ParseException exception = assertException(ParseException.class, new RunnableEx() {
      public void run() throws IOException {
        new JsonParser(new StringReader(input), 3).parse();
      }
    });
    assertEquals(4, exception.getLine());
    assertEquals(0, exception.getColumn());
    assertEquals(24, exception.getOffset());
  }

  @Test
  public void parseFailsOnTooDeeplyNestedArray() {
    JsonArray array = new JsonArray();
    for (int i = 0; i < 1001; i++) {
      array = new JsonArray().add(array);
    }
    final String input = array.toString();
    ParseException exception = assertException(ParseException.class, new RunnableEx() {
      public void run() throws IOException {
        new JsonParser(input).parse();
      }
    });
    assertEquals("Nesting too deep at 1:1001", exception.getMessage());
  }

  @Test
  public void parseFailsOnTooDeeplyNestedObject() {
    JsonObject object = new JsonObject();
    for (int i = 0; i < 1001; i++) {
      object = new JsonObject().add("foo", object);
    }
    final String input = object.toString();
    ParseException exception = assertException(ParseException.class, new RunnableEx() {
      public void run() throws IOException {
        new JsonParser(input).parse();
      }
    });
    assertEquals("Nesting too deep at 1:7001", exception.getMessage());
  }

  @Test
  public void parseFailsOnTooDeeplyNestedMixedObject() {
    JsonValue value = new JsonObject();
    for (int i = 0; i < 1001; i++) {
      value = i % 2 == 0 ? new JsonArray().add(value) : new JsonObject().add("foo", value);
    }
    final String input = value.toString();
    ParseException exception = assertException(ParseException.class, new RunnableEx() {
      public void run() throws IOException {
        new JsonParser(input).parse();
      }
    });
    assertEquals("Nesting too deep at 1:4001", exception.getMessage());
  }

  @Test
  public void parseDoesNotFailWithManyArrays() throws IOException {
    JsonArray array = new JsonArray();
    for (int i = 0; i < 1001; i++) {
      array.add(new JsonArray().add(7));
    }
    final String input = array.toString();
    JsonValue result = new JsonParser(input).parse();
    assertTrue(result.isArray());
  }

  @Test
  public void parseDoesNotFailWithManyEmptyArrays() throws IOException {
    JsonArray array = new JsonArray();
    for (int i = 0; i < 1001; i++) {
      array.add(new JsonArray());
    }
    final String input = array.toString();
    JsonValue result = new JsonParser(input).parse();
    assertTrue(result.isArray());
  }

  @Test
  public void parseDoesNotFailWithManyObjects() throws IOException {
    JsonArray array = new JsonArray();
    for (int i = 0; i < 1001; i++) {
      array.add(new JsonObject().add("a", 7));
    }
    final String input = array.toString();
    JsonValue result = new JsonParser(input).parse();
    assertTrue(result.isArray());
  }

  @Test
  public void parseDoesNotFailWithManyEmptyObjects() throws IOException {
    JsonArray array = new JsonArray();
    for (int i = 0; i < 1001; i++) {
      array.add(new JsonObject());
    }
    final String input = array.toString();
    JsonValue result = new JsonParser(input).parse();
    assertTrue(result.isArray());
  }

  @Test
  public void arraysEmpty() {
    assertEquals("[]", parse("[]").toString());
  }

  @Test
  public void arraysSingleValue() {
    assertEquals("[92]", parse("[92]").toString());
  }

  @Test
  public void arraysMultipleValues() {
    assertEquals("[92,42]", parse("[92,42]").toString());
  }

  @Test
  public void arraysWithWhitespaces() {
    assertEquals("[92,42]", parse("[ 92 , 42 ]").toString());
  }

  @Test
  public void arraysNested() {
    assertEquals("[[92]]", parse("[[92]]").toString());
    assertEquals("[[[]]]", parse("[[[]]]").toString());
    assertEquals("[[92],42]", parse("[[92],42]").toString());
    assertEquals("[[92],[42]]", parse("[[92],[42]]").toString());
    assertEquals("[[92],[42]]", parse("[[92],[42]]").toString());
    assertEquals("[{\"yogurt\":[92]},{\"coconut\":[42]}]", parse("[{\"yogurt\":[92]},{\"coconut\":[42]}]").toString());
  }

  @Test
  public void arraysIllegalSyntax() {
    assertParseException(1, "Expected value", "[,]");
    assertParseException(4, "Expected ',' or ']'", "[92 42]");
    assertParseException(4, "Expected value", "[92,]");
  }

  @Test
  public void arraysIncomplete() {
    assertParseException(1, "Unexpected end of input", "[");
    assertParseException(2, "Unexpected end of input", "[ ");
    assertParseException(3, "Unexpected end of input", "[92");
    assertParseException(4, "Unexpected end of input", "[92 ");
    assertParseException(4, "Unexpected end of input", "[92,");
    assertParseException(5, "Unexpected end of input", "[92, ");
  }

  @Test
  public void objectsEmpty() {
    assertEquals("{}", parse("{}").toString());
  }

  @Test
  public void objectsSingleValue() {
    assertEquals("{\"yogurt\":92}", parse("{\"yogurt\":92}").toString());
  }

  @Test
  public void objectsMultipleValues() {
    assertEquals("{\"yogurt\":92,\"coconut\":42}", parse("{\"yogurt\":92,\"coconut\":42}").toString());
  }

  @Test
  public void objectsWhitespace() {
    assertEquals("{\"yogurt\":92,\"coconut\":42}", parse("{ \"yogurt\" : 92, \"coconut\" : 42 }").toString());
  }

  @Test
  public void objectsNested() {
    assertEquals("{\"yogurt\":{}}", parse("{\"yogurt\":{}}").toString());
    assertEquals("{\"yogurt\":{\"coconut\":42}}", parse("{\"yogurt\":{\"coconut\": 42}}").toString());
    assertEquals("{\"yogurt\":{\"coconut\":{\"chocolate\":42}}}",
      parse("{\"yogurt\":{\"coconut\": {\"chocolate\": 42}}}").toString());
    assertEquals("{\"yogurt\":[{\"coconut\":{\"chocolate\":[[42]]}}]}",
      parse("{\"yogurt\":[{\"coconut\": {\"chocolate\": [[42]]}}]}").toString());
  }

  @Test
  public void objectsIllegalSyntax() {
    assertParseException(1, "Expected name", "{,}");
    assertParseException(1, "Expected name", "{:}");
    assertParseException(1, "Expected name", "{92}");
    assertParseException(4, "Expected ':'", "{\"a\"}");
    assertParseException(5, "Expected ':'", "{\"a\" \"b\"}");
    assertParseException(5, "Expected value", "{\"a\":}");
    assertParseException(8, "Expected name", "{\"a\":92,}");
    assertParseException(8, "Expected name", "{\"a\":92,42");
  }

  @Test
  public void objectsIncomplete() {
    assertParseException(1, "Unexpected end of input", "{");
    assertParseException(2, "Unexpected end of input", "{ ");
    assertParseException(2, "Unexpected end of input", "{\"");
    assertParseException(4, "Unexpected end of input", "{\"a\"");
    assertParseException(5, "Unexpected end of input", "{\"a\" ");
    assertParseException(5, "Unexpected end of input", "{\"a\":");
    assertParseException(6, "Unexpected end of input", "{\"a\": ");
    assertParseException(7, "Unexpected end of input", "{\"a\":92");
    assertParseException(8, "Unexpected end of input", "{\"a\":92 ");
    assertParseException(8, "Unexpected end of input", "{\"a\":92,");
    assertParseException(9, "Unexpected end of input", "{\"a\":92, ");
  }

  @Test
  public void stringsEmptyStringIsAccepted() {
    assertEquals("", parse("\"\"").asString());
  }

  @Test
  public void stringsAsciiCharactersAreAccepted() {
    assertEquals(" ", parse("\" \"").asString());
    assertEquals("a", parse("\"a\"").asString());
    assertEquals("yogurt", parse("\"yogurt\"").asString());
    assertEquals("A2-D2", parse("\"A2-D2\"").asString());
    assertEquals("\u007f", parse("\"\u007f\"").asString());
  }

  @Test
  public void stringsNonAsciiCharactersAreAccepted() {
    assertEquals("Русский", parse("\"Русский\"").asString());
    assertEquals("العربية", parse("\"العربية\"").asString());
    assertEquals("日本語", parse("\"日本語\"").asString());
  }

  @Test
  public void stringsControlCharactersAreRejected() {
    /* JSON strings MUST NOT contain characters < 0x20 */
    assertParseException(3, "Expected valid string character", "\"--\n--\"");
    assertParseException(3, "Expected valid string character", "\"--\r\n--\"");
    assertParseException(3, "Expected valid string character", "\"--\t--\"");
    assertParseException(3, "Expected valid string character", "\"--\u0000--\"");
    assertParseException(3, "Expected valid string character", "\"--\u001f--\"");
  }

  @Test
  public void stringsValidEscapesAreAccepted() {
    /* Valid escapes are \" \\ \/ \b \f \n \r \t and unicode escapes */
    assertEquals(" \" ", parse("\" \\\" \"").asString());
    assertEquals(" \\ ", parse("\" \\\\ \"").asString());
    assertEquals(" / ", parse("\" \\/ \"").asString());
    assertEquals(" \u0008 ", parse("\" \\b \"").asString());
    assertEquals(" \u000c ", parse("\" \\f \"").asString());
    assertEquals(" \r ", parse("\" \\r \"").asString());
    assertEquals(" \n ", parse("\" \\n \"").asString());
    assertEquals(" \t ", parse("\" \\t \"").asString());
  }

  @Test
  public void stringsEscapeAtStart() {
    assertEquals("\\x", parse("\"\\\\x\"").asString());
  }

  @Test
  public void stringsEscapeAtEnd() {
    assertEquals("x\\", parse("\"x\\\\\"").asString());
  }

  @Test
  public void stringsIllegalEscapesAreRejected() {
    assertParseException(2, "Expected valid escape sequence", "\"\\a\"");
    assertParseException(2, "Expected valid escape sequence", "\"\\x\"");
    assertParseException(2, "Expected valid escape sequence", "\"\\000\"");
  }

  @Test
  public void stringsValidUnicodeEscapesAreAccepted() {
    assertEquals("\u0021", parse("\"\\u0021\"").asString());
    assertEquals("\u4711", parse("\"\\u4711\"").asString());
    assertEquals("\uffff", parse("\"\\uffff\"").asString());
    assertEquals("\uabcdx", parse("\"\\uabcdx\"").asString());
  }

  @Test
  public void stringsIllegalUnicodeEscapesAreRejected() {
    assertParseException(3, "Expected hexadecimal digit", "\"\\u \"");
    assertParseException(3, "Expected hexadecimal digit", "\"\\ux\"");
    assertParseException(5, "Expected hexadecimal digit", "\"\\u20 \"");
    assertParseException(6, "Expected hexadecimal digit", "\"\\u000x\"");
  }

  @Test
  public void stringsIncompleteStringsAreRejected() {
    assertParseException(1, "Unexpected end of input", "\"");
    assertParseException(4, "Unexpected end of input", "\"yogurt");
    assertParseException(5, "Unexpected end of input", "\"yogurt\\");
    assertParseException(6, "Unexpected end of input", "\"yogurt\\n");
    assertParseException(6, "Unexpected end of input", "\"yogurt\\u");
    assertParseException(7, "Unexpected end of input", "\"yogurt\\u0");
    assertParseException(9, "Unexpected end of input", "\"yogurt\\u000");
    assertParseException(10, "Unexpected end of input", "\"yogurt\\u0000");
  }

  @Test
  public void numbersInteger() {
    assertEquals(new JsonNumber("0"), parse("0"));
    assertEquals(new JsonNumber("-0"), parse("-0"));
    assertEquals(new JsonNumber("1"), parse("1"));
    assertEquals(new JsonNumber("-1"), parse("-1"));
    assertEquals(new JsonNumber("23"), parse("23"));
    assertEquals(new JsonNumber("-23"), parse("-23"));
    assertEquals(new JsonNumber("1234567890"), parse("1234567890"));
    assertEquals(new JsonNumber("123456789012345678901234567890"), parse("123456789012345678901234567890"));
  }

  @Test
  public void numbersMinusZero() {
    /* Allowed for JSON and Java */
    JsonValue value = parse("-0");
    assertEquals(0, value.asInt());
    assertEquals(0l, value.asLong());
    assertEquals(0f, value.asFloat(), 0);
    assertEquals(0d, value.asDouble(), 0);
  }

  @Test
  public void numbersDecimal() {
    assertEquals(new JsonNumber("0.23"), parse("0.23"));
    assertEquals(new JsonNumber("-0.23"), parse("-0.23"));
    assertEquals(new JsonNumber("1234567890.12345678901234567890"), parse("1234567890.12345678901234567890"));
  }

  @Test
  public void numbersWithExponent() {
    assertEquals(new JsonNumber("0.1e9"), parse("0.1e9"));
    assertEquals(new JsonNumber("0.1e9"), parse("0.1e9"));
    assertEquals(new JsonNumber("0.1E9"), parse("0.1E9"));
    assertEquals(new JsonNumber("-0.23e9"), parse("-0.23e9"));
    assertEquals(new JsonNumber("0.23e9"), parse("0.23e9"));
    assertEquals(new JsonNumber("0.23e+9"), parse("0.23e+9"));
    assertEquals(new JsonNumber("0.23e-9"), parse("0.23e-9"));
  }

  @Test
  public void numbersWithInvalidFormat() {
    assertParseException(0, "Expected value", "+1");
    assertParseException(0, "Expected value", ".1");
    assertParseException(1, "Unexpected character", "02");
    assertParseException(2, "Unexpected character", "-02");
    assertParseException(1, "Expected digit", "-x");
    assertParseException(2, "Expected digit", "1.x");
    assertParseException(2, "Expected digit", "1ex");
    assertParseException(3, "Unexpected character", "1e1x");
  }

  @Test
  public void numbersIncomplete() {
    assertParseException(1, "Unexpected end of input", "-");
    assertParseException(2, "Unexpected end of input", "1.");
    assertParseException(4, "Unexpected end of input", "1.0e");
    assertParseException(5, "Unexpected end of input", "1.0e-");
  }

  @Test
  public void nullComplete() {
    assertEquals(Json.NULL, parse("null"));
  }

  @Test
  public void nullIncomplete() {
    assertParseException(1, "Unexpected end of input", "n");
    assertParseException(2, "Unexpected end of input", "nu");
    assertParseException(3, "Unexpected end of input", "nul");
  }

  @Test
  public void nullWithIllegalCharacter() {
    assertParseException(1, "Expected 'u'", "nx");
    assertParseException(2, "Expected 'l'", "nux");
    assertParseException(3, "Expected 'l'", "nulx");
    assertParseException(4, "Unexpected character", "nullx");
  }

  private static void assertParseException(int offset, String message, final String json) {
    ParseException exception = assertException(ParseException.class, new Runnable() {
      public void run() {
        parse(json);
      }
    });
    assertEquals(offset, exception.getOffset());
    assertThat(exception.getMessage(), StringStartsWith.startsWith(message + " at"));
  }

  private static void assertParseException(int offset, int line, int column, final String json) {
    ParseException exception = assertException(ParseException.class, new Runnable() {
      public void run() {
        parse(json);
      }
    });
    assertEquals("offset", offset, exception.getOffset());
    assertEquals("line", line, exception.getLine());
    assertEquals("column", column, exception.getColumn());
  }

  private static JsonValue parse(String json) {
    try {
      return new JsonParser(json).parse();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
