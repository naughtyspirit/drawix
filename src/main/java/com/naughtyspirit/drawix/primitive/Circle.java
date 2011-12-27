package com.naughtyspirit.drawix.primitive;

import com.naughtyspirit.drawix.collision.BoundingShape;
import com.naughtyspirit.drawix.collision.CircleBounding;
import com.naughtyspirit.drawix.collision.HasBoundingShape;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES10.*;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 25-12-2011
 */
public class Circle extends BaseDrawablePrimitive implements HasBoundingShape {

  private final float radius;

  public Circle(Vertex origin, float radius) {
    super(origin);
    this.radius = radius;
  }

  @Override
  public BoundingShape getBoundingShape() {
    return new CircleBounding(getOrigin(), radius);
  }

  @Override
  public void draw() {
    int segments = 1000;
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glEnable(GL_LINE_SMOOTH);
    ByteBuffer buffer = ByteBuffer.allocateDirect(4 * 2 * (segments + 2));
    buffer.order(ByteOrder.nativeOrder());
    FloatBuffer vertices = buffer.asFloatBuffer();

    final float coefficient = 2.0f * (float) Math.PI / segments;

    for (int i = 0; i <= segments; i++) {
      float rads = i * coefficient;
      float j = (float) (radius * Math.cos(rads) + getOrigin().getX());
      float k = (float) (radius * Math.sin(rads) + getOrigin().getY());

      vertices.put(j);
      vertices.put(k);
    }
    vertices.put(getOrigin().getY());
    vertices.put(getOrigin().getX());
    vertices.position(0);
    glVertexPointer(2, GL_FLOAT, 0, vertices);

//    glDrawArrays(GL_LINE_STRIP, 0, segments + 1);
    glDrawArrays(GL_TRIANGLE_FAN, 0, segments + 1);
    glDisable(GL_BLEND);
  }
}
