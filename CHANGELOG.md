<p align="center"><img src="https://bitbucket.org/arcticicestudio/icecore-json/raw/develop/src/main/assets/icecore-json-logo-banner.svg"/></p>

<p align="center"><img src="https://bitbucket.org/favicon.ico" width=24 height=24/> <a href="https://bitbucket.org/arcticicestudio/icecore-json/downloads"><img src="https://img.shields.io/badge/release-0.7.0-blue.svg"/></a> <a href="https://bitbucket.org/arcticicestudio/icecore-json/downloads"><img src="https://img.shields.io/badge/pre--release-0.8.0--frost.1-blue.svg"/></a> <img src="http://central.sonatype.org/favicon.ico" width=24 height=24/> <a href="http://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22com.arcticicestudio%22%20AND%20a%3A%22icecore-json%22"><img src="https://img.shields.io/maven-central/v/com.arcticicestudio/icecore-json.svg"/></a> <img src="https://oss.sonatype.org/favicon.ico"/> <a href="https://oss.sonatype.org/content/repositories/snapshots/com/arcticicestudio/icecore-json"><img src="https://img.shields.io/badge/snapshot----blue.svg"/></a> <img src="https://bintray.com/favicon.ico" width=24 height=24/> <a href='https://bintray.com/arcticicestudio/IceCore/icecore-json/_latestVersion'><img src='https://api.bintray.com/packages/arcticicestudio/IceCore/icecore-json/images/download.svg'></a></p>

---

# 0.8.0-frost.1 (2016-06-23)
## Features
### API
  - Implemented the `+ getVersion():String` method to return the [ArcVer][arcver-github]- and [SemVer][semver] compatible version.

## Improvements
### Documentation
  - Improved JavaDoc for the overriden `+ equals(Object):boolean` methods to explain the conditions which imply equality for `JsonObject` and `JsonArray`.
  In particular, point out that `JsonObject`s are considered equal only if the members have the same order.

## Bug Fixes
### API
  - Fixed the static method `+ Json.array():JsonValue` to return the correct type `JsonArray` instead of `JsonValue`.
  New method signature: `+ Json.array():JsonArray`

# 0.8.0-frost.0 (2016-06-04) - Streaming API
## Features
### Streaming API
The new streaming API allows processing with events to a given JSON handler while reading input.
It makes it possible to
  - register a handler that receives callbacks during parsing
  - avoid creating an object representation of the entire input in this mode
  - interrupt the parsing at any point

The `JsonParser` now issues events to a given `JsonHandler` instead of creating a `JsonValue` itself.

#### Use cases
##### Reading without creating data
**Example: Syntax highlighting in an editor**  
For this case you don't care about the content, only about the text positions where syntactical elements begin and end.

**Example: Analyzing/Verifying the structure of a JSON**  
In this case, the content would matter (e.g. object names and value types), but you would not build up a structure.

##### Creating a custom data structure without intermediate `JsonValue`
This could be useful because
  - the application requires a custom data types type anyway and it's faster to create the right structure right away
  - a custom data structure could be more lightweight by immediately transforming or even skipping values

Example:  
A custom document handler would know that the value for an object member named "time" is a string representing a time.
Instead of storing the string in a `JsonValue`, it could store it in a `Date` field of the object being created.
There should be no `JsonValue` involved unless this type is part of the custom data structure.

##### Skipping parts of the inputs
The application wants to skip parts of a long document.
This could be useful because
  - the document is too large to fit in memory
  - skipping parts of the document could improve reading performance

The parser still needs to read every single character, but by telling it to skip a part, it could avoid filling the capture buffer and save CPU cycles.

| Class | Description |
| ----- | ----------- |
| `com.arcticicestudio.icecore.json.JsonHandler` | A handler for parser events. Instances of this class can be given to `JsonParser`. The parser will then call the methods of the given handler while reading the input. |
| `com.arcticicestudio.icecore.json.Location` | An immutable object that represents a location in the parsed text. |

## Tests
### Streaming API
  - Implemented new unit tests and adjusted existing for the streaming API:

