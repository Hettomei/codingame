module Roman

# 4 -> [4]
  def split_int(int)
    []
  end

  def to_roman(int)
    a = ""
    int.times do
      a << "I"
    end
    a
  end
end
