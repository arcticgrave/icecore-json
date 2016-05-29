/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON API Test                   +
project   icecore-json                    +
file      JsonTest.java                   +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-29 21:03 UTC+0200       +
modified  2016-05-29 21:14 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Tests the JSON API class "Json".

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
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests the JSON API class {@link Json}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class JsonTest {

  @Test
  public void literalConstants() {
    assertTrue(Json.NULL.isNull());
    assertTrue(Json.TRUE.isTrue());
    assertTrue(Json.FALSE.isFalse());
  }

  @Test
  public void valueInt() {
    assertEquals("0", Json.value(0).toString());
    assertEquals("23", Json.value(23).toString());
    assertEquals("-1", Json.value(-1).toString());
    assertEquals("2147483647", Json.value(Integer.MAX_VALUE).toString());
    assertEquals("-2147483648", Json.value(Integer.MIN_VALUE).toString());
  }
}
