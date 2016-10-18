/*
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
title      JSON Literal                                    +
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
 * Represents a JSON literal.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.3.0
 */
class JsonLiteral extends JsonValue {

  private final String value;
  private final boolean isNull;
  private final boolean isTrue;
  private final boolean isFalse;

  /**
   * Initializes the JSON literal values.
   *
   * @param value the JSON literal to be literalized
   */
  JsonLiteral(String value) {
    this.value = value;
    isNull = "null".equals(value);
    isTrue = "true".equals(value);
    isFalse = "false".equals(value);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.5.0
   */
  @Override
  void write(JsonWriter writer) throws IOException {
    writer.writeLiteral(value);
  }

  @Override
  public boolean isNull() {
    return isNull;
  }

  @Override
  public boolean isTrue() {
    return isTrue;
  }

  @Override
  public boolean isFalse() {
    return isFalse;
  }

  @Override
  public boolean isBoolean() {
    return isTrue || isFalse;
  }

  @Override
  public boolean asBoolean() {
    return isNull ? super.asBoolean() : isTrue;
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public int hashCode() {
    return value.hashCode();
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
    JsonLiteral other = (JsonLiteral)object;
    return value.equals(other.value);
  }
}