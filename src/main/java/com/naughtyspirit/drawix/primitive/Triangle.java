package com.naughtyspirit.drawix.primitive;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.List;

import static android.opengl.GLES10.*;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 12/25/11
 */
public class Triangle {
  private List<Vertex> vertexList = new LinkedList<Vertex>();

  public Triangle(Vertex a, Vertex b, Vertex c) {
    vertexList.add(a);
    vertexList.add(b);
    vertexList.add(c);
  }

  public void draw() {
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertexList.size() * 2 * 4);
    byteBuffer.order(ByteOrder.nativeOrder());
    FloatBuffer vertices = byteBuffer.asFloatBuffer();
    for(Vertex vertex : vertexList) {
      vertices.put(vertex.getX());
      vertices.put(vertex.getY());
    }
    vertices.flip();
    vertices.position(0);
    glVertexPointer(2, GL_FLOAT, 0, vertices);
    glDrawArrays(GL_TRIANGLES, 0, 3);
  }
}
