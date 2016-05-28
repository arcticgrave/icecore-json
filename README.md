IceCore - JSON
==============

### Description
Lightweight module library as part of the [IceCore](https://bitbucket.org/arcticicestudio/icecore) engine, providing a fast, minimal and simple [JSON](http://json.org) parser and writer.  
It's not an object mapper, but a bare-bones library that aims at being
  - **lightweight**: object representation with minimal memory footprint (e.g. no HashMaps)
  - **fast**: high performance comparable with other state-of-the-art parsers
  - **minimal**: no dependencies, single package with just a few classes, small download size (< 35kB)
  - **simple**: reading, writing and modifying JSON with minimal code (short names, fluent style)

Usage
-----

The class `Json` is the entrypoint to the minimal-json API, use it to parse and to create JSON.

  - [Parse JSON](#parse-json)
  - [JSON values](#json-values)
  - [JSON arrays](#json-arrays)
  - [JSON objects](#json-objects)

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

### JSON arrays

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

### JSON objects

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
