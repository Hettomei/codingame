function VuAiguille(x, y, rayon, width){
  this.x = x;
  this.y = y;
  this.rayon = rayon;
  this.width = width;

  this.draw = function (context, angle){
    context.beginPath();
    context.lineWidth = 1;

    context.moveTo(
      this.x,
      this.y
    );

    context.lineTo(
      this.x + this.rayon * Math.cos(angle),
      this.y + this.rayon * Math.sin(angle)
    );
    context.stroke();
  };
};
