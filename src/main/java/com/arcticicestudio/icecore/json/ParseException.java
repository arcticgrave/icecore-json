/*
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
title      Parse Exception                                 +
project    icecore-json                                    +
version    0.8.0-frost.1                                   +
repository https://github.com/arcticicestudio/icecore-json +
author     Arctic Ice Studio                               +
email      development@arcticicestudio.com                 +
copyright  Copyright (C) 2016                              +
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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