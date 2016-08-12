/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Handler                    +
project   icecore-json                    +
file      JsonHandler.java                +
version   0.8.0-frost.1                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
+++++++++++++++++++++++++++++++++++++++++++
*/
package com.arcticicestudio.icecore.json;

/**
 * A handler for parser events.
 * <p>
 *   Instances of this class can be given to a {@link JsonParser}.
 *   The parser will then call the methods of the given handler while reading the input.
 * </p>
 *
 * <p>
 *   The default implementations of these methods do nothing.
 *   Subclasses may override only those methods they are interested in.
 *   They can use {@link #getLocation()} to access the current character position of the parser at any point.
 *   The {@code start*} methods will be called while the location points to the first character of the parsed element.
 *   The {@code end*} methods will be called while the location points to the character position that directly follows
 *   the last character of the parsed element.
 *   Example:
 * <pre>
 * ["lorem ipsum"]
 *  ^            ^
 *  startString  endString
 * </pre>
 * </p>
 * <p>
 *   Subclasses that build an object representation of the parsed JSON can return arbitrary handler objects for
 *   JSON arrays and JSON objects in {@link #startArray()} and {@link #startObject()}.
 *   These handler objects will then be provided in all subsequent parser events for this particular array or object.
 *   They can be used to keep track the elements of a JSON array or object.
 * </p>
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
   * Indicates the beginning of a {@code null} literal in the JSON input.
   * <p>
   *   This method will be called when reading the first character of the literal.
   * </p>
   */
  public void startNull() {}

  /**
   * Indicates the end of a {@code null} literal in the JSON input.
   * <p>
   *   This method will be called after reading the last character of the literal.
   * </p>
   */
  public void endNull() {}

  /**
   * Indicates the beginning of a boolean literal ({@code true} or {@code false}) in the JSON input.
   * <p>
   *   This method will be called when reading the first character of the literal.
   * </p>
   *
   * @since 0.8.0
   */
  public void startBoolean() {}

  /**
   * Indicates the end of a boolean literal ({@code true} or {@code false}) in the JSON input.
   * <p>
   *   This method will be called after reading the last character of the literal.
   * </p>
   *
   * @param value The parsed boolean value
   * @since 0.8.0
   */
  public void endBoolean(boolean value) {}

  /**
   * Indicates the beginning of a string in the JSON input.
   * <p>
   *   This method will be called when reading the opening double quote character ({@code '&quot;'}).
   * </p>
   */
  public void startString() {}

  /**
   * Indicates the end of a string in the JSON input.
   * <p>
   *   This method will be called after reading the closing double quote character ({@code '&quot;'}).
   * </p>
   *
   * @param string The parsed string
   */
  public void endString(String string) {}

  /**
   * Indicates the beginning of a number in the JSON input.
   * <p>
   *   This method will be called when reading the first character of the number.
   * </p>
   */
  public void startNumber() {}

  /**
   * Indicates the end of a number in the JSON input.
   * <p>
   *   This method will be called after reading the last character of the number.
   * </p>
   *
   * @param string The parsed number string
   */
  public void endNumber(String string) {}

  /**
   * Indicates the beginning of an array in the JSON input.
   * <p>
   *   This method will be called when reading the opening square bracket character ({@code '['}).
   * </p>
   * <p>
   *   This method may return an object to handle subsequent parser events for this array.
   *   This array handler will then be provided in all calls to {@link #startArrayValue(Object)},
   *   {@link #endArrayValue(Object)}, and {@link #endArray(Object)} for this array.
   * </p>
   *
   * @return a handler for this array, or {@code null} if not needed
   */
  public A startArray() {
    return null;
  }

  /**
   * Indicates the end of an array in the JSON input.
   * <p>
   *   This method will be called after reading the closing square bracket character ({@code ']'}).
   * </p>
   *
   * @param array The array handler returned from {@link #startArray()}, or {@code null} if not provided
   */
  public void endArray(A array) {}

  /**
   * Indicates the beginning of an array element in the JSON input.
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
   * Indicates the end of an array element in the JSON input.
   * <p>
   *   This method will be called after reading the last character of the element value, just after the
   *   {@code end} method for the specific element type (like {@link #endString(String)},
   *   {@link #endNumber(String)} etc.).
   * </p>
   *
   * @param array The array handler returned from {@link #startArray()}, or {@code null} if not provided
   */
  public void endArrayValue(A array) {}

  /**
   * Indicates the beginning of an object in the JSON input.
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

  /**
   * Indicates the end of an object in the JSON input.
   * <p>
   *   This method will be called after reading the closing curly bracket character ({@code '}'}).
   * </p>
   *
   * @param object The object handler returned from {@link #startObject()}, or {@code null} if not provided
   */
  public void endObject(O object) {}

  /**
   * Indicates the beginning of the name of an object member in the JSON input.
   * <p>
   *   This method will be called when reading the opening quote character ('&quot;') of the member name.
   * </p>
   *
   * @param object The object handler returned from {@link #startObject()}, or {@code null} if not provided
   */
  public void startObjectName(O object) {}

  /**
   * Indicates the end of an object member name in the JSON input.
   * <p>
   *   This method will be called after reading the closing quote character ({@code '"'}) of the member name.
   * </p>
   *
   * @param object The object handler returned from {@link #startObject()}, or {@code null} if not provided
   * @param name The parsed member name
   */
  public void endObjectName(O object, String name) {}

  /**
   * Indicates the beginning of the name of an object member in the JSON input.
   * <p>
   *   This method will be called when reading the opening quote character ('&quot;') of the member name.
   * </p>
   *
   * @param object The object handler returned from {@link #startObject()}, or {@code null} if not provided
   * @param name The member name
   */
  public void startObjectValue(O object, String name) {}

  /**
   * Indicates the end of an object member value in the JSON input.
   * <p>
   *   This method will be called after reading the last character of the member value, just after the {@code end}
   *   method for the specific member type (like {@link #endString(String)}, {@link #endNumber(String)}, etc.).
   * </p>
   *
   * @param object The object handler returned from {@link #startObject()}, or {@code null} if not provided
   * @param name The parsed member name
   */
  public void endObjectValue(O object, String name) {}
}
