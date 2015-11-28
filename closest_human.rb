MAX_DIST = 19000 ** 2 + 6000 ** 2

def p ob
  STDERR.puts ob.inspect
end

module Distance

  def distance(obj)
    (x - obj.x) ** 2 + (y - obj.y) ** 2
  end

  def on?(obj)
    x == obj.x && y == obj.y
  end
end

class Ash

  CAN_KILL_ZOMBIE = 2000 ** 2
  attr_reader :x, :y

  include Distance

  def initialize str, zombies
    ash = str.split(" ").map { |x| x.to_i }
    @x  = ash[0]
    @y  = ash[1]
    @zombies = zombies
  end

  def can_kill_a_zombie?
    can_kill?(closest_zombie)
  end

  def can_kill?(zombie)
    zombie.distance(self) < CAN_KILL_ZOMBIE
  end

  def can_kill_on_next?(zombie)
    zombie.distance(self) < CAN_KILL_ZOMBIE * 2
  end

  def closest_zombie
    @closest_zombie ||= @zombies.closest(self)
  end

end

class Humans

  def initialize
    @humans = []
  end

  def << h
    @humans << h
  end

  # obj should have x and y
  def closest(obj)
    human = @humans.first

    min_dist = MAX_DIST

    @humans.each do |h|
      dist = h.distance(obj)
      if dist < min_dist
        human = h
        min_dist = dist
      end
    end

    human
  end

  def count
    @humans.count
  end

  def destroy(human)
    @humans.delete(human)
  end

end

class Human

  KILLED_BY_ZOMBI = 360000

  include Distance

  attr_reader :x, :y
  def initialize str
    human = str.split(" ").map { |x| x.to_i }
    @id = human[0]
    @x  = human[1]
    @y  = human[2]
  end

  def considered_dead?(zombies)
    closest_zombie(zombies).distance(self) < KILLED_BY_ZOMBI
  end

  def closest_zombie(zombies)
    @closest_zombie ||= zombies.closest(self)
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

    min_dist = MAX_DIST

    @zombies.each do |z|
      dist = z.distance(obj)
      if dist < min_dist
        zombie = z
        min_dist = dist
      end
    end

    zombie
  end

  def count
    @zombies.count
  end

end

class Zombie

  attr_reader :x, :y, :xnext, :ynext

  include Distance

  def initialize str
    zombie = str.split(" ").map { |x| x.to_i }
    @id    = zombie[0]
    @x     = zombie[1]
    @y     = zombie[2]
    @xnext = zombie[3]
    @ynext = zombie[4]
  end

end


def human_ash_can_save(humans, ash, zombies)
  human = nil

  loop do
    human = humans.closest(ash)

    if humans.count == 1
      break
    end

    if human.considered_dead?(zombies)
      zombie = human.closest_zombie(zombies)
      if ash.can_kill_on_next?(zombie)
        break
      else
        humans.destroy(human)
      end
    else
      break
    end
  end

  human
end

STDOUT.sync = true # DO NOT REMOVE

rest_move = 0

loop do
  zombies = Zombies.new
  ash = Ash.new(gets, zombies)

  human_count = gets.to_i
  humans = Humans.new
  human_count.times do
    humans << Human.new(gets)
  end

  zombie_count = gets.to_i
  zombie_count.times do
    zombies << Zombie.new(gets)
  end

  human = human_ash_can_save(humans, ash, zombies)
  # Si on est sur un humain,
  # mais qu'on peut tuer personne,
  # alors on s'approche du zombie le plus proche
  if rest_move > 0
    rest_move -= 1
    coord = ash.closest_zombie
  elsif (ash.on?(human) && !ash.can_kill_a_zombie?)
    rest_move = 1
    coord = ash.closest_zombie
  else
    coord = human
  end

  puts "#{coord.x} #{coord.y}"
end
