require_relative "./chute"
require_relative "./pop"

class SimuleGame

  def initialize(a)
    @a = a
  end

  def simule
    puts "@a"
    pp @a
    c = Chute.new(@a.dup).chute
    puts "@a"
    pp @a
    puts "chute"
    pp c
    pop = Pop.new(c.dup).pop
    puts "pop"
    pp pop
    if pop == @a
      pop
    else
      SimuleGame.new(pop.dup).simule
    end
  end

end
