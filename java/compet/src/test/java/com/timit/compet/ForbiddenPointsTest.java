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
            + "     ###  ##  ##  #.#   ###     \n"
            + "     #.#  .#  #.  ###   #.#     \n"
            + "          ##  ##        ###     \n"
            + "     #.#                        \n"
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

  @Test
  void newLoopAvecSerpent() {
    // 1,0 1,1 1,2 2,2
    Set<Point> murs = new HashSet();
    murs.add(new Point(5, 3));
    murs.add(new Point(6, 3));
    murs.add(new Point(7, 3));
    murs.add(new Point(8, 3));
    murs.add(new Point(9, 3));
    murs.add(new Point(10, 3));
    Board board = new Board(murs, 8, 5);
    ForbiddenPoints fp = new ForbiddenPoints(board);
    PowerUp[] powerups = new PowerUp[] {};

    assertEquals("######\n", fp.toString());

    Snake s;
    Point head;
    Point[] parts;
    Point tail;
    head = new Point(8, 2);
    parts =
        new Point[] {
          new Point(8, 1), // fdsdsfsdfsdfdsfsd dfs
          new Point(7, 1),
          new Point(6, 1),
          new Point(6, 2)
        };
    tail = new Point(5, 2);
    s = new Snake(1, true, head, parts, tail);

    Snake[] snakes = new Snake[] {s};

    fp.newLoop(snakes, powerups);
    /* on en est a :
    P powerup, s, snake, t, tail, h head

       sss
    P ts h
    ########

    la piece tout a gauche
    Donc on interdit l interieur entre s et h
    on ne voit pas le t de tail car n est pas un point forbidden
    */

    assertEquals(
        "" // pour le linter mec
            + " sss  \n"
            + " s.s  \n"
            + "######\n",
        fp.toString());
  }

  @Test
  void newLoopAvecSerpentNonRegression1() {
    /*
    // MON serpent : on ne doit pas bloquer
    // LE SERPENT D UN AUTRE, on bloque
           ##
          s #
          s
          s
          s
          s
        */
    Set<Point> murs = new HashSet();
    murs.add(new Point(4, 1));
    murs.add(new Point(5, 1));
    murs.add(new Point(5, 2));
    Board board = new Board(murs, 5, 3);
    ForbiddenPoints fp = new ForbiddenPoints(board);
    PowerUp[] powerups = new PowerUp[] {};

    Point head = new Point(3, 2);
    Point[] parts = new Point[] {new Point(3, 3), new Point(3, 4), new Point(3, 5)};
    Point tail = new Point(3, 6);
    Snake mySnake = new Snake(1, true, head, parts, tail);
    Snake myOponnent = new Snake(1, false, head, parts, tail);
    Snake[] snakes = new Snake[] {mySnake};

    fp.newLoop(snakes, powerups);
    assertEquals(
        "" // pour le linter mec
            + " ##\n"
            + "s #\n"
            + "s  \n",
        +"s  \n",
        +"s  \n",
        fp.toString());

    snakes = new Snake[] {myOponnent};
    fp.newLoop(snakes, powerups);
    assertEquals(
        "" // pour le linter mec
            + " ##\n"
            + "s.#\n"
            + "s  \n",
        fp.toString());
  }

  @Test
  void newLoopAvecSerpentNonRegression2() {
    /*
     ###
     #.#
      s
      s
      s
    */
    Set<Point> murs = new HashSet();
    murs.add(new Point(0, 0));
    murs.add(new Point(1, 0));
    murs.add(new Point(2, 0));
    murs.add(new Point(0, 1));
    murs.add(new Point(2, 1));
    Board board = new Board(murs, 5, 6);
    ForbiddenPoints fp = new ForbiddenPoints(board);
    PowerUp[] powerups = new PowerUp[] {};

    Point head = new Point(1, 2);
    Point[] parts = new Point[] {new Point(1, 3)};
    Point tail = new Point(1, 4);
    Snake s = new Snake(1, true, head, parts, tail);
    Snake[] snakes = new Snake[] {s};

    fp.newLoop(snakes, powerups);
    assertEquals(
        "" // pour le linter mec
            + "###\n"
            + "#.#\n"
            + " s \n"
            + " s \n",
        fp.toString());
  }

  @Test
  void newLoopAvecSerpentNonRegression3() {
    /*
    # ss
    ##ts
    ## h ##
    ########
    */
    Set<Point> murs = new HashSet();
    murs.add(new Point(0, 0));
    murs.add(new Point(0, 1));
    murs.add(new Point(0, 2));
    murs.add(new Point(0, 3));
    murs.add(new Point(1, 1));
    murs.add(new Point(1, 2));
    murs.add(new Point(1, 3));
    murs.add(new Point(2, 3));
    murs.add(new Point(3, 3));
    murs.add(new Point(4, 3));
    murs.add(new Point(5, 3));
    murs.add(new Point(6, 3));
    murs.add(new Point(7, 3));
    murs.add(new Point(5, 2));
    murs.add(new Point(6, 2));
    Board board = new Board(murs, 8, 6);
    ForbiddenPoints fp = new ForbiddenPoints(board);
    PowerUp[] powerups = new PowerUp[] {};

    Point head = new Point(3, 2);
    Point[] parts = new Point[] {new Point(3, 1), new Point(3, 0), new Point(2, 0)};
    Point tail = new Point(2, 1);
    Snake s = new Snake(1, true, head, parts, tail);
    Snake[] snakes = new Snake[] {s};

    fp.newLoop(snakes, powerups);
    assertEquals(
        "" // pour le linter mec
            + "# ss    \n"
            + "## s    \n"
            + "## s ## \n"
            + "########\n",
        fp.toString());
  }
}
