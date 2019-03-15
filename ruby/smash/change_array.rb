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
