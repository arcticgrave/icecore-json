/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Number Test                +
project   icecore-json                    +
file      JsonNumberTest.java             +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-29 08:45 UTC+0200       +
modified  2016-05-29 08:46 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Tests the JSON string value representation class "JsonNumber".

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
 * Tests the JSON value representation class {@link JsonNumber}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class JsonNumberTest {

  private StringWriter output;
  private JsonWriter writer;

  @Before
  public void setUp() {
    output = new StringWriter();
    writer = new JsonWriter(output);
  }

  @Test
  public void constructorFailsWithNull() {
    assertException(NullPointerException.class, "string is null", new Runnable() {
      public void run() {
        new JsonNumber(null);
      }
    });
  }

  @Test
  public void write() throws IOException {
    new JsonNumber("92").write(writer);
    assertEquals("92", output.toString());
  }

  @Test
  public void toStringReturnsInputString() {
    assertEquals("yogurt", new JsonNumber("yogurt").toString());
  }
}
