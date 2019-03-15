var road = parseInt(readline()); // the length of the road before the gap.
var gap = parseInt(readline()); // the length of the gap.
var platform = parseInt(readline()); // the length of the landing platform.

// game loop
while (true) {
  var speed = parseInt(readline()); // the motorbike's speed.
  var coordX = parseInt(readline()); // the position on the road of the motorbike.

  var b = "SPEED";

  if (speed > gap + 1){
    b = "SLOW" ;
  }

  if (speed == gap + 1){
    b = "WAIT";
  }

  if (coordX == road - 1) {
    b = "JUMP";
  }

  if (coordX >= road){
    b = "SLOW";
  }

  print(b);


}
