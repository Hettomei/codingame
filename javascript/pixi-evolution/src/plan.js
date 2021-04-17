import * as PIXI from "pixi.js";

function render(referent) {
  const plan = new PIXI.Graphics();
  plan.lineStyle({ width: 3, color: 0xffffff, alpha: 0.5 });
  // x axis
  plan.moveTo(0, referent.initY);
  plan.lineTo(window.innerWidth, referent.initY);

  // y axis
  plan.moveTo(referent.initX, 0);
  plan.lineTo(referent.initX, window.innerHeight);

  // little x lines
  plan.lineStyle({ width: 1, color: 0xffffff, alpha: 0.1 });
  let i = 0;
  while (referent.placeY(i) > 0) {
    i = i + 1;
    plan.moveTo(0, referent.placeY(i));
    plan.lineTo(window.innerWidth, referent.placeY(i));
  }

  // little y lines
  plan.lineStyle({ width: 1, color: 0xffffff, alpha: 0.1 });
  let j = 0;
  while (referent.placeX(j) < window.innerWidth) {
    j = j + 1;
    plan.moveTo(referent.placeX(j), 0);
    plan.lineTo(referent.placeX(j), window.innerHeight);
  }

  return plan;
}

export { render };
