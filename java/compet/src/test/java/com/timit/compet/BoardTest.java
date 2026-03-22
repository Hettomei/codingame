package com.timit.compet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;
import java.util.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BoardTest {

  private final InputStream systemIn = System.in;

  private ByteArrayInputStream testIn;

  private void provideInput(String... data) {
    StringBuilder sb = new StringBuilder();
    for (String s : data) {
      sb.append(s);
      sb.append("\n");
    }
    testIn = new ByteArrayInputStream(sb.toString().getBytes());
    System.setIn(testIn);
  }

  @AfterEach
  public void restoreSystemInputOutput() {
    System.setIn(systemIn);
  }

  @Test
  void builds() {
    provideInput(
        "7 5",
        ".#.#.##",
        "#.#.#.#",
        ".#.#.##",
        "#.#.#.#",
        ".#.#.##",
        "                       ",
        "");
    Scanner in = new Scanner(System.in);
    Board board = Board.build(in);
    in.close();

    assertFalse(board.isForbidden(0, 0));
    assertTrue(board.isAvailable(0, 0));
    assertTrue(board.isForbidden(1, 0));
    assertFalse(board.isAvailable(1, 0));
    assertFalse(board.isForbidden(0, 5));
    assertTrue(board.isAvailable(0, 5));

    board.buildSol();
    assertTrue(board.isForbidden(0, 5));
    assertFalse(board.isAvailable(0, 5));

    ForbiddenPoints fp = new ForbiddenPoints(board);
    assertTrue(fp.isAvailable(new Point(0, 0)));
    assertFalse(fp.isAvailable(new Point(1, 0)));
    assertTrue(fp.isAvailable(new Point(2, 0)));
  }
}
