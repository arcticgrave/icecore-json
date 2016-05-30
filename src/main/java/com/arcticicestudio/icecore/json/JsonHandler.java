/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Handler                    +
project   icecore-json                    +
file      JsonHandler.java                +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-30 21:44 UTC+0200       +
modified  2016-05-30 21:45 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
A handler for parser events.

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

import com.arcticicestudio.icecore.json.JsonParser.Location;

/**
 * A handler for parser events.
 *
 * @param <A> The type of handlers used for JSON arrays
 * @param <O> The type of handlers used for JSON objects
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @see JsonParser
 * @since 0.8.0
 */
public abstract class JsonHandler<A, O> {

  JsonParser parser;

  /**
   * Returns the current parser location.
   *
   * @return the current parser location
   */
  protected Location getLocation() {
    return parser.getLocation();
  }

  /**
   * Indicates the beginning of a JSON {@code null} literal in the input.
   *
   * <p>
   *   This method will be called when reading the first character of the literal.
   * </p>
   */
  public void startNull() {}

  /**
   * Indicates the end of a JSON {@code null} literal in the input.
   *
   * <p>
   *   This method will be called when reading the last character of the literal.
   * </p>
   */
  public void endNull() {}

  /**
   * Indicates the beginning of a JSON {@code true} literal in the input.
   *
   * <p>
   *   This method will be called when reading the first character of the literal.
   * </p>
   */
  public void startTrue() {}

  /**
   * Indicates the end of a JSON {@code true} literal in the input.
   *
   * <p>
   *   This method will be called when reading the last character of the literal.
   * </p>
   */
  public void endTrue() {}

  /**
   * Indicates the beginning of a JSON {@code false} literal in the input.
   *
   * <p>
   *   This method will be called when reading the first character of the literal.
   * </p>
   */
  public void startFalse() {}

  /**
   * Indicates the end of a JSON {@code false} literal in the input.
   *
   * <p>
   *   This method will be called when reading the last character of the literal.
   * </p>
   */
  public void endFalse() {}

  /**
   * Indicates the beginning of a JSON string in the input.
   *
   * <p>
   *   This method will be called when reading the opening double quote character ({@code '"'}).
   * </p>
   */
  public void startString() {}

  /**
   * Indicates the end of a JSON string in the input.
   *
   * <p>
   *   This method will be called when reading the closing double quote character ({@code '"'}).
   * </p>
   *
   * @param string The parsed string
   */
  public void endString(String string) {}

  /**
   * Indicates the beginning of a JSON number in the input.
   *
   * <p>
   *   This method will be called when reading the first character of the number.
   * </p>
   */
  public void startNumber() {}

  /**
   * Indicates the end of a JSON number in the input.
   *
   * <p>
   *   This method will be called when reading the last character of the number.
   * </p>
   *
   * @param string The parsed number string
   */
  public void endNumber(String string) {}

  /**
   * Indicates the beginning of a JSON array in the input.
   *
   * <p>
   *   This method will be called when reading the opening square bracket character ({@code '['}).
   * </p>
   * <p>
   *   This method may return an object to handle subsequent parser events for this array.
   *   This array handler will then be provided in all calls to {@link #startArrayValue(Object)},
   *   {@link #startArrayValue()}, {@link #endArrayValue(Object)}, {@link #endArrayValue()},
   *   {@link #endArray(Object)}, and {@link #endArray()} for this array.
   * </p>
   *
   * @return a handler for this array, or {@code null} if not needed
   */
  public A startArray() {
    return null;
  }

  /**
   * Indicates the end of a JSON array in the input.
   *
   * <p>
   *   This method will be called when reading the closing square bracket character ({@code ']'}).
   * </p>
   *
   * @param array The array handler returned from {@link #startArray()}, or {@code null} if not provided
   */
  public void endArray(A array) {}

  /**
   * Indicates the beginning of an array element in the input.
   *
   * <p>
   *   This method will be called when reading the first character of the element,
   *   just before the call to the {@code start} method for the specific element type ({@link #startString()},
   *   {@link #startNumber()}, etc.).
   * </p>
   *
   * @param array The array handler returned from {@link #startArray()}, or {@code null} if not provided
   */
  public void startArrayValue(A array) {}

  /**
   * Indicates the end of a JSON array element in the input.
   *
   * <p>
   *   This method will be called when reading the last character of the element value, just after the
   *   {@code end} method for the specific element type (like {@link #endString(String)}, {@link #endString()},
   *   {@link #endNumber(String)}, {@link #endNumber()} etc.).
   * </p>
   *
   * @param array The array handler returned from {@link #startArray()}, or {@code null} if not provided
   */
  public void endArrayValue(A array) {}

  /**
   * Indicates the beginning of a JSON object in the input.
   *
   * <p>
   *   This method will be called when reading the opening curly bracket character ({@code '{'}).
   * </p>
   * <p>
   *   This method may return an object to handle subsequent parser events for this object.
   *   This object handler will be provided in all calls to {@link #startObjectName(Object)},
   *   {@link #endObjectName(Object, String)}, {@link #startObjectValue(Object, String)},
   *   {@link #endObjectValue(Object, String)}, and {@link #endObject(Object)} for this object.
   * </p>
   *
   * @return a handler for this object, or {@code null} if not needed
   */
  public O startObject() {
    return null;
  }
}
