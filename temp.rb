# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.

@n = gets.to_i # the number of temperatures to analyse
@temps = gets.chomp.split(" ").map(&:to_i).sort # the n temperatures expressed as integers ranging from -273 to 5526
@temps = [0] if @temps.empty?

STDERR.puts @n.inspect
STDERR.puts @temps.inspect

close0 = 5526
value = 5526
@temps.each do |t|
  if t.abs <= close0
    close0 = t.abs
    value = t
  end
end
# Write an action using puts
# To debug: STDERR.puts "Debug messages..."

puts "#{value}"
