/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Array                      +
project   icecore-json                    +
file      JsonArray.java                  +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-28 11:42 UTC+0200       +
modified  2016-05-28 11:43 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Represents a JSON array, an ordered collection of JSON values.

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a JSON array, an ordered collection of JSON values.
 * <p>
 *   Elements can be added using the {@code add(...)} methods which accept instances of {@link JsonValue}, strings,
 *   primitive numbers and boolean values.
 *   To replace an element of an array, use the {@code set(int, ...)} methods.
 * </p>
 * <p>
 *   Elements can be accessed by their index using {@link #get(int)}.
 *   This class also supports iterating over the elements in document order using an {@link #iterator()} or an
 *   enhanced for loop:
 * </p>
 * <pre>
 * for (JsonValue value : jsonArray) {
 *   ...
 * }
 * </pre>
 * <p>
 *   An equivalent {@link List} can be obtained from the method {@link #values()}.
 * </p>
 * <p>
 *   Note that this class is <strong>not thread-safe!</strong><br>
 *   If multiple threads access a {@code JsonArray} instance concurrently, while at least one of these threads modifies
 *   the contents of this array, access to the instance must be synchronized externally.
 *   Failure to do so may lead to an inconsistent state.
 * </p>
 * <p>
 *   This class is <strong>not supposed to be extended</strong> by clients.
 * </p>
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.1.0
 */
public class JsonArray extends JsonValue implements Iterable<JsonValue> {

  private final List<JsonValue> values;

  /**
   * Creates a new empty JsonArray.
   */
  public JsonArray() {
    values = new ArrayList<JsonValue>();
  }

  /**
   * Creates a new JsonArray with the contents of the specified JSON array.
   *
   * @param array the JsonArray to get the initial contents from which <strong>MUST NOT</strong> be {@code null}.
   */
  public JsonArray(JsonArray array) {
    this(array, false);
  }

  /**
   * Initializes the contents of the specified JSON array with a modifiability state based on the boolean value of the
   * {@code unmodifiable} parameter.
   *
   * @param array the JsonArray to get the initial contents from which <strong>MUST NOT</strong> be {@code null}.
   * @param unmodifiable the state of the modifiability
   */
  private JsonArray(JsonArray array, boolean unmodifiable) {
    if (array == null) {
      throw new NullPointerException("array is null");
    }
    if (unmodifiable) {
      values = Collections.unmodifiableList(array.values);
    } else {
      values = new ArrayList<JsonValue>(array.values);
    }
  }
}