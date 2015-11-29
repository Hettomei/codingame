@n = gets.to_i
chevaux = []

@n.times do
  chevaux << gets.to_i
end

previous = nil
value = 10000000

chevaux.each do |c|
  if previous && (c - previous) < value
    value = c - previous
  end

  previous = c
end

puts value
