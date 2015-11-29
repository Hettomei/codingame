def group_succ(bin)
  a = []
  b = ""
  previous = nil

  # ["1", "0", "0", "0", "0", "1", "1"]
  bin.each do |i|
    if i == previous
    else
      a << b
      b = ""
    end
    b << i
    previous = i
  end
  a << b
  a.shift
  a
end

def to_0(arr)
  total = []
  arr.each do |str|
    if str[0] == "0"
      a = "00"
    else
      a = "0"
    end
    total << "#{a} #{'0' * str.length}"
  end
  total
end

@message = gets.chomp
STDERR.puts @message.inspect

seq = []
@message.codepoints.each do |cr|
  a = cr.to_s(2).chars
  a.unshift('0') if a.length == 6
  seq << a
end

seq.flatten!

puts to_0(group_succ(seq)).join(" ")
