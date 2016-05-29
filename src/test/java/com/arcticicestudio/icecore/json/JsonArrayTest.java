/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Array Test                 +
project   icecore-json                    +
file      JsonArrayTest.java              +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-29 10:00 UTC+0200       +
modified  2016-05-29 10:01 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Tests the JSON array structure representation class "JsonArray".

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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/**
 * Tests the JSON array structure representation class {@link JsonArray}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class JsonArrayTest {

  private JsonArray array;

  @Before
  public void setUp() {
    array = new JsonArray();
  }

  @Test
  public void copyConstructorFailsWithNull() {
    assertException(NullPointerException.class, "array is null", new Runnable() {
      public void run() {
        new JsonArray(null);
      }
    });
  }

  @Test
  public void copyConstructorHasSameValues() {
    array.add(92);
    JsonArray copy = new JsonArray(array);
    assertEquals(array.values(), copy.values());
  }

  @Test
  public void copyConstructorWorksOnSafeCopy() {
    JsonArray copy = new JsonArray(array);
    array.add(92);
    assertTrue(copy.isEmpty());
  }

  @Test
  public void unmodifiableArrayHasSameValues() {
    array.add(92);
    JsonArray unmodifiableArray = JsonArray.unmodifiableArray(array);
    assertEquals(array.values(), unmodifiableArray.values());
  }

  @Test
  public void unmodifiableArrayReflectsChanges() {
    JsonArray unmodifiableArray = JsonArray.unmodifiableArray(array);
    array.add(92);
    assertEquals(array.values(), unmodifiableArray.values());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void unmodifiableArrayPreventsModification() {
    JsonArray unmodifiableArray = JsonArray.unmodifiableArray(array);
    unmodifiableArray.add(92);
  }

  @Test
  public void isEmptyIsTrueAfterCreation() {
    assertTrue(array.isEmpty());
  }

  @Test
  public void isEmptyIsFalseAfterAdd() {
    array.add(true);
    assertFalse(array.isEmpty());
  }

  @Test
  public void sizeIsZeroAfterCreation() {
    assertEquals(0, array.size());
  }

  @Test
  public void sizeOsOneAfterAdd() {
    array.add(true);
    assertEquals(1, array.size());
  }

  @Test
  public void iteratorIsEmptyAfterCreation() {
    assertFalse(array.iterator().hasNext());
  }

  @Test
  public void iteratorHasNextAfterAdd() {
    array.add(true);
    Iterator<JsonValue> iterator = array.iterator();
    assertTrue(iterator.hasNext());
    assertEquals(Json.TRUE, iterator.next());
    assertFalse(iterator.hasNext());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void iteratorDoesNotAllowModification() {
    array.add(92);
    Iterator<JsonValue> iterator = array.iterator();
    iterator.next();
    iterator.remove();
  }

  @Test(expected = ConcurrentModificationException.class)
  public void iteratorDetectsConcurrentModification() {
    Iterator<JsonValue> iterator = array.iterator();
    array.add(92);
    iterator.next();
  }

  @Test
  public void valuesIsEmptyAfterCreation() {
    assertTrue(array.values().isEmpty());
  }

  @Test
  public void valuesContainsValueAfterAdd() {
    array.add(true);
    assertEquals(1, array.values().size());
    assertEquals(Json.TRUE, array.values().get(0));
  }

  @Test
  public void valuesReflectsChanges() {
    List<JsonValue> values = array.values();
    array.add(true);
    assertEquals(array.values(), values);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void valuesPreventsModification() {
    List<JsonValue> values = array.values();
    values.add(Json.TRUE);
  }

  @Test
  public void getReturnsValue() {
    array.add(92);
    JsonValue value = array.get(0);
    assertEquals(Json.value(92), value);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void getFailsWithInvalidIndex() {
    array.get(0);
  }

  @Test
  public void addInt() {
    array.add(92);
    assertEquals("[92]", array.toString());
  }

  @Test
  public void addIntEnablesChaining() {
    assertSame(array, array.add(92));
  }

  @Test
  public void addLong() {
    array.add(92l);
    assertEquals("[92]", array.toString());
  }

  @Test
  public void addLongEnablesChaining() {
    assertSame(array, array.add(92l));
  }

  @Test
  public void addFloat() {
    array.add(3.14f);
    assertEquals("[3.14]", array.toString());
  }

  @Test
  public void addFloatEnablesChaining() {
    assertSame(array, array.add(3.14f));
  }

  @Test
  public void addDouble() {
    array.add(3.14d);
    assertEquals("[3.14]", array.toString());
  }

  @Test
  public void addDoubleEnablesChaining() {
    assertSame(array, array.add(3.14d));
  }

  @Test
  public void addBoolean() {
    array.add(true);
    assertEquals("[true]", array.toString());
  }

  @Test
  public void addBooleanEnablesChaining() {
    assertSame(array, array.add(true));
  }
}