| Class | Method | Description |
| ----- | ------ | ----------- |
| `com.arcticicestudio.icecore.json.JsonParserTest` | `+ setUp():void` | - |
| `com.arcticicestudio.icecore.json.JsonParserTest` | `+ constructorRejectsNullHandler():void` | - |
| `com.arcticicestudio.icecore.json.JsonParserTest` | `+ parseStringRejectsNull():void` | - |
| `com.arcticicestudio.icecore.json.JsonParserTest` | `+ parseReaderRejectsNull():void {exceptions=IOException}` | - |
| `com.arcticicestudio.icecore.json.JsonParserTest` | `+ parseReaderRejectsNegativeBufferSize():void {exceptions=IOException}` | - |
| `com.arcticicestudio.icecore.json.JsonParserTest` | `+ parseNull():void` | - |
| `com.arcticicestudio.icecore.json.JsonParserTest` | `+ parseTrue():void` | - |
| `com.arcticicestudio.icecore.json.JsonParserTest` | `+ parseFalse():void` | - |
| `com.arcticicestudio.icecore.json.JsonParserTest` | `+ parseString():void` | - |
| `com.arcticicestudio.icecore.json.JsonParserTest` | `+ parseStringEmpty():void` | - |
| `com.arcticicestudio.icecore.json.JsonParserTest` | `+ parseNumber():void` | - |
| `com.arcticicestudio.icecore.json.JsonParserTest` | `+ parseArray():void` | - |
| `com.arcticicestudio.icecore.json.JsonParserTest` | `+ parseArrayEmpty():void` | - |
| `com.arcticicestudio.icecore.json.JsonParserTest` | `+ parseObjectEmpty():void` | - |
| `com.arcticicestudio.icecore.json.JsonParserTest` | `+ parseCanBeCalledTwice():void` | - |

### JSON Object
  - Implemented test methods for `JsonObject` with repeated properties names:

| Class | Method | Description |
| ----- | ------ | ----------- |
| `com.arcticicestudio.icecore.json.JsonObjectTest` | `+ keyRepetitionAllowsMultipleEntries():void` | - |
| `com.arcticicestudio.icecore.json.JsonObjectTest` | `+ keyRepetitionGetsLastEntry():void` | - |
| `com.arcticicestudio.icecore.json.JsonObjectTest` | `+ keyRepetitionEqualityConsidersRepetitions():void` | - |

## Improvements
### JSON Parser
  - The start column numbers now start at `1`
    Most editors regard the first character position of a text as `1:1`.
    This seems more logical than `1:0` which is currently returned for the first position by `icecore-json`.
    Adapt this established pattern and let column numbers start at `1`, as line numbers do.

    With this change, there is an easy rule:
      - `offset` is for programs, it starts at `0`
      - `line` and `column` are for humans, they start at `1`

    This leads to a slight API change in ParseException.
    However, since the effect can only be seen in the case of an error, it seems acceptable.
  - The `readArray():void` method now increments before comparing to the `MAX_NESTING_LEVEL` constant to make the condition more expressive

## Bug Fixes
### JSON Parser
  - Replaced a fixed number for the maximal nesting level with the corresponding constant `MAX_NESTING_LEVEL`

### Unit Tests
  - Fixed invalid test data where values are not matching the specified statements

# 0.7.0 (2016-05-29) - API
## Improvements
### JSON Parser
  - Prevent stack overflow on deeply nested input.
  When the input is too deeply nested, the parser can run into a stack overflow.
  `StackOverflowError` is a VM Error, as such it should not be catched but rather be prevented.
  The parser will now throw a `ParseException` when the nesting level exceeds `1000`.

## Tests
  Implemented new test classes:

