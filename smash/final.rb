
class ChangeArray
  DOT = ".".freeze

  def initialize(rows, position, color)
    @rows = rows
    @position = position
    @color = color
  end

  def transform
    other = []
    done = false
    column.reverse.each do |c|
      if done
        other << c
      elsif c == "."
        done = true
        other << @color
      else
        other << c
      end
    end
    other.reverse!

    copy = @rows.transpose
    copy[@position] = other
    copy.transpose
  end

  def column
    @column ||= transpose[@position]
  end

  def transpose
    @transpose ||= @rows.transpose
  end

end
class ConsecutiveColor

  Vec = Struct.new(:x, :y)

  def initialize(rows, position)
    @rows = rows
    @position = position
  end

  def count
    return 0 if column.all? { |c| c == "." || c == "0" }

    memo = []
    count_recur(start_position.x, start_position.y, memo)
  end

  def count_recur(x, y, memo)
    if x < 0 ||
        y < 0 ||
        x >= @rows.first.length ||
        y >= @rows.length ||
        @rows[y][x] != last_color ||
        memo.include?([x,y])
      0
    else
      memo << [x,y]
      1 +
        count_recur(x - 1, y, memo) +
        count_recur(x, y - 1, memo) +
        count_recur(x + 1, y, memo) +
        count_recur(x, y + 1, memo)
    end
  end

  def start_position
    return @start_position if defined?(@start_position)
    i = 0
    flag = false

    column.each_with_index do |c, j|
      next if flag

      if c != '0' && c != '.'
        i = j
        flag = true
      end
    end

    @start_position = Vec.new(@position, i + 1)
  end

  def last_color
    @last_color ||= @rows[start_position.y][start_position.x]
  end

  def column
    @column ||= @rows.transpose[@position]
  end

end
STDOUT.sync = true

def p obj
  STDERR.puts obj.inspect
end

Possible = Struct.new(:array, :rotate)

loop do
  color_a, color_b = gets.split(" ")
  7.times do
    # color_a: color of the first block
    # color_b: color of the attached block
    color_aa, color_bb = gets.split(" ")
  end

  my_rows = []
  12.times do
    # One line of the map ('.' = empty, '0' = skull block, '1' to '5' = colored block)
    my_rows << gets.chomp.split("")
  end

  12.times do
    row = gets.chomp
  end

  best_consecutive_color = 0
  best_position = 0
  best_rotation = 0

  rotation = 0
  5.times do |i|
    next unless my_rows[0][i] == "."
    next unless my_rows[0][i+1] == "."
    new_array = ChangeArray.new(my_rows, i, color_a).transform
    new_array = ChangeArray.new(new_array, i+1, color_b).transform

    if color_a != color_b
      consecutive_color = ConsecutiveColor.new(new_array, i).count + ConsecutiveColor.new(new_array, i + 1).count
    else
      consecutive_color = ConsecutiveColor.new(new_array, i).count
    end

    if best_consecutive_color < consecutive_color
      best_position = i
      best_rotation = rotation
      best_consecutive_color = consecutive_color
    end
  end

  rotation = 1
  6.times do |i|
    next unless my_rows[0][i] == "."
    next unless my_rows[1][i] == "."
    new_array = ChangeArray.new(my_rows, i, color_a).transform
    new_array2 = ChangeArray.new(new_array, i, color_b).transform

    if color_a != color_b
      consecutive_color = ConsecutiveColor.new(new_array, i).count + ConsecutiveColor.new(new_array2, i).count
    else
      consecutive_color = ConsecutiveColor.new(new_array2, i).count
    end

    if best_consecutive_color < consecutive_color
      best_position = i
      best_rotation = rotation
      best_consecutive_color = consecutive_color
    end
  end

  rotation = 2
  [1,2,3,4,5].each do |i|
    next unless my_rows[0][i] == "."
    next unless my_rows[0][i-1] == "."
    new_array = ChangeArray.new(my_rows, i, color_a).transform
    new_array = ChangeArray.new(new_array, i-1, color_b).transform

    if color_a != color_b
      consecutive_color = ConsecutiveColor.new(new_array, i).count + ConsecutiveColor.new(new_array, i - 1).count
    else
      consecutive_color = ConsecutiveColor.new(new_array, i).count
    end

    if best_consecutive_color < consecutive_color
      best_position = i
      best_rotation = rotation
      best_consecutive_color = consecutive_color
    end
  end

  rotation = 3
  6.times do |i|
    next unless my_rows[0][i] == "."
    next unless my_rows[1][i] == "."
    new_array = ChangeArray.new(my_rows, i, color_b).transform
    new_array2 = ChangeArray.new(new_array, i, color_a).transform

    if color_a != color_b
      consecutive_color = ConsecutiveColor.new(new_array, i).count + ConsecutiveColor.new(new_array2, i).count
    else
      consecutive_color = ConsecutiveColor.new(new_array2, i).count
    end

    if best_consecutive_color < consecutive_color
      best_position = i
      best_rotation = rotation
      best_consecutive_color = consecutive_color
    end
  end

  puts "#{best_position} #{best_rotation}"
end
