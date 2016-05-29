/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Writer Test                +
project   icecore-json                    +
file      JsonWriterTest.java             +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-29 20:40 UTC+0200       +
modified  2016-05-29 20:41 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Tests the JSON writer class "JsonWriter".

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
Mockito
  (http://mockito.org)
Arctic Versioning Specification (ArcVer)
  (http://specs.arcticicestudio.com/arcver)
*/
package com.arcticicestudio.icecore.json;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Tests the JSON writer class {@link JsonWriter}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class JsonWriterTest {

  private StringWriter output;
  private JsonWriter writer;

  @Before
  public void setUp() {
    output = new StringWriter();
    writer = new JsonWriter(output);
  }

  @Test
  public void writeLiteral() throws IOException {
    writer.writeLiteral("yogurt");
    assertEquals("yogurt", output.toString());
  }

  @Test
  public void writeNumber() throws IOException {
    writer.writeNumber("92");
    assertEquals("92", output.toString());
  }

  @Test
  public void writeStringEmpty() throws IOException {
    writer.writeString("");
    assertEquals("\"\"", output.toString());
  }

  @Test
  public void writeStingEscapesBackslashes() throws IOException {
    writer.writeString("yogurt\\coconut");
    assertEquals("\"yogurt\\\\coconut\"", output.toString());
  }
}
