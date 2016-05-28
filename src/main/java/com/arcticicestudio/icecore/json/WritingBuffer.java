/*
+++++++++++++++++++++++++++++++++++++++++++
title     Writing Buffer                  +
project   icecore-json                    +
file      WritingBuffer.java              +
version   0.5.0                           +
author    Arctic Ice Studio               +
email     development@arcticicestudio.com +
website   http://arcticicestudio.com      +
copyright Copyright (C) 2016              +
created   2016-05-28 14:57 UTC+0200       +
modified  2016-05-28 15:09 UTC+0200       +
+++++++++++++++++++++++++++++++++++++++++++

[Description]
A lightweight writing buffer to reduce the amount of write operations to be performed on the underlying writer.

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

/**
 * A lightweight writing buffer to reduce the amount of write operations to be performed on the underlying writer.
 * <p>
 * <strong>This implementation is not thread-safe.</strong><br>
 * It deliberately deviates from the contract of {@link java.io.Writer}.
 * In particular, it does not flush or close the wrapped writer nor does it ensure that the wrapped writer is open.
 * </p>
 *
 * @author Arctic Ice Studio &lt;development@arcticicestudio.com&gt;
 * @since 0.5.0
 */
class WritingBuffer extends Writer {

  private final Writer writer;
  private final char[] buffer;
  private int fill = 0;

  WritingBuffer(Writer writer) {
    this(writer, 16);
  }

  WritingBuffer(Writer writer, int bufferSize) {
    this.writer = writer;
    buffer = new char[bufferSize];
  }

  @Override
  public void write(int c) throws IOException {
    if (fill > buffer.length - 1) {
      flush();
    }
    buffer[fill++] = (char)c;
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    if (fill > buffer.length - len) {
      flush();
      if (len > buffer.length) {
        writer.write(cbuf, off, len);
        return;
      }
    }
    System.arraycopy(cbuf, off, buffer, fill, len);
    fill += len;
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    if (fill > buffer.length - len) {
      flush();
      if (len > buffer.length) {
        writer.write(str, off, len);
        return;
      }
    }
    str.getChars(off, off + len, buffer, fill);
    fill += len;
  }

  /**
   * Flushes the internal buffer but does not flush the wrapped writer.
   */
  @Override
  public void flush() throws IOException {
    writer.write(buffer, 0, fill);
    fill = 0;
  }

  /**
   * Does not close or flush the wrapped writer.
   */
  @Override
  public void close() throws IOException {}
}