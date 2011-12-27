package com.naughtyspirit.drawix.collision;

import com.naughtyspirit.drawix.primitive.Vertex;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 27-12-2011
 */
public class CircleBounding implements BoundingShape {

  private final Vertex origin;
  private final float radius;

  public CircleBounding(Vertex origin, float radius) {
    this.origin = origin;
    this.radius = radius;
  }

  public Vertex getOrigin() {
    return origin;
  }

  public float getRadius() {
    return radius;
  }

  @Override
  public boolean isOverlappingWith(CircleBounding other) {
    float distance = getOrigin().distanceTo(other.getOrigin());
    return distance <= getRadius() + other.getRadius();
  }

  @Override
  public boolean isOverlappingWith(RectangleBounding other) {
    return other.isOverlappingWith(this);
  }

  @Override
  public boolean isOverlappingWith(Vertex vertex) {
    return origin.distanceTo(vertex) < radius;
  }
}
