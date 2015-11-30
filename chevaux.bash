# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.

# read N
# chevaux=()
# for (( i=0; i<N; i++ )); do
#     read Pi
#     chevaux+=($Pi)
# done
# echo ${chevaux[@]}

chevaux=(234 543 23 12 1 321 50 43)
number=${#chevaux[@]}

sorted=()

# sort $chevaux from min to max
while [ ${#sorted[@]} -lt $number ]
do
  minus=${chevaux[0]}

  n=0
  found=0
  for i in ${chevaux[@]}; do
    if [ $i -lt $minus ]
    then
      minus=$i
      found=$n
    fi
    n=$(($n + 1))
  done

  # unset keep nil at index
  unset chevaux[$found]

  # remove empty elem created with unset
  chevaux=("${chevaux[@]}")

  sorted+=($minus)
done

echo ${sorted[@]}

previous=-111111111
value=10000000000

for i in ${sorted[@]}; do
  newVal=$(($i - $previous))
  if [ $newVal -lt $value ]
  then
    value=$newVal
  fi

  previous=$i
done

echo $value
