class Chute

  def initialize(a)
    @a = a
  end

  def chute
    a = nnext
    if a == Chute.new(a.dup).nnext
      a
    else
      self.new(a.dup).nnext
    end
  end

  def nnext
    other = @a

    (@a.size - 1).times do |y|
      (@a[y].size).times do |x|
        if @a[y][x] != "." && @a[y+1][x] == "."
          save =  @a[y][x]
          other[y][x] = '.'
          other[y+1][x] = save
        end
      end
    end

    other
  end

end
