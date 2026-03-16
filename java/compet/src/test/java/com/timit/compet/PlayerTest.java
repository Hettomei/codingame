package com.timit.compet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PlayerTest {

  @Test
  void isToutDroitVersLeHaut() {
    Snake s;
    Point head;
    Point[] parts;
    Point tail;

    head = new Point(0, 0);
    parts = new Point[] {new Point(0, 1)};
    tail = new Point(0, 2);
    s = new Snake(1, true, head, parts, tail);
    assertTrue(s.isToutDroitVersLeHaut());

    head = new Point(0, 0);
    parts = new Point[] {new Point(0, 1), new Point(0, 2)};
    tail = new Point(0, 3);
    s = new Snake(1, true, head, parts, tail);
    assertTrue(s.isToutDroitVersLeHaut());

    // le corps derive
    head = new Point(0, 0);
    parts = new Point[] {new Point(0, 1), new Point(1, 1)};
    tail = new Point(1, 2);
    s = new Snake(1, true, head, parts, tail);
    assertFalse(s.isToutDroitVersLeHaut());

    // la queue derive
    head = new Point(0, 0);
    parts = new Point[] {new Point(0, 1), new Point(0, 2)};
    tail = new Point(1, 2);
    s = new Snake(1, true, head, parts, tail);
    assertFalse(s.isToutDroitVersLeHaut());
  }
}
