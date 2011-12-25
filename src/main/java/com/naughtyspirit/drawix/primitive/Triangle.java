package com.naughtyspirit.drawix.primitive;

import java.util.LinkedList;
import java.util.List;

/**
 * Author: Venelin Valkov <venelin@naughtyspirit.com>
 * Date: 12/25/11
 */
public class Triangle {
  private List<Vertex> vertices = new LinkedList<Vertex>();

  public Triangle(Vertex a, Vertex b, Vertex c) {
    vertices.add(a);
    vertices.add(b);
    vertices.add(c);
  }
}
