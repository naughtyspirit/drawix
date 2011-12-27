package com.naughtyspirit.drawix.collision;

import com.naughtyspirit.drawix.primitive.Vertex;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 27-12-2011
 */
public interface BoundingShape {
  boolean isOverlappingWith(CircleBounding other);
  boolean isOverlappingWith(RectangleBounding other);
  boolean isOverlappingWith(Vertex vertex);
}
