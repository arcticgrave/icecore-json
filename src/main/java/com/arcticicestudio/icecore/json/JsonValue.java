/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Value                      +
project   icecore-json                    +
file      JsonValue.java                  +
version   0.8.0-frost.1                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
+++++++++++++++++++++++++++++++++++++++++++
*/
package com.arcticicestudio.icecore.json;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Represents a JSON value.
 *
 * <p>
 *   This can be a JSON <strong>object</strong>, an <strong>array</strong>, a <strong>number</strong>,
 *   a <strong>string</strong>, or one of the literals <strong>true</strong>, <strong>false</strong>
 *   and <strong>null</strong>.
 * </p>
 * <p>
 *   The literals <strong>true</strong>, <strong>false</strong> and <strong>null</strong> are represented by the
 *   constants {@link Json#TRUE}, {@link Json#FALSE}, and {@link Json#NULL}.
 * </p>
 * <p>
 *   JSON <strong>objects</strong> and <strong>arrays</strong> are represented by the subtypes {@link JsonObject} and
 *   {@link JsonArray}.
 *   Instances of these types can be created using the public constructors of these classes.
 * </p>
 * <p>
 *   Instances that represent JSON <strong>numbers</strong>, <strong>strings</strong> and <strong>boolean</strong>
 *   values can be created using the static factory methods {@link Json#value(String)}, {@link Json#value(long)},
 *   {@link Json#value(double)} etc.
 * </p>
 * <p>
 *   In order to find out whether an instance of this class is of a certain type, the methods {@link #isObject()},
 *   {@link #isArray()}, {@link #isString()}, {@link #isNumber()} etc. can be used.
 * </p>
 * <p>
 *   If the type of a JSON value is known, the methods {@link #asObject()}, {@link #asArray()}, {@link #asString()},
 *   {@link #asInt()} etc. can be used to get this value directly in the appropriate target type.
 * </p>
 * <p>
 *   This class is <strong>not supposed to be extended</strong> by clients.
 * </p>
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.1.0
 */
public abstract class JsonValue implements Serializable {

  /*
   * Prevents subclasses outside of this package.
   */
  JsonValue() {}

  /**
   * Detects whether this value represents a JSON object.
   * <p>
   *   If this is the case, this value is an instance of {@link JsonObject}.
   * </p>
   *
   * @return {@code true} if this value is an instance of {@link JsonObject}
   */
  public boolean isObject() {
    return false;
  }

  /**
   * Detects whether this value represents a JSON array.
   * <p>
   *   If this is the case, this value is an instance of {@link JsonArray}.
   * </p>
   *
   * @return {@code true} if this value is an instance of {@link JsonArray}
   */
  public boolean isArray() {
    return false;
  }

  /**
   * Detects whether this value represents a JSON number.
   * <p>
   *   If this is the case, this value is an instance of {@link JsonNumber}.
   * </p>
   *
   * @return {@code true} if this value represents a {@link JsonNumber}
   */
  public boolean isNumber() {
    return false;
  }

  /**
   * Detects whether this value represents a JSON string.
   * <p>
   *   If this is the case, this value is an instance of {@link JsonNumber}.
   * </p>
   *
   * @return {@code true} if this value represents a {@link JsonString}
   */
  public boolean isString() {
    return false;
  }

  /**
   * Detects whether this value represents a boolean value.
   *
   * @return {@code true} if this value represents either the JSON literal {@link Json#TRUE} or {@link Json#FALSE}
   */
  public boolean isBoolean() {
    return false;
  }

  /**
   * Detects whether this value represents the JSON literal {@code true}.
   *
   * @return {@code true} if this value represents the JSON literal {@link Json#TRUE}
   */
  public boolean isTrue() {
    return false;
  }

  /**
   * Detects whether this value represents the JSON literal {@code false}.
   *
   * @return {@code true} if this value represents the JSON literal {@link Json#FALSE}
   */
  public boolean isFalse() {
    return false;
  }

  /**
   * Detects whether this value represents the JSON literal {@code null}.
   *
   * @return {@code true} if this value represents the JSON literal {@link Json#NULL}
   */
  public boolean isNull() {
    return false;
  }

  /**
   * Returns this JSON value as {@link JsonObject}, assuming that this value represents a JSON object.
   * <p>
   *   If this is not the case, an exception is thrown.
   * </p>
   *
   * @return a {@link JsonObject} for this value
   * @throws UnsupportedOperationException if this value is not a JSON object
   */
  public JsonObject asObject() {
    throw new UnsupportedOperationException("Not an object: " + toString());
  }

  /**
   * Returns this JSON value as {@link JsonArray}, assuming that this value represents a JSON array.
   * <p>
   *   If this is not the case, an exception is thrown.
   * </p>
   *
   * @return a {@link JsonArray} for this value
   * @throws UnsupportedOperationException if this value is not a JSON array
   */
  public JsonArray asArray() {
    throw new UnsupportedOperationException("Not an array: " + toString());
  }

  /**
   * Returns this JSON value as an {@code int} value, assuming that this value represents a JSON number that can be
   * interpreted as Java {@code int}.
   * <p>
   *   If this is not the case, an exception is thrown.
   * </p>
   * <p>
   *   To be interpreted as Java {@code int}, the JSON number must neither contain an exponent nor a fraction part.
   *   Moreover, the number must be in the {@code Integer} range.
   * </p>
   *
   * @return this value as {@code int}
   * @throws UnsupportedOperationException if this value is not a JSON number
   * @throws NumberFormatException if this JSON number can not be interpreted as {@code int} value
   */
  public int asInt() {
    throw new UnsupportedOperationException("Not a number: " + toString());
  }

  /**
   * Returns this JSON value as a {@code long} value, assuming that this value represents a JSON number that can be
   * interpreted as Java {@code long}.
   * <p>
   *   If this is not the case, an exception is thrown.
   * </p>
   * <p>
   *   To be interpreted as Java {@code long}, the JSON number must neither contain an exponent nor a fraction part.
   *   Moreover, the number must be in the {@code Long} range.
   * </p>
   *
   * @return this value as {@code long}
   * @throws UnsupportedOperationException if this value is not a JSON number
   * @throws NumberFormatException if this JSON number can not be interpreted as {@code long} value
   */
  public long asLong() {
    throw new UnsupportedOperationException("Not a number: " + toString());
  }

  /**
   * Returns this JSON value as a {@code float} value, assuming that this value represents a JSON number.
   * <p>
   *   If this is not the case, an exception is thrown.
   * </p>
   * <p>
   *   If the JSON number is out of the {@code Float} range, {@link Float#POSITIVE_INFINITY} or
   *   {@link Float#NEGATIVE_INFINITY} is returned.
   * </p>
   *
   * @return this value as {@code float}
   * @throws UnsupportedOperationException if this value is not a JSON number
   */
  public float asFloat() {
    throw new UnsupportedOperationException("Not a number: " + toString());
  }

  /**
   * Returns this JSON value as a {@code double} value, assuming that this value represents a JSON number.
   * <p>
   *   If this is not the case, an exception is thrown.
   * </p>
   * <p>
   *   If the JSON number is out of the {@code Double} range, {@link Double#POSITIVE_INFINITY} or
   *   {@link Double#NEGATIVE_INFINITY} is returned.
   * </p>
   *
   * @return this value as {@code double}
   * @throws UnsupportedOperationException if this value is not a JSON number
   */
  public double asDouble() {
    throw new UnsupportedOperationException("Not a number: " + toString());
  }

  /**
   * Returns this JSON value as String, assuming that this value represents a JSON string.
   * <p>
   *   If this is not the case, an exception is thrown.
   * </p>
   *
   * @return the string represented by this value
   * @throws UnsupportedOperationException if this value is not a JSON string
   */
  public String asString() {
    throw new UnsupportedOperationException("Not a string: " + toString());
  }

  /**
   * Returns this JSON value as a {@code boolean} value, assuming that this value is either {@code true} or
   * {@code false}.
   * <p>
   *   If this is not the case, an exception is thrown.
   * </p>
   *
   * @return this value as {@code boolean}
   * @throws UnsupportedOperationException if this value is neither {@code true} or {@code false}
   */
  public boolean asBoolean() {
    throw new UnsupportedOperationException("Not a boolean: " + toString());
  }

  /**
   * Writes the JSON representation of this value to the given writer in its minimal form, without any additional
   * whitespace.
   * <p>
   *   Writing performance can be improved by using a {@link java.io.BufferedWriter BufferedWriter}.
   * </p>
   *
   * @param writer the writer to write this value to
   * @throws IOException if an I/O error occurs in the writer
   * @since 0.5.0
   */
  public void writeTo(Writer writer) throws IOException {
    writeTo(writer, WriterConfig.MINIMAL);
  }

  /**
   * Writes the JSON representation of this value to the given writer using the given formatting.
   * <p>
   *   Writing performance can be improved by using a {@link java.io.BufferedWriter BufferedWriter}.
   * </p>
   *
   * @param writer the writer to write this value to
   * @param config a configuration that controls the formatting or {@code null} for the minimal form
   * @throws IOException if an I/O error occurs in the writer
   * @since 0.5.0
   */
  public void writeTo(Writer writer, WriterConfig config) throws IOException {
    if (writer == null) {
      throw new NullPointerException("writer is null");
    }
    if (config == null) {
      throw new NullPointerException("config is null");
    }
    WritingBuffer buffer = new WritingBuffer(writer, 128);
    write(config.createWriter(buffer));
    buffer.flush();
  }

  /**
   * Returns the JSON string for this value in its minimal form, without any additional whitespace.
   * <p>
   *   The result is guaranteed to be a valid input for the method {@link #readFrom(String)} and to create a value that
   *   is <em>equal</em> to this object.
   * </p>
   *
   * @return a JSON string that represents this value
   * @since 0.5.0
   */
  @Override
  public String toString() {
    return toString(WriterConfig.MINIMAL);
  }

  /**
   * Returns the JSON string for this value using the given formatting.
   *
   * @param config a configuration that controls the formatting or {@code null} for the minimal form
   * @return a JSON string that represents this value
   * @since 0.5.0
   */
  public String toString(WriterConfig config) {
    StringWriter writer = new StringWriter();
    try {
      writeTo(writer, config);
    } catch (IOException exception) {
      // StringWriter does not throw IOExceptions
      throw new RuntimeException(exception);
    }
    return writer.toString();
  }

  /**
   * Indicates whether some other object is <em>equal to</em> this one.
   * <p>
   *   Two JsonValues are considered equal if and only if they represent the same JSON text.
   *   As a consequence, two given {@link JsonObject} may be different even though they contain the same set of names
   *   with the same values, but in a different order.
   * </p>
   *
   * @param object the reference object with which to compare
   * @return {@code true} if this object is the same as the object argument, {@code false} otherwise
   */
  @Override
  public boolean equals(Object object) {
    return super.equals(object);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * @since 0.5.0
   */
  abstract void write(JsonWriter writer) throws IOException;
}