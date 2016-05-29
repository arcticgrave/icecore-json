/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Parser Exception Test      +
project   icecore-json                    +
file      ParseExceptionTest.java         +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-29 20:37 UTC+0200       +
modified  2016-05-29 20:38 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Tests the JSON parser exception class "ParseException".

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

import org.junit.Test;

/**
 * Tests the JSON parser exception class {@link ParseException}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class ParseExceptionTest {

  @Test
  public void position() {
    ParseException exception = new ParseException("Yogurt", 17, 23, 42);
    assertEquals(17, exception.getOffset());
    assertEquals(23, exception.getLine());
    assertEquals(42, exception.getColumn());
  }
}
