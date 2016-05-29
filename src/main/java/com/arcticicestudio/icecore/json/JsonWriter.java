/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Writer                     +
project   icecore-json                    +
file      JsonWriter.java                 +
version   0.7.0                           +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-28 14:24 UTC+0200       +
modified  2016-05-28 14:36 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Writes the generated JSON object.

[Copyright]
Copyright (C) 2016 Arctic Ice Studio <development@arcticicestudio.com>

[References]
JSON
  (http://json.org)
ECMA-404 1st Edition (October 2013)
  (http://www.ecma-international.org/publications/files/ECMA-ST/ECMA-404.pdf)
Java 8 API Documentation
  (https://docs.oracle.com/javase/8/docs/api/)
Arctic Versioning Specification (ArcVer)
  (http://specs.arcticicestudio.com/arcver)
StackOverflow
  (http://stackoverflow.com/questions/2965293/javascript-parse-error-on-u2028-unicode-character)
*/
package com.arcticicestudio.icecore.json;

import java.io.IOException;
import java.io.Writer;

/**
 * Writes the generated JSON object.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.5.0
 */

class JsonWriter {

  private static final int CONTROL_CHARACTERS_END = 0x001f;

  private static final char[] QUOT_CHARS = {'\\', '"'};
  private static final char[] BS_CHARS = {'\\', '\\'};
  private static final char[] LF_CHARS = {'\\', 'n'};
  private static final char[] CR_CHARS = {'\\', 'r'};
  private static final char[] TAB_CHARS = {'\\', 't'};
  /*
   * In JavaScript, "U+2028" and "U+2029" characters count as line endings and must be encoded.
   *
   * See the related StackOverflow thread for more information:
   * http://stackoverflow.com/questions/2965293/javascript-parse-error-on-u2028-unicode-character
   */
  private static final char[] UNICODE_2028_CHARS = {'\\', 'u', '2', '0', '2', '8'};
  private static final char[] UNICODE_2029_CHARS = {'\\', 'u', '2', '0', '2', '9'};
  private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    'a', 'b', 'c', 'd', 'e', 'f'};

  protected final Writer writer;

  JsonWriter(Writer writer) {
    this.writer = writer;
  }

  protected void writeLiteral(String value) throws IOException {
    writer.write(value);
  }

  protected void writeNumber(String string) throws IOException {
    writer.write(string);
  }

  protected void writeString(String string) throws IOException {
    writer.write('"');
    writeJsonString(string);
    writer.write('"');
  }

  protected void writeArrayOpen() throws IOException {
    writer.write('[');
  }

  protected void writeArrayClose() throws IOException {
    writer.write(']');
  }

  protected void writeArraySeparator() throws IOException {
    writer.write(',');
  }

  protected void writeObjectOpen() throws IOException {
    writer.write('{');
  }

  protected void writeObjectClose() throws IOException {
    writer.write('}');
  }

  protected void writeMemberName(String name) throws IOException {
    writer.write('"');
    writeJsonString(name);
    writer.write('"');
  }

  protected void writeMemberSeparator() throws IOException {
    writer.write(':');
  }

  protected void writeObjectSeparator() throws IOException {
    writer.write(',');
  }

  protected void writeJsonString(String string) throws IOException {
    int length = string.length();
    int start = 0;
    for (int index = 0; index < length; index++) {
      char[] replacement = getReplacementChars(string.charAt(index));
      if (replacement != null) {
        writer.write(string, start, index - start);
        writer.write(replacement);
        start = index + 1;
      }
    }
    writer.write(string, start, length - start);
  }

  private static char[] getReplacementChars(char ch) {
    if (ch > '\\') {
      if (ch < '\u2028' || ch > '\u2029') {
        /*
         * The lower range contains 'a' .. 'z'.
         * Only 2 checks required.
         */
        return null;
      }
      return ch == '\u2028' ? UNICODE_2028_CHARS : UNICODE_2029_CHARS;
    }
    if (ch == '\\') {
      return BS_CHARS;
    }
    if (ch > '"') {
      /*
       * This range contains '0' .. '9' and 'A' .. 'Z'.
       * Need 3 checks to get here.
       */
      return null;
    }
    if (ch == '"') {
      return QUOT_CHARS;
    }
    if (ch > CONTROL_CHARACTERS_END) {
      return null;
    }
    if (ch == '\n') {
      return LF_CHARS;
    }
    if (ch == '\r') {
      return CR_CHARS;
    }
    if (ch == '\t') {
      return TAB_CHARS;
    }
    return new char[] {'\\', 'u', '0', '0', HEX_DIGITS[ch >> 4 & 0x000f], HEX_DIGITS[ch & 0x000f]};
  }
}