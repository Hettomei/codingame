import * as PIXI from "pixi.js";
import * as plan from "./plan";
import * as referent from "./referent";
import { debug } from "./tools";

const app = new PIXI.Application();
document.body.appendChild(app.view);

app.stage.interactive = true;
app.renderer.autoResize = true;
app.renderer.backgroundColor = 0x061639;
app.renderer.view.style.position = "absolute";
app.renderer.view.style.display = "block";
app.renderer.autoResize = true;
app.renderer.resize(window.innerWidth, window.innerHeight);

app.stage.addChild(plan.render(referent));

const graphics = new PIXI.Graphics();
graphics.lineStyle(2, 0xffffff, 1);

app.stage.addChild(graphics);

const all = [
  addLineFunction((x) => x, 0, 4),
  addLineFunction((x) => x + 1, 0, 3),
  addLineFunction(() => 1 / 2, 1, 5),
  addPointFunction((x) => Math.sqrt(x), 0, 5),
  addPointFunction((x) => 1 / x, 0, 5),
];

function addLineFunction(f, min, max) {
  // Put a big step so it writes only one line
  return addPointFunction(f, min, max, max);
}

function addPointFunction(f, min, max, step = 0.05) {
  graphics.moveTo(referent.placeX(min), referent.placeY(f(min)));

  let i = min;
  while (i + step < max) {
    i = i + step;
    graphics.lineTo(referent.placeX(i), referent.placeY(f(i)));
  }

  graphics.lineTo(referent.placeX(max), referent.placeY(f(max)));

  const basicText = new PIXI.Text(f.toString(), { fill: 0xffffff });
  basicText.x = referent.placeX(max);
  basicText.y = referent.placeY(f(max));
  app.stage.addChild(basicText);

  return f;
}

const bob = new PIXI.Graphics();
bob.lineStyle(2, 0x00ff00, 1);
bob.drawRoundedRect(0, 0, 15, 40, 5);
app.stage.addChild(bob);

let x = 0;
let i = 0;
let f = all[i];
let vx = 0.005;
app.ticker.add(() => {});

debug(all);

function downListener(event) {
  vx += 0.001;
  if (event.key === "ArrowLeft") {
    x -= vx;
  } else if (event.key === "ArrowRight") {
    x += vx;
  } else if (event.key === "ArrowUp") {
    i = i + 1;
    f = all[i % all.length];
  } else if (event.key === "ArrowDown") {
    i = i - 1;
    if (i < 0) {
      i = all.length - 1;
    }
    f = all[i % all.length];
  }

  bob.x = referent.placeX(x) - 15;
  bob.y = referent.placeY(f(x)) - 40;
}

function upListener() {
  vx = 0.005;
}

window.addEventListener("keydown", downListener, false);
window.addEventListener("keyup", upListener, false);
