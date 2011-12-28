package com.naughtyspirit.drawix.primitive;

import java.util.LinkedList;
import java.util.List;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 28-12-2011
 */
public class Group extends BaseDrawablePrimitive {

  private List<BaseDrawablePrimitive> primitives = new LinkedList<BaseDrawablePrimitive>();

  public Group(Vertex origin) {
    super(origin);
  }

  public void add(BaseDrawablePrimitive primitive) {
    primitives.add(primitive);
  }

  @Override
  protected void doDraw() {
    for (BaseDrawablePrimitive primitive : primitives) {
      primitive.draw();
    }
  }
}
