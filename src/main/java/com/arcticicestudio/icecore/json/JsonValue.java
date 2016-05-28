/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Value                      +
project   icecore-json                    +
file      JsonValue.java                  +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-28 10:38 UTC+0200       +
modified  2016-05-28 10:39 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Represents a JSON value.

[Copyright]
Copyright (C) 2016 Arctic Ice Studio <development@arcticicestudio.com>

[References]
JSON
  (http://json.org)
ECMA-404 1st Edition (October 2013)
  (http://www.ecma-international.org/publications/files/ECMA-ST/ECMA-404.pdf)
Java 8 API Documentation
  (https://docs.oracle.com/javase/8/docs/api/)
Arctic Versioning Specification (ArcVer)
  (http://specs.arcticicestudio.com/arcver)
*/
package com.arcticicestudio.icecore.json;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Represents a JSON value.
 * <p>This can be a JSON <strong>object</strong>, an <strong>array</strong>, a <strong>number</strong>,
 * a <strong>string</strong>, or one of the literals <strong>true</strong>, <strong>false</strong>
 * and <strong>null</strong>.
 * </p>
 * <p>
 * The literals <strong>true</strong>, <strong>false</strong> and <strong>null</strong> are represented by the constants
 * {@link Json#TRUE}, {@link Json#FALSE}, and {@link Json#NULL}.
 * </p>
 * <p>
 * JSON <strong>objects</strong> and <strong>arrays</strong> are represented by the subtypes {@link JsonObject} and
 * {@link JsonArray}.
 * Instances of these types can be created using the public constructors of these classes.
 * </p>
 * <p>
 * Instances that represent JSON <strong>numbers</strong>, <strong>strings</strong> and <strong>boolean</strong> values
 * can be created using the static factory methods {@link Json#value(String)}, {@link Json#value(long)},
 * {@link Json#value(double)} etc.
 * </p>
 * <p>
 * In order to find out whether an instance of this class is of a certain type, the methods {@link #isObject()},
 * {@link #isArray()}, {@link #isString()}, {@link #isNumber()} etc. can be used.
 * </p>
 * <p>
 * If the type of a JSON value is known, the methods {@link #asObject()}, {@link #asArray()}, {@link #asString()},
 * {@link #asInt()} etc. can be used to get this value directly in the appropriate target type.
 * </p>
 * <p>
 * This class is <strong>not supposed to be extended</strong> by clients.
 * </p>
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.1.0
 */
public abstract class JsonValue implements Serializable {

  /*
 * Prevents subclasses outside of this package
 */
  JsonValue() {}
}