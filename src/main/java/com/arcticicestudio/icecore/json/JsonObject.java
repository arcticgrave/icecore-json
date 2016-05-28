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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

  private final List<String> names;
  private final List<JsonValue> values;
  private transient HashIndexTable table;

  /**
   * Creates a new empty JsonObject.
   */
  public JsonObject() {
    names = new ArrayList<String>();
    values = new ArrayList<JsonValue>();
    table = new HashIndexTable();
  }

  /**
   * Creates a new JsonObject, initialized with the contents of the specified JSON object.
   *
   * @param object the JSON object to get the initial contents from which <strong>MUST NOT</strong> be {@code null}
   */
  public JsonObject(JsonObject object) {
    this(object, false);
  }

  /**
   * Initializes the contents of the specified JSON object with a modifiability state based on the boolean value of the
   * {@code unmodifiable} parameter.
   *
   * @param object the JsonObject to get the initial contents from which <strong>MUST NOT</strong> be {@code null}.
   * @param unmodifiable the state of the modifiability
   */
  private JsonObject(JsonObject object, boolean unmodifiable) {
    if (object == null) {
      throw new NullPointerException("object is null");
    }
    if (unmodifiable) {
      names = Collections.unmodifiableList(object.names);
      values = Collections.unmodifiableList(object.values);
    } else {
      names = new ArrayList<String>(object.names);
      values = new ArrayList<JsonValue>(object.values);
    }
    table = new HashIndexTable();
    updateHashIndex();
  }

  /**
   * Returns an unmodifiable JsonObject for the specified one.
   * This method allows to provide read-only access to a JsonObject.
   * <p>
   *   The returned JsonObject is backed by the given object and reflect changes that happen to it.
   *   Attempts to modify the returned JsonObject result in an {@code UnsupportedOperationException}.
   * </p>
   *
   * @param object the JsonObject for which an unmodifiable JsonObject is to be returned
   * @return an unmodifiable view of the specified JsonObject
   */
  public static JsonObject unmodifiableObject(JsonObject object) {
    return new JsonObject(object, true);
  }

  /**
   * Appends a new member to the end of this object, with the specified name and the specified JSON value.
   * <p>
   *   <strong>This method does not prevent duplicate names!</strong>
   *   Calling this method with a name that already exists in the object will append another member with the same name.
   *   In order to replace existing members, use the method {@code set(name, value)} instead.
   *   Note that <strong><em>add</em> is much faster than <em>set</em></strong>, because it does not need to search for
   *   existing members.
   *   Therefore <em>add</em> should be preferred when constructing new objects.
   * </p>
   *
   * @param name the name of the member to add
   * @param value the value of the member to add, must not be {@code null}
   * @return the object itself, to enable method chaining
   */
  public JsonObject add(String name, JsonValue value) {
    if (name == null) {
      throw new NullPointerException("name is null");
    }
    if (value == null) {
      throw new NullPointerException("value is null");
    }
    table.add(name, names.size());
    names.add(name);
    values.add(value);
    return this;
  }

  @Override
  public boolean isObject() {
    return true;
  }

  @Override
  public JsonObject asObject() {
    return this;
  }

  int indexOf(String name) {
    int index = table.get(name);
    if (index != -1 && name.equals(names.get(index))) {
      return index;
    }
    return names.lastIndexOf(name);
  }

  private synchronized void readObject(ObjectInputStream inputStream)
    throws IOException, ClassNotFoundException
  {
    inputStream.defaultReadObject();
    table = new HashIndexTable();
    updateHashIndex();
  }

  private void updateHashIndex() {
    int size = names.size();
    for (int i = 0; i < size; i++) {
      table.add(names.get(i), i);
    }
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + names.hashCode();
    result = 31 * result + values.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    JsonObject other = (JsonObject)obj;
    return names.equals(other.names) && values.equals(other.values);
  }

  /**
   * Represents a member of a JSON object, a pair of a name and a value.
   *
   * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
   * @since 0.2.0
   */
  public static class Member {}

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

    int get(Object name) {
      int slot = hashSlotFor(name);
      /* Subtract 1, 0 stands for empty */
      return (hashTable[slot] & 0xff) - 1;
    }

    private int hashSlotFor(Object element) {
      return element.hashCode() & hashTable.length - 1;
    }
  }
}