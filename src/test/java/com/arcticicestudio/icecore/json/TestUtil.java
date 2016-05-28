/*
+++++++++++++++++++++++++++++++++++++++++++
title     Test Util                       +
project   icecore-json                    +
file      TestUtil.java                   +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-28 20:00 UTC+0200       +
modified  2016-05-28 20:01 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Provides test utils.

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Provides test utils.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.7.0
 */
public class TestUtil {

  public static <T extends Exception> T assertException(Class<T> type, String message, Runnable runnable) {
    return assertException(type, message, adapt(runnable));
  }

  public static <T extends Exception> T assertException(Class<T> type, String message, RunnableEx runnable) {
    T exception = assertException(type, runnable);
    assertEquals("exception message", message, exception.getMessage());
    return exception;
  }

  public static <T extends Exception> T assertException(Class<T> type, Runnable runnable) {
    return assertException(type, adapt(runnable));
  }

  public static <T extends Exception> T assertException(Class<T> type, RunnableEx runnable) {
    T exception = catchException(runnable, type);
    assertNotNull("Expected exception: " + type.getName(), exception);
    return exception;
  }
}
