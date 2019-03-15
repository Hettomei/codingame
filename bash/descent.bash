while true; do
  read spaceX spaceY

  max=0
  index=0

  for (( i=0; i<8; i++ )); do
    # mountainH: represents the height of one mountain, from 9 to 0. Mountain heights are provided from left to right.
    read mountainH
    if [ "$mountainH" -gt "$max"]
    then
      max=$mountainH
      index=$i
    fi
  done

  if [ "$spaceX" -eq "$index" ]
  then
    echo FIRE
  else
    echo HOLD
  fi
done
