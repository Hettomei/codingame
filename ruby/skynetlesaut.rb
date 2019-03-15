def p o
  STDERR.puts o.inspect
end

STDOUT.sync = true # DO NOT REMOVE
# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.

@road = gets.to_i # the length of the road before the gap.
@gap = gets.to_i # the length of the gap.
@platform = gets.to_i # the length of the landing platform.

# game loop
loop do
  speed = gets.to_i # the motorbike's speed.
  coord_x = gets.to_i # the position on the road of the motorbike.

  b = "SPEED"

  if speed > @gap + 1
    b = "SLOW"
  end

  if speed == @gap + 1
    b = "WAIT"
  end

  if coord_x == @road - 1
    b = "JUMP"
  end

  if coord_x >= @road
    b = "SLOW" # A single line containing one of 4 keywords: SPEED, SLOW, JUMP, WAIT.
  end

  puts b

end
