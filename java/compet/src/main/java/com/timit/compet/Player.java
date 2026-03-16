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

  static Point move(Point a, Dir dir) {
    if (dir == Dir.LEFT) return new Point(a, Fix.RELATIF_LEFT);
    if (dir == Dir.RIGHT) return new Point(a, Fix.RELATIF_RIGHT);
    if (dir == Dir.UP) return new Point(a, Fix.RELATIF_UP);
    return new Point(a, Fix.RELATIF_DOWN);
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

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }
}

class ForbiddenPoints {
  Set<Point> immuable;
  Set<Point> change;

  ForbiddenPoints() {
    immuable = new HashSet<>();
    change = new HashSet<>();
  }

  void restartWithSnakes(Snake... snakes) {
    change.clear();
    for (Snake s : snakes) {
      change.add(s.head);
      for (Point p : s.parts) change.add(p);
      // change.add(s.tail); // non, pas la queue, forte chance qu elle bouge
    }
  }

  void remove(Point p) {
    change.remove(p);
  }

  void add(Point p) {
    change.add(p);
  }

  void addImmuable(Board board) {
    int y = 0;
    for (Level[] lineLevel : board.level) {
      int x = 0;
      for (Level level : lineLevel) {
        if (level == Level.MUR) immuable.add(new Point(x, y));
        x++;
      }
      y++;
    }
  }

  boolean isAvailable(Point point) {
    return !immuable.contains(point) && !change.contains(point);
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

  Point getLastPart() {
    return parts[parts.length - 1];
  }

  public String toString() {
    String mmine = isMine ? "mine " : "other";
    return "Snake" + " " + mmine + " " + id + " " + head + Arrays.toString(parts) + tail;
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

class Fix {
  // Point relatif
  static Point RELATIF_LEFT = new Point(-1, 0);
  static Point RELATIF_RIGHT = new Point(1, 0);
  static Point RELATIF_UP = new Point(0, -1);
  static Point RELATIF_DOWN = new Point(0, 1);
}

class Computer {

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
      // T.d(powerup + " d: " + d);
    }
    return closest;
  }

  /*
   * ordre des coordonnées :
   * 0,0  1,0  2,0  3,0
   * 0,1  1,1  2,1  3,1
   * 0,2  1,2  2,2  3,2
   */
  // utilisé pour savoir si la tete
  // peut se deplacer relativement dans cette direction
  static boolean canGo(Snake s, Point relative, ForbiddenPoints forbiddenPoints) {
    // TOUS sauf la premiere qui bouge et sauf la derniere qui va disparaitre lors du mouvement
    // SAUF si on vient de gober un truc // TODO peut etre a code peut etre non pertinent
    Point nextPosition = new Point(s.head, relative);
    for (Point part : s.parts) {
      if (part.equals(nextPosition)) return false;
    }
    return forbiddenPoints.isAvailable(nextPosition);
  }

  static Dir getDirection(Snake s, PowerUp p, ForbiddenPoints forbiddenPoints) {
    if (s.head.x < p.x && canGo(s, Fix.RELATIF_RIGHT, forbiddenPoints)) return Dir.RIGHT;
    if (s.head.x > p.x && canGo(s, Fix.RELATIF_LEFT, forbiddenPoints)) return Dir.LEFT;
    // Le UP, ajouter la condition "si rien en dessous, ne pas faire" mais ca marche pas tjrs.... il
    // peut y avoir un block en plein milieu du worms. Donc non trivial
    if (s.head.y > p.y && canGo(s, Fix.RELATIF_UP, forbiddenPoints)) return Dir.UP;
    if (s.head.y < p.y && canGo(s, Fix.RELATIF_DOWN, forbiddenPoints)) return Dir.DOWN;

    if (s.head.y > p.y && canGo(s, Fix.RELATIF_UP, forbiddenPoints)) return Dir.UP;
    if (s.head.y < p.y && canGo(s, Fix.RELATIF_DOWN, forbiddenPoints)) return Dir.DOWN;
    if (s.head.x < p.x && canGo(s, Fix.RELATIF_RIGHT, forbiddenPoints)) return Dir.RIGHT;
    if (s.head.x > p.x && canGo(s, Fix.RELATIF_LEFT, forbiddenPoints)) return Dir.LEFT;

    if (s.head.x > p.x && canGo(s, Fix.RELATIF_LEFT, forbiddenPoints)) return Dir.LEFT;
    if (s.head.y > p.y && canGo(s, Fix.RELATIF_UP, forbiddenPoints)) return Dir.UP;
    if (s.head.x < p.x && canGo(s, Fix.RELATIF_RIGHT, forbiddenPoints)) return Dir.RIGHT;
    if (s.head.y < p.y && canGo(s, Fix.RELATIF_DOWN, forbiddenPoints)) return Dir.DOWN;

    // on a tout fait mais on etait forbidden : on assoupli les regles
    /* ca couvre ce cas : le s veut "monter" pour le p
       p

      ##
      ssss
      ##
    */
    if (canGo(s, Fix.RELATIF_RIGHT, forbiddenPoints)) return Dir.RIGHT;
    if (canGo(s, Fix.RELATIF_LEFT, forbiddenPoints)) return Dir.LEFT;
    if (canGo(s, Fix.RELATIF_UP, forbiddenPoints)) return Dir.UP;
    if (canGo(s, Fix.RELATIF_DOWN, forbiddenPoints)) return Dir.DOWN;
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
      forbiddenPoints.restartWithSnakes(snakes);
      // for (Snake s : snakes) T.d(s);
      // for (PowerUp p : powerups) T.d(p);
      // T.d("");

      // Trouver le powerup le plus proche pour le premier serpent
      StringBuilder mouve = new StringBuilder();
      for (Snake s : snakes) {
        if (!s.isMine) continue;
        PowerUp closest = Computer.closestPowerUp(powerups, s);
        // T.d("closest " + closest + " of " + s);

        Dir dir = Computer.getDirection(s, closest, forbiddenPoints);
        forbiddenPoints.add(Point.move(s.head, dir));
        // si on est pas a 1 du bonus, on supprime la derniere partie qui avance
        if (Point.distance(closest, s.head) != 1) {
          forbiddenPoints.remove(s.getLastPart());
          // } else {
        }
        mouve.append(s.id);
        mouve.append(" ");
        mouve.append(dir.name());
        mouve.append(";");
      }
      T.p(mouve);

      // TODO, si il part a droit a l infinie, le stopper
      // TODO, si il up et tombe dans le vide, ne pas up
      loop++;
    }
    in.close();
  }
}
