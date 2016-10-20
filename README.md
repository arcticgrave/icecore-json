<p align="center"><img src="https://cdn.rawgit.com/arcticicestudio/icecore-json/develop/src/main/assets/icecore-json-logo-banner.svg"/></p>

<p align="center"><img src="https://cdn.travis-ci.org/images/favicon-c566132d45ab1a9bcae64d8d90e4378a.svg" width=24 height=24/> <a href="https://travis-ci.org/arcticicestudio/icecore-json"><img src="https://img.shields.io/travis/arcticicestudio/icecore-json/develop.svg"/></a> <img src="https://circleci.com/favicon.ico" width=24 height=24/> <a href="https://circleci.com/gh/arcticicestudio/icecore-json"><img src="https://circleci.com/gh/arcticicestudio/icecore-json.svg?style=shield&circle-token=be8b5b0a914996239ba512525f91c626e7d00ba8"/></a> <img src="https://codecov.io/favicon.ico" width=24 height=24/> <a href="https://codecov.io/gh/arcticicestudio/icecore-json"><img src="https://codecov.io/gh/arcticicestudio/icecore-json/branch/develop/graph/badge.svg"/></a> <img src="https://assets-cdn.github.com/favicon.ico" width=24 height=24/> <a href="https://github.com/arcticicestudio/icecore-json/releases/latest"><img src="https://img.shields.io/github/release/arcticicestudio/icecore-json.svg"/></a> <a href="https://github.com/arcticicestudio/icecore-json/releases/latest"><img src="https://img.shields.io/badge/pre--release-0.8.0--frost.1-blue.svg"/></a> <img src="http://central.sonatype.org/favicon.ico" width=24 height=24/> <a href="http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.arcticicestudio%22%20AND%20a%3A%22icecore-json%22"><img src="https://img.shields.io/maven-central/v/com.arcticicestudio/icecore-json.svg"/></a> <img src="https://oss.sonatype.org/favicon.ico"/> <a href="https://oss.sonatype.org/content/repositories/snapshots/com/arcticicestudio/icecore-json"><img src="https://img.shields.io/badge/oss--sonatype---_-blue.svg"/></a> <img src="https://bintray.com/favicon.ico" width=24 height=24/> <a href='https://bintray.com/arcticicestudio/IceCore/icecore-json/_latestVersion'><img src='https://api.bintray.com/packages/arcticicestudio/IceCore/icecore-json/images/download.svg'></a> <a href="https://oss.jfrog.org/webapp/#/artifacts/browse/tree/General/oss-snapshot-local/com/arcticicestudio/icecore-json"><img src="https://img.shields.io/badge/artifactory---_-green.svg"/></a></p>

<p align="center">A fast, minimal and simple <a href="http://json.org">JSON</a> parser and writer.</p>

---

It's not an object mapper, but a bare-bones library that aims at being
  - **lightweight**: object representation with minimal memory footprint (e.g. no HashMaps)
  - **simple**: reading, writing and modifying JSON with minimal code (short names, fluent style)
  - **fast**: high performance comparable with other state-of-the-art parsers
  - **minimal**: no dependencies, single package with just a few classes, small download size (< 35kB)

