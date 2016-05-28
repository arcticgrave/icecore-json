/*
+++++++++++++++++++++++++++++++++++++++++++++++++
title     Writer Configuration                  +
project   icecore-json                          +
file      WriterConfig.java                     +
version   0.6.0                                 +
author    Arctic Ice Studio                     +
email     development@arcticicestudio.com       +
website   http://arcticicestudio.com            +
copyright Copyright (C) 2016                    +
created   2016-05-28 14:39 UTC+0200             +
modified  2016-05-28 14:44 UTC+0200             +
+++++++++++++++++++++++++++++++++++++++++++++++++

[Description]
Controls the formatting of the JSON output.

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

import java.io.Writer;

/**
 * Controls the formatting of the JSON output.
 * Use one of the available constants.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.5.0
 */
public abstract class WriterConfig {

  /**
   * Write JSON in its minimal form, without any additional whitespace.
   * This is the default.
   */
  public static WriterConfig MINIMAL = new WriterConfig() {
    @Override
    JsonWriter createWriter(Writer writer) {
      return new JsonWriter(writer);
    }
  };

  /**
   * Write JSON in pretty-print, with each value on a separate line and an indentation of two spaces.
   */
  public static WriterConfig PRETTY_PRINT = PrettyPrint.indentWithSpaces(2);

  abstract JsonWriter createWriter(Writer writer);
}