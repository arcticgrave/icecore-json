/*
+++++++++++++++++++++++++++++++++++++++++++
title     Type Mocking Test               +
project   icecore-json                    +
file      TypeMockingTest.java            +
version   0.8.0-frost.0                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-29 21:37 UTC+0200       +
modified  2016-05-29 21:39 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Tests mocking to make sure types do not prevent mocking by final or visibility constructs.

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
package com.arcticicestudio.icecore.json.mocking;

import static org.junit.Assert.assertNotNull;

import com.arcticicestudio.icecore.json.JsonArray;
import com.arcticicestudio.icecore.json.JsonObject;
import com.arcticicestudio.icecore.json.JsonValue;
import com.arcticicestudio.icecore.json.ParseException;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests mocking to make sure types do not prevent mocking by final or visibility constructs.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class TypeMockingTest {

  @Test
  public void mockValue() {
    JsonValue jsonValue = Mockito.mock(JsonValue.class);
    assertNotNull(jsonValue);
  }

  @Test
  public void mockObject() {
    JsonObject jsonObject = Mockito.mock(JsonObject.class);
    assertNotNull(jsonObject);
  }

  @Test
  public void mockArray() {
    JsonArray jsonArray = Mockito.mock(JsonArray.class);
    assertNotNull(jsonArray);
  }

  @Test
  public void mockParseException() {
    Mockito.mock(ParseException.class);
  }
}
