/*
++++++++++++++++++++++++++++++++++++++++++++
title     JSON Public API                  +
project   icecore-json                     +
file      Json.java                        +
version   0.7.0                            +
author    Arctic Ice Studio                +
email     development@arcticicestudio.com  +
website   http://arcticicestudio.com       +
copyright Copyright (C) 2016               +
created   2016-05-28 16:06 UTC+0200        +
modified  2016-06-04 07:26 UTC+0200        +
++++++++++++++++++++++++++++++++++++++++++++

[Description]
Serves as the entry point to the "IceCore - JSON" public API.

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

/**
 * This class serves as the entry point to the "IceCore - JSON" public API.
 * <p>
 *   To <strong>parse</strong> a given JSON input, use the {@code parse()} methods:
 * </p>
 * <pre>
 * JsonObject object = Json.parse(string).asObject();
 * </pre>
 * <p>
 *   To <strong>create</strong> a JSON data structure to be serialized, use the methods {@code value()},
 *   {@code array()} and {@code object()}.
 *   The following example snippet will produce the JSON string <em>{"foo": 23, "bar": true}</em>:
 * </p>
 * <pre>
 * String string = Json.object().add("foo", 23).add("bar", true).toString();
 * </pre>
 * <p>
 *   To create a JSON array from a given Java array, you can use one of the {@code array()} methods with varargs
 *   parameters:
 * </p>
 * <pre>
 * String[] names = ...
 * JsonArray array = Json.array(names);
 * </pre>
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.6.0
 */
public final class Json {

  /*
 * Prevents the instantiation
 */
  private Json() {}

  /**
   * Represents the JSON literal {@code null}.
   */
  public static final JsonValue NULL = new JsonLiteral("null");

  /**
   * Represents the JSON literal {@code true}.
   */
  public static final JsonValue TRUE = new JsonLiteral("true");

  /**
   * Represents the JSON literal {@code false}.
   */
  public static final JsonValue FALSE = new JsonLiteral("false");

  /**
   * Returns a {@link JsonValue} instance that represents the given {@code int} value.
   *
   * @param value the value to get a JSON representation for
   * @return a JSON value that represents the given value
   */
  public static JsonValue value(int value) {
    return new JsonNumber(Integer.toString(value, 10));
  }

  /**
   * Returns a {@link JsonValue} instance that represents the given {@code long} value.
   *
   * @param value the value to get a JSON representation for
   * @return a JSON value that represents the given value
   */
  public static JsonValue value(long value) {
    return new JsonNumber(Long.toString(value, 10));
  }

  /**
   * Returns a {@link JsonValue} instance that represents the given {@code float} value.
   *
   * @param value the value to get a JSON representation for
   * @return a JSON value that represents the given value
   */
  public static JsonValue value(float value) {
    if (Float.isInfinite(value) || Float.isNaN(value)) {
      throw new IllegalArgumentException("Infinite and NaN values not permitted in JSON");
    }
    return new JsonNumber(cutOffPointZero(Float.toString(value)));
  }

  /**
   * Returns a {@link JsonValue} instance that represents the given {@code double} value.
   *
   * @param value the value to get a JSON representation for
   * @return a JSON value that represents the given value
   */
  public static JsonValue value(double value) {
    if (Double.isInfinite(value) || Double.isNaN(value)) {
      throw new IllegalArgumentException("Infinite and NaN values not permitted in JSON");
    }
    return new JsonNumber(cutOffPointZero(Double.toString(value)));
  }

  /**
   * Returns a {@link JsonValue} instance that represents the given string.
   *
   * @param string the string to get a JSON representation for
   * @return a JSON value that represents the given string
   */
  public static JsonValue value(String string) {
    return string == null ? NULL : new JsonString(string);
  }

  /**
   * Returns a {@link JsonValue} instance that represents the given {@code boolean} value.
   *
   * @param value the value to get a JSON representation for
   * @return a JSON value that represents the given value
   */
  public static JsonValue value(boolean value) {
    return value ? TRUE : FALSE;
  }

  /**
   * Creates a new empty {@link JsonArray}.
   * <p>
   *   This is equivalent to creating a new {@link JsonArray} using the constructor.
   * </p>
   *
   * @return a new empty JSON array of {@link JsonArray}
   */
  public static JsonValue array() {
    return new JsonArray();
  }

  /**
   * Creates a new {@link JsonArray} that contains the JSON representations of the given {@code int} values.
   *
   * @param values the values to be included in the new JSON array
   * @return a new JSON array of {@link JsonArray} that contains the given values
   */
  public static JsonArray array(int... values) {
    if (values == null) {
      throw new NullPointerException("values is null");
    }
    JsonArray array = new JsonArray();
    for (int value : values) {
      array.add(value);
    }
    return array;
  }

  /**
   * Creates a new {@link JsonArray} that contains the JSON representations of the given {@code long} values.
   *
   * @param values the values to be included in the new JSON array
   * @return a new JSON array of {@link JsonArray} that contains the given values
   */
  public static JsonArray array(long... values) {
    if (values == null) {
      throw new NullPointerException("values is null");
    }
    JsonArray array = new JsonArray();
    for (long value : values) {
      array.add(value);
    }
    return array;
  }

