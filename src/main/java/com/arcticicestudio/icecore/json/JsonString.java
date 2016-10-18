/*
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
title      JSON String                                     +
project    icecore-json                                    +
version    0.8.0-frost.1                                   +
repository https://github.com/arcticicestudio/icecore-json +
author     Arctic Ice Studio                               +
email      development@arcticicestudio.com                 +
copyright  Copyright (C) 2016                              +
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*/
package com.arcticicestudio.icecore.json;

import java.io.IOException;

/**
 * Represents a JSON string.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.1.0
 */
class JsonString extends JsonValue {

  private final String string;

  /**
   * Initializes the JSON string value.
   *
   * @param string the JSON string to be literalized
   */
  JsonString(String string) {
    if (string == null) {
      throw new NullPointerException("string is null");
    }
    this.string = string;
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.5.0
   */
  @Override
  void write(JsonWriter writer) throws IOException {
    writer.writeString(string);
  }

  @Override
  public boolean isString() {
    return true;
  }

  @Override
  public String asString() {
    return string;
  }

  @Override
  public int hashCode() {
    return string.hashCode();
  }

  @Override
  public String toString() {
    return string;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null) {
      return false;
    }
    if (getClass() != object.getClass()) {
      return false;
    }
    JsonString other = (JsonString)object;
    return string.equals(other.string);
  }
}