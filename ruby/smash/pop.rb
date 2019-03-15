class Pop

  def initialize(a)
    @a = a
  end

  def pop
    (@a.size).times do |y|
      (@a[y].size).times do |x|
        if @a[y][x] != "." && @a[y][x] != "0"
          memo = []
          c = count(y, x, @a[y][x], memo)
          if c >= 4
            memo.each do |yy, xx|
              @a[yy][xx] = "."
            end
          end
        end
      end
    end
    @a
  end

  def count(y, x, color, memo)
    if y < 0 ||
        x < 0 ||
        y >= @a.length ||
        x >= @a.first.length ||
        @a[y][x] != color ||
        memo.include?([y,x])
      0
    else
      memo << [y,x]
      1 +
        count(y    , x - 1, color, memo) +
        count(y - 1, x    , color, memo) +
        count(y    , x + 1, color, memo) +
        count(y + 1, x    , color, memo)
    end
  end

end
