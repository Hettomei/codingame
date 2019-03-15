def p o
  STDERR.puts o.inspect
end

STDOUT.sync = true # DO NOT REMOVE
# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.

@surface_n = gets.to_i # the number of points used to draw the surface of Mars.
@surface_n.times do
  gets
end

# game loop
a = 0
loop do
  # v_speed: the vertical speed (in m/s), can be negative.
  # fuel: the quantity of remaining fuel in liters.
  # power: the thrust power (0 to 4).
  x, y, h_speed, v_speed, fuel, rotate, power = gets.split(" ").collect {|x| x.to_i}

  a += 1
  # go up
  if a > 12
    puts "0 4"
  else
    puts "0 0"
  end
end