## Getting started
### Setup
To use icecore-json it must be available on your classpath.  
You can use it as a dependency for your favorite build tool or [download the latest version](https://github.com/arcticicestudio/icecore-json/releases/latest).

<img src="http://apache.org/favicons/favicon.ico" width=16 height=16/> <a href="https://maven.apache.org">Apache Maven</a>
```xml
<dependency>
  <groupId>com.arcticicestudio</groupId>
  <artifactId>icecore-json</artifactId>
  <version>0.8.0-frost.1</version>
</dependency>
```

<img src="https://gradle.org/wp-content/uploads/fbrfg/favicon.ico" width=16 height=16/> <a href="https://gradle.org">Gradle</a>
```java
compile(group: 'com.arcticicestudio', name: 'icecore-json', version: '0.8.0-frost.1')
```

<img src="http://apache.org/favicons/favicon.ico" width=16 height=16/> <a href="https://ant.apache.org/ivy">Apache Ivy</a>
```xml
<dependency org="com.arcticicestudio" name="icecore-json" rev="0.8.0-frost.1" />
```

Development snapshots are available via [OSS Sonatype](https://oss.sonatype.org/content/repositories/snapshots/com/arcticicestudio/icecore-json).

### Build
Build and install icecore-json into your local repository without GPG signing:  
```
mvn clean install
```

Signed artifacts may be build by using the `sign-gpg` profile with a provided `gpg.keyname` property:
```
mvn clean install -Dgpg.keyname=YourGPGKeyId
```

Continuous integration builds are running at [Travis CI](https://travis-ci.org/arcticicestudio/icecore-json) and [Circle CI](https://circleci.com/bb/arcticicestudio/icecore-json).

## Usage Guide
This is a basic guide to show the common usage of the icecore-json API.  
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
  - [Concurrency](#concurrency)

### Parse JSON
You can parse JSON from a `String` or from a `java.io.Reader`.  
You *don't* need to wrap your reader in a `BufferedReader`, as the parse method uses a reading buffer.  
```java
JsonValue value = Json.parse(string);
```

### JSON values
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
This subtype of `JsonValue` provides a `get` method to access the elements of a JSON array.  
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
Members of a JSON object can be accessed by name using the `get` method:  
```java
JsonObject object = Json.parse(input).asObject();
String name = object.get("name").asString();
int quantity = object.get("quantity").asInt();
```

There are also shorthand methods like `getString`, `getInt`, `getDouble`, etc. that directly return the expected type.  
These methods require a default value that is returned when the member is not found.  
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

### Example
This is a example on how to extract nested contents:  
```json
{
  "order": 4378,
  "items": [
    {
      "name": "Yogurt",
      "id": "28469",
      "quantity": 5
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

#### Create JSON values
The entrypoint class `Json` also has methods to create instances of `JsonValue` from Java strings, numbers, and boolean values.  
```java
JsonValue name = Json.value("Yogurt");
JsonValue protein = Json.value(28);
```

And there are methods for creating empty arrays and objects as well.  
Use these together with `add` to create data structures.  
```java
JsonObject article = Json.object().add("name", "Yogurt").add("protein", 28);
// -> {"name": "Yogurt", "protein": 28}

JsonArray article = Json.array().add("Bob").add(16);
// -> ["Coconut", 16]
```

You can also create JSON arrays conveniently from Java arrays such as `String[]`, `int[]`, `boolean[]`, etc.:  
```java
String[] javaNames = {"Yogurt", "Coconut"};
JsonArray jsonNames = Json.array(names);
```

#### Modify JSON arrays and objects
You can replace or remove array elements based on their index.  
The index must be valid.  
```java
jsonArray.set(1, 24);
jsonArray.remove(1);
```

Likewise, members of JSON objects can be modified by their name.  
If the name does not exist, `set` will append a new member.  
```java
jsonObject.set("quantity", 12);
jsonObject.remove("quantity");
```

`JsonObject` also provides a `merge` method that copies all members from a given JSON object:  
```java
jsonObject.merge(otherObject);
```

#### Output JSON
The `toString` method of a `JsonValue` returns valid JSON strings.  
You can also write to a `java.io.Writer` using `writeTo`:  
```java
String json = jsonValue.toString();
jsonValue.writeTo(writer);
```

Both methods accept an additonal parameter to enable formatted output.  
```java
String json = jsonValue.toString(WriterConfig.PRETTY_PRINT);
jsonValue.writeTo(writer, WriterConfig.PRETTY_PRINT);
```

## Concurrency
The JSON structures in this library (`JsonObject` and `JsonArray`) are deliberately **not thread-safe** to keep them fast and simple.  
In the rare case that JSON data structures must be accessed from multiple threads, while at least one of these threads modifies their contents, the application must ensure proper synchronization.

Iterators will throw a `ConcurrentModificationException` when the contents of a JSON structure have been modified after the creation of the iterator.

---

## Development
[![](https://img.shields.io/badge/Changelog-0.8.0--frost.1-blue.svg)](https://github.com/arcticicestudio/icecore-strman/blob/v0.8.0-frost.1/CHANGELOG.md) [![](https://img.shields.io/badge/Workflow-gitflow_Branching_Model-blue.svg)](http://nvie.com/posts/a-successful-git-branching-model) [![](https://img.shields.io/badge/Versioning-ArcVer_0.8.0-blue.svg)](https://github.com/arcticicestudio/arcver)

### Contribution
Please report issues/bugs, feature requests and suggestions for improvements to the [issue tracker](https://github.com/arcticicestudio/icecore-json/issues).

## Credits
The codebase of this project is inspired by the awesome [minimal-json](https://github.com/ralfstx/minimal-json) project.

---

<p align="center"> <img src="http://arcticicestudio.com/favicon.ico" width=16 height=16/> Copyright &copy; 2016 Arctic Ice Studio</p>

<p align="center"><a href="http://www.apache.org/licenses/LICENSE-2.0"><img src="https://img.shields.io/badge/License-Apache_2.0-blue.svg"/></a></p>
