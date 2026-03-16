package com.timit.compet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PointTest {

  @Test
  void constructorTest() {
    Point a = new Point(3, 5);
    Point b = new Point("3,5");
    assertEquals(a, b);
  }

  @Test
  void constructorAddPointsTest() {
    Point a = new Point(3, 5);
    assertEquals(new Point(a, Point.UP), new Point(3, 4));

    // La somme de tous fait 0
    Point b = new Point(Point.UP, Point.DOWN);
    Point c = new Point(Point.LEFT, Point.RIGHT);
    assertEquals(new Point(0, 0), new Point(b, c));
  }

  @Test
  void distanceTest() {
    Point a = new Point(3, 5);
    Point b = new Point(3, 5);
    Point c = new Point(4, 5);
    Point d = new Point(4, 6);
    Point e = new Point(5, 7);
    assertEquals(0, Point.distance(a, b));
    assertEquals(1, Point.distance(a, c));
    assertEquals(2, Point.distance(a, d));
    assertEquals(8, Point.distance(a, e));
  }
}
