package com.naughtyspirit.drawix.primitive;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES10.*;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 25-12-2011
 */
public class Line extends BaseDrawablePrimitive {

  private final Vertex destination;

  public Line(Vertex origin, Vertex destination) {
    super(origin);
    this.destination = destination;
  }

  public Vertex getDestination() {
    return destination;
  }

  @Override
  protected void doDraw() {
    ByteBuffer buffer = ByteBuffer.allocateDirect(4 * 2 * 2);
    buffer.order(ByteOrder.nativeOrder());
    FloatBuffer vertices = buffer.asFloatBuffer();

    vertices.put(getOrigin().getX());
    vertices.put(getOrigin().getY());
    vertices.put(destination.getX());
    vertices.put(destination.getY());
    vertices.position(0);

    glVertexPointer(2, GL_FLOAT, 0, vertices);
    glDrawArrays(GL_LINES, 0, 2);
  }
}
