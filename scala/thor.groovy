input = new Scanner(System.in);

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 * ---
 * Hint: You can use the debug stream to print initialTX and initialTY, if Thor seems not follow your orders.
 **/

  lightX = input.nextInt() // the X position of the light of power
  lightY = input.nextInt() // the Y position of the light of power
  initialTX = input.nextInt() // Thor's starting X position
initialTY = input.nextInt() // Thor's starting Y position

  posX = initialTX
  posY = initialTY
  // game loop
  while (true) {
    remainingTurns = input.nextInt() // The remaining amount of turns Thor can move. Do not remove this line.

       a = ""


      if(lightY < posY){
        posY -= 1
          a += "N"
      } else if(lightY > posY){
        posY += 1
          a += "S"
      }

    if(lightX > posX){
      posX += 1
        a += "E"
    } else if(lightX < posX){
      posX -= 1
        a += "W"
    }

    println a // A single line providing the move to be made: N NE E SE S SW W or NW
  }
