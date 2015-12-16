require 'pp'

STDOUT.sync = true # DO NOT REMOVE

class Node

  attr_reader :x, :y

  def initialize(x, y, c)
    @x = x
    @y = y
    @c = c
  end

  def good?
    @c == "0"
  end

  def bad?
    !good?
  end

  def print_coord
    if good?
      "#{@x} #{@y}"
    else
      "-1 -1"
    end
  end

end

width = gets.to_i
height = gets.to_i
points = []

height.times do |y|
  points << gets.chomp.chars.each_with_index.map do |c, x|
    Node.new(x, y, c)
  end
end

height.times do |y|
  a = ''
  width.times do |x|
    po = points[y][x]
    po_droite = points[y][x + 1] || Node.new(-1, -1, ".")
    po_bas = (points[y + 1] && points[y + 1][x]) || Node.new(-1, -1, ".")

    if po.nil? || po.bad?
      next
    else
      a = "#{po.print_coord} #{po_droite.print_coord} #{po_bas.print_coord}"
    end
    puts a
    b = gets.chomp
    puts b
    puts a == b
    puts "-------"
  end
end

a = ""