| Class | Description |
| ----- | ----------- |
| `com.arcticicestudio.icecore.json.JsonArrayTest` | Tests the JSON array structure representation class `JsonArray`. |
| `com.arcticicestudio.icecore.json.JsonLiteralTest` | Tests the JSON literals representation class `JsonLiteral`. |
| `com.arcticicestudio.icecore.json.JsonNumberTest` | Tests the JSON number-values representation class `JsonNumber`. |
| `com.arcticicestudio.icecore.json.JsonObjectTest` | Tests the JSON object structure representation class `JsonObject`. |
| `com.arcticicestudio.icecore.json.JsonParserTest` | Tests the JSON parser class `JsonParser`. |
| `com.arcticicestudio.icecore.json.JsonStringTest` | Tests the JSON string value representation class `JsonString`. |
| `com.arcticicestudio.icecore.json.JsonTest` | Tests the JSON API class `Json`. |
| `com.arcticicestudio.icecore.json.JsonValue` | Tests the JSON values representation class `JsonValue`. |
| `com.arcticicestudio.icecore.json.JsonWriter` | Tests the JSON writer class `JsonWriter`. |
| `com.arcticicestudio.icecore.json.ParseException` | Tests the JSON parser exception class `ParseException`. |
| `com.arcticicestudio.icecore.json.PrettyPrint` | Tests the JSON writer pretty print class `PrettyPrint`. |
| `com.arcticicestudio.icecore.json.TestUtil` | Provides test utils. |
| `com.arcticicestudio.icecore.json.WritingBuffer` | Tests the JSON writer writing buffer class `WritingBuffer`. |
| `com.arcticicestudio.icecore.json.mocking.TypeMockingTest` | Tests mocking to make sure types do not prevent mocking by final or visibility constructs. |

# 0.6.0 (2016-05-28) - API
This version includes the API entrypoint class `Json` to write and parse JSON.

## Features
### API
  - Implemented new classes:

| Class | Description |
| ----- | ----------- |
| `com.arcticicestudio.icecore.json.Json` | Serves as the entry point to the "IceCore - JSON" public API. |
| `com.arcticicestudio.icecore.json.package-info` | Package Info for `com.arcticicestudio.icecore.json`. |

  - Implemented new `add` methods to make use of the API:

| Class | Method | Throws | Description |
| ----- | ------ | ------ | ----------- |
| `com.arcticicestudio.icecore.json.JsonObject` | `+ add(String, int) : JsonObject` | - | Appends a new member to the end of this object, with the specified name and the JSON representation of the specified `int` value. |
| `com.arcticicestudio.icecore.json.JsonObject` | `+ add(String, long) : JsonObject` | - | Appends a new member to the end of this object, with the specified name and the JSON representation of the specified `long` value. |
| `com.arcticicestudio.icecore.json.JsonObject` | `+ add(String, float) : JsonObject` | - | Appends a new member to the end of this object, with the specified name and the JSON representation of the specified `float` value. |
| `com.arcticicestudio.icecore.json.JsonObject` | `add(String, double) : JsonObject` | - | Appends a new member to the end of this object, with the specified name and the JSON representation of the specified `double` value. |
| `com.arcticicestudio.icecore.json.JsonObject` | `+ add(String, boolean) : JsonObject` | - | Appends a new member to the end of this object, with the specified name and the JSON representation of the specified `boolean` value. |
| `com.arcticicestudio.icecore.json.JsonObject` | `+ add(String, String) : JsonObject` | - | Appends a new member to the end of this object, with the specified name and the JSON representation of the specified string. |
| `com.arcticicestudio.icecore.json.JsonObject` | `+ set(String, int) : JsonObject` | - | Sets the value of the member with the specified name to the JSON representation of the specified `int` value. |
| `com.arcticicestudio.icecore.json.JsonObject` | `+ set(String, long) : JsonObject` | - | Sets the value of the member with the specified name to the JSON representation of the specified `long` value. |
| `com.arcticicestudio.icecore.json.JsonObject` | `+ set(String, float) : JsonObject` | - | Sets the value of the member with the specified name to the JSON representation of the specified `float` value. |
| `com.arcticicestudio.icecore.json.JsonObject` | `+ set(String, double) : JsonObject` | - | Sets the value of the member with the specified name to the JSON representation of the specified `double` value. |
| `com.arcticicestudio.icecore.json.JsonObject` | `+ set(String, String) : JsonObject` | - | Sets the value of the member with the specified name to the JSON representation of the specified string. |

### Documentation
  - Added a basic "User Guide" to the README to show the common usage of the icecore-json API.
  - Added "Concurrency" section to inform about the thread-safety of the JSON library structures

# 0.5.0 (2016-05-28)
**NOTE**: This is a development-progressive-only version and represents a non-executable state!**

## Features
### JSON Writing
  - Implemented new classes:

