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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.arcticicestudio.icecore.json.JsonObject.Member;

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

  @Test
  public void sizeZeroAfterCreation() {
    assertEquals(0, object.size());
  }

  @Test
  public void sizeOneAfterAdd() {
    object.add("a", true);
    assertEquals(1, object.size());
  }

  @Test
  public void namesEmptyAfterCreation() {
    assertTrue(object.names().isEmpty());
  }

  @Test
  public void namesContainsNameAfterAdd() {
    object.add("yogurt", true);
    List<String> names = object.names();
    assertEquals(1, names.size());
    assertEquals("yogurt", names.get(0));
  }

  @Test
  public void namesReflectsChanges() {
    List<String> names = object.names();
    object.add("yogurt", true);
    assertEquals(1, names.size());
    assertEquals("yogurt", names.get(0));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void namesPreventsModification() {
    List<String> names = object.names();
    names.add("yogurt");
  }

  @Test
  public void iteratorIsEmptyAfterCreation() {
    assertFalse(object.iterator().hasNext());
  }

  @Test
  public void iteratorHasNextAfterAdd() {
    object.add("a", true);
    Iterator<Member> iterator = object.iterator();
    assertTrue(iterator.hasNext());
  }

  @Test
  public void iteratorNextReturnsActualValue() {
    object.add("a", true);
    Iterator<Member> iterator = object.iterator();
    assertEquals(new Member("a", Json.TRUE), iterator.next());
  }

  @Test
  public void iteratorNextProgressesToNextValue() {
    object.add("a", true);
    object.add("b", false);
    Iterator<Member> iterator = object.iterator();
    iterator.next();
    assertTrue(iterator.hasNext());
    assertEquals(new Member("b", Json.FALSE), iterator.next());
  }

  @Test(expected = NoSuchElementException.class)
  public void iteratorNextFailsAtEnd() {
    Iterator<Member> iterator = object.iterator();
    iterator.next();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void iteratorDoesNotAllowModification() {
    object.add("a", 92);
    Iterator<Member> iterator = object.iterator();
    iterator.next();
    iterator.remove();
  }

  @Test(expected = ConcurrentModificationException.class)
  public void iteratorDetectsConcurrentModification() {
    Iterator<Member> iterator = object.iterator();
    object.add("a", 92);
    iterator.next();
  }

  @Test
  public void getFailsWithNullName() {
    assertException(NullPointerException.class, "name is null", new Runnable() {
      public void run() {
        object.get(null);
      }
    });
  }

  @Test
  public void getReturnsNullForNonExistingMember() {
    assertNull(object.get("yogurt"));
  }

  @Test
  public void getReturnsValueForName() {
    object.add("yogurt", true);
    assertEquals(Json.TRUE, object.get("yogurt"));
  }

  @Test
  public void getReturnsLastValueForName() {
    object.add("yogurt", false).add("yogurt", true);
    assertEquals(Json.TRUE, object.get("yogurt"));
  }

  @Test
  public void getIntReturnsValueFromMember() {
    object.add("yogurt", 92);
    assertEquals(92, object.getInt("yogurt", 42));
  }

  @Test
  public void getIntReturnsDefaultForMissingMember() {
    assertEquals(92, object.getInt("yogurt", 92));
  }

  @Test
  public void getLongReturnsValueFromMember() {
    object.add("yogurt", 92l);
    assertEquals(92l, object.getLong("yogurt", 42l));
  }

  @Test
  public void getLongReturnsDefaultForMissingMember() {
    assertEquals(92l, object.getLong("yogurt", 92l));
  }

  @Test
  public void getFloatReturnsValueFromMember() {
    object.add("yogurt", 3.14f);
    assertEquals(3.14f, object.getFloat("yogurt", 1.41f), 0);
  }

  @Test
  public void getFloatReturnsDefaultForMissingMember() {
    assertEquals(3.14f, object.getFloat("yogurt", 3.14f), 0);
  }

  @Test
  public void getDoubleReturnsValueFromMember() {
    object.add("yogurt", 3.14);
    assertEquals(3.14, object.getDouble("yogurt", 1.41), 0);
  }

  @Test
  public void getDoubleReturnsDefaultForMissingMember() {
    assertEquals(3.14, object.getDouble("yogurt", 3.14), 0);
  }
}
