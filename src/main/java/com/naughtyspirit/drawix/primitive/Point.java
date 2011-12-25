package com.naughtyspirit.drawix.primitive;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES10.*;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 12/25/11
 */
public class Point {
  private final Vertex a;

  public Point(Vertex a) {
    this.a = a;
  }

  public Vertex getA() {
    return a;
  }

  public void draw() {
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(2 * 2 * 4);
    byteBuffer.order(ByteOrder.nativeOrder());
    FloatBuffer vertices = byteBuffer.asFloatBuffer();
    vertices.put(a.getX());
    vertices.put(a.getY());
    vertices.flip();
    vertices.position(0);
    glVertexPointer(2, GL_FLOAT, 0, vertices);
    glDrawArrays(GL_POINTS, 0, 1);
  }
}