  /**
   * Creates a new {@link JsonArray} that contains the JSON representations of the given {@code float} values.
   *
   * @param values the values to be included in the new JSON array
   * @return a new JSON array of {@link JsonArray} that contains the given values
   */
  public static JsonArray array(float... values) {
    if (values == null) {
      throw new NullPointerException("values is null");
    }
    JsonArray array = new JsonArray();
    for (float value : values) {
      array.add(value);
    }
    return array;
  }

  /**
   * Creates a new {@link JsonArray} that contains the JSON representations of the given {@code double} values.
   *
   * @param values the values to be included in the new JSON array
   * @return a new JSON array of {@link JsonArray} that contains the given values
   */
  public static JsonArray array(double... values) {
    if (values == null) {
      throw new NullPointerException("values is null");
    }
    JsonArray array = new JsonArray();
    for (double value : values) {
      array.add(value);
    }
    return array;
  }

  /**
   * Creates a new {@link JsonArray} that contains the JSON representations of the given {@code boolean} values.
   *
   * @param values the values to be included in the new JSON array
   * @return a new JSON array of {@link JsonArray} that contains the given values
   */
  public static JsonArray array(boolean... values) {
    if (values == null) {
      throw new NullPointerException("values is null");
    }
    JsonArray array = new JsonArray();
    for (boolean value : values) {
      array.add(value);
    }
    return array;
  }

  /**
   * Creates a new {@link JsonArray} that contains the JSON representations of the given strings.
   *
   * @param strings the strings to be included in the new JSON array
   * @return a new JSON array of {@link JsonArray} that contains the given strings
   */
  public static JsonArray array(String... strings) {
    if (strings == null) {
      throw new NullPointerException("values is null");
    }
    JsonArray array = new JsonArray();
    for (String value : strings) {
      array.add(value);
    }
    return array;
  }

  /**
   * Creates a new empty {@link JsonObject}.
   * <p>
   *   This is equivalent to creating a new {@link JsonObject} using the constructor.
   * </p>
   *
   * @return a new empty JSON object of {@link JsonObject}
   */
  public static JsonObject object() {
    return new JsonObject();
  }

  /**
   * Parses the given input string as JSON.
   * The input must contain a valid JSON value, optionally padded with whitespace.
   *
   * @param string the input string, must be valid JSON
   * @return a value that represents the parsed JSON
   * @throws ParseException if the input is not valid JSON
   */
  public static JsonValue parse(String string) {
    if (string == null) {
      throw new NullPointerException("string is null");
    }
    DefaultHandler handler = new DefaultHandler();
    new JsonParser(handler).parse(string);
    return handler.getValue();
  }

  /**
   * Reads the entire input from the given reader and parses it as JSON.
   * The input must contain a valid JSON value, optionally padded with whitespace.
   * <p>
   *   Characters are read in chunks into an input buffer.
   *   Hence, wrapping a reader in an additional {@code BufferedReader} likely won't improve reading performance.
   * </p>
   *
   * @param reader the reader to read the JSON value from
   * @return a value that represents the parsed JSON
   * @throws IOException if an I/O error occurs in the reader
   * @throws ParseException if the input is not valid JSON
   */
  public static JsonValue parse(Reader reader) throws IOException {
    if (reader == null) {
      throw new NullPointerException("reader is null");
    }
    DefaultHandler handler = new DefaultHandler();
    new JsonParser(handler).parse(reader);
    return handler.getValue();
  }

  /**
   * Cuts of the the point and a the following zero digit ({@code .0}).
   *
   * @param string the string which contains the section that is to be cut
   * @return the cut off string
   */
  private static String cutOffPointZero(String string) {
    if (string.endsWith(".0")) {
      return string.substring(0, string.length() - 2);
    }
    return string;
  }

  static class DefaultHandler extends JsonHandler<JsonArray, JsonObject> {

    protected JsonValue value;

    @Override
    public JsonArray startArray() {
      return new JsonArray();
    }

    @Override
    public JsonObject startObject() {
      return new JsonObject();
    }

    @Override
    public void endNull() {
      value = NULL;
    }

    /**
     * @since 0.8.0
     */
    @Override
    public void endBoolean(boolean bool) {
      value = bool ? TRUE : FALSE;
    }

    @Override
    public void endString(String string) {
      value = new JsonString(string);
    }

    public void endNumber(String string) {
      value = new JsonNumber(string);
    }

    @Override
    public void endArray(JsonArray array) {
      value = array;
    }

    @Override
    public void endObject(JsonObject object) {
      value = object;
    }

    @Override
    public void endArrayValue(JsonArray array) {
      array.add(value);
    }

    @Override
    public void endObjectValue(JsonObject object, String name) {
      object.add(name, value);
    }

    JsonValue getValue() {
      return value;
    }
  }
}