/*
+++++++++++++++++++++++++++++++++++++++++++
title     JSON Object                     +
project   icecore-json                    +
file      JsonObject.java                 +
version                                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-28 12:03 UTC+0200       +
modified  2016-05-28 12:04 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Represents a JSON object, a set of name/value pairs, where the names are strings and the values are JSON values.

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
 * Represents a JSON object, a set of name/value pairs, where the names are strings and the values are JSON values.
 * <p>
 *   Members can be added using the {@code add(String, ...)} methods which accept instances of {@link JsonValue},
 *   strings, primitive numbers, and boolean values.
 *   To modify certain values of an object, use the {@code set(String, ...)} methods.
 *   Please note that the {@code add} methods are faster than {@code set} as they do not search for existing members.
 *   On the other hand, the {@code add} methods do not prevent adding multiple members with the same name.
 *   Duplicate names are discouraged but not prohibited by JSON.
 * </p>
 * <p>
 *   Members can be accessed by their name using {@link #get(String)}.
 *   A list of all names can be obtained from the method {@link #names()}.
 *   This class also supports iterating over the members in document order using an {@link #iterator()} or an
 *   enhanced for loop:
 * </p>
 * <pre>
 * for (Member member : jsonObject) {
 *   String name = member.getName();
 *   JsonValue value = member.getValue();
 *   ...
 * }
 * </pre>
 * <p>
 *   Even though JSON objects are unordered by definition, instances of this class preserve the order of members
 *   to allow processing in document order and to guarantee a predictable output.
 * </p>
 * <p>
 *   <strong>Note that this class is not thread-safe!</strong>.<br>
 *   If multiple threads access a {@code JsonObject} instance concurrently, while at least one of these threads modifies
 *   the contents of this object, access to the instance must be synchronized externally.
 *   Failure to do so may lead to an inconsistent state.
 * </p>
 * <p>
 *   This class is <strong>not supposed to be extended</strong> by clients.
 * </p>
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.2.0
 */
public class JsonObject extends JsonValue implements Iterable<Member> {

  /**
   * Represents a indexed hash table to handle JSON object member.
   *
   * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
   * @since 0.2.0
   */
  static class HashIndexTable {

    /* The size MUST be a power of two. */
    private final byte[] hashTable = new byte[32];

    public HashIndexTable() {}

    public HashIndexTable(HashIndexTable original) {
      System.arraycopy(original.hashTable, 0, hashTable, 0, hashTable.length);
    }

    void add(String name, int index) {
      int slot = hashSlotFor(name);
      if (index < 0xff) {
        /* Increment by 1, 0 stands for empty. */
        hashTable[slot] = (byte)(index + 1);
      } else {
        hashTable[slot] = 0;
      }
    }

    void remove(int index) {
      for (int i = 0; i < hashTable.length; i++) {
        if (hashTable[i] == index + 1) {
          hashTable[i] = 0;
        } else if (hashTable[i] > index + 1) {
          hashTable[i]--;
        }
      }
    }

    private int hashSlotFor(Object element) {
      return element.hashCode() & hashTable.length - 1;
    }
  }
}