function AiguilleSeconde (center_x, center_y, rayon_oclock, context){
  this.center_x = center_x;
  this.center_y = center_y;
  this.rayon_oclock = rayon_oclock;
  this.context = context;

  this.seconde_to_radian = function(){
    var date = new Date() ; //Date(10,10,10,10,10,15);
    var secondes = date.getSeconds() + date.getMilliseconds()/1000;
    //console.log(secondes);

    return (secondes * 180/30) * Math.PI / 180 - Math.PI/2;
  };

  this.draw = function(){
    new VuAiguille(
      this.center_x,
      this.center_y,
      this.rayon_oclock - 10
    ).draw(this.context, this.seconde_to_radian())
  }

}
