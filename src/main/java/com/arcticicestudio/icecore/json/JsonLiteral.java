/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Literal                    +
project   icecore-json                    +
file      JsonLiteral.java                +
version   0.7.0                           +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-28 13:40 UTC+0200       +
modified  2016-05-28 15:15 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Represents a JSON literal.

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