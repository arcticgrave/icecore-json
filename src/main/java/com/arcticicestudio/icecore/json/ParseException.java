/*
++++++++++++++++++++++++++++++++++++++++++++
title     Parse Exception                  +
project   icecore-json                     +
file      ParseException.java              +
version   0.8.0-frost.1                    +
author    Arctic Ice Studio                +
email     development@arcticicestudio.com  +
website   http://arcticicestudio.com       +
copyright Copyright (C) 2016               +
created   2016-05-28 14:12 UTC+0200        +
modified  2016-06-04 08:03 UTC+0200        +
++++++++++++++++++++++++++++++++++++++++++++

[Description]
An unchecked exception to indicate that an input does not qualify as valid JSON.

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

/**
 * An unchecked exception to indicate that an input does not qualify as valid JSON.
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.4.0
 */
public class ParseException extends RuntimeException {

  private final Location location;

  /**
   * @since 0.8.0
   */
  ParseException(String message, Location location) {
    super(message + " at " + location);
    this.location = location;
  }

  /**
   * Returns the location at which the error occurred.
   *
   * @return the error location
   * @since 0.8.0
   */
  public Location getLocation() {
    return location;
  }
}