/*
+++++++++++++++++++++++++++++++++++++++++++
title     Writer Configuration            +
project   icecore-json                    +
file      WriterConfig.java               +
version   0.8.0-frost.1                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
+++++++++++++++++++++++++++++++++++++++++++
*/
package com.arcticicestudio.icecore.json;

import java.io.Writer;

/**
 * Controls the formatting of the JSON output with one of the available constants.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.5.0
 */
public abstract class WriterConfig {

  /**
   * Write JSON in its minimal form, without any additional whitespace.
   * <p>
   *   This is the default configuration.
   * </p>
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