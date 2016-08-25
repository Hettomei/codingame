STDOUT.sync=1
n->m{m.to_i}
def c;gets.split.map(n);end
_,_,_,x,d,_,_,g=c
e=(g.times.map{c}+[[x,d]]).sort{|a,b|a[0]<=>b[0]}
loop{i,j,k=gets.split
j=j.to_i
l=e.find{|z,a|z==i.to_i}
puts (k=="RIGHT" && l[1] >= j || k == "LEFT" && l[1] <= j)?"WAIT":"BLOCK"}
