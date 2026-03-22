package com.timit.compet;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

  @Test
  void display() {
    Set<Point> murs = new HashSet();
    murs.add(new Point(1, 0));
    murs.add(new Point(1, 1));
    murs.add(new Point(1, 2));
    Board board = new Board(murs, 3, 3);
    ForbiddenPoints fp = new ForbiddenPoints(board);
    assertEquals("#\n" + "#\n" + "#\n", fp.toString());

    board.buildSol();
    assertEquals(
        ""
            + "      #      \n" //    ajout d un comme pour le linter
            + "      #      \n"
            + "      #      \n"
            + "#############\n",
        fp.toString());
  }

  @Test
  void displayEtCulDeSac() {
    /* TODO à coder
    tout ce qui est cul de sac, on degage :

    ###  ##  ##   # #  ###
    # #   #  #    ###  # #
         ##  ##        ###
    */
    Set<Point> murs = new HashSet();
    // part 1
    murs.add(new Point(0, 0));
    murs.add(new Point(1, 0));
    murs.add(new Point(2, 0));
    murs.add(new Point(0, 1));
    murs.add(new Point(2, 1));

    // part 2
    murs.add(new Point(5, 0));
    murs.add(new Point(6, 0));
    murs.add(new Point(6, 1));
    murs.add(new Point(5, 2));
    murs.add(new Point(6, 2));

    // part 3
    murs.add(new Point(9, 0));
    murs.add(new Point(10, 0));
    murs.add(new Point(9, 1));
    murs.add(new Point(9, 2));
    murs.add(new Point(10, 2));

    // part 4
    murs.add(new Point(13, 0));
    murs.add(new Point(15, 0));
    murs.add(new Point(13, 1));
    murs.add(new Point(14, 1));
    murs.add(new Point(15, 1));

    // part 5
    murs.add(new Point(19, 0));
    murs.add(new Point(20, 0));
    murs.add(new Point(21, 0));
    murs.add(new Point(19, 1));
    murs.add(new Point(21, 1));
    murs.add(new Point(19, 2));
    murs.add(new Point(20, 2));
    murs.add(new Point(21, 2));

    // part proche du sol
    murs.add(new Point(0, 3));
    murs.add(new Point(2, 3));

    Board board = new Board(murs, 22, 4);
    ForbiddenPoints fp = new ForbiddenPoints(board);

    assertEquals(
        ""
            + "###  ##  ##  # #   ###\n" //    ajout d un comme pour le linter
            + "# #   #  #   ###   # #\n"
            + "     ##  ##        ###\n"
            + "# #                   \n",
        fp.toString());

    board.buildSol();
    assertEquals(
        ""
            + "     ###  ##  ##  # #   ###     \n"
            + "     # #   #  #   ###   # #     \n"
            + "          ##  ##        ###     \n"
            + "     # #                        \n"
            + "################################\n",
        fp.toString());

    // Ne contient rien
    assertFalse(fp.culDeSac.contains(new Point(1, 1)));
    fp.buildCulDeSac();
    // puis le contient
    assertTrue(fp.culDeSac.contains(new Point(1, 1)));
    assertEquals(
        ""
            + "     ###  ##  ##  ###   ###     \n"
            + "     ###  ##  ##  ###   ###     \n"
            + "          ##  ##        ###     \n"
            + "     ###                        \n"
            + "################################\n",
        fp.toString());
  }

  @Test
  void newLoop() {
    Set<Point> murs = new HashSet();
    murs.add(new Point(1, 0));
    murs.add(new Point(1, 1));
    murs.add(new Point(1, 2));
    Board board = new Board(murs, 3, 3);
    ForbiddenPoints fp = new ForbiddenPoints(board);

    PowerUp[] powerups = new PowerUp[] {new PowerUp(4, 4), new PowerUp(5, 4)};

    Snake s;
    Point head;
    Point[] parts;
    Point tail;
    head = new Point(0, 0);
    parts = new Point[] {new Point(0, 1), new Point(0, 2)};
    tail = new Point(0, 3);
    s = new Snake(1, true, head, parts, tail);

    Snake[] snakes = new Snake[] {s};

    assertTrue(fp.isAvailable(s.head));
    assertTrue(fp.isAvailable(4, 4)); // le powerup

    fp.newLoop(snakes, powerups);
    assertFalse(fp.isAvailable(s.head));
    assertTrue(fp.isAvailable(4, 4)); // le powerup, case libre

    // Loin d un powerup, alors c est bon c est valide car elle va avancer
    assertTrue(fp.isAvailable(s.tail));

    powerups = new PowerUp[] {new PowerUp(1, 0)};
    fp.newLoop(snakes, powerups);
    // a coté d un powerup,
    // alors elle risque de rester
    assertFalse(fp.isAvailable(s.tail));
  }
}
