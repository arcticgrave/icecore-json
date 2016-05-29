/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Pretty Print Test          +
project   icecore-json                    +
file      PrettyPrintTest.java            +
version   0.7.0                           +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-29 21:03 UTC+0200       +
modified  2016-05-29 21:14 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Tests the JSON writer pretty print class "PrettyPrint".

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

import static com.arcticicestudio.icecore.json.PrettyPrint.indentWithSpaces;
import static com.arcticicestudio.icecore.json.PrettyPrint.indentWithTabs;
import static com.arcticicestudio.icecore.json.PrettyPrint.singleLine;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import static java.util.Locale.US;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Tests the JSON writer pretty print class {@link PrettyPrint}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class PrettyPrintTest {

  private StringWriter output;

  @Before
  public void setUp() {
    output = new StringWriter();
  }

  @Test
  public void indentWithSpacesEmptyArray() throws IOException {
    new JsonArray().writeTo(output, indentWithSpaces(2));
    assertEquals("[\n  \n]", output.toString());
  }

  @Test
  public void indentWithSpacesEmptyObject() throws IOException {
    new JsonObject().writeTo(output, indentWithSpaces(2));
    assertEquals("{\n  \n}", output.toString());
  }

  @Test
  public void indentWithSpacesArray() throws IOException {
    new JsonArray().add(23).add(42).writeTo(output, indentWithSpaces(2));
    assertEquals("[\n  23,\n  42\n]", output.toString());
  }

  @Test
  public void indentWithSpacesNestedArray() throws IOException {
    new JsonArray().add(23).add(new JsonArray().add(42)).writeTo(output, indentWithSpaces(2));
    assertEquals("[\n  23,\n  [\n    42\n  ]\n]", output.toString());
  }

  @Test
  public void indentWithSpacesObject() throws IOException {
    new JsonObject().add("a", 23).add("b", 42).writeTo(output, indentWithSpaces(2));
    assertEquals("{\n  \"a\": 23,\n  \"b\": 42\n}", output.toString());
  }

  @Test
  public void indentWithSpacesNestedObject() throws IOException {
    new JsonObject().add("a", 23).add("b", new JsonObject().add("c", 42)).writeTo(output, indentWithSpaces(2));
    assertEquals("{\n  \"a\": 23,\n  \"b\": {\n    \"c\": 42\n  }\n}", output.toString());
  }

  @Test
  public void indentWithSpacesZero() throws IOException {
    new JsonArray().add(23).add(42).writeTo(output, indentWithSpaces(0));
    assertEquals("[\n23,\n42\n]", output.toString());
  }

  @Test
  public void indentWithSpacesOne() throws IOException {
    new JsonArray().add(23).add(42).writeTo(output, indentWithSpaces(1));
    assertEquals("[\n 23,\n 42\n]", output.toString());
  }

  @Test
  public void indentWithSpacesFailsWithNegativeValues() {
    try {
      indentWithSpaces(-1);
      fail();
    } catch (IllegalArgumentException ex) {
      assertTrue(ex.getMessage().toLowerCase(US).contains("negative"));
    }
  }

  @Test
  public void indentWithSpacesCreatesIndependentInstances() {
    Writer writer = mock(Writer.class);
    WriterConfig config = indentWithSpaces(1);
    Object instance1 = config.createWriter(writer);
    Object instance2 = config.createWriter(writer);
    assertNotSame(instance1, instance2);
  }

  @Test
  public void indentWithTabsArray() throws IOException {
    new JsonArray().add(23).add(42).writeTo(output, indentWithTabs());
    assertEquals("[\n\t23,\n\t42\n]", output.toString());
  }

  @Test
  public void indentWithTabsCreatesIndependentInstances() {
    Writer writer = mock(Writer.class);
    WriterConfig config = indentWithTabs();
    Object instance1 = config.createWriter(writer);
    Object instance2 = config.createWriter(writer);
    assertNotSame(instance1, instance2);
  }

  @Test
  public void singleLineNestedArray() throws IOException {
    new JsonArray().add(23).add(new JsonArray().add(42)).writeTo(output, singleLine());
    assertEquals("[23, [42]]", output.toString());
  }

  @Test
  public void singleLineNestedObject() throws IOException {
    new JsonObject().add("a", 23).add("b", new JsonObject().add("c", 42)).writeTo(output, singleLine());
    assertEquals("{\"a\": 23, \"b\": {\"c\": 42}}", output.toString());
  }

  @Test
  public void singleLineCreatesIndependentInstances() {
    Writer writer = mock(Writer.class);
    WriterConfig config = singleLine();
    Object instance1 = config.createWriter(writer);
    Object instance2 = config.createWriter(writer);
    assertNotSame(instance1, instance2);
  }
}
