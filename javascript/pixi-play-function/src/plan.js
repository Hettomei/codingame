import * as PIXI from "pixi.js";

function render(referent) {
  const plan = new PIXI.Graphics();
  plan.lineStyle({ width: 2, color: 0xffffff, alpha: 0.2 });
  plan.moveTo(-10, referent.y);
  plan.lineTo(window.innerWidth, referent.y);
  plan.closePath();

  plan.moveTo(referent.x, -200);
  plan.lineTo(referent.x, window.innerHeight);
  plan.closePath();
  return plan;
}

export { render };
