package com.timit.compet;

import java.io.*;
import java.math.*;
import java.util.*;

// Tools
class T {
  // debug
  static void d(Object o) {
    System.err.println("" + o);
  }

  // print
  static void p(Object o) {
    System.out.println("" + o);
  }
}

enum Level {
  VIDE,
  MUR,
  ENERGIE,
  TETE,
  CORPS,
}

class Board {

  Level[][] level;

  Board(Level[][] level) {
    this.level = level;
  }

  static Board build(Scanner in) {
    int width = in.nextInt();
    int height = in.nextInt();
    Level[][] level = new Level[height][width];
    in.nextLine(); // utile pour passer à la suite
    for (int row = 0; row < height; row++) {
      int j;
      String rawLine = in.nextLine();
      T.d(rawLine);
      String[] line = rawLine.split("");
      int col = 0;
      for (String s : line) {
        if (s.equals(".")) {
          level[row][col] = Level.VIDE;
        } else if (s.equals("#")) {
          level[row][col] = Level.MUR;
        }
        col++;
      }
    }
    return new Board(level);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < level.length; row++) {
      for (int col = 0; col < level[row].length; col++) {
        sb.append(col);
        sb.append(",");
        sb.append(row);
        sb.append(level[row][col]);
        sb.append(" ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}

class Snake {
  int id;
  String[] body;

  // Head place
  int x;
  int y;

  boolean isMine;

  Snake(int id, String body, boolean isMine) {
    this.id = id;
    this.body = body.split(":");
    this.isMine = isMine;
    String[] head = this.body[0].split(",");
    x = Integer.valueOf(head[0]);
    y = Integer.valueOf(head[1]);
    // Remove head from body
    this.body = Arrays.copyOfRange(this.body, 1, this.body.length);
  }

  public String toString() {
    return "Snake"
        + " id:"
        + id
        + " isMine:"
        + isMine
        + " head:"
        + x
        + ","
        + y
        + " body:"
        + Arrays.toString(body);
  }

  static Snake[] builds(Scanner in, Set<Integer> myIds) {
    int count = in.nextInt();
    Snake[] snakes = new Snake[count];
    for (int i = 0; i < count; i++) {
      int id = in.nextInt();
      snakes[i] = new Snake(id, in.next(), myIds.contains(id));
    }
    return snakes;
  }
}

class PowerUp {
  int x;
  int y;

  PowerUp(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public String toString() {
    return "PowerUp:" + x + "," + y;
  }

  static PowerUp[] builds(Scanner in) {
    int powerSourceCount = in.nextInt();
    PowerUp[] powerups = new PowerUp[powerSourceCount];
    for (int i = 0; i < powerSourceCount; i++) {
      powerups[i] = new PowerUp(in.nextInt(), in.nextInt());
    }
    return powerups;
  }
}

class Player {

  static Set<Integer> getIds(int n, Scanner in) {
    Set<Integer> ids = new HashSet<Integer>();
    for (int i = 0; i < n; i++) {
      ids.add(in.nextInt());
    }
    return ids;
  }

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    int myId = in.nextInt();
    Board board = Board.build(in);

    int snakePerPlayer = in.nextInt();
    Set<Integer> myIds;
    Set<Integer> opponentIds;

    if (myId == 0) {
      myIds = getIds(snakePerPlayer, in);
      opponentIds = getIds(snakePerPlayer, in);
    } else {
      opponentIds = getIds(snakePerPlayer, in);
      myIds = getIds(snakePerPlayer, in);
    }

    int loop = 0;
    while (loop < 250) {
      PowerUp[] powerups = PowerUp.builds(in);
      Snake[] snakes = Snake.builds(in);
      // Avant cette ligne tout ok
      for (int i : myIds) T.d(snakes[i]);
      T.d("--");
      for (int i : opponentIds) T.d(snakes[i]);

      T.p("WAIT");
      loop++;
    }
    in.close();
  }
}
