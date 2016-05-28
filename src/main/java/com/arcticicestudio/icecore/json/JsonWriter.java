/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Writer                     +
project   icecore-json                    +
file      JsonWriter.java                 +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-28 14:24 UTC+0200       +
modified  2016-05-28 14:25 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Writes the generated JSON object.

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
StackOverflow
  (http://stackoverflow.com/questions/2965293/javascript-parse-error-on-u2028-unicode-character)
*/
package com.arcticicestudio.icecore.json;

import java.io.Writer;

/**
 * Writes the generated JSON object.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.5.0
 */

class JsonWriter {

  protected final Writer writer;

  JsonWriter(Writer writer) {
    this.writer = writer;
  }

  protected void writeLiteral(String value) throws IOException {
    writer.write(value);
  }
}