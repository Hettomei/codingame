package com.timit.compet;

import java.io.*;
import java.math.*;
import java.util.*;

// Tools
class T {
  // debug
  static void d(Object o) {
    System.err.println(">> " + o);
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

class Point {
  int x;
  int y;

  Point(Point a, Point relative) {
    x = a.x + relative.x;
    y = a.y + relative.y;
  }

  Point(String coord) {
    String[] coords = coord.split(",");
    x = Integer.valueOf(coords[0]);
    y = Integer.valueOf(coords[1]);
  }

  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  static int distance(Point a, Point b) {
    return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
  }

  public String toString() {
    return "(" + x + "," + y + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Point)) return false;
    Point other = (Point) o;
    return this.x == other.x && this.y == other.y;
  }
}

class ForbiddenPoints {
  Set<Point> immuable;

  ForbiddenPoints() {
    immuable = new HashSet<>();
  }

  void addImmuable(Board board) {
    immuable.add(new Point(1, 0));
  }
}

class Snake {
  int id;
  Point head;
  Point[] parts;
  Point tail;

  boolean isMine;

  Snake(int id, boolean isMine, Point head, Point[] parts, Point tail) {
    this.id = id;
    this.isMine = isMine;
    // la tete
    this.head = head;
    // Tout sauf la tete et la queue
    this.parts = parts;
    // la queue
    this.tail = tail;
  }

  public String toString() {
    String mmine = isMine ? "mine " : "other";
    return "Snake" + " " + mmine + " " + id + " " + head + Arrays.toString(parts) + tail;
  }

  // utilisé pour savoir si la tete
  // peut se deplacer relativement dans cette direction
  boolean canGo(Point relative) {
    // TOUS sauf la premiere qui bouge et sauf la derniere qui va disparaitre lors du mouvement
    // SAUF si on vient de gober un truc // TODO peut etre a code peut etre non pertinent
    Point nextPosition = new Point(head, relative);
    for (Point part : parts) {
      if (part.equals(nextPosition)) return false;
    }

    return true;
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

      String[] body = in.next().split(":");
      Point head = new Point(body[0]);
      // Tout sauf la tete et la queue
      Point[] parts = new Point[body.length - 2];
      for (int j = 0; j < body.length - 2; j++) {
        parts[j] = new Point(body[j + 1]);
      }
      Point tail = new Point(body[body.length - 1]);

      snakes[i] = new Snake(id, myIds.contains(id), head, parts, tail);
    }
    return snakes;
  }
}

class Computer {

  // Point relatif
  static Point RELATIF_LEFT = new Point(-1, 0);
  static Point RELATIF_RIGHT = new Point(1, 0);
  static Point RELATIF_UP = new Point(0, -1);
  static Point RELATIF_DOWN = new Point(0, 1);

  static PowerUp closestPowerUp(PowerUp[] powerups, Snake snake) {
    int max_distance = 1000 * 1000;
    PowerUp closest = powerups[0];
    for (PowerUp powerup : powerups) {
      // sqrt((sx-px)²+(sy-py)²) // sx = snake.x ; px = powerup.x
      int d = Point.distance(snake.head, powerup);
      if (d < max_distance) {
        max_distance = d;
        closest = powerup;
      }
      T.d(powerup + " d: " + d);
    }
    return closest;
  }

  /*
   * ordre des coordonnées :
   * 0,0  1,0  2,0  3,0
   * 0,1  1,1  2,1  3,1
   * 0,2  1,2  2,2  3,2
   */

  static boolean canGo(Snake s, Point relative) {
    // TOUS sauf la premiere qui bouge et sauf la derniere qui va disparaitre lors du mouvement
    // SAUF si on vient de gober un truc // TODO peut etre a code peut etre non pertinent
    Point nextPosition = new Point(s.head, relative);
    for (Point part : s.parts) {
      if (part.equals(nextPosition)) return false;
    }

    return true;
  }

  static Dir getDirection(Snake s, PowerUp p) {
    if (s.head.x < p.x && canGo(s, RELATIF_RIGHT)) return Dir.RIGHT;
    if (s.head.x > p.x && canGo(s, RELATIF_LEFT)) return Dir.LEFT;
    if (s.head.y < p.y && canGo(s, RELATIF_DOWN)) return Dir.DOWN;
    if (s.head.y > p.y && canGo(s, RELATIF_UP)) return Dir.UP;
    return Dir.UP;
  }
}

class PowerUp extends Point {

  PowerUp(int x, int y) {
    super(x, y);
  }

  public String toString() {
    return "PowerUp " + super.toString();
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
    ForbiddenPoints forbiddenPoints = new ForbiddenPoints();
    Scanner in = new Scanner(System.in);
    int myId = in.nextInt();
    Board board = Board.build(in);
    forbiddenPoints.addImmuable(board);

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

      // TODO, si il part a droit a l infinie, le stopper

      // For codinGame
      T.p(myFirstSnake.id + " " + dir.name() + ";");
      loop++;
    }
    in.close();
  }
}
