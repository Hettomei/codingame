STDOUT.sync = true

def p obj
  STDERR.puts obj.inspect
end

Possible = Struct.new(:array, :rotate)

loop do
  color_a, color_b = gets.split(" ")
  7.times do
    # color_a: color of the first block
    # color_b: color of the attached block
    color_aa, color_bb = gets.split(" ")
  end

  my_rows = []
  12.times do
    # One line of the map ('.' = empty, '0' = skull block, '1' to '5' = colored block)
    my_rows << gets.chomp.split("")
  end

  12.times do
    row = gets.chomp
  end

  best_consecutive_color = 0
  best_position = 0
  best_rotation = 0

  rotation = 0
  5.times do |i|
    next unless my_rows[0][i] == "."
    next unless my_rows[0][i+1] == "."
    new_array = ChangeArray.new(my_rows, i, color_a).transform
    new_array = ChangeArray.new(new_array, i+1, color_b).transform

    if color_a != color_b
      consecutive_color = ConsecutiveColor.new(new_array, i).count + ConsecutiveColor.new(new_array, i + 1).count
    else
      consecutive_color = ConsecutiveColor.new(new_array, i).count
    end

    if best_consecutive_color < consecutive_color
      best_position = i
      best_rotation = rotation
      best_consecutive_color = consecutive_color
    end
  end

  rotation = 1
  6.times do |i|
    next unless my_rows[0][i] == "."
    next unless my_rows[1][i] == "."
    new_array = ChangeArray.new(my_rows, i, color_a).transform
    new_array2 = ChangeArray.new(new_array, i, color_b).transform

    if color_a != color_b
      consecutive_color = ConsecutiveColor.new(new_array, i).count + ConsecutiveColor.new(new_array2, i).count
    else
      consecutive_color = ConsecutiveColor.new(new_array2, i).count
    end

    if best_consecutive_color < consecutive_color
      best_position = i
      best_rotation = rotation
      best_consecutive_color = consecutive_color
    end
  end

  rotation = 2
  [1,2,3,4,5].each do |i|
    next unless my_rows[0][i] == "."
    next unless my_rows[0][i-1] == "."
    new_array = ChangeArray.new(my_rows, i, color_a).transform
    new_array = ChangeArray.new(new_array, i-1, color_b).transform

    if color_a != color_b
      consecutive_color = ConsecutiveColor.new(new_array, i).count + ConsecutiveColor.new(new_array, i - 1).count
    else
      consecutive_color = ConsecutiveColor.new(new_array, i).count
    end

    if best_consecutive_color < consecutive_color
      best_position = i
      best_rotation = rotation
      best_consecutive_color = consecutive_color
    end
  end

  rotation = 3
  6.times do |i|
    next unless my_rows[0][i] == "."
    next unless my_rows[1][i] == "."
    new_array = ChangeArray.new(my_rows, i, color_b).transform
    new_array2 = ChangeArray.new(new_array, i, color_a).transform

    if color_a != color_b
      consecutive_color = ConsecutiveColor.new(new_array, i).count + ConsecutiveColor.new(new_array2, i).count
    else
      consecutive_color = ConsecutiveColor.new(new_array2, i).count
    end

    if best_consecutive_color < consecutive_color
      best_position = i
      best_rotation = rotation
      best_consecutive_color = consecutive_color
    end
  end

  puts "#{best_position} #{best_rotation}"
end
