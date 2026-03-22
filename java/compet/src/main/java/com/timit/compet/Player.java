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

class Board {

  Set<Point> murs;
  Set<Point> leSol;

  int width;
  int height;

  Board(Set<Point> murs, int width, int height) {
    this.murs = murs;
    this.width = width;
    this.height = height;
    this.leSol = new HashSet();
  }

  boolean isForbidden(int x, int y) {
    return !isAvailable(x, y);
  }

  boolean isAvailable(int x, int y) {
    return isAvailable(new Point(x, y));
  }

  boolean isAvailable(Point p) {
    return !murs.contains(p) && !leSol.contains(p);
  }

  void buildSol() {
    // interdit d aller dans le sol si le sol ressemble a :
    // ###  ##   ####  ##  #####
    for (int x = -5; x < width + 5; x++) {
      leSol.add(new Point(x, height));
    }

    // interdit d aller tout a gauche
    // interdit d aller tout a droite
    // et on fait depasser de 5
    // ###  ##   ####  ##  #####
    // for (int y = -5; y <= height; y++) {
    //   leSol.add(new Point(-1, y));
    //   leSol.add(new Point(width, y));
    // }
  }

  static Board build(Scanner in) {
    int width = in.nextInt();
    int height = in.nextInt();
    Set<Point> murs = new HashSet();
    in.nextLine(); // utile pour passer à la suite
    for (int row = 0; row < height; row++) {
      String rawLine = in.nextLine();
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
    for (int row = -1; row < height + 1; row++) {
      for (int col = -3; col < width + 3; col++) {
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

  /*
   * Quand c est cote a cote, ca vaut 1
   * quand c est en diago, ca vaut 2
   * Quand c est au meme endroit ... 0
   */
  static int distance(Point a, Point b) {
    return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
  }

  String name() {
    if (this.equals(UP)) return "UP";
    if (this.equals(DOWN)) return "DOWN";
    if (this.equals(LEFT)) return "LEFT";
    if (this.equals(RIGHT)) return "RIGHT";
    return "ERREUR DE NOM";
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
  // Toutes les cases interdites a chaque tour
  Set<Point> newTurnForbidden;
  Set<Point> newTurnCulDeSac;
  Set<Point> culDeSac;

  ForbiddenPoints(Board board) {
    this.board = board;
    newTurnForbidden = new HashSet();
    newTurnCulDeSac = new HashSet();
    culDeSac = new HashSet();
  }

  /*  .
   * .X.  => x vaut 4 si il y en a 4 autour
   *  .
   */
  int caseLibreAutour(int x, int y) {
    int count = 0;
    if (isAvailableSansCulDeSac(x - 1, y)) count++;
    if (isAvailableSansCulDeSac(x + 1, y)) count++;
    if (isAvailableSansCulDeSac(x, y - 1)) count++;
    if (isAvailableSansCulDeSac(x, y + 1)) count++;

    return count;
  }

  int caseLibreAutourSansCulDeSac(Point p) {
    return caseLibreAutour(p.x, p.y);
  }

  // Toutes les cases ou on peut juste mettre la tete :
  // C est un cul de sac !
  void buildCulDeSac() {
    int minX = findMinimalX();
    int maxX = findMaximalX();
    int minY = findMinimalY();
    int maxY = findMaximalY();

    for (int y = minY; y <= maxY; y++) {
      for (int x = minX; x <= maxX; x++) {
        if (isAvailable(x, y) && caseLibreAutour(x, y) < 2) culDeSac.add(new Point(x, y));
      }
    }
  }

  void newLoop(Snake[] snakes, PowerUp[] powerups) {
    newTurnForbidden.clear();
    newTurnCulDeSac.clear();
    for (Snake s : snakes) {
      newTurnForbidden.add(s.head);
      newTurnForbidden.addAll(s.getParts());
      // Si la tete est a 1 du plus proche,
      // alors il y a des chance qu il va le bouffer
      // donc ajouter la queue pour pas taper dedans
      if (Computer.snakeTouchePiece(s, powerups)) newTurnForbidden.add(s.tail);
    }
    // Maintenant qu'on a fait tout le newTurnForbidden.
    // on va tout parcourir, et interdir tous les cul de sac

    for (Snake s : snakes) {
      newTurnCulDeSac.addAll(s.listCulDeSac(board));
    }
  }

  void remove(Point p) {
    newTurnForbidden.remove(p);
  }

  void add(Point p) {
    newTurnForbidden.add(p);
  }

  boolean isAvailable(Point point) {
    return board.isAvailable(point) // long comment pour linter
        && !newTurnForbidden.contains(point)
        && !newTurnCulDeSac.contains(point)
        && !culDeSac.contains(point);
  }

  boolean isAvailable(int x, int y) {
    return isAvailable(new Point(x, y));
  }

  boolean isAvailableSansCulDeSac(Point point) {
    return board.isAvailable(point) // long comment pour linter
        && !newTurnForbidden.contains(point)
        && !culDeSac.contains(point);
  }

  boolean isAvailableSansCulDeSac(int x, int y) {
    return isAvailableSansCulDeSac(new Point(x, y));
  }

  int findMinimalX() {
    int minimal = 1000;
    for (Point p : newTurnForbidden) {
      if (p.x < minimal) minimal = p.x;
    }
    for (Point p : board.murs) {
      if (p.x < minimal) minimal = p.x;
    }
    for (Point p : board.leSol) {
      if (p.x < minimal) minimal = p.x;
    }
    return minimal;
  }

  int findMaximalX() {
    int max = 0;
    for (Point p : newTurnForbidden) {
      if (p.x > max) max = p.x;
    }
    for (Point p : board.murs) {
      if (p.x > max) max = p.x;
    }
    for (Point p : board.leSol) {
      if (p.x > max) max = p.x;
    }
    return max;
  }

  int findMinimalY() {
    int minimal = 1000;
    for (Point p : newTurnForbidden) {
      if (p.y < minimal) minimal = p.y;
    }
    for (Point p : board.murs) {
      if (p.y < minimal) minimal = p.y;
    }
    for (Point p : board.leSol) {
      if (p.y < minimal) minimal = p.y;
    }
    return minimal;
  }

  int findMaximalY() {
    int max = 0;
    for (Point p : newTurnForbidden) {
      if (p.y > max) max = p.y;
    }
    for (Point p : board.murs) {
      if (p.y > max) max = p.y;
    }
    for (Point p : board.leSol) {
      if (p.y > max) max = p.y;
    }
    return max;
  }

  public String toString() {
    int minX = findMinimalX();
    int maxX = findMaximalX();
    int minY = findMinimalY();
    int maxY = findMaximalY();
    StringBuilder sb = new StringBuilder();

    // find minimal x, max x
    // find min y, max y

    sb.append("\n");
    for (int y = minY; y <= maxY; y++) {
      for (int x = minX; x <= maxX; x++) {

        if (isAvailable(x, y)) {
          sb.append(" ");
        } else {
          if (newTurnForbidden.contains(new Point(x, y))) sb.append("s");
          else if (newTurnCulDeSac.contains(new Point(x, y))) sb.append(".");
          else if (culDeSac.contains(new Point(x, y))) sb.append(".");
          else sb.append("#");
        }
      }
      sb.append("\n");
    }
    return sb.toString();
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
    this.head = head;
    this.parts = parts;
    this.tail = tail;
  }

  public String toString() {
    String mmine = isMine ? "mine " : "other";
    return "Snake" + " " + mmine + " " + id + " " + head + Arrays.toString(parts) + tail;
  }

  int getCount() {
    return 2 + parts.length;
  }

  boolean notPartOfMe(Point aEvaluer) {
    return !partOfMe(aEvaluer);
  }

  boolean partOfMe(Point aEvaluer) {
    return head.equals(aEvaluer) && tail.equals(aEvaluer) && getParts().contains(aEvaluer);
  }

  Set<Point> getParts() {
    return new HashSet(Arrays.asList(parts));
  }

  // chez snake
  boolean isAvailable(int x, int y, Board board) {
    Point aEvaluer = new Point(x, y);
    return board.isAvailable(aEvaluer)
        && !head.equals(aEvaluer)
        && !tail.equals(aEvaluer)
        && !getParts().contains(aEvaluer);
  }

  // chez snake
  boolean isCulDeSac(Point aEvaluer, Board board) {
    int x = aEvaluer.x;
    int y = aEvaluer.y;

    int count = 0;
    if (isAvailable(x - 1, y, board)) count++;
    if (isAvailable(x + 1, y, board)) count++;
    if (isAvailable(x, y - 1, board)) count++;
    if (isAvailable(x, y + 1, board)) count++;

    return count < 2;
  }

  void addToAvoidIfNeeded(Point aEvaluer, Board board, Set<Point> avoid) {
    if (board.isAvailable(aEvaluer) && notPartOfMe(aEvaluer) && isCulDeSac(aEvaluer, board)) {
      avoid.add(aEvaluer);
    }
  }

  Set<Point> listCulDeSac(Board board) {
    Set<Point> avoid = new HashSet();
    // les petits miens, on s en fou
    if (isMine && getCount() <= 4) return avoid;

    // pour chaque partie existante, on regarde les 4 case autour.
    // si une case est libre, on la compare au board
    if (!isMine) {
      // sans la tete quand c est le mien
      addToAvoidIfNeeded(Point.move(head, Point.UP), board, avoid);
      addToAvoidIfNeeded(Point.move(head, Point.DOWN), board, avoid);
      addToAvoidIfNeeded(Point.move(head, Point.LEFT), board, avoid);
      addToAvoidIfNeeded(Point.move(head, Point.RIGHT), board, avoid);
    }

    for (Point p : parts) {
      addToAvoidIfNeeded(Point.move(p, Point.UP), board, avoid);
      addToAvoidIfNeeded(Point.move(p, Point.DOWN), board, avoid);
      addToAvoidIfNeeded(Point.move(p, Point.LEFT), board, avoid);
      addToAvoidIfNeeded(Point.move(p, Point.RIGHT), board, avoid);
    }

    // tail, on fait pas ?
    // addToAvoidIfNeeded(Point.move(tail, Point.UP), board, avoid);
    // addToAvoidIfNeeded(Point.move(tail, Point.DOWN), board, avoid);
    // addToAvoidIfNeeded(Point.move(tail, Point.LEFT), board, avoid);
    // addToAvoidIfNeeded(Point.move(tail, Point.RIGHT), board, avoid);

    return avoid;
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

  static boolean snakeTouchePiece(Snake snake, PowerUp[] powerups) {
    for (PowerUp powerup : powerups) {
      if (Point.distance(snake.head, powerup) == 1) return true;
    }
    return false;
  }

  static PowerUp closestPowerUp(PowerUp[] powerups, Snake snake, Board board) {
    PowerUp closest = powerups[0];
    int max_distance = Point.distance(snake.head, closest);
    for (PowerUp powerup : powerups) {
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
    board.buildSol();

    ForbiddenPoints forbiddenPoints = new ForbiddenPoints(board);
    forbiddenPoints.buildCulDeSac();
    T.d("FORBIDDEN\n" + forbiddenPoints);

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
      forbiddenPoints.newLoop(snakes, powerups);
      T.d("forbid\n" + forbiddenPoints);

      StringBuilder resultat = new StringBuilder();
      for (Snake s : snakes) {
        if (!s.isMine) continue;
        PowerUp closest = Computer.closestPowerUp(powerups, s, board);
        Point dir = Computer.getDirection(s, closest, forbiddenPoints);
        Point newHead = Point.move(s.head, dir);
        forbiddenPoints.add(newHead);
        // On avait deja ajouté la queue si proche de 1. donc on ne fait rien ici.
        resultat.append(s.id + " " + dir.name() + ";");
      }
      T.p(resultat);

      loop++;
    }
    in.close();
  }
}
