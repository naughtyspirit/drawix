package com.naughtyspirit.drawix.primitive;

import com.naughtyspirit.drawix.collision.BoundingShape;
import com.naughtyspirit.drawix.collision.HasBoundingShape;
import com.naughtyspirit.drawix.collision.RectangleBounding;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static android.opengl.GLES10.*;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 25-12-2011
 */
public class Rectangle extends BaseDrawablePrimitive implements HasBoundingShape {

  private final List<Vertex> vertexList = new ArrayList<Vertex>();

  public Rectangle(Vertex origin, Vertex destination) {
    super(origin);
    vertexList.add(origin);

    // c
    vertexList.add(new Vertex(origin.getX(), destination.getY()));
    // d
    vertexList.add(new Vertex(destination.getX(), origin.getY()));

    // c
    vertexList.add(new Vertex(origin.getX(), destination.getY()));
    // d
    vertexList.add(new Vertex(destination.getX(), origin.getY()));

    vertexList.add(destination);
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
    glDrawArrays(GL_TRIANGLES, 3, 3);
  }

  @Override
  public BoundingShape getBoundingShape() {
    return new RectangleBounding(vertexList.get(1), getWidth(), getHeight());
  }

  private float getHeight() {
    return vertexList.get(0).distanceTo(vertexList.get(1));
  }

  private float getWidth() {
    return vertexList.get(1).distanceTo(vertexList.get(2));
  }
}
