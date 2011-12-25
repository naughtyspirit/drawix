package com.naughtyspirit.drawix.primitive;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 12/25/11
 */
public class Square {

  private final Vertex a, b;

  public Square(Vertex a, Vertex b) {
    this.a = a;
    this.b = b;
  }

  public Vertex getA() {
    return a;
  }

  public Vertex getB() {
    return b;
  }

  private float getLengthBetweenVertices() {
    return (float) Math.sqrt(Math.pow(a.getY() - a.getX(), 2) + Math.pow(b.getY() - b.getX(), 2));
  }

  private float getSideLength() {
    return getLengthBetweenVertices() / 2;
  }
}
