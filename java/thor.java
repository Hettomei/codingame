import java.util.*;
import java.io.*;
import java.math.*;

class Player {

  public static void main(String args[]) {
    Scanner in = new Scanner(System.in);
    int lightX = in.nextInt(); // the X position of the light of power
    int lightY = in.nextInt(); // the Y position of the light of power
    int initialTX = in.nextInt(); // Thor's starting X position
    int initialTY = in.nextInt(); // Thor's starting Y position

    // game loop
    while (true) {
      int remainingTurns = in.nextInt(); // The remaining amount of turns Thor can move. Do not remove this line.
      String str = "";

      if (initialTY > lightY){
        initialTY -= 1 ;
        str += "N";
      }
      else if (initialTY < lightY) {
        initialTY += 1;
        str += "S";
      }

      if (initialTX < lightX) {
        initialTX += 1;
        str += "E";
      }
      else if (initialTX > lightX) {
        initialTX -= 1;
        str += "W";
      }

      System.out.println(str);
    }
  }
}
