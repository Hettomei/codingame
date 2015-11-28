def p ob
  STDERR.puts ob.inspect
end

class Ash

  attr_reader :x, :y

  def initialize str
    ash = str.split(" ").map { |x| x.to_i }
    @x  = ash[0]
    @y  = ash[1]
  end

end

class Human

  def initialize str
    human = str.split(" ").map { |x| x.to_i }
    @id = human[0]
    @x  = human[1]
    @y  = human[2]
  end

end

class Zombies

  def initialize
    @zombies = []
  end

  def << z
    @zombies << z
  end

  # obj should have x and y
  def closest(obj)
    zombie = @zombies.first

    min_dist = 19000 ** 2 + 6000 ** 2

    @zombies.each do |z|
      dist = z.distance(obj)
      if dist < min_dist
        zombie = z
        min_dist = dist
      end
    end

    zombie
  end

end

class Zombie

  attr_reader :x, :y, :xnext, :ynext

  def initialize str
    zombie = str.split(" ").map { |x| x.to_i }
    @id    = zombie[0]
    @x     = zombie[1]
    @y     = zombie[2]
    @xnext = zombie[3]
    @ynext = zombie[4]
  end

  def distance(obj)
    (@x - obj.x) ** 2 + (@y - obj.y) ** 2
  end

end

STDOUT.sync = true # DO NOT REMOVE

loop do
  ash = Ash.new(gets)

  human_count = gets.to_i
  human_count.times do
    h = Human.new(gets)
  end

  zombie_count = gets.to_i
  zs = Zombies.new
  zombie_count.times do
    zs << Zombie.new(gets)
  end

  z = zs.closest(ash)

  puts "#{z.x} #{z.y}"
end
