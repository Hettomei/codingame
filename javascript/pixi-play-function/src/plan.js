import * as PIXI from "pixi.js";

function render(referent) {
  const plan = new PIXI.Graphics();
  plan.lineStyle({ width: 3, color: 0xffffff, alpha: 0.5 });
  // x axis
  plan.moveTo(0, referent.y);
  plan.lineTo(window.innerWidth, referent.y);

  // y axis
  plan.moveTo(referent.x, 0);
  plan.lineTo(referent.x, window.innerHeight);

  // little x lines
  plan.lineStyle({ width: 1, color: 0xffffff, alpha: 0.1 });
  let i = 0;
  while (referent.y - i * referent.zoom > 0) {
    i = i + 1;
    plan.moveTo(0, referent.y - i * referent.zoom);
    plan.lineTo(window.innerWidth, referent.y - i * referent.zoom);
  }

  // little y lines
  plan.lineStyle({ width: 1, color: 0xffffff, alpha: 0.1 });
  let j = 0;
  while (referent.x + j * referent.zoom < window.innerWidth) {
    j = j + 1;
    plan.moveTo(referent.x + j * referent.zoom, 0);
    plan.lineTo(referent.x + j * referent.zoom, window.innerHeight);
  }

  return plan;
}

export { render };
