package com.timit.compet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ForbiddenPointsTest {

  @Test
  void isAvailable() {
    ForbiddenPoints fp = new ForbiddenPoints();
    Level[][] level =
        new Level[][] {
          {Level.VIDE, Level.MUR, Level.VIDE},
          {Level.VIDE, Level.MUR, Level.VIDE},
          {Level.VIDE, Level.MUR, Level.MUR},
        };
    // 1,0 1,1 1,2 2,2
    Board board = new Board(level);
    fp.addImmuable(board);

    T.d(fp.immuable);

    // ligne 1
    assertTrue(fp.isAvailable(new Point(0, 0)));
    assertFalse(fp.isAvailable(new Point(1, 0)));
    assertTrue(fp.isAvailable(new Point(2, 0)));
    // ligne 2
    assertTrue(fp.isAvailable(new Point(0, 1)));
    assertFalse(fp.isAvailable(new Point(1, 1)));
    assertTrue(fp.isAvailable(new Point(2, 1)));
    // ligne 3
    assertTrue(fp.isAvailable(new Point(0, 2)));
    assertFalse(fp.isAvailable(new Point(1, 2)));
    assertFalse(fp.isAvailable(new Point(2, 2)));
  }
}
