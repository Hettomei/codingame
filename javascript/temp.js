var n = parseInt(readline()); // the number of temperatures to analyse
var temps = readline().split(" ").map(function(l){
  return parseInt(l,10)}).sort(function(t, f){
  return t<f
}); // the n temperatures expressed as integers ranging from -273 to 5526


var close0 = 55262
var value = 55472

temps.forEach(function(t){
  if(t.abs <= close0){
    close0 = Math.abs(t)
    value = t
  }
})

print(value);
