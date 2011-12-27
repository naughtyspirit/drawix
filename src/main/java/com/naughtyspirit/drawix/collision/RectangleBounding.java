package com.naughtyspirit.drawix.collision;

import com.naughtyspirit.drawix.primitive.Vertex;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 27-12-2011
 */
public class RectangleBounding implements BoundingShape {

  private final Vertex origin;
  private final float width;
  private final float height;

  public RectangleBounding(Vertex origin, float width, float height) {
    this.origin = origin;
    this.width = width;
    this.height = height;
  }

  @Override
  public boolean isOverlappingWith(CircleBounding c) {
    float closestX = c.getOrigin().getX();
    float closestY = c.getOrigin().getY();
    if (c.getOrigin().getX() < origin.getX()) {
      closestX = origin.getX();
    } else if (c.getOrigin().getX() > origin.getX() + width) {
      closestX = origin.getX() + width;
    }
    if (c.getOrigin().getY() < origin.getY()) {
      closestY = origin.getY();
    } else if (c.getOrigin().getY() > origin.getY() + height) {
      closestY = origin.getY() + height;
    }
    return c.getOrigin().distanceTo(new Vertex(closestX, closestY)) < c.getRadius();
  }

  @Override
  public boolean isOverlappingWith(RectangleBounding other) {
    if (origin.getX() < other.origin.getX() + other.width &&
            origin.getX() + width > other.origin.getX() &&
            origin.getY() < other.origin.getY() + other.height &&
            origin.getY() + height > other.origin.getY()) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isOverlappingWith(Vertex vertex) {
    return vertex.getX() >= origin.getX() && vertex.getX() <= origin.getX() + width &&
           vertex.getY() >= origin.getY() && vertex.getY() <= origin.getY() + height;
  }
}
