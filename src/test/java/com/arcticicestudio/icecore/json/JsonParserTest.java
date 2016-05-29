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
