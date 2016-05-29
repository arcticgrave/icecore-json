/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Object Test                +
project   icecore-json                    +
file      JsonObjectTest.java             +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-29 16:46 UTC+0200       +
modified  2016-05-29 16:47 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Tests the JSON object structure representation class "JsonObject".

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

import static com.arcticicestudio.icecore.json.TestUtil.assertException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the JSON object structure representation class {@link JsonObject}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class JsonObjectTest {

  private JsonObject object;

  @Before
  public void setUp() {
    object = new JsonObject();
  }

  @Test
  public void copyConstructorFailsWithNull() {
    assertException(NullPointerException.class, "object is null", new Runnable() {
      public void run() {
        new JsonObject(null);
      }
    });
  }

  @Test
  public void copyConstructorHasSameValues() {
    object.add("yogurt", 92);
    JsonObject copy = new JsonObject(object);
    assertEquals(object.names(), copy.names());
    assertSame(object.get("yogurt"), copy.get("yogurt"));
  }

  @Test
  public void copyConstructorWorksOnSafeCopy() {
    JsonObject copy = new JsonObject(object);
    object.add("yogurt", 92);
    assertTrue(copy.isEmpty());
  }

  @Test
  public void unmodifiableObjectHasSameValues() {
    object.add("yogurt", 92);
    JsonObject unmodifiableObject = JsonObject.unmodifiableObject(object);
    assertEquals(object.names(), unmodifiableObject.names());
    assertSame(object.get("yogurt"), unmodifiableObject.get("yogurt"));
  }

  @Test
  public void unmodifiableObjectReflectsChanges() {
    JsonObject unmodifiableObject = JsonObject.unmodifiableObject(object);
    object.add("yogurt", 92);
    assertEquals(object.names(), unmodifiableObject.names());
    assertSame(object.get("yogurt"), unmodifiableObject.get("yogurt"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void unmodifiableObjectPreventsModification() {
    JsonObject unmodifiableObject = JsonObject.unmodifiableObject(object);
    unmodifiableObject.add("yogurt", 92);
  }

  @Test
  public void isEmptyTrueAfterCreation() {
    assertTrue(object.isEmpty());
  }

  @Test
  public void isEmptyFalseAfterAdd() {
    object.add("yogurt", true);
    assertFalse(object.isEmpty());
  }
}
