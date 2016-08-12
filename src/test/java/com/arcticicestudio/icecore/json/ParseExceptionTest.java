/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Parser Exception Test      +
project   icecore-json                    +
file      ParseExceptionTest.java         +
version   0.8.0-frost.1                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
+++++++++++++++++++++++++++++++++++++++++++
*/
package com.arcticicestudio.icecore.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the JSON parser exception class {@link ParseException}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class ParseExceptionTest {

  private Location location;

  @Before
  public void setUp() {
    location = new Location(4711, 23, 42);
  }

  @Test
  public void location() {
    ParseException exception = new ParseException("Foo", location);
    assertSame(location, exception.getLocation());
  }

  @Test
  public void message() {
    ParseException exception = new ParseException("Yogurt", location);
    assertEquals("Yogurt at 23:42", exception.getMessage());
  }
}
