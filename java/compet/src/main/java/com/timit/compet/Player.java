package com.timit.compet;

import java.io.*;
import java.math.*;
import java.util.*;

// Tools
class T {
  // debug
  static void d(Object o) {
    System.err.println("> " + o);
  }

  // print
  static void p(Object o) {
    System.out.println("" + o);
  }
}

enum Dir {
  UP,
  DOWN,
  LEFT,
  RIGHT,
  WAIT
}

enum Level {
  VIDE,
  MUR,
  ENERGIE,
  TETE,
  CORPS
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

  // Head place
  int x;
  int y;

  String[] body;

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

  static Snake getMyFirst(Snake[] snakes) {
    for (Snake s : snakes) {
      if (s.isMine) return s;
    }
    return null;
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

class Computer {
  static PowerUp closestPowerUp(PowerUp[] powerups, Snake snake) {
    float max_distance = 1000 * 1000;
    PowerUp closest = powerups[0];
    for (PowerUp p : powerups) {
      // sqrt((sx-px)²+(sy-py)²) // sx = snake.x ; px = powerup.x
      float d = (snake.x - p.x) * (snake.x - p.x) + (snake.y - p.y) * (snake.y - p.y);
      if (d < max_distance) {
        max_distance = d;
        closest = p;
      }
      T.d(p + " d:" + d);
    }
    return closest;
  }

  /*
   * ordre des coordonnées :
   * 0,0  1,0  2,0  3,0
   * 0,1  1,1  2,1  3,1
   * 0,2  1,2  2,2  3,2
   */
  static Dir getDirection(Snake s, PowerUp p) {
    if (s.x < p.x) return Dir.RIGHT;
    if (s.x > p.x) return Dir.LEFT;
    if (s.y < p.y) return Dir.DOWN;
    if (s.y > p.y) return Dir.UP;
    return Dir.UP;
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
      Snake[] snakes = Snake.builds(in, myIds);
      // for (Snake s : snakes) T.d(s);
      // for (PowerUp p : powerups) T.d(p);
      // T.d("");

      // Trouver le powerup le plus proche pour le premier serpent
      Snake myFirstSnake = Snake.getMyFirst(snakes);
      PowerUp closest = Computer.closestPowerUp(powerups, myFirstSnake);
      T.d("closest " + closest + " of " + myFirstSnake);
      // Trouver la direction
      Dir dir = Computer.getDirection(myFirstSnake, closest);
      T.p(myFirstSnake.id + " " + dir.name() + ";");
      loop++;
    }
    in.close();
  }
}
