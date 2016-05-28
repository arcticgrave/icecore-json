/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Parser                     +
project   icecore-json                    +
file      JsonParser.java                 +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-28 13:49 UTC+0200       +
modified  2016-05-28 13:50 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
A JSON parser.

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
*/
package com.arcticicestudio.icecore.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * A JSON parser.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.4.0
 */
class JsonParser {

  private static final int MIN_BUFFER_SIZE = 10;
  private static final int DEFAULT_BUFFER_SIZE = 1024;

  private final Reader reader;
  private final char[] buffer;
  private int line;
  private int captureStart;
  private int current;

  /*
   * |                      bufferOffset
   *                        v
   * [a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t]        < input
   *                       [l|m|n|o|p|q|r|s|t|?|?]    < buffer
   *                          ^               ^
   *                       |  index           fill
   */

  JsonParser(String string) {
    this(new StringReader(string),
      Math.max(MIN_BUFFER_SIZE, Math.min(DEFAULT_BUFFER_SIZE, string.length())));
  }

  JsonParser(Reader reader) {
    this(reader, DEFAULT_BUFFER_SIZE);
  }

  JsonParser(Reader reader, int buffersize) {
    this.reader = reader;
    buffer = new char[buffersize];
    line = 1;
    captureStart = -1;
  }

  JsonValue parse() throws IOException {
    read();
    skipWhiteSpace();
    JsonValue result = readValue();
    skipWhiteSpace();
    if (!isEndOfText()) {
      throw error("Unexpected character");
    }
    return result;
  }

  private JsonValue readValue() throws IOException {
    switch (current) {
      case 'n':
        return readNull();
      case 't':
        return readTrue();
      case 'f':
        return readFalse();
      case '"':
        return readString();
      case '[':
        return readArray();
      case '{':
        return readObject();
      case '-':
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
        return readNumber();
      default:
        throw expected("value");
    }
  }

  private JsonArray readArray() throws IOException {
    read();
    JsonArray array = new JsonArray();
    skipWhiteSpace();
    if (readChar(']')) {
      return array;
    }
    do {
      skipWhiteSpace();
      array.add(readValue());
      skipWhiteSpace();
    } while (readChar(','));
    if (!readChar(']')) {
      throw expected("',' or ']'");
    }
    return array;
  }

  private JsonObject readObject() throws IOException {
    read();
    JsonObject object = new JsonObject();
    skipWhiteSpace();
    if (readChar('}')) {
      return object;
    }
    do {
      skipWhiteSpace();
      String name = readName();
      skipWhiteSpace();
      if (!readChar(':')) {
        throw expected("':'");
      }
      skipWhiteSpace();
      object.add(name, readValue());
      skipWhiteSpace();
    } while (readChar(','));
    if (!readChar('}')) {
      throw expected("',' or '}'");
    }
    return object;
  }

  private String readName() throws IOException {
    if (current != '"') {
      throw expected("name");
    }
    return readStringInternal();
  }

  private JsonValue readNull() throws IOException {
    read();
    readRequiredChar('u');
    readRequiredChar('l');
    readRequiredChar('l');
    return Json.NULL;
  }

  private JsonValue readTrue() throws IOException {
    read();
    readRequiredChar('r');
    readRequiredChar('u');
    readRequiredChar('e');
    return Json.TRUE;
  }

  private JsonValue readFalse() throws IOException {
    read();
    readRequiredChar('a');
    readRequiredChar('l');
    readRequiredChar('s');
    readRequiredChar('e');
    return Json.FALSE;
  }
}