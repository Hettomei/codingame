//objet point
function Point(x , y){
  this.x = x;
  this.y = y;
}

Point.prototype.draw = function (context){
  context.beginPath();
  context.arc(this.x, this.y, 5, 0, Math.PI*2);
  context.fillStyle = "#FF0000";
  context.fill();
  context.closePath();
};
