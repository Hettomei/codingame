def to_i str
  str.tr(",", ".").to_f
end

class Defib

  attr_reader :name

  def initialize params, target
    @id, @name, @adresse, @tel, @long, @lat = params.split(";")
    @lat = to_i(@lat)
    @long = to_i(@long)
    @target = target
  end

  def distance
    @distance ||= begin
                    x = (@target.long - @long) * Math.cos((@lat + @target.lat)/2)
                    y = (@target.lat - @lat)
                    x ** 2 + y ** 2
                  end
  end

end

:wqa

# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.

obj = Struct.new(:long, :lat).new(to_i(gets.chomp), to_i(gets.chomp))
n = gets.to_i
defibs = []
n.times do |i|
  defibs << Defib.new(gets.chomp, obj)
end

max = defibs.first
defibs.each do |defib|
  if max.distance > defib.distance
    max = defib
  end
end

puts max.name
