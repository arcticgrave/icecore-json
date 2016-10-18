/*
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
title      JSON Literal Test                               +
project    icecore-json                                    +
version    0.8.0-frost.1                                   +
repository https://github.com/arcticicestudio/icecore-json +
author     Arctic Ice Studio                               +
email      development@arcticicestudio.com                 +
copyright  Copyright (C) 2016                              +
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*/
package com.arcticicestudio.icecore.json;

import static com.arcticicestudio.icecore.json.TestUtil.serializeAndDeserialize;
import static com.arcticicestudio.icecore.json.Json.FALSE;
import static com.arcticicestudio.icecore.json.Json.NULL;
import static com.arcticicestudio.icecore.json.Json.TRUE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;

import java.io.IOException;

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

  @Test
  public void NULLWrite() throws IOException {
    JsonWriter writer = mock(JsonWriter.class);
    NULL.write(writer);
    verify(writer).writeLiteral("null");
    verifyNoMoreInteractions(writer);
  }

  @Test
  public void TRUEWrite() throws IOException {
    JsonWriter writer = mock(JsonWriter.class);
    TRUE.write(writer);
    verify(writer).writeLiteral("true");
    verifyNoMoreInteractions(writer);
  }

  @Test
  public void FALSEWrite() throws IOException {
    JsonWriter writer = mock(JsonWriter.class);
    FALSE.write(writer);
    verify(writer).writeLiteral("false");
    verifyNoMoreInteractions(writer);
  }

  @Test
  public void NULLToString() {
    assertEquals("null", NULL.toString());
  }

  @Test
  public void TRUEToString() {
    assertEquals("true", TRUE.toString());
  }

  @Test
  public void FALSEToString() {
    assertEquals("false", FALSE.toString());
  }

  @Test
  public void NULLEquals() {
    assertTrue(NULL.equals(NULL));
    assertFalse(NULL.equals(null));
    assertFalse(NULL.equals(TRUE));
    assertFalse(NULL.equals(FALSE));
    assertFalse(NULL.equals(Json.value("null")));
  }

  @Test
  public void TRUEEquals() {
    assertTrue(TRUE.equals(TRUE));
    assertFalse(TRUE.equals(null));
    assertFalse(TRUE.equals(FALSE));
    assertFalse(TRUE.equals(Boolean.TRUE));
    assertFalse(NULL.equals(Json.value("true")));
  }

  @Test
  public void FALSEEquals() {
    assertTrue(FALSE.equals(FALSE));
    assertFalse(FALSE.equals(null));
    assertFalse(FALSE.equals(TRUE));
    assertFalse(FALSE.equals(Boolean.FALSE));
    assertFalse(NULL.equals(Json.value("false")));
  }

  @Test
  public void NULLIsSerializable() throws Exception {
    assertEquals(NULL, serializeAndDeserialize(NULL));
    assertTrue(serializeAndDeserialize(NULL).isNull());
  }

  @Test
  public void TRUEIsSerializable() throws Exception {
    assertEquals(TRUE, serializeAndDeserialize(TRUE));
    assertTrue(serializeAndDeserialize(TRUE).isBoolean());
    assertTrue(serializeAndDeserialize(TRUE).isTrue());
  }

  @Test
  public void FALSEIsSerializable() throws Exception {
    assertEquals(FALSE, serializeAndDeserialize(FALSE));
    assertTrue(serializeAndDeserialize(FALSE).isBoolean());
    assertTrue(serializeAndDeserialize(FALSE).isFalse());
  }

  @Test
  public void sameAfterDeserialization() throws Exception {
    JsonArray array = new JsonArray().add(NULL).add(NULL);
    JsonArray deserialized = serializeAndDeserialize(array);
    assertNotSame(NULL, deserialized.get(0));
    assertSame(deserialized.get(0), deserialized.get(1));
  }
}