package com.naughtyspirit.drawix.primitive;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 25-12-2011
 */
public class Vertex {

  private float x, y;

  public Vertex(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public float distanceTo(Vertex other) {
    return (float) Math.sqrt(x * other.x + y * other.y);
  }
}
