# Auto-generated code below aims at helping you parse
# the standard input according to the problem statement.

# read N

previous=-110000000
value=110000000

for i in $(cat tie.txt | sort -n ); do
  newVal=$(($i - $previous))
  if [ $newVal -lt $value ]
  then
    value=$newVal
  fi

  previous=$i
done

echo $value
