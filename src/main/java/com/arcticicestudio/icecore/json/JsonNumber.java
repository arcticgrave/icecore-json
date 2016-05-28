/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Number                     +
project   icecore-json                    +
file      JsonNumber.java                 +
version   0.2.0                           +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-28 11:06 UTC+0200       +
modified  2016-05-28 11:07 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Represents a JSON number.

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