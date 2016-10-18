/*
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
title      JSON Writer Test                                +
project    icecore-json                                    +
version    0.8.0-frost.1                                   +
repository https://github.com/arcticicestudio/icecore-json +
author     Arctic Ice Studio                               +
email      development@arcticicestudio.com                 +
copyright  Copyright (C) 2016                              +
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*/
package com.arcticicestudio.icecore.json;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Tests the JSON writer class {@link JsonWriter}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class JsonWriterTest {

  private StringWriter output;
  private JsonWriter writer;

  @Before
  public void setUp() {
    output = new StringWriter();
    writer = new JsonWriter(output);
  }

  @Test
  public void writeLiteral() throws IOException {
    writer.writeLiteral("yogurt");
    assertEquals("yogurt", output.toString());
  }

  @Test
  public void writeNumber() throws IOException {
    writer.writeNumber("92");
    assertEquals("92", output.toString());
  }

  @Test
  public void writeStringEmpty() throws IOException {
    writer.writeString("");
    assertEquals("\"\"", output.toString());
  }

  @Test
  public void writeStingEscapesBackslashes() throws IOException {
    writer.writeString("yogurt\\coconut");
    assertEquals("\"yogurt\\\\coconut\"", output.toString());
  }

  @Test
  public void writeArrayParts() throws IOException {
    writer.writeArrayOpen();
    writer.writeArraySeparator();
    writer.writeArrayClose();
    assertEquals("[,]", output.toString());
  }

  @Test
  public void writeObjectParts() throws IOException {
    writer.writeObjectOpen();
    writer.writeMemberSeparator();
    writer.writeObjectSeparator();
    writer.writeObjectClose();
    assertEquals("{:,}", output.toString());
  }

  @Test
  public void writeMemberNameEmpty() throws IOException {
    writer.writeMemberName("");
    assertEquals("\"\"", output.toString());
  }

  @Test
  public void writeMemberNameEscapesBackslashes() throws IOException {
    writer.writeMemberName("yogurt\\coconut");
    assertEquals("\"yogurt\\\\coconut\"", output.toString());
  }

  @Test
  public void escapesQuotes() throws IOException {
    writer.writeString("a\"b");
    assertEquals("\"a\\\"b\"", output.toString());
  }

  @Test
  public void escapesEscapedQuotes() throws IOException {
    writer.writeString("yogurt\\\"coconut");
    assertEquals("\"yogurt\\\\\\\"coconut\"", output.toString());
  }

  @Test
  public void escapesNewLine() throws IOException {
    writer.writeString("yogurt\ncoconut");
    assertEquals("\"yogurt\\ncoconut\"", output.toString());
  }

  @Test
  public void escapesWindowsNewLine() throws IOException {
    writer.writeString("yogurt\r\ncoconut");
    assertEquals("\"yogurt\\r\\ncoconut\"", output.toString());
  }

  @Test
  public void escapesTabs() throws IOException {
    writer.writeString("yogurt\tcoconut");
    assertEquals("\"yogurt\\tcoconut\"", output.toString());
  }

  @Test
  public void escapesSpecialCharacters() throws IOException {
    writer.writeString("yogurt\u2028coconut\u2029");
    assertEquals("\"yogurt\\u2028coconut\\u2029\"", output.toString());
  }

  @Test
  public void escapesZeroCharacter() throws IOException {
    writer.writeString(string('y', 'o', 'g', 'u', 'r', 't', (char)0, 'c', 'o', 'c', 'o', 'n', 'u', 't'));
    assertEquals("\"yogurt\\u0000coconut\"", output.toString());
  }

  @Test
  public void escapesEscapeCharacter() throws IOException {
    writer.writeString(string('y', 'o', 'g', 'u', 'r', 't', (char)27, 'c', 'o', 'c', 'o', 'n', 'u', 't'));
    assertEquals("\"yogurt\\u001bcoconut\"", output.toString());
  }

  @Test
  public void escapesControlCharacters() throws IOException {
    writer.writeString(string((char)1, (char)8, (char)15, (char)16, (char)31));
    assertEquals("\"\\u0001\\u0008\\u000f\\u0010\\u001f\"", output.toString());
  }

  @Test
  public void escapesFirstChar() throws IOException {
    writer.writeString(string('\\', 'x'));
    assertEquals("\"\\\\x\"", output.toString());
  }

  @Test
  public void escapesLastChar() throws IOException {
    writer.writeString(string('x', '\\'));
    assertEquals("\"x\\\\\"", output.toString());
  }

  private static String string(char... chars) {
    return String.valueOf(chars);
  }
}
