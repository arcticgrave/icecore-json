/*
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
title      JSON Number                                     +
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
 * Represents a JSON number.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.1.0
 */
class JsonNumber extends JsonValue {

  private final String string;

  /**
   * Initializes the JSON number value.
   *
   * @param string the JSON number to be literalized
   */
  JsonNumber(String string) {
    if (string == null) {
      throw new NullPointerException("string is null");
    }
    this.string = string;
  }

  @Override
  public boolean isNumber() {
    return true;
  }

  @Override
  public int asInt() {
    return Integer.parseInt(string, 10);
  }

  @Override
  public long asLong() {
    return Long.parseLong(string, 10);
  }

  @Override
  public float asFloat() {
    return Float.parseFloat(string);
  }

  @Override
  public double asDouble() {
    return Double.parseDouble(string);
  }

  @Override
  public String toString() {
    return string;
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.5.0
   */
  @Override
  void write(JsonWriter writer) throws IOException {
    writer.writeNumber(string);
  }

  @Override
  public int hashCode() {
    return string.hashCode();
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
    JsonNumber other = (JsonNumber)object;
    return string.equals(other.string);
  }
}