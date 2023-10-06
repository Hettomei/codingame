function Fire(x, y, angle, context){
  this.x = x;
  this.y = y;

  //angle in rad
  this.current_angle = angle;
  this.sin_angle = Math.sin(this.current_angle);
  this.cos_angle = Math.cos(this.current_angle);

  this.cos_angle_x = this.cos_angle * 10;
  this.sin_angle_x = this.sin_angle * 10;

  //Static
  this.context = context;
  this.move_step = 1;

}

Fire.prototype.update = function(delta){
  var compute = this.move_step * delta;
  this.x += compute * this.cos_angle;
  this.y += compute * this.sin_angle;
};

Fire.prototype.draw = function (){
  this.context.beginPath();
  this.context.moveTo(
      this.x,
      this.y
      );

  this.context.lineTo(
      this.x + this.cos_angle_x,
      this.y + this.sin_angle_x
      );

  this.context.stroke();
};