| Class | Description |
| ----- | ----------- |
| `com.arcticicestudio.icecore.json.JsonWriter` | Writes the generated JSON object. |
| `com.arcticicestudio.icecore.json.PrettyPrint` | Enables human readable JSON output by inserting whitespace between values after commas and colons. |
| `com.arcticicestudio.icecore.json.WriterConfig` | Controls the formatting of the JSON output. It can be used by one of the available constants. |
| `com.arcticicestudio.icecore.json.WritingBuffer` | A lightweight writing buffer to reduce the amount of write operations to be performed on the underlying writer. |

  - Implemented new methods:

| Class | Method | Throws | Description |
| ----- | ------ | ------ | ----------- |
| `com.arcticicestudio.icecore.json.JsonArray` | `@Override write(JsonWriter) : void` | `IOException` | - |
| `com.arcticicestudio.icecore.json.JsonLiteral` | `@Override write(JsonWriter) : void` | `IOException` | - |
| `com.arcticicestudio.icecore.json.JsonNumber` | `@Override write(JsonWriter) : void` | `IOException` | - |
| `com.arcticicestudio.icecore.json.JsonObject` | `@Override write(JsonWriter) : void` | `IOException` | - |
| `com.arcticicestudio.icecore.json.JsonString` | `@Override write(JsonWriter) : void` | `IOException` | - |
| `com.arcticicestudio.icecore.json.JsonValue` | `@Override writeTo(Writer) : void` | `IOException` | Writes the JSON representation of this value to the given writer in its minimal form, without any additional whitespace. |
| `com.arcticicestudio.icecore.json.JsonValue` | `writeTo(Writer,WriterConfig) : void` | `IOException` | Writes the JSON representation of this value to the given writer using the given formatting. |
| `com.arcticicestudio.icecore.json.JsonValue` | `@Override toString() : String` | - | Returns the JSON string for this value in its minimal form, without any additional whitespace. |
| `com.arcticicestudio.icecore.json.JsonValue` | `@Override toString(WriterConfig) : String` | - | Returns the JSON string for this value using the given formatting. |
| `com.arcticicestudio.icecore.json.JsonValue` | `abstract write(JsonWriter) : String` | `IOException` | - |

# 0.4.0 (2016-05-28)
**NOTE**: This is a development-progressive-only version and represents a non-executable state!**

## Features
### JSON Parsing
  - Implemented new classes:

| Class | Description |
| ----- | ----------- |
| `com.arcticicestudio.icecore.json.JsonParser` | Represents a JSON parser. |
| `com.arcticicestudio.icecore.json.ParseException` | Represents an unchecked exception to indicate that an input does not qualify as valid JSON. |

# 0.3.0 (2016-05-28)
**NOTE**: This is a development-progressive-only version and represents a non-executable state!**

## Features
### JSON Literal Representations
  - Implemented new classes:

| Class | Description |
| ----- | ----------- |
| `com.arcticicestudio.icecore.json.JsonLiteral` | Represents a JSON literal. |

# 0.2.0 (2016-05-28)
**NOTE**: This is a development-progressive-only version and represents a non-executable state!**

## Features
### JSON Structure Representations
  - Implemented new classes:

| Class | Description |
| ----- | ----------- |
| `com.arcticicestudio.icecore.json.JsonArray` | Represents a JSON array, an ordered collection of JSON values. |
| `com.arcticicestudio.icecore.json.JsonObject` | Represents a JSON object, a set of name/value pairs, where the names are strings and the values are JSON values. |

# 0.1.0 (2016-05-28)
**NOTE**: This is a development-progressive-only version and represents a non-executable state!**

## Features
### JSON Value Representations
  - Implemented new classes:

| Class | Description |
| ----- | ----------- |
| `com.arcticicestudio.icecore.json.JsonValue` | Represents a JSON value. |
| `com.arcticicestudio.icecore.json.JsonNumber` | Represents a JSON number. |
| `com.arcticicestudio.icecore.json.JsonString` | Represents a JSON string. |

# 0.0.0 (2016-05-27) - Project Initialization

[arcver-github]: https://github.com/arcticicestudio/arcver
[semver]: http://semver.org
