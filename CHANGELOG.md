IceCore - JSON
==============

## 0.6.0 (2016-05-28) - API
This version includes the API entrypoint class `Json` to write and parse JSON.

### Features
#### API
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

#### Documentation
  - Added a basic "User Guide" to the README to show the common usage of the icecore-json API.
  - Added "Concurrency" section to inform about the thread-safety of the JSON library structures

## 0.5.0 (2016-05-28)
**NOTE**: This is a development-progressive-only version and represents a non-executable state!**

### Features
#### JSON Writing
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

## 0.4.0 (2016-05-28)
**NOTE**: This is a development-progressive-only version and represents a non-executable state!**

### Features
#### JSON Parsing
  - Implemented new classes:

| Class | Description |
| ----- | ----------- |
| `com.arcticicestudio.icecore.json.JsonParser` | Represents a JSON parser. |
| `com.arcticicestudio.icecore.json.ParseException` | Represents an unchecked exception to indicate that an input does not qualify as valid JSON. |

## 0.3.0 (2016-05-28)
**NOTE**: This is a development-progressive-only version and represents a non-executable state!**

### Features
#### JSON Literal Representations
  - Implemented new classes:

| Class | Description |
| ----- | ----------- |
| `com.arcticicestudio.icecore.json.JsonLiteral` | Represents a JSON literal. |

## 0.2.0 (2016-05-28)
**NOTE**: This is a development-progressive-only version and represents a non-executable state!**

### Features
#### JSON Structure Representations
  - Implemented new classes:

| Class | Description |
| ----- | ----------- |
| `com.arcticicestudio.icecore.json.JsonArray` | Represents a JSON array, an ordered collection of JSON values. |
| `com.arcticicestudio.icecore.json.JsonObject` | Represents a JSON object, a set of name/value pairs, where the names are strings and the values are JSON values. |

## 0.1.0 (2016-05-28)
**NOTE**: This is a development-progressive-only version and represents a non-executable state!**

### Features
#### JSON Value Representations
  - Implemented new classes:

| Class | Description |
| ----- | ----------- |
| `com.arcticicestudio.icecore.json.JsonValue` | Represents a JSON value. |
| `com.arcticicestudio.icecore.json.JsonNumber` | Represents a JSON number. |
| `com.arcticicestudio.icecore.json.JsonString` | Represents a JSON string. |

## 0.0.0 (2016-05-27) - Project Initialization
