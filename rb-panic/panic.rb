STDOUT.sync = true

def p(*obj)
  obj.each { |o| STDERR.puts(o.inspect) }
end

# nb_floors               : number of floors
# width                   : width of the area
# nb_rounds               : maximum number of rounds
# exit_floor              : floor on which the exit is found
# exit_pos                : position of the exit on its floor
# nb_total_clones         : number of generated clones
# nb_additional_elevators : ignore (always zero)
# nb_elevators            : number of elevators
@nb_floors, @width, @nb_rounds, @exit_floor,
  @exit_pos, @nb_total_clones,
  @nb_additional_elevators, @nb_elevators = gets.split(" ").map(&:to_i)

p(@nb_floors, @width, @nb_rounds, @exit_floor,
  @exit_pos, @nb_total_clones,
  @nb_additional_elevators, @nb_elevators)

Exit = Struct.new(:floor, :pos)

exits = @nb_elevators.times.map{
  floor, pos = gets.split(" ").map(&:to_i)
  Exit.new(floor, pos)
}
  .concat([Exit.new(@exit_floor, @exit_pos)])
  .sort{|a,b| a.floor <=> b.floor}
p *exits

loop do
  # clone_floor : floor of the leading clone
  # clone_pos   : position of the leading clone on its floor
  # direction   : direction of the leading clone: LEFT or RIGHT
  clone_floor, clone_pos, direction = gets.split(" ")
  clone_floor = clone_floor.to_i
  clone_pos   = clone_pos.to_i

  next_exit = exits.find{ |e| e.floor == clone_floor }

  p(clone_floor, clone_pos, direction)

  # chercher la prochaine sortie
  if (direction == "RIGHT" && next_exit.pos >= clone_pos) ||
      (direction == "LEFT" && next_exit.pos <= clone_pos)
    puts "WAIT"
  else
    puts "BLOCK"
  end

  # if clone_pos >= (@width - 1)
  #   puts "BLOCK"
  # elsif (clone_pos <= 0)
  #   puts "BLOCK"
  # else
  #   puts "WAIT"
  # end
end
