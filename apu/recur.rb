require 'pp'

STDOUT.sync = true # DO NOT REMOVE

class Node

  attr_reader :x, :y

  def initialize(x, y, all)
    @x = x
    @y = y
    @c = (all[y] && all[y][x]) || "."
    @right = nil
    @bottom = nil

    if all[y] && all[y][x + 1]
      @right = Node.new(x + 1, y, all)
    end

    if all[y + 1] && all[y + 1][x]
      @bottom = Node.new(x, y + 1, all)
    end

  end

  def print_coord
    if good?
      "#{x} #{y}"
    else
      "-1 -1"
    end
  end

  def good?
    @c == "0"
  end

  def bad?
    !good?
  end

  def valid_right
    if @right.nil?
      Node.new(-1, -1, [])
    elsif @right.good?
      @right
    elsif @right.bad?
      @right.valid_right
    end
  end

  def valid_bottom
    if @bottom.nil?
      Node.new(-1, -1, [])
    elsif @bottom.good?
      @bottom
    elsif @bottom.bad?
      @bottom.valid_bottom
    end
  end

  def next_valid_existing_node
    if @right.nil?
      if @bottom.nil?
        nil
      elsif @bottom.good?
        @bottom
      elsif @bottom.bad?
        @bottom.next_valid_existing_node
      end
    elsif @right.good?
      @right
    elsif @right.bad?
      @right.next_valid_existing_node
    end
  end
end

width = gets.to_i
height = gets.to_i
all = []

y = 0

height.times do
  all << gets.chomp.chars
end

all.each do |a|
  p a
end

next_node = Node.new(0, 0, all)

if next_node.bad?
  next_node = next_node.next_valid_existing_node
end

# begin
#   a = "#{next_node.print_coord} #{next_node.valid_right.print_coord} #{next_node.valid_bottom.print_coord}"
#   puts a
#   b = gets.chomp
#   puts b
#   puts a == b
#   next_node = next_node.next_valid_existing_node
# end while next_node
