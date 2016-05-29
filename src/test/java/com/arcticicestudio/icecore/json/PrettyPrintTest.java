/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Pretty Print Test          +
project   icecore-json                    +
file      PrettyPrintTest.java            +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-29 21:03 UTC+0200       +
modified  2016-05-29 21:04 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Tests the JSON writer pretty print class "PrettyPrint".

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

import static com.arcticicestudio.icecore.json.PrettyPrint.indentWithSpaces;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Tests the JSON writer pretty print class {@link PrettyPrint}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class PrettyPrintTest {

  private StringWriter output;

  @Before
  public void setUp() {
    output = new StringWriter();
  }

  @Test
  public void indentWithSpacesEmptyArray() throws IOException {
    new JsonArray().writeTo(output, indentWithSpaces(2));
    assertEquals("[\n  \n]", output.toString());
  }
}
