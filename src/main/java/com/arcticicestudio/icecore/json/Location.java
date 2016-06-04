/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Parser Location            +
project   icecore-json                    +
file      Location.java                   +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-06-04 07:51 UTC+0200       +
modified  2016-06-04 07:56 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
An immutable object that represents a location in the parsed text.

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
 * An immutable object that represents a location in the parsed text.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.8.0
 */
public class Location {

  /**
   * The absolute character index, starting at 0.
   */
  public final int offset;

  /**
   * The line number, starting at 1.
   */
  public final int line;

  /**
   * The column number, starting at 1.
   */
  public final int column;

  Location(int offset, int line, int column) {
    this.offset = offset;
    this.column = column;
    this.line = line;
  }

  @Override
  public String toString() {
    return line + ":" + column;
  }

  @Override
  public int hashCode() {
    return offset;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Location other = (Location)obj;
    return offset == other.offset && column == other.column && line == other.line;
  }
}