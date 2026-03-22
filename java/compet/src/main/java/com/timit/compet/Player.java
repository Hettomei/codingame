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

enum Level {
  VIDE,
  MUR,
  ENERGIE,
  TETE,
  CORPS
}

class Board {

  Set<Point> murs;
  Set<Point> enceinte;

  int width;
  int height;

  Board(Set<Point> murs, int width, int height) {
    this.murs = murs;
    this.width = width;
    this.height = height;
    this.enceinte = new HashSet();
  }

  boolean isForbidden(int x, int y) {
    Point p = new Point(x, y);
    return murs.contains(p) || enceinte.contains(p);
  }

  boolean isAvailable(int x, int y) {
    return isAvailable(new Point(x, y));
  }

  boolean isAvailable(Point p) {
    return !murs.contains(p) && !enceinte.contains(p);
  }

  void buildSol() {
    // interdit d aller dans le sol si le sol ressemble a :
    // ###  ##   ####  ##  #####
    for (int x = -5; x < width + 5; x++) {
      enceinte.add(new Point(x, height));
    }

    // interdit d aller tout a gauche
    // interdit d aller tout a droite
    // et on fait depasser de 5
    // ###  ##   ####  ##  #####
    // for (int y = -5; y <= height; y++) {
    //   enceinte.add(new Point(-1, y));
    //   enceinte.add(new Point(width, y));
    // }
  }

  static Board build(Scanner in) {
    int width = in.nextInt();
    int height = in.nextInt();
    Set<Point> murs = new HashSet();
    in.nextLine(); // utile pour passer à la suite
    for (int row = 0; row < height; row++) {
      String rawLine = in.nextLine();
      T.d(rawLine);
      int col = 0;
      for (String s : rawLine.split("")) {
        if (s.equals("#")) {
          murs.add(new Point(col, row));
        }
        col++;
      }
    }
    return new Board(murs, width, height);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n");
    for (int row = -4; row < height + 3; row++) {
      for (int col = -4; col < width + 3; col++) {
        if (murs.contains(new Point(col, row))) {
          sb.append("#");
        } else {
          sb.append(" ");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}

class Point {
  static Point LEFT = new Point(-1, 0);
  static Point RIGHT = new Point(1, 0);
  static Point UP = new Point(0, -1);
  static Point DOWN = new Point(0, 1);

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

  static Point move(Point a, Point direction) {
    return new Point(a, direction);
  }

  static int distance(Point a, Point b) {
    return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
  }

  static String name(Point p) {
    if (p.equals(UP)) return "UP";
    if (p.equals(DOWN)) return "DOWN";
    if (p.equals(LEFT)) return "LEFT";
    if (p.equals(RIGHT)) return "RIGHT";
    return "RIGHT";
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
  Board board;
  Set<Point> change;

  ForbiddenPoints(Board board) {
    this.board = board;
    change = new HashSet();
  }

  void restartWithSnakes(Snake[] snakes, PowerUp[] powerups) {
    change.clear();
    for (Snake s : snakes) {
      change.add(s.head);
      for (Point p : s.parts) change.add(p);
      int distance = Computer.closestDistance(powerups, s);
      // Si la tete est a 1 du plus proche, alors il y a des chance qu il va le bouffer
      // donc ajouter la queue pour pas taper dedans
      if (distance == 1) change.add(s.tail);
    }
  }

  void remove(Point p) {
    change.remove(p);
  }

  void add(Point p) {
    change.add(p);
  }

  boolean isAvailable(Point point) {
    return board.isAvailable(point) && !change.contains(point);
  }

  boolean isAvailable(int x, int y) {
    return isAvailable(new Point(x, y));
  }

  int findMinimalX() {
    int minimal = 1000;
    for (Point p : board.murs) {
      if (p.x < minimal) minimal = p.x;
    }
    for (Point p : board.enceinte) {
      if (p.x < minimal) minimal = p.x;
    }
    return minimal;
  }

  int findMaximalX() {
    int max = 0;
    for (Point p : board.murs) {
      if (p.x > max) max = p.x;
    }
    for (Point p : board.enceinte) {
      if (p.x > max) max = p.x;
    }
    return max;
  }

  int findMinimalY() {
    int minimal = 1000;
    for (Point p : board.murs) {
      if (p.y < minimal) minimal = p.y;
    }
    for (Point p : board.enceinte) {
      if (p.y < minimal) minimal = p.y;
    }
    return minimal;
  }

  int findMaximalY() {
    int max = 0;
    for (Point p : board.murs) {
      if (p.y > max) max = p.y;
    }
    for (Point p : board.enceinte) {
      if (p.y > max) max = p.y;
    }
    return max;
  }

  public String display() {
    int minX = findMinimalX();
    int maxX = findMaximalX();
    int minY = findMinimalY();
    int maxY = findMaximalY();
    StringBuilder sb = new StringBuilder();

    // find minimal x, max x
    // find min y, max y

    for (int y = minY; y <= maxY; y++) {
      for (int x = minX; x <= maxX; x++) {

        if (isAvailable(x, y)) {
          sb.append(" ");
        } else {
          sb.append("#");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  /* TODO à coder
  tout ce qui est cul de sac, on degage :

  ###  ##  ##   # #
  # #   #  #    ###
       ##  ##
  */
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
    this.head = head;
    this.parts = parts;
    this.tail = tail;
  }

  public String toString() {
    String mmine = isMine ? "mine " : "other";
    return "Snake" + " " + mmine + " " + id + " " + head + Arrays.toString(parts) + tail;
  }

  boolean isToutDroitVersLeHaut() {
    Point next = head;
    for (Point part : parts) {
      next = Point.move(next, Point.DOWN);
      if (!next.equals(part)) return false;
    }
    next = Point.move(next, Point.DOWN);
    return next.equals(tail);
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
  static Random r = new Random();

  static int closestDistance(PowerUp[] powerups, Snake snake) {
    int max_distance = Point.distance(snake.head, powerups[0]);
    for (PowerUp powerup : powerups) {
      int d = Point.distance(snake.head, powerup);
      if (d < max_distance) {
        max_distance = d;
      }
    }
    return max_distance;
  }

  // Exclure un powerup :
  // si il est n case loin de toute plateforme
  // ne pas le prendre
  static boolean isAccessible(PowerUp powerup, Snake snake, Board board) {
    Point head = snake.head;

    // powerup.isAwayFromAnyBlock

    // le serpent est au dessus ou meme niveau, pas de risque
    if (head.y <= powerup.y) return true;
    // ils ne sont pas aligné, pas de risque
    if (head.x != powerup.x) return true;
    // il y a qu une case d ecart en haut, on peut l atteindre
    Point next = Point.move(head, Point.UP);
    if (next.equals(powerup)) return true;

    // Donc la
    // ils sont aligné, et le serpent est en dessous,
    // alors si il est tout droit vers le haut, c est mort
    return !snake.isToutDroitVersLeHaut();
  }

  static PowerUp closestPowerUp(PowerUp[] powerups, Snake snake, Board board) {
    PowerUp closest = powerups[0];
    int max_distance = Point.distance(snake.head, closest);
    for (PowerUp powerup : powerups) {
      // if (!isAccessible(powerup, snake, board)) {
      //   continue;
      // }

      // On peut l atteindre, on calcul la distance
      int d = Point.distance(snake.head, powerup);
      if (d < max_distance) {
        max_distance = d;
        closest = powerup;
      }
    }
    return closest;
  }

  /*
  * ordre des coordonnées :
  * -------------------->
  * | 0,0  1,0  2,0  3,0
  * | 0,1  1,1  2,1  3,1
  * | 0,2  1,2  2,2  3,2
  *\|

  * utilisé pour savoir si la tete
  * peut se deplacer relativement dans cette direction
  */
  static boolean canGo(Snake s, Point relative, ForbiddenPoints forbiddenPoints) {
    // TOUS sauf la premiere qui bouge et sauf la derniere qui va disparaitre lors du mouvement
    Point nextPosition = Point.move(s.head, relative);
    for (Point part : s.parts) {
      if (part.equals(nextPosition)) return false;
    }
    return forbiddenPoints.isAvailable(nextPosition);
  }

  static Point getDirection(Snake s, PowerUp p, ForbiddenPoints forbiddenPoints) {
    Point head = s.head;
    // si tu veux atteindre le truc, en priorité, monte
    // si il veut aller a droite, mais que son corps est a droite, monter
    if (head.x < p.x && canGo(s, Point.RIGHT, forbiddenPoints)) return Point.RIGHT;
    if (head.x < p.x && canGo(s, Point.UP, forbiddenPoints)) return Point.UP;

    // si tu veux atteindre le truc, en priorité, monte
    if (head.x > p.x && canGo(s, Point.LEFT, forbiddenPoints)) return Point.LEFT;
    if (head.x > p.x && canGo(s, Point.UP, forbiddenPoints)) return Point.UP;

    if (head.y > p.y && canGo(s, Point.UP, forbiddenPoints)) return Point.UP;
    if (head.y > p.y && canGo(s, Point.RIGHT, forbiddenPoints)) return Point.RIGHT;
    if (head.y > p.y && canGo(s, Point.LEFT, forbiddenPoints)) return Point.LEFT;

    if (head.y < p.y && canGo(s, Point.DOWN, forbiddenPoints)) return Point.DOWN;
    if (head.y < p.y && canGo(s, Point.RIGHT, forbiddenPoints)) return Point.RIGHT;
    if (head.y < p.y && canGo(s, Point.LEFT, forbiddenPoints)) return Point.LEFT;

    // Le UP, ajouter la condition "si rien en dessous, ne pas faire" mais ca marche pas tjrs.... il
    // peut y avoir un block en plein milieu du worms. Donc non trivial

    // on a tout fait mais on etait forbidden : on assoupli les regles
    /* ca couvre ce cas : le s veut "monter" pour le p
       p

      ##
      ssss                 <ssss   p
      ##
    */

    List<Point> choices = new ArrayList();
    if (canGo(s, Point.UP, forbiddenPoints)) choices.add(Point.UP);
    if (canGo(s, Point.DOWN, forbiddenPoints)) choices.add(Point.DOWN);
    if (canGo(s, Point.LEFT, forbiddenPoints)) choices.add(Point.LEFT);
    if (canGo(s, Point.RIGHT, forbiddenPoints)) choices.add(Point.RIGHT);
    // on peut rien faire ? on va au pif ? // TODO a reflechir
    if (choices.size() == 0) return Point.UP;

    // int r1 = r.nextInt(1000); // Generate random integers in range 0 to 999
    return choices.get(r.nextInt(choices.size()));
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
    Set<Integer> ids = new HashSet();
    for (int i = 0; i < n; i++) {
      ids.add(in.nextInt());
    }
    return ids;
  }

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    int myId = in.nextInt();
    Board board = Board.build(in);
    ForbiddenPoints forbiddenPoints = new ForbiddenPoints(board);

    int snakePerPlayer = in.nextInt();
    Set<Integer> myIds;
    Set<Integer> opponentIds;

    // if (myId == 0) {
    myIds = getIds(snakePerPlayer, in);
    opponentIds = getIds(snakePerPlayer, in);
    // } else {
    //   opponentIds = getIds(snakePerPlayer, in);
    // myIds = getIds(snakePerPlayer, in);
    // }

    int loop = 0;
    while (loop < 250) {
      PowerUp[] powerups = PowerUp.builds(in);
      Snake[] snakes = Snake.builds(in, myIds);
      forbiddenPoints.restartWithSnakes(snakes, powerups);

      StringBuilder resultat = new StringBuilder();
      for (Snake s : snakes) {
        if (!s.isMine) continue;
        PowerUp closest = Computer.closestPowerUp(powerups, s, board);
        Point dir = Computer.getDirection(s, closest, forbiddenPoints);
        Point newHead = Point.move(s.head, dir);
        forbiddenPoints.add(newHead);
        // On avait deja ajouté la queue si proche de 1. donc on ne fait rien ici.
        resultat.append(s.id + " " + Point.name(dir) + ";");
      }
      T.p(resultat);

      loop++;
    }
    in.close();
  }
}
