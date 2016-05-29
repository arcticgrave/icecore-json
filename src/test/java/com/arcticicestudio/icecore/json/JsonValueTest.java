/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Value Test                 +
project   icecore-json                    +
file      JsonValueTest.java              +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-29 09:44 UTC+0200       +
modified  2016-05-29 09:45 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Tests the JSON values representation class "JsonValue".

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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.arcticicestudio.icecore.json.TestUtil.RunnableEx;

import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;



/**
 * Tests the JSON values representation class {@link JsonValue}.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class JsonValueTest {

  @Test
  public void writeTo() throws IOException {
    JsonValue value = new JsonObject();
    Writer writer = new StringWriter();
    value.writeTo(writer);
    assertEquals("{}", writer.toString());
  }

  @Test
  public void writeToFailsWithNullWriter() {
    final JsonValue value = new JsonObject();

    assertException(NullPointerException.class, "writer is null", new RunnableEx() {
      public void run() throws IOException {
        value.writeTo(null, WriterConfig.MINIMAL);
      }
    });
  }

  @Test
  public void writeToFailsWithNullConfig() {
    final JsonValue value = new JsonObject();

    assertException(NullPointerException.class, "config is null", new RunnableEx() {
      public void run() throws IOException {
        value.writeTo(new StringWriter(), null);
      }
    });
  }

  @Test
  public void toStringFailsWithNullConfig() {
    final JsonValue value = new JsonObject();

    assertException(NullPointerException.class, "config is null", new RunnableEx() {
      public void run() throws IOException {
        value.toString(null);
      }
    });
  }

  @Test
  public void writeToDoesNotCloseWriter() throws IOException {
    JsonValue value = new JsonObject();
    Writer writer = spy(new StringWriter());
    value.writeTo(writer);
    verify(writer, never()).close();
  }

  @Test
  public void asObjectFailsOnIncompatibleType() {
    assertException(UnsupportedOperationException.class, "Not an object: null", new Runnable() {
      public void run() {
        Json.NULL.asObject();
      }
    });
  }

  @Test
  public void asArrayFailsOnIncompatibleType() {
    assertException(UnsupportedOperationException.class, "Not an array: null", new Runnable() {
      public void run() {
        Json.NULL.asArray();
      }
    });
  }

  @Test
  public void asStringFailsOnIncompatibleType() {
    assertException(UnsupportedOperationException.class, "Not a string: null", new Runnable() {
      public void run() {
        Json.NULL.asString();
      }
    });
  }

  @Test
  public void asIntFailsOnIncompatibleType() {
    assertException(UnsupportedOperationException.class, "Not a number: null", new Runnable() {
      public void run() {
        Json.NULL.asInt();
      }
    });
  }

  @Test
  public void asLongFailsOnIncompatibleType() {
    assertException(UnsupportedOperationException.class, "Not a number: null", new Runnable() {
      public void run() {
        Json.NULL.asLong();
      }
    });
  }

  @Test
  public void asFloatFailsOnIncompatibleType() {
    assertException(UnsupportedOperationException.class, "Not a number: null", new Runnable() {
      public void run() {
        Json.NULL.asFloat();
      }
    });
  }

  @Test
  public void asDoubleFailsOnIncompatibleType() {
    assertException(UnsupportedOperationException.class, "Not a number: null", new Runnable() {
      public void run() {
        Json.NULL.asDouble();
      }
    });
  }

  @Test
  public void asBooleanFailsOnIncompatibleType() {
    assertException(UnsupportedOperationException.class, "Not a boolean: null", new Runnable() {
      public void run() {
        Json.NULL.asBoolean();
      }
    });
  }
}
