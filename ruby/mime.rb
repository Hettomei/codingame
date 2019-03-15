
# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.

@n = gets.to_i # Number of elements which make up the association table.
@q = gets.to_i # Number Q of file names to be analyzed.
mimes = []
@n.times do
  # ext: file extension
  # mt: MIME type.
  ext, mt = gets.split(" ")
  mimes << [ext, ext.downcase, mt]
end
a = []
@q.times do
  fname = gets.chomp # One file name per line.

  if fname[-1] == "."
    fname << "fdsfdsfdsfds"
  end

   if fname.split(".").count == 1
     fname << ".fdsfdsfdsfds"
   end

b = "UNKNOWN"
fext = fname.split(".").last.downcase

  mimes.each do |ext, extdown,translate|
    if fext == extdown
      b = translate
      break
    end
  end

   a << b
end
puts a.join("\n")

# Write an action using puts
# To debug: STDERR.puts "Debug messages..."

