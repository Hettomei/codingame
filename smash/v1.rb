STDOUT.sync = true

def p obj
  STDERR.puts obj.inspect
end

loop do
  color_a = []
  color_b = []
  8.times do
    # color_a: color of the first block
    # color_b: color of the attached block
    c = gets.split(" ")
    color_a << c.first
    color_b << c.last
  end

  my_rows = []
  12.times do
    # One line of the map ('.' = empty, '0' = skull block, '1' to '5' = colored block)
    my_rows << gets.chomp.split("")
  end
  12.times { gets.chomp }

  my_rows[0][0] = color_a.first
  my_rows[0][1] = color_b.first

  sg = SimuleGame.new(my_rows)

end
