test = []
test << '1;Maison de la Prevention Sante;6 rue Maguelone 340000 Montpellier;;3,87952263361082;43,6071285339217'
test << '2;Hotel de Ville;1 place Georges Freche 34267 Montpellier;;3,89652239197876;43,5987299452849'
test << '3;Zoo de Lunaret;50 avenue Agropolis 34090 Mtp;;3,87388031141133;43,6395872778854'

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

# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.

obj = Struct.new(:long, :lat).new(to_i("3,879483"), to_i("43,608177"))
n = "3".to_i
defibs = []
n.times do |i|
  defibs << Defib.new(test[i], obj)
end

max = defibs.first
defibs.each do |defib|
  if max.distance > defib.distance
    max = defib
  end
end

puts max.name
