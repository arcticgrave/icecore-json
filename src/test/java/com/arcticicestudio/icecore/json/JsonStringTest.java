/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON String Test                +
project   icecore-json                    +
file      JsonStringTest.java             +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-28 20:35 UTC+0200       +
modified  2016-05-28 20:36 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Tests the JSON string value representation class "JsonString".

[Copyright]
Copyright (C) 2016 Arctic Ice Studio <development@arcticicestudio.com>

[References]
JSON
  (http://json.org)
ECMA-404 1st Edition (October 2013)
  (http://www.ecma-international.org/publications/files/ECMA-ST/ECMA-404.pdf)
Java 8 API Documentation
  (https://docs.oracle.com/javase/8/docs/api/)
JUnit
  (http://junit.org)
Arctic Versioning Specification (ArcVer)
  (http://specs.arcticicestudio.com/arcver)
*/
package com.arcticicestudio.icecore.json;

import static com.arcticicestudio.icecore.json.TestUtil.assertException;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Tests the JSON value representation class {@link JsonString}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class JsonStringTest {

  private StringWriter stringWriter;
  private JsonWriter jsonWriter;

  @Before
  public void setUp() {
    stringWriter = new StringWriter();
    jsonWriter = new JsonWriter(stringWriter);
  }

  @Test
  public void constructorFailsWithNull() {
    assertException(NullPointerException.class, "string is null", new Runnable() {
      public void run() {
        new JsonString(null);
      }
    });
  }

  @Test
  public void write() throws IOException {
    new JsonString("yoghurt").write(jsonWriter);
    assertEquals("\"yoghurt\"", stringWriter.toString());
  }

  @Test
  public void writeEscapesStrings() throws IOException {
    new JsonString("yog\\hurt").write(jsonWriter);
    assertEquals("\"yog\\\\hurt\"", stringWriter.toString());
  }
}
