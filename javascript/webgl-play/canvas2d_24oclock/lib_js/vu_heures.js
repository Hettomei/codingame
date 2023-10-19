function VuHeures(x, y, r) {
  this.x = x;
  this.y = y;
  this.rayon = r;
  this.size_hour = this.rayon - this.rayon / 5;

  this.draw = function (context) {
    //We need this to start at HOUR 1
    var angle = (3 * Math.PI) / 2 + Math.PI / 12;
    var cos_angle, sin_angle;

    for (var i = 1; i <= 24; i++) {
      //to gain speed : save cos and sin
      cos_angle = Math.cos(angle);
      sin_angle = Math.sin(angle);

      this.draw_line(cos_angle, sin_angle, context);
      this.draw_hour(cos_angle, sin_angle, i, context);
      this.draw_min(cos_angle, sin_angle, i, context);

      angle = angle + Math.PI / 12;
    }
  };

  this.draw_hour = function (cos_angle, sin_angle, heure, context) {
    context.font = "bold 14pt Calibri";
    context.textAlign = "center";
    context.fillStyle = "#FF0000";
    context.fillText(
      heure,
      this.x + (this.size_hour - 20) * cos_angle,
      this.y + (this.size_hour - 20) * sin_angle,
    );
  };

  this.draw_min = function (cos_angle, sin_angle, min, context) {
    if (min % 2 == 1) return;
    context.font = "italic 11pt Calibri";
    context.textAlign = "center";
    context.fillStyle = "#000";
    context.fillText(
      (min * 60) / 24 + " min",
      this.x + (this.size_hour - 50) * cos_angle,
      this.y + (this.size_hour - 50) * sin_angle,
    );
  };

  this.draw_line = function (cos_angle, sin_angle, context) {
    context.beginPath();
    context.lineWidth = 1;
    context.strokeStyle = "#000";

    context.moveTo(
      this.x + this.size_hour * cos_angle,
      this.y + this.size_hour * sin_angle,
    );

    context.lineTo(
      this.x + this.rayon * cos_angle,
      this.y + this.rayon * sin_angle,
    );
    context.stroke();
  };
}
