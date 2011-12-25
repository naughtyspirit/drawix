package com.naughtyspirit.drawix.primitive;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.LinkedList;
import java.util.List;

import static android.opengl.GLES10.*;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 12/25/11
 */
public class Rectangle {
  private final List<Vertex> vertexList = new LinkedList<Vertex>();

  public Rectangle(Vertex a, Vertex b) {
    vertexList.add(a);

    // c
    vertexList.add(new Vertex(a.getX(), b.getY()));
    // d
    vertexList.add(new Vertex(b.getX(), a.getY()));

    // c
    vertexList.add(new Vertex(a.getX(), b.getY()));
    // d
    vertexList.add(new Vertex(b.getX(), a.getY()));

    vertexList.add(b);
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
    glDrawArrays(GL_TRIANGLES, 3, 3);
  }
}
