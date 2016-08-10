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
