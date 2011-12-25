package com.naughtyspirit.drawix.primitive;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.List;

import static android.opengl.GLES10.*;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 25-12-2011
 */
public class Triangle extends BaseDrawablePrimitive {

  private List<Vertex> vertexList = new LinkedList<Vertex>();

  public Triangle(Vertex origin, Vertex b, Vertex c) {
    super(origin);
    vertexList.add(origin);
    vertexList.add(b);
    vertexList.add(c);
  }

  @Override
  public void draw() {
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertexList.size() * 2 * 4);
    byteBuffer.order(ByteOrder.nativeOrder());
    FloatBuffer vertices = byteBuffer.asFloatBuffer();
    for (Vertex vertex : vertexList) {
      vertices.put(vertex.getX());
      vertices.put(vertex.getY());
    }
    vertices.flip();
    vertices.position(0);
    glVertexPointer(2, GL_FLOAT, 0, vertices);
    glDrawArrays(GL_TRIANGLES, 0, 3);
  }
}
