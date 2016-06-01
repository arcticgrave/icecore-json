/*
++++++++++++++++++++++++++++++++++++++++++++
title     Parse Exception                  +
project   icecore-json                     +
file      ParseException.java              +
version   0.7.0                            +
author    Arctic Ice Studio                +
email     development@arcticicestudio.com  +
website   http://arcticicestudio.com       +
copyright Copyright (C) 2016               +
created   2016-05-28 14:12 UTC+0200        +
modified  2016-06-01 22:30 UTC+0200        +
++++++++++++++++++++++++++++++++++++++++++++

[Description]
An unchecked exception to indicate that an input does not qualify as valid JSON.

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
 * An unchecked exception to indicate that an input does not qualify as valid JSON.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.4.0
 */
public class ParseException extends RuntimeException {

  private final int offset;
  private final int line;
  private final int column;

  ParseException(String message, int offset, int line, int column) {
    super(message + " at " + line + ":" + column);
    this.offset = offset;
    this.line = line;
    this.column = column;
  }

  /**
   * Returns the absolute character index at which the error occurred.
   * The offset of the first character of a document is 0.
   *
   * @return the character offset at which the error occurred, will be &gt;= 0
   */
  public int getOffset() {
    return offset;
  }

  /**
   * Returns the line number in which the error occurred.
   * The number of the first line is 1
   *
   * @return the line in which the error occurred, will be &gt;= 1
   */
  public int getLine() {
    return line;
  }

  /**
   * Returns the column number at which the error occurred, i.e. the number of the character in its line.
   * The number of the first character of a line is 1.
   *
   * @return the column in which the error occurred, will be &gt;= 1
   */
  public int getColumn() {
    return column;
  }
}