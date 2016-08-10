#!/bin/bash

echo '' > final.rb
cat change_array.rb >> final.rb
cat consecutive_color.rb >> final.rb
cat v0.rb >> final.rb

echo "-----------"
cat final.rb | pbcopy
cat final.rb
echo "-----------"
