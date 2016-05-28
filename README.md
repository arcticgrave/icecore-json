IceCore - JSON
==============

### Description
Lightweight module library as part of the [IceCore](https://bitbucket.org/arcticicestudio/icecore) engine, providing a fast, minimal and simple [JSON](http://json.org) parser and writer.  
It's not an object mapper, but a bare-bones library that aims at being
  - **lightweight**: object representation with minimal memory footprint (e.g. no HashMaps)
  - **fast**: high performance comparable with other state-of-the-art parsers
  - **minimal**: no dependencies, single package with just a few classes, small download size (< 35kB)
  - **simple**: reading, writing and modifying JSON with minimal code (short names, fluent style)

Usage Guide
-----------
This is a basic guide to show the common usage of this library.
The API documentation can be found in the JavaDoc.

The class `Json` is the entrypoint to the icecore-json API, use it to parse and to create JSON.

  - [Parse JSON](#parse-json)
  - [JSON values](#json-values)
  - [JSON arrays](#json-arrays)
  - [JSON objects](#json-objects)
  - [Example](#example)
    - [Create JSON values](#create-json-values)
    - [Modify JSON arrays and objects](#modify-json-arrays-and-objects)
    - [Output JSON](#output-json)

#### Parse JSON

You can parse JSON from a `String` or from a `java.io.Reader`.
You *don't* need to wrap your reader in a BufferedReader, as the parse method uses a reading buffer.

```java
JsonValue value = Json.parse(string);
```

#### JSON values

JSON values are represented by the type `JsonValue`.
A `JsonValue` can contain a JSON array, object, string, number, or one of the literals `true`, `false`, and `null`.
To transform a `JsonValue` into a Java type, use the methods `asString`, `asInt`, `asFloat`, `asArray` etc., depending on the expected type.
To query the actual type, use one of the methods `isString`, `isNumber`, `isArray`, `isObject`, `isBoolean`, and `isNull`.

```java
if (value.isString()) {
  String string = value.asString();
  // ...
} else if (value.isArray()) {
  JsonArray array = value.asArray();
  // ...
}
```

#### JSON arrays

The method `asArray` returns an instance of `JsonArray`.
This subtype of `JsonValue` provides a `get` method to access the elements of a JSON array:

```java
JsonArray array = Json.parse(reader).asArray();
String name = array.get(0).asString();
int quantity = array.get(1).asInt();
```

You can also iterate over the elements of a `JsonArray`, which are again also JSON values:

```java
for (JsonValue value : jsonArray) {
  // ...
}
```

#### JSON objects

Similar to `JsonArray`, the type `JsonObject` represents JSON objects, the map type in JSON.
Members of a JSON object can be accessed by name using the `get` method.

```java
JsonObject object = Json.parse(input).asObject();
String name = object.get("name").asString();
int quantity = object.get("quantity").asInt();
```

There are also shorthand methods like `getString`, `getInt`, `getDouble`, etc. that directly return the expected type. These methods require a default value that is returned when the member is not found:

```java
String name = object.getString("name", "Unknown");
int age = object.getInt("quantity", 1);
```

You can also iterate over the members of a JSON object:

```java
for (Member member : jsonObject) {
  String name = member.getName();
  JsonValue value = member.getValue();
  // ...
}
```

#### Example
This is a example on how to extract nested contents.

```json
{
  "order": 4378,
  "items": [
    {
      "name": "Yoghurt",
      "id": "28469",
      "quantity": 5,
    },
    {
      "name": "Coconut",
      "id": "19875",
      "quantity": 2
    }
  ]
}
```

The following snippet extracts the names and quantities of all items:

```java
JsonArray items = Json.parse(json).asObject().get("items").asArray();
for (JsonValue item : items) {
  String name = item.asObject().getString("name", "Unknown Item");
  int quantity = item.asObject().getInt("quantity", 1);
  //..
}
```

##### Create JSON values

The entrypoint class `Json` also has methods to create instances of `JsonValue` from Java strings, numbers, and boolean values:

```java
JsonValue name = Json.value("Yoghurt");
JsonValue protein = Json.value(28);
```

And there are methods for creating empty arrays and objects as well.
Use these together with `add` to create data structures:

```java
JsonObject article = Json.object().add("name", "Yoghurt").add("protein", 28);
// -> {"name": "Yoghurt", "protein": 28}

JsonArray article = Json.array().add("Bob").add(16);
// -> ["Coconut", 16]
```

You can also create JSON arrays conveniently from Java arrays such as `String[]`, `int[]`, `boolean[]`, etc.:

```java
String[] javaNames = {"Yoghurt", "Coconut"};
JsonArray jsonNames = Json.array(names);
```

##### Modify JSON arrays and objects

You can replace or remove array elements based on their index.
The index must be valid.

```java
jsonArray.set(1, 24);
jsonArray.remove(1);
```

Likewise, members of JSON objects can be modified by their name.
If the name does not exist, `set` will append a new member:

```java
jsonObject.set("quantity", 12);
jsonObject.remove("quantity");
```

`JsonObject` also provides a `merge` method that copies all members from a given JSON object:

```java
jsonObject.merge(otherObject);
```

##### Output JSON

The `toString` method of a `JsonValue` returns valid JSON strings.
You can also write to a `java.io.Writer` using `writeTo`:

```java
String json = jsonValue.toString();
jsonValue.writeTo(writer);
```

Both methods accept an additonal parameter to enable formatted output:

```java
String json = jsonValue.toString(WriterConfig.PRETTY_PRINT);
jsonValue.writeTo(writer, WriterConfig.PRETTY_PRINT);
```

---

### Concurrency
The JSON structures in this library (`JsonObject` and `JsonArray`) are deliberately **not thread-safe** to keep them fast and simple.
In the rare case that JSON data structures must be accessed from multiple threads, while at least one of these threads modifies their contents, the application must ensure proper synchronization.

Iterators will throw a `ConcurrentModificationException` when the contents of a JSON structure have been modified after the creation of the iterator.

### Version
[`0.5.0`](https://bitbucket.org/arcticicestudio/icecore-json/downloads)  

For older versions check out the private [Bitbucket Git Repository](https://bitbucket.org/arcticicestudio/icecore-json).

### Changelog
[`0.5.0`](CHANGELOG.md)

### Development
#### Workflow
This project follows the [gitflow](http://nvie.com/posts/a-successful-git-branching-model) branching model.

#### Specifications
This project follows the [Arctic Versioning Specification (ArcVer)](https://github.com/arcticicestudio/arcver).

### Dependencies
#### Development
  - [`junit@4.12.0`](http://junit.org)
  - [`hamcrest-core@1.3.0`](http://hamcrest.org)

**Engines**
  - [`ant@1.9.6`](http://ant.apache.org)
  - [`git@2.8.*`](https://git-scm.com)
  - [`odin@0.1.0`](~/yggdrasil/Odin)

**Skeletons**
  - [`glacier-apache-ant@0.11.0`](https://github.com/arcticicestudio/glacier-apache-ant)
  - [`glacier-git@0.23.0`](https://github.com/arcticicestudio/glacier-git)

### Contribution
Please report issues/bugs, suggestions for improvements and feature requests to the [issuetracker](https://bitbucket.org/arcticicestudio/icecore-json/issues) or via [email](mailto:bugs@arcticicestudio.com)

### Author
The [IceCore](https://bitbucket.org/arcticicestudio/icecore) engine project and [IceCore - JSON](https://bitbucket.org/arcticicestudio/icecore-json) module is developed and authored by [Arctic Ice Studio](http://arcticicestudio.com).

### Copyright
<a href="mailto:development@arcticicestudio.com"><img src="http://www.arcticicestudio.com/assets/content/image/ais-logo.png" width=48 height=48 alt="Arctic Ice Studio Logo"/> Copyright &copy; 2016 Arctic Ice Studio</a>

---

### References
  - [IceCore](https://bitbucket.org/arcticicestudio/icecore-json)
  - [IceCore - Core](https://bitbucket.org/arcticicestudio/icecore-core)
  - [JSON](http://json.org)
  - [ECMA-404 1st Edition (October 2013)](http://www.ecma-international.org/publications/files/ECMA-ST/ECMA-404.pdf)
  - [ArcVer](https://github.com/arcticicestudio/arcver)
  - [gitflow](http://nvie.com/posts/a-successful-git-branching-model)
  - [Glacier - Apache Ant](https://github.com/arcticicestudio/glacier-apache-ant)
  - [Glacier - Git](https://github.com/arcticicestudio/glacier-git)
