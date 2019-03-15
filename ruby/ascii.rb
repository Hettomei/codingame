def p o
  STDERR.puts o.inspect
end

def get arr
  @hauteur.times do |i|
    a = ""
    arr.each do |n|
      a << @ascii[i].chars.each_slice(@largeur).to_a[n].join()
    end
    puts a
  end
end

@largeur = gets.to_i
@hauteur = gets.to_i
@str = gets.chomp
p @str
p ""
@ascii = []
@hauteur.times do
  a = gets.chomp
  @ascii << a
  p a
end


val = @str.upcase.gsub(/[^A-Z]/, "@").codepoints.map { |d| d - 65 }
get(val)
