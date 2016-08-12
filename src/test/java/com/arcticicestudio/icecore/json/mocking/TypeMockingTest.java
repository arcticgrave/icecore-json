/*
+++++++++++++++++++++++++++++++++++++++++++
title     Type Mocking Test               +
project   icecore-json                    +
file      TypeMockingTest.java            +
version   0.8.0-frost.1                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
+++++++++++++++++++++++++++++++++++++++++++
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
