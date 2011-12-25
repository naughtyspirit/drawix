package com.naughtyspirit.drawix.primitive;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 12/25/11
 */
public class Vertex {
  private float x, y;

  public Vertex(float y, float x) {
    this.y = y;
    this.x = x;
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }
}
