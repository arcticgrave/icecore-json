/*
++++++++++++++++++++++++++++++++++++++++++++
title     JSON Public API                  +
project   icecore-json                     +
file      Json.java                        +
version                                    +
author    Arctic Ice Studio                +
email     development@arcticicestudio.com  +
website   http://arcticicestudio.com       +
copyright Copyright (C) 2016               +
created   2016-05-28 16:06 UTC+0200        +
modified  2016-05-28 16:07 UTC+0200        +
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
}