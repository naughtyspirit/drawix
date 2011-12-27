package com.naughtyspirit.drawix.primitive;

import android.graphics.Color;
import com.naughtyspirit.drawix.collision.BoundingShape;

import static android.opengl.GLES10.glColor4f;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 25-12-2011
 */
public abstract class BaseDrawablePrimitive {

  private final Vertex origin;
  private int color;
  private float transparency;

  protected BaseDrawablePrimitive(Vertex origin) {
    this.origin = origin;
    color = Color.WHITE;
    transparency = 1.0f;
  }

  public Vertex getOrigin() {
    return origin;
  }

  public void draw() {
    glColor4f((float) Color.red(color)/255, (float)Color.green(color)/255, (float)Color.blue(color)/255, transparency);
    doDraw();
  }

  protected abstract void doDraw();

  public void setColor(int color) {
    this.color = color;
  }

  public void setTransparency(float transparency) {
    this.transparency = transparency;
  }
}
