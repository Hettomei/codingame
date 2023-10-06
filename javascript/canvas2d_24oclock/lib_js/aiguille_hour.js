function AiguilleHour (center_x, center_y, rayon_oclock, context){
  this.center_x = center_x;
  this.center_y = center_y;
  this.rayon_oclock = rayon_oclock;
  this.context = context;

  this.seconde_to_radian = function(){
    var date = new Date() ; //Date(10,10,10,10,10,15);
    var hours = date.getHours() + date.getMinutes() / 60 ;
    //console.log(hours);

    return (hours * 180/12) * Math.PI / 180 - Math.PI/2;
  };

  this.draw = function(){
    new VuAiguille(
      this.center_x,
      this.center_y,
      this.rayon_oclock - 200
    ).draw(this.context, this.seconde_to_radian())
  }

}
