STDOUT.sync = true # DO NOT REMOVE

class Node

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

  def print_sibling(root)
    if good?
      puts "#{print_coord} #{valid_right.print_coord} #{valid_bottom.print_coord}"
    end

    @right && @right.print_sibling(root)

    if root == self
      @bottom && @bottom.print_sibling(@bottom)
    end
  end

end

width = gets.to_i
height = gets.to_i
all = []

height.times do
  all << gets.chomp.chars
end

next_node = Node.new(0, 0, all)
next_node.print_sibling(next_node)
