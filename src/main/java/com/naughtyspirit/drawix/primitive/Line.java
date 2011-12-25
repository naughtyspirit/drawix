package com.naughtyspirit.drawix.primitive;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 12/25/11
 */
public class Line {

  private final Vertex a, b;

  public Line(Vertex a, Vertex b) {
    this.a = a;
    this.b = b;
  }

  public Vertex getA() {
    return a;
  }

  public Vertex getB() {
    return b;
  }
}
