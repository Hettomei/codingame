STDOUT.sync = true

# node: the total number of nodes in the level, including the gateways
# link_number: the number of links
# passerel_number: the number of exit gateways
@node, @link_number, @passerel_number = gets.split(" ").map(&:to_i)
@sorties = []

Ligne = Struct.new(:n1, :n2)

def closest mechant
  link = nil
  @sorties.each do |s|
    link = @nodes.find do |n|
      (n.n1 == mechant || n.n2 == mechant) && (n.n1 == s || n.n2 == s)
    end
    break if link
  end

  link
end

@nodes = []
@link_number.times do
  # n1: N1 and N2 defines a link_number between these nodes
  n1, n2 = gets.split(" ").map(&:to_i)
  @nodes << Ligne.new(n1, n2)
end

@passerel_number.times do
  @sorties << gets.to_i
end

# game loop
loop do
  mechant = gets.to_i # The index of the node on which the Skynet agent is positioned this turn

  link = closest(mechant)

  if link
  else
    @sorties.each do |s|
      link = @nodes.find do |n|
        (n.n1 == s || n.n2 == s)
      end
      break if link
    end
  end

  @nodes.delete(link)
  puts "#{link.n1} #{link.n2}"

end
