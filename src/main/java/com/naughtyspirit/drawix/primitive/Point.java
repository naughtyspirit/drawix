package com.naughtyspirit.drawix.primitive;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES10.*;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 25-12-2011
 */
public class Point extends BaseDrawablePrimitive {

  public Point(Vertex origin) {
    super(origin);
  }

  @Override
  public void draw() {
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(2 * 2 * 4);
    byteBuffer.order(ByteOrder.nativeOrder());
    FloatBuffer vertices = byteBuffer.asFloatBuffer();
    vertices.put(getOrigin().getX());
    vertices.put(getOrigin().getY());
    vertices.flip();
    vertices.position(0);
    glVertexPointer(2, GL_FLOAT, 0, vertices);
    glDrawArrays(GL_POINTS, 0, 1);
  }
}
