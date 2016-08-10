import math._
import scala.util._

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 * ---
 * Hint: You can use the debug stream to print initialTX and initialTY, if Thor seems not follow your orders.
 **/
object Player extends App {
  // lightx: the X position of the light of power
  // lighty: the Y position of the light of power
  // initialtx: Thor's starting X position
  // initialty: Thor's starting Y position
  val Array(lightx, lighty, initialtx, initialty) = for(i <- readLine split " ") yield i.toInt
  var posX = initialtx
  var posY = initialty

    // game loop
    while(true) {
      val remainingturns = readInt // The remaining amount of turns Thor can move. Do not remove this line.
        // Write an action using println
        // To debug: Console.err.println("Debug messages...")

        var a = ""

        if(lightx > posX){
          posX += 1
          a += "E"
        } else if(lightx < posX){
          posX -= 1
          a += "W"
        }

        if(lighty < posY){
          posY -= 1
          a += "N"
        } else if(lighty > posY){
          posY += 1
          a += "S"
        }
          println(a) // A single line providing the move to be made: N NE E SE S SW W or NW
    }
}
