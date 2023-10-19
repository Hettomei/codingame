function Cadran(x, y, r) {
  this.x = x;
  this.y = y;
  this.rayon = r;

  this.draw = function (context) {
    context.beginPath();
    context.arc(this.x, this.y, this.rayon, 0, Math.PI * 2);
    context.lineWidth = 5;
    context.strokeStyle = "#000";
    context.stroke();

    new VuHeures(this.x, this.y, this.rayon).draw(context);
  };
}
