/*
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
title      JSON Array Test                                 +
project    icecore-json                                    +
version    0.8.0-frost.1                                   +
repository https://github.com/arcticicestudio/icecore-json +
author     Arctic Ice Studio                               +
email      development@arcticicestudio.com                 +
copyright  Copyright (C) 2016                              +
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*/
package com.arcticicestudio.icecore.json;

import static com.arcticicestudio.icecore.json.TestUtil.assertException;
import static com.arcticicestudio.icecore.json.TestUtil.serializeAndDeserialize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.io.IOException;
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

  @Test
  public void addString() {
    array.add("yogurt");
    assertEquals("[\"yogurt\"]", array.toString());
  }

  @Test
  public void addStringEnablesChaining() {
    assertSame(array, array.add("yogurt"));
  }

  @Test
  public void addStringToleratesNull() {
    array.add((String)null);
    assertEquals("[null]", array.toString());
  }

  @Test
  public void addJsonNull() {
    array.add(Json.NULL);
    assertEquals("[null]", array.toString());
  }

  @Test
  public void addJsonArray() {
    array.add(new JsonArray());
    assertEquals("[[]]", array.toString());
  }

  @Test
  public void addJsonObject() {
    array.add(new JsonObject());
    assertEquals("[{}]", array.toString());
  }

  @Test
  public void addJsonEnablesChaining() {
    assertSame(array, array.add(Json.NULL));
  }

  @Test
  public void addJsonFailsWithNull() {
    assertException(NullPointerException.class, "value is null", new Runnable() {
      public void run() {
        array.add((JsonValue)null);
      }
    });
  }

  @Test
  public void addJsonNestedArray() {
    JsonArray innerArray = new JsonArray();
    innerArray.add(92);
    array.add(innerArray);
    assertEquals("[[92]]", array.toString());
  }

  @Test
  public void addJsonNestedArrayModifiedAfterAdd() {
    JsonArray innerArray = new JsonArray();
    array.add(innerArray);
    innerArray.add(92);
    assertEquals("[[92]]", array.toString());
  }

  @Test
  public void addJsonNestedObject() {
    JsonObject innerObject = new JsonObject();
    innerObject.add("a", 92);
    array.add(innerObject);
    assertEquals("[{\"a\":92}]", array.toString());
  }

  @Test
  public void addJsonNestedObjectModifiedAfterAdd() {
    JsonObject innerObject = new JsonObject();
    array.add(innerObject);
    innerObject.add("a", 92);
    assertEquals("[{\"a\":92}]", array.toString());
  }

  @Test
  public void setInt() {
    array.add(false);
    array.set(0, 92);
    assertEquals("[92]", array.toString());
  }

  @Test
  public void setIntEnablesChaining() {
    array.add(false);
    assertSame(array, array.set(0, 92));
  }

  @Test
  public void setLong() {
    array.add(false);
    array.set(0, 92l);
    assertEquals("[92]", array.toString());
  }

  @Test
  public void setLongEnablesChaining() {
    array.add(false);
    assertSame(array, array.set(0, 92l));
  }

  @Test
  public void setFloat() {
    array.add(false);
    array.set(0, 3.14f);
    assertEquals("[3.14]", array.toString());
  }

  @Test
  public void setFloatEnablesChaining() {
    array.add(false);
    assertSame(array, array.set(0, 3.14f));
  }

  @Test
  public void setDouble() {
    array.add(false);
    array.set(0, 3.14d);
    assertEquals("[3.14]", array.toString());
  }

  @Test
  public void setDoubleEnablesChaining() {
    array.add(false);
    assertSame(array, array.set(0, 3.14d));
  }

  @Test
  public void setBoolean() {
    array.add(false);
    array.set(0, true);
    assertEquals("[true]", array.toString());
  }

  @Test
  public void setBooleanEnablesChaining() {
    array.add(false);
    assertSame(array, array.set(0, true));
  }

  @Test
  public void setString() {
    array.add(false);
    array.set(0, "yogurt");
    assertEquals("[\"yogurt\"]", array.toString());
  }

  @Test
  public void setStringEnablesChaining() {
    array.add(false);
    assertSame(array, array.set(0, "yogurt"));
  }

  @Test
  public void setJsonNull() {
    array.add(false);
    array.set(0, Json.NULL);
    assertEquals("[null]", array.toString());
  }

  @Test
  public void setJsonArray() {
    array.add(false);
    array.set(0, new JsonArray());
    assertEquals("[[]]", array.toString());
  }

  @Test
  public void setJsonObject() {
    array.add(false);
    array.set(0, new JsonObject());
    assertEquals("[{}]", array.toString());
  }

  @Test
  public void setJsonFailsWithNull() {
    array.add(false);

    assertException(NullPointerException.class, "value is null", new Runnable() {
      public void run() {
        array.set(0, (JsonValue)null);
      }
    });
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void setJsonFailsWithInvalidIndex() {
    array.set(0, Json.NULL);
  }

  @Test
  public void setJsonEnablesChaining() {
    array.add(false);
    assertSame(array, array.set(0, Json.NULL));
  }

  @Test
  public void setJsonReplacesDifferentArrayElements() {
    array.add(3).add(6).add(9);
    array.set(1, 4).set(2, 5);
    assertEquals("[3,4,5]", array.toString());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void removeFailsWithInvalidIndex() {
    array.remove(0);
  }

  @Test
  public void removeRemovesElement() {
    array.add(92);
    array.remove(0);
    assertEquals("[]", array.toString());
  }

  @Test
  public void removeKeepsOtherElements() {
    array.add("a").add("b").add("c");
    array.remove(1);
    assertEquals("[\"a\",\"c\"]", array.toString());
  }

  @Test
  public void writeEmpty() throws IOException {
    JsonWriter writer = mock(JsonWriter.class);
    array.write(writer);
    InOrder inOrder = inOrder(writer);
    inOrder.verify(writer).writeArrayOpen();
    inOrder.verify(writer).writeArrayClose();
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  public void writeWithSingleValue() throws IOException {
    JsonWriter writer = mock(JsonWriter.class);
    array.add(92);
    array.write(writer);
    InOrder inOrder = inOrder(writer);
    inOrder.verify(writer).writeArrayOpen();
    inOrder.verify(writer).writeNumber("92");
    inOrder.verify(writer).writeArrayClose();
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  public void writeWithMultipleValues() throws IOException {
    JsonWriter writer = mock(JsonWriter.class);
    array.add(92).add("yogurt").add(false);
    array.write(writer);
    InOrder inOrder = inOrder(writer);
    inOrder.verify(writer).writeArrayOpen();
    inOrder.verify(writer).writeNumber("92");
    inOrder.verify(writer).writeArraySeparator();
    inOrder.verify(writer).writeString("yogurt");
    inOrder.verify(writer).writeArraySeparator();
    inOrder.verify(writer).writeLiteral("false");
    inOrder.verify(writer).writeArrayClose();
    inOrder.verifyNoMoreInteractions();
  }

  @Test
  public void isArray() {
    assertTrue(array.isArray());
  }

  @Test
  public void asArray() {
    assertSame(array, array.asArray());
  }

  @Test
  public void equalsTrueForSameInstance() {
    assertTrue(array.equals(array));
  }

  @Test
  public void equalsFalseForNull() {
    assertFalse(array.equals(null));
  }

  @Test
  public void equalsTrueForEqualArrays() {
    assertTrue(array().equals(array()));
    assertTrue(array("yogurt", "coconut").equals(array("yogurt", "coconut")));
  }

  @Test
  public void equalsFalseForDifferentArrays() {
    assertFalse(array("yogurt", "coconut").equals(array("yogurt", "coconut", "chocolate")));
    assertFalse(array("yogurt", "coconut").equals(array("coconut", "yogurt")));
  }

  @Test
  public void hashCodeEqualsForEqualArrays() {
    assertTrue(array().hashCode() == array().hashCode());
    assertTrue(array("yogurt").hashCode() == array("yogurt").hashCode());
  }

  @Test
  public void hashCodeDiffersForDifferentArrays() {
    assertFalse(array().hashCode() == array("yogurt").hashCode());
    assertFalse(array("yogurt").hashCode() == array("coconut").hashCode());
  }

  @Test
  public void equalsFalseForSubclass() {
    assertFalse(array.equals(new JsonArray(array) {}));
  }

  @Test
  public void canBeSerializedAndDeserialized() throws Exception {
    array.add(true).add(3.14d).add(92).add("yogurt").add(new JsonArray().add(false));
    assertEquals(array, serializeAndDeserialize(array));
  }

  @Test
  public void deserializedArrayCanBeAccessed() throws Exception {
    array.add(92);
    JsonArray deserializedArray = serializeAndDeserialize(array);
    assertEquals(92, deserializedArray.get(0).asInt());
  }

  private static JsonArray array(String... values) {
    JsonArray array = new JsonArray();
    for (String value : values) {
      array.add(value);
    }
    return array;
  }
}
