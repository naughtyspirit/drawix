package com.naughtyspirit.drawix.primitive;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES10.*;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 25-12-2011
 */
public class Circle extends BaseDrawablePrimitive {

  private final float radius;

  public Circle(Vertex origin, float radius) {
    super(origin);
    this.radius = radius;
  }

  @Override
  public void draw() {
    int segments = 10;
    int a = 0;
    ByteBuffer vbb = ByteBuffer.allocateDirect(4 * 2 * (segments + 2));
    vbb.order(ByteOrder.nativeOrder());
    FloatBuffer vertices = vbb.asFloatBuffer();
    int additionalSegment = 1;

    final float coefficient = 2.0f * (float) Math.PI / segments;

    for (int i = 0; i <= segments; i++) {
      float rads = i * coefficient;
      float j = (float) (radius * Math.cos(rads + a) + getOrigin().getX());
      float k = (float) (radius * Math.sin(rads + a) + getOrigin().getY());

      vertices.put(j);
      vertices.put(k);
    }
    vertices.put(getOrigin().getY());
    vertices.put(getOrigin().getX());
    vertices.position(0);
    glVertexPointer(2, GL_FLOAT, 0, vertices);

    glDrawArrays(GL_LINE_STRIP, 0, segments + additionalSegment);
  }
}
