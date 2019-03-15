STDOUT.sync=1
c=->{gets.split.map(&:to_i)}
b=c[]
e=(1..b[7]).map{c[]}+[b[3,2]]
loop{i,j,k=gets.split
j=j.to_i
l=e.find{|z,a|z==i.to_i}
puts k[?R]&&l[1]>=j||k[?L]&&l[1]<=j ?"WAIT":"BLOCK"}
