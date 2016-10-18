/*
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
title      JSON Writing Buffer Test                        +
project    icecore-json                                    +
version    0.8.0-frost.1                                   +
repository https://github.com/arcticicestudio/icecore-json +
author     Arctic Ice Studio                               +
email      development@arcticicestudio.com                 +
copyright  Copyright (C) 2016                              +
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
*/
package com.arcticicestudio.icecore.json;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Tests the JSON writer writing buffer class {@link WritingBuffer}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class WritingBufferTest {

  private static final int BUFFER_SIZE = 16;
  private StringWriter wrapped;
  private WritingBuffer writer;

  @Before
  public void setUp() {
    wrapped = new StringWriter();
    writer = new WritingBuffer(wrapped, BUFFER_SIZE);
  }

  @Test
  public void testFlushEmpty() throws IOException {
    writer.flush();
    assertEquals("", wrapped.toString());
  }

  @Test
  public void testWriteChar() throws IOException {
    writer.write('c');
    writer.flush();
    assertEquals("c", wrapped.toString());
  }

  @Test
  public void testWriteCharFit() throws IOException {
    writer.write(createString(BUFFER_SIZE - 1));
    writer.write('c');
    writer.flush();
    assertEquals(createString(BUFFER_SIZE - 1) + "c", wrapped.toString());
  }

  @Test
  public void testWriteCharExceeding() throws IOException {
    writer.write(createString(BUFFER_SIZE));
    writer.write('c');
    writer.flush();
    assertEquals(createString(BUFFER_SIZE) + "c", wrapped.toString());
  }

  @Test
  public void testWriteCharArray() throws IOException {
    writer.write("foobar".toCharArray(), 1, 3);
    writer.flush();
    assertEquals("oob", wrapped.toString());
  }

  @Test
  public void testWriteCharArrayFit() throws IOException {
    writer.write(createString(BUFFER_SIZE - 3));
    writer.write("foobar".toCharArray(), 1, 3);
    writer.flush();
    assertEquals(createString(BUFFER_SIZE - 3) + "oob", wrapped.toString());
  }

  @Test
  public void testWriteCharArrayExceeding() throws IOException {
    writer.write(createString(BUFFER_SIZE - 2));
    writer.write("foobar".toCharArray(), 1, 3);
    writer.flush();
    assertEquals(createString(BUFFER_SIZE - 2) + "oob", wrapped.toString());
  }

  @Test
  public void testWriteCharArrayExceedingBuffer() throws IOException {
    writer.write(createChars(BUFFER_SIZE + 1));
    writer.flush();
    assertEquals(createString(BUFFER_SIZE + 1), wrapped.toString());
  }

  @Test
  public void testWriteString() throws IOException {
    writer.write("foobar", 1, 3);
    writer.flush();
    assertEquals("oob", wrapped.toString());
  }

  @Test
  public void testWriteStringFit() throws IOException {
    writer.write(createString(BUFFER_SIZE - 3));
    writer.write("foobar", 1, 3);
    writer.flush();
    assertEquals(createString(BUFFER_SIZE - 3) + "oob", wrapped.toString());
  }

  @Test
  public void testWriteStringExceeding() throws IOException {
    writer.write(createString(BUFFER_SIZE - 2));
    writer.write("foobar", 1, 3);
    writer.flush();
    assertEquals(createString(BUFFER_SIZE - 2) + "oob", wrapped.toString());
  }

  @Test
  public void testWriteStringExceedingBuffer() throws IOException {
    writer.write(createString(BUFFER_SIZE + 1));
    writer.flush();
    assertEquals(createString(BUFFER_SIZE + 1), wrapped.toString());
  }

  private static String createString(int length) {
    return new String(createChars(length));
  }

  private static char[] createChars(int length) {
    char[] array = new char[length];
    Arrays.fill(array, 'x');
    return array;
  }
}
