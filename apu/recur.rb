STDOUT.sync = true # DO NOT REMOVE

class Node

  @@already_print = []

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
      "#{@x} #{@y}"
    else
      "-1 -1"
    end
  end

  def good?
    @c == "0" && !printed?
  end

  def bad?
    !good?
  end

  def printed?
    @@already_print.find{ |node| node.x == @x && node.y == @y }
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

  def next_valid_bottom
    if @bottom.nil?
      @bottom
    elsif @bottom.good?
      @bottom
    elsif @bottom.bad?
      @bottom.next_valid_right
    end
  end

  def next_valid_right
    if @right.nil?
      @right
    elsif @right.good?
      @right
    elsif @right.bad?
      @right.next_valid_right
    end
  end

  def print_sibling
    return nil if printed?

    puts "#{print_coord} #{valid_right.print_coord} #{valid_bottom.print_coord}"
    @@already_print << self
    b = gets
    puts b.chomp if b
    puts '-'

    (next_valid_right && next_valid_right.print_sibling) && (next_valid_bottom && next_valid_bottom.print_sibling)
  end

end

width = gets.to_i
height = gets.to_i
all = []

height.times do
  all << gets.chomp.chars
end

all.each do |a|
  p a
end

next_node = Node.new(0, 0, all)

if next_node.bad?
  next_node = next_node.next_valid_right
end

next_node.print_sibling if next_node # needed if all point are "."

p "----------------"
begin
  b = gets
  p (b.chomp) if b
end while b
