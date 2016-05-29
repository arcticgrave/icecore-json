/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Literal Test               +
project   icecore-json                    +
file      JsonLiteralTest.java            +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-29 09:32 UTC+0200       +
modified  2016-05-29 09:33 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Tests the JSON literals representation class "JsonLiteral".

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

import static com.arcticicestudio.icecore.json.Json.FALSE;
import static com.arcticicestudio.icecore.json.Json.NULL;
import static com.arcticicestudio.icecore.json.Json.TRUE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests the JSON literals representation class {@link JsonLiteral}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class JsonLiteralTest {

  @Test
  public void isNull() {
    assertTrue(NULL.isNull());
    assertFalse(TRUE.isNull());
    assertFalse(FALSE.isNull());
  }

  @Test
  public void isTrue() {
    assertTrue(TRUE.isTrue());
    assertFalse(NULL.isTrue());
    assertFalse(FALSE.isTrue());
  }

  @Test
  public void isFalse() {
    assertTrue(FALSE.isFalse());
    assertFalse(NULL.isFalse());
    assertFalse(TRUE.isFalse());
  }

  @Test
  public void isBoolean() {
    assertTrue(TRUE.isBoolean());
    assertTrue(FALSE.isBoolean());
    assertFalse(NULL.isBoolean());
  }
}