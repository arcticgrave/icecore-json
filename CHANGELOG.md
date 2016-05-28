IceCore - JSON
==============

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
