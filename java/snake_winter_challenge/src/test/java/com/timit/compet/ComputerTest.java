package com.timit.compet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;
import org.junit.jupiter.api.Test;

class ComputerTest {

  @Test
  void snakeTouchePiece() {
    /*
     *	H la tete du snake
     *	P la piece
     * FALSE :
     *      P
     *     T
     *
     * TRUE :
     *     P
     *     T
     */
    PowerUp[] powerups;

    Snake snake;
    Point head;
    Point[] parts;
    Point tail;
    head = new Point(4, 4);
    parts = new Point[] {};
    tail = new Point(4, 5);
    snake = new Snake(1, true, head, parts, tail);

    powerups = new PowerUp[] {new PowerUp(3, 4)};
    assertTrue(Computer.snakeTouchePiece(snake, powerups));

    powerups = new PowerUp[] {new PowerUp(5, 4)};
    assertTrue(Computer.snakeTouchePiece(snake, powerups));

    powerups = new PowerUp[] {new PowerUp(4, 3)};
    assertTrue(Computer.snakeTouchePiece(snake, powerups));

    powerups = new PowerUp[] {new PowerUp(4, 5)};
    assertTrue(Computer.snakeTouchePiece(snake, powerups));

    powerups = new PowerUp[] {new PowerUp(5, 3)};
    assertFalse(Computer.snakeTouchePiece(snake, powerups));
  }
}
