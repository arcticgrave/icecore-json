/*
+++++++++++++++++++++++++++++++++++++++++++
title     Pretty Print                    +
project   icecore-json                    +
file      PrettyPrint.java                +
version   0.8.0-frost.1                   +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-28 14:44 UTC+0200       +
modified  2016-05-28 14:55 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
Enables human readable JSON output by inserting whitespace between values after commas and colons.

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
import java.io.Writer;
import java.util.Arrays;

/**
 * Enables human readable JSON output by inserting whitespace between values after commas and colons.
 * <p>
 *   Example:<br>
 *   <pre>
 *     jsonValue.writeTo(writer, PrettyPrint.singleLine());
 *   </pre>
 * </p>
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.5.0
 */
public class PrettyPrint extends WriterConfig {

  private final char[] indentChars;

  protected PrettyPrint(char[] indentChars) {
    this.indentChars = indentChars;
  }

  /**
   * Print every value on a separate line.
   * <p>
   *   Use tabs ({@code \t}) for indentation.
   * </p>
   *
   * @return A PrettyPrint instance for wrapped mode with tab indentation
   */
  public static PrettyPrint singleLine() {
    return new PrettyPrint(null);
  }

  /**
   * Print every value on a separate line.
   * <p>
   *   Use the given number of spaces for indentation.
   * </p>
   *
   * @param number The number of spaces to use
   * @return A PrettyPrint instance for wrapped mode with spaces indentation
   */
  public static PrettyPrint indentWithSpaces(int number) {
    if (number < 0) {
      throw new IllegalArgumentException("number is negative");
    }
    char[] chars = new char[number];
    Arrays.fill(chars, ' ');
    return new PrettyPrint(chars);
  }

  /**
   * Do not break lines, but still insert whitespace between values.
   *
   * @return A PrettyPrint instance for single-line mode
   */
  public static PrettyPrint indentWithTabs() {
    return new PrettyPrint(new char[] {'\t'});
  }

  @Override
  protected JsonWriter createWriter(Writer writer) {
    return new PrettyPrintWriter(writer, indentChars);
  }

  private static class PrettyPrintWriter extends JsonWriter {

    private final char[] indentChars;
    private int indent;

    private PrettyPrintWriter(Writer writer, char[] indentChars) {
      super(writer);
      this.indentChars = indentChars;
    }

    @Override
    protected void writeArrayOpen() throws IOException {
      indent++;
      writer.write('[');
      writeNewLine();
    }

    @Override
    protected void writeArrayClose() throws IOException {
      indent--;
      writeNewLine();
      writer.write(']');
    }

    @Override
    protected void writeArraySeparator() throws IOException {
      writer.write(',');
      if (!writeNewLine()) {
        writer.write(' ');
      }
    }

    @Override
    protected void writeObjectOpen() throws IOException {
      indent++;
      writer.write('{');
      writeNewLine();
    }

    @Override
    protected void writeObjectClose() throws IOException {
      indent--;
      writeNewLine();
      writer.write('}');
    }

    @Override
    protected void writeMemberSeparator() throws IOException {
      writer.write(':');
      writer.write(' ');
    }

    @Override
    protected void writeObjectSeparator() throws IOException {
      writer.write(',');
      if (!writeNewLine()) {
        writer.write(' ');
      }
    }

    private boolean writeNewLine() throws IOException {
      if (indentChars == null) {
        return false;
      }
      writer.write('\n');
      for (int i = 0; i < indent; i++) {
        writer.write(indentChars);
      }
      return true;
    }
  }
}