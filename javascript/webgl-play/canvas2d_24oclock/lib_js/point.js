//objet point
function Point(x, y) {
  this.x = x;
  this.y = y;

  this.draw = function (context) {
    context.beginPath();
    context.arc(this.x, this.y, 10, 0, Math.PI * 2);
    context.fillStyle = "#000000";
    context.fill();
    context.closePath();
  };
}
