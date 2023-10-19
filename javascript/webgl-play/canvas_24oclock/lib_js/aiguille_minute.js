function AiguilleMinute(center_x, center_y, rayon_oclock, context) {
  this.center_x = center_x;
  this.center_y = center_y;
  this.rayon_oclock = rayon_oclock;
  this.context = context;

  this.seconde_to_radian = function () {
    var date = new Date(); //Date(10,10,10,10,10,15);
    var minutes = date.getMinutes() + date.getSeconds() / 60;
    //console.log(minutes);

    return (((minutes * 180) / 30) * Math.PI) / 180 - Math.PI / 2;
  };

  this.draw = function () {
    new VuAiguille(this.center_x, this.center_y, this.rayon_oclock - 150).draw(
      this.context,
      this.seconde_to_radian(),
      "#0000ff",
      5,
    );
  };
}
