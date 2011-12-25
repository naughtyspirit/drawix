package com.naughtyspirit.drawix.primitive;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 25-12-2011
 */
public abstract class BaseDrawablePrimitive {
  private final Vertex origin;

  protected BaseDrawablePrimitive(Vertex origin) {
    this.origin = origin;
  }

  public Vertex getOrigin() {
    return origin;
  }

  public abstract void draw();
}
