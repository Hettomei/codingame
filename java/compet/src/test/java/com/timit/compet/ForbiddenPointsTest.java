package com.timit.compet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;
import org.junit.jupiter.api.Test;

class ForbiddenPointsTest {

  @Test
  void isAvailable() {
    // 1,0 1,1 1,2 2,2
    Set<Point> murs = new HashSet();
    murs.add(new Point(1, 0));
    murs.add(new Point(1, 1));
    murs.add(new Point(1, 2));
    Board board = new Board(murs, 3, 3);
    ForbiddenPoints fp = new ForbiddenPoints(board);

    // ligne 1
    assertTrue(fp.isAvailable(new Point(0, 0)));
    assertFalse(fp.isAvailable(new Point(1, 0)));
    assertTrue(fp.isAvailable(new Point(2, 0)));
    // ligne 2
    assertTrue(fp.isAvailable(new Point(0, 1)));
    assertFalse(fp.isAvailable(new Point(1, 1)));
    assertTrue(fp.isAvailable(new Point(2, 1)));
  }
}
