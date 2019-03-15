STDOUT.sync = true # DO NOT REMOVE
# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.

# w: width of the building.
# h: height of the building.
w, h = gets.split(" ").collect {|x| x.to_i}
window = Struct.new(:x0, :y0, :x1, :y1).new(0, 0, w, h)
STDERR.puts window.inspect

n = gets.to_i # maximum number of turns before game over.

# position
x, y = gets.split(" ").collect {|x| x.to_i}

loop do
  next_dir = gets.chomp
  STDERR.puts next_dir
  # next_dir can be location (U, UR, R, DR, D, DL, L or UL)
  if ["U", "D"].include?(next_dir)
    up_down = next_dir
    left_right = nil
  elsif ["R", "L"].include?(next_dir)
    up_down = nil
    left_right = next_dir
  else
    up_down = next_dir[0]
    left_right = next_dir[1]
  end

  if up_down == 'U'
    window.y1 = y
  elsif up_down == 'D'
    window.y0 = y
  end

  if up_down
    y = ((window.y1 - window.y0) / 2) + window.y0
  end

  if left_right == 'L'
    window.x1 = x
  elsif left_right == 'R'
    window.x0 = x
  end

  if left_right
    x = ((window.x1 - window.x0) / 2) + window.x0
  end

  STDERR.puts window.inspect

  puts "#{x} #{y}" # the location of the next window Batman should jump to.
end
