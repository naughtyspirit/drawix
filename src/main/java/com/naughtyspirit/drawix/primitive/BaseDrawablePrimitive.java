package com.naughtyspirit.drawix.primitive;

import android.graphics.Color;
import com.naughtyspirit.drawix.collision.BoundingShape;
import com.naughtyspirit.drawix.collision.HasBoundingShape;

import static android.opengl.GLES10.glColor4f;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 25-12-2011
 */
public abstract class BaseDrawablePrimitive {

  private final Vertex origin;
  private int color;
  private float transparency;
  private boolean isSelected;

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

  public boolean isOverlappingWith(Vertex point) {
    if(this instanceof HasBoundingShape) {
      HasBoundingShape boundingShapeHolder = (HasBoundingShape) this;
      return boundingShapeHolder.getBoundingShape().isOverlappingWith(point);
    }
    return false;
  }

  protected abstract void doDraw();

  public void setColor(int color) {
    this.color = color;
  }

  public void setTransparency(float transparency) {
    this.transparency = transparency;
  }

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
  }

  public void moveTo(Vertex point) {
    getOrigin().moveTo(point);
  }
}
