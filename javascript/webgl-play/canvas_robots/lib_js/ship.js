function Ship(x, y, context){
  // changing val
  this.x = x;
  this.y = y;

  this.move = 0;
  this.turn = 0;
  this.current_angle = 3 * Math.PI / 2;

  //Static
  this.context = context;
  this.rayon = 20;
  this.move_step = 0.1;
  this.angle_step = 0.1 * Math.PI / 180; //in radian, so it s 5 degrée into radian

  this.last_changing_direction = 0;
  this.next_changing_direction = 0;

  this.fires = [];
};

Ship.prototype.sin_angle = function(){
  return Math.sin(this.current_angle);
};

Ship.prototype.cos_angle = function(){
  return Math.cos(this.current_angle);
};

Ship.prototype.update = function(delta){
  this.current_angle += this.turn * this.angle_step * delta;

  var compute = this.move * this.move_step * delta;
  this.x += compute * this.cos_angle();
  this.y += compute * this.sin_angle();

  this.fires.forEach(function(entry) {
    entry.update(delta);
  });
};

Ship.prototype.turn_right = function(){
  this.turn = 1;
};

Ship.prototype.turn_left = function(){
  this.turn = -1;
};

Ship.prototype.turn_stop = function(){
  this.turn = 0;
};

Ship.prototype.go_back = function(){
  this.move = -1;
};

Ship.prototype.go_forward = function(){
  this.move = 1;
};

Ship.prototype.stop = function(){
  this.move = 0;
};

Ship.prototype.fire = function(){
  this.fires.push(new Fire(
        this.x,
        this.y,
        this.current_angle,
        this.context
        ));
};

Ship.prototype.draw = function (){
  this.context.beginPath();
  this.context.arc(this.x, this.y, this.rayon, 0, Math.PI*2);
  this.context.lineWidth = 3;
  this.context.stroke();

  new Point(this.x, this.y).draw(this.context);

  //this.context.beginPath();
  this.context.moveTo(
      this.x,
      this.y
      );

  this.context.lineTo(
      this.x + this.rayon * this.cos_angle(),
      this.y + this.rayon * this.sin_angle()
      );

  this.context.stroke();

  this.fires.forEach(function(entry) {
    entry.draw();
  });
};

Ship.prototype.update_direction = function(delta){

  this.last_changing_direction += delta;

  if(this.last_changing_direction > this.next_changing_direction){
    this.last_changing_direction = 0;
    this.next_changing_direction = Math.floor(Math.random()*3000) + 1000;
    var rand_turn = Math.floor(Math.random()*2);
    var rand_move = Math.floor(Math.random()*2);

    if(rand_turn === 1){
      this.turn_right();
    }else{
      this.turn_left();
    }

    if(rand_move === 1){
      this.go_back();
    }else{
      this.go_forward();
    }

  }
  this.update(delta);
};
