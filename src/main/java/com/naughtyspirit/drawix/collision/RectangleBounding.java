package com.naughtyspirit.drawix.collision;

import com.naughtyspirit.drawix.primitive.Vertex;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 27-12-2011
 */
public class RectangleBounding implements BoundingShape {

  private final Vertex lowerLeft;
  private final float width;
  private final float height;

  public RectangleBounding(Vertex lowerLeft, float width, float height) {
    this.lowerLeft = lowerLeft;
    this.width = width;
    this.height = height;
  }

  @Override
  public boolean isOverlappingWith(CircleBounding c) {
    float closestX = c.getOrigin().getX();
    float closestY = c.getOrigin().getY();
    if (c.getOrigin().getX() < lowerLeft.getX()) {
      closestX = lowerLeft.getX();
    } else if (c.getOrigin().getX() > lowerLeft.getX() + width) {
      closestX = lowerLeft.getX() + width;
    }
    if (c.getOrigin().getY() < lowerLeft.getY()) {
      closestY = lowerLeft.getY();
    } else if (c.getOrigin().getY() > lowerLeft.getY() + height) {
      closestY = lowerLeft.getY() + height;
    }
    return c.getOrigin().distanceTo(new Vertex(closestX, closestY)) < c.getRadius();
  }

  @Override
  public boolean isOverlappingWith(RectangleBounding other) {
    if (lowerLeft.getX() < other.lowerLeft.getX() + other.width &&
            lowerLeft.getX() + width > other.lowerLeft.getX() &&
            lowerLeft.getY() < other.lowerLeft.getY() + other.height &&
            lowerLeft.getY() + height > other.lowerLeft.getY()) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isOverlappingWith(Vertex vertex) {
    return lowerLeft.getX() <= vertex.getX() && lowerLeft.getX() + width >= vertex.getX() &&
            lowerLeft.getY() <= vertex.getY() && lowerLeft.getY() + height >= vertex.getY();
  }
}
