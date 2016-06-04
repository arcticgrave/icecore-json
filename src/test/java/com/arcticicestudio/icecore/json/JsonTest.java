/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON API Test                   +
project   icecore-json                    +
file      JsonTest.java                   +
version   0.8.0-frost.0                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-29 21:03 UTC+0200       +
modified  2016-05-29 21:28 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Tests the JSON API class "Json".

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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.arcticicestudio.icecore.json.TestUtil.RunnableEx;

/**
 * Tests the JSON API class {@link Json}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class JsonTest {

  @Test
  public void literalConstants() {
    assertTrue(Json.NULL.isNull());
    assertTrue(Json.TRUE.isTrue());
    assertTrue(Json.FALSE.isFalse());
  }

  @Test
  public void valueInt() {
    assertEquals("0", Json.value(0).toString());
    assertEquals("23", Json.value(23).toString());
    assertEquals("-1", Json.value(-1).toString());
    assertEquals("2147483647", Json.value(Integer.MAX_VALUE).toString());
    assertEquals("-2147483648", Json.value(Integer.MIN_VALUE).toString());
  }

  @Test
  public void valueLong() {
    assertEquals("0", Json.value(0l).toString());
    assertEquals("9223372036854775807", Json.value(Long.MAX_VALUE).toString());
    assertEquals("-9223372036854775808", Json.value(Long.MIN_VALUE).toString());
  }

  @Test
  public void valueFloat() {
    assertEquals("23.5", Json.value(23.5f).toString());
    assertEquals("-3.1416", Json.value(-3.1416f).toString());
    assertEquals("1.23E-6", Json.value(0.00000123f).toString());
    assertEquals("-1.23E7", Json.value(-12300000f).toString());
  }

  @Test
  public void valueFloatCutsOffPointZero() {
    assertEquals("0", Json.value(0f).toString());
    assertEquals("-1", Json.value(-1f).toString());
    assertEquals("10", Json.value(10f).toString());
  }

  @Test
  public void valueFloatFailsWithInfinity() {
    String message = "Infinite and NaN values not permitted in JSON";
    assertException(IllegalArgumentException.class, message, new Runnable() {
      public void run() {
        Json.value(Float.POSITIVE_INFINITY);
      }
    });
  }

  @Test
  public void valueFloatFailsWithNaN() {
    String message = "Infinite and NaN values not permitted in JSON";
    assertException(IllegalArgumentException.class, message, new Runnable() {
      public void run() {
        Json.value(Float.NaN);
      }
    });
  }

  @Test
  public void valueDouble() {
    assertEquals("23.5", Json.value(23.5d).toString());
    assertEquals("3.1416", Json.value(3.1416d).toString());
    assertEquals("1.23E-6", Json.value(0.00000123d).toString());
    assertEquals("1.7976931348623157E308", Json.value(1.7976931348623157E308d).toString());
  }

  @Test
  public void valueDoubleCutsOffPointZero() {
    assertEquals("0", Json.value(0d).toString());
    assertEquals("-1", Json.value(-1d).toString());
    assertEquals("10", Json.value(10d).toString());
  }

  @Test
  public void valueDoubleFailsWithInfinity() {
    String message = "Infinite and NaN values not permitted in JSON";
    assertException(IllegalArgumentException.class, message, new Runnable() {
      public void run() {
        Json.value(Double.POSITIVE_INFINITY);
      }
    });
  }

  @Test
  public void valueDoubleFailsWithNaN() {
    String message = "Infinite and NaN values not permitted in JSON";
    assertException(IllegalArgumentException.class, message, new Runnable() {
      public void run() {
        Json.value(Double.NaN);
      }
    });
  }

  @Test
  public void valueBoolean() {
    assertSame(Json.TRUE, Json.value(true));
    assertSame(Json.FALSE, Json.value(false));
  }

  @Test
  public void valueString() {
    assertEquals("", Json.value("").asString());
    assertEquals("Yogurt", Json.value("Yogurt").asString());
    assertEquals("\"Yogurt\"", Json.value("\"Yogurt\"").asString());
  }

  @Test
  public void valueStringToleratesNull() {
    assertSame(Json.NULL, Json.value(null));
  }

  @Test
  public void array() {
    assertEquals(new JsonArray(), Json.array());
  }

  @Test
  public void arrayInt() {
    assertEquals(new JsonArray().add(23), Json.array(23));
    assertEquals(new JsonArray().add(23).add(42), Json.array(23, 42));
  }

  @Test
  public void arrayIntFailsWithNull() {
    TestUtil.assertException(NullPointerException.class, "values is null", new Runnable() {
      public void run() {
        Json.array((int[])null);
      }
    });
  }

  @Test
  public void arrayLong() {
    assertEquals(new JsonArray().add(23l), Json.array(23l));
    assertEquals(new JsonArray().add(23l).add(42l), Json.array(23l, 42l));
  }

  @Test
  public void arrayLongFailsWithNull() {
    TestUtil.assertException(NullPointerException.class, "values is null", new Runnable() {
      public void run() {
        Json.array((long[])null);
      }
    });
  }

  @Test
  public void arrayFloat() {
    assertEquals(new JsonArray().add(3.14f), Json.array(3.14f));
    assertEquals(new JsonArray().add(3.14f).add(1.41f), Json.array(3.14f, 1.41f));
  }

  @Test
  public void arrayFloatFailsWithNull() {
    TestUtil.assertException(NullPointerException.class, "values is null", new Runnable() {
      public void run() {
        Json.array((float[])null);
      }
    });
  }

  @Test
  public void arrayDouble() {
    assertEquals(new JsonArray().add(3.14d), Json.array(3.14d));
    assertEquals(new JsonArray().add(3.14d).add(1.41d), Json.array(3.14d, 1.41d));
  }

  @Test
  public void arrayDoubleFailsWithNull() {
    TestUtil.assertException(NullPointerException.class, "values is null", new Runnable() {
      public void run() {
        Json.array((double[])null);
      }
    });
  }

  @Test
  public void arrayBoolean() {
    assertEquals(new JsonArray().add(true), Json.array(true));
    assertEquals(new JsonArray().add(true).add(false), Json.array(true, false));
  }

  @Test
  public void arrayBooleanFailsWithNull() {
    TestUtil.assertException(NullPointerException.class, "values is null", new Runnable() {
      public void run() {
        Json.array((boolean[])null);
      }
    });
  }

  @Test
  public void arrayString() {
    assertEquals(new JsonArray().add("yogurt"), Json.array("yogurt"));
    assertEquals(new JsonArray().add("yogurt").add("coconut"), Json.array("yogurt", "coconut"));
  }

  @Test
  public void arrayStringFailsWithNull() {
    TestUtil.assertException(NullPointerException.class, "values is null", new Runnable() {
      public void run() {
        Json.array((String[])null);
      }
    });
  }

  @Test
  public void object() {
    assertEquals(new JsonObject(), Json.object());
  }

  @Test
  public void parseString() {
    assertEquals(Json.value(23), Json.parse("23"));
  }

  @Test
  public void parseStringFailsWithNull() {
    TestUtil.assertException(NullPointerException.class, "string is null", new Runnable() {
      public void run() {
        Json.parse((String)null);
      }
    });
  }

  @Test
  public void parseReader() throws IOException {
    Reader reader = new StringReader("23");
    assertEquals(Json.value(23), Json.parse(reader));
  }

  @Test
  public void parseReaderFailsWithNull() {
    TestUtil.assertException(NullPointerException.class, "reader is null", new RunnableEx() {
      public void run() throws IOException {
        Json.parse((Reader)null);
      }
    });
  }
}
