/*
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
title      JSON Number Test                                +
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
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Tests the JSON number-values representation class {@link JsonNumber}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class JsonNumberTest {

  private StringWriter output;
  private JsonWriter writer;

  @Before
  public void setUp() {
    output = new StringWriter();
    writer = new JsonWriter(output);
  }

  @Test
  public void constructorFailsWithNull() {
    assertException(NullPointerException.class, "string is null", new Runnable() {
      public void run() {
        new JsonNumber(null);
      }
    });
  }

  @Test
  public void write() throws IOException {
    new JsonNumber("92").write(writer);
    assertEquals("92", output.toString());
  }

  @Test
  public void toStringReturnsInputString() {
    assertEquals("yogurt", new JsonNumber("yogurt").toString());
  }

  @Test
  public void isNumber() {
    assertTrue(new JsonNumber("92").isNumber());
  }

  @Test
  public void asInt() {
    assertEquals(92, new JsonNumber("92").asInt());
  }

  @Test(expected = NumberFormatException.class)
  public void asIntFailsWithExceedingValues() {
    new JsonNumber("10000000000").asInt();
  }

  @Test(expected = NumberFormatException.class)
  public void asIntFailsWithExponent() {
    new JsonNumber("1e5").asInt();
  }

  @Test(expected = NumberFormatException.class)
  public void asIntFailsWithFractional() {
    new JsonNumber("92.5").asInt();
  }

  @Test
  public void asLong() {
    assertEquals(92l, new JsonNumber("92").asLong());
  }

  @Test(expected = NumberFormatException.class)
  public void asLongFailsWithExceedingValues() {
    new JsonNumber("10000000000000000000").asLong();
  }

  @Test(expected = NumberFormatException.class)
  public void asLongFailsWithExponent() {
    new JsonNumber("1e5").asLong();
  }

  @Test(expected = NumberFormatException.class)
  public void asLongFailsWithFractional() {
    new JsonNumber("92.5").asLong();
  }

  @Test
  public void asFloat() {
    assertEquals(92.05f, new JsonNumber("92.05").asFloat(), 0);
  }

  @Test
  public void asFloatReturnsInfinityForExceedingValues() {
    assertEquals(Float.POSITIVE_INFINITY, new JsonNumber("1e50").asFloat(), 0);
    assertEquals(Float.NEGATIVE_INFINITY, new JsonNumber("-1e50").asFloat(), 0);
  }

  @Test
  public void asDouble() {
    double result = new JsonNumber("92.05").asDouble();
    assertEquals(92.05, result, 0);
  }

  @Test
  public void asDoubleReturnsInfinityForExceedingValues() {
    assertEquals(Double.POSITIVE_INFINITY, new JsonNumber("1e500").asDouble(), 0);
    assertEquals(Double.NEGATIVE_INFINITY, new JsonNumber("-1e500").asDouble(), 0);
  }

  @Test
  public void equalsTrueForSameInstance() {
    JsonNumber number = new JsonNumber("92");
    assertTrue(number.equals(number));
  }

  @Test
  public void equalsTrueForEqualNumberStrings() {
    assertTrue(new JsonNumber("92").equals(new JsonNumber("92")));
  }

  @Test
  public void equalsFalseForDifferentNumberStrings() {
    assertFalse(new JsonNumber("92").equals(new JsonNumber("42")));
    assertFalse(new JsonNumber("1e+5").equals(new JsonNumber("1e5")));
  }

  @Test
  public void equalsFalseForNull() {
    assertFalse(new JsonNumber("92").equals(null));
  }

  @Test
  public void equalsFalseForSubclass() {
    assertFalse(new JsonNumber("92").equals(new JsonNumber("92") {}));
  }

  @Test
  public void hashCodeEqualsForEqualStrings() {
    assertTrue(new JsonNumber("92").hashCode() == new JsonNumber("92").hashCode());
  }

  @Test
  public void hashCodeDiffersForDifferentStrings() {
    assertFalse(new JsonNumber("92").hashCode() == new JsonNumber("42").hashCode());
  }

  @Test
  public void canBeSerializedAndDeserialized() throws Exception {
    JsonNumber number = new JsonNumber("3.14");
    assertEquals(number, serializeAndDeserialize(number));
  }
}
