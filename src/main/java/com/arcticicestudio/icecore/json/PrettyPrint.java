/*
+++++++++++++++++++++++++++++++++++++++++++
title     Pretty Print                    +
project   icecore-json                    +
file      PrettyPrint.java                +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-28 14:44 UTC+0200       +
modified  2016-05-28 14:45 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Enables human readable JSON output by inserting whitespace between values after commas and colons.

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
 * Enables human readable JSON output by inserting whitespace between values after commas and colons.
 * <p>
 *   Example:<br>
 *   <pre>
 *     jsonValue.writeTo(writer, PrettyPrint.singleLine());
 *   </pre>
 * </p>
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.5.0
 */
public class PrettyPrint extends WriterConfig {

  private final char[] indentChars;

  protected PrettyPrint(char[] indentChars) {
    this.indentChars = indentChars;
  }
}