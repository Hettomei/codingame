import * as PIXI from "pixi.js";
import * as plan from "./plan";
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

const referent = {
  x: 40,
  y: window.innerHeight - 50,
  zoom: 200,
};

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
  graphics.moveTo(
    referent.x + min * referent.zoom,
    referent.y - f(min) * referent.zoom
  );
  graphics.lineTo(
    referent.x + max * referent.zoom,
    referent.y - f(max) * referent.zoom
  );

  const basicText = new PIXI.Text(f.toString(), { fill: 0xffffff });
  basicText.x = referent.x + max * referent.zoom;
  basicText.y = referent.y - f(max) * referent.zoom;
  app.stage.addChild(basicText);

  return f;
}

function addPointFunction(f, min, max) {
  const step = 0.05;

  graphics.moveTo(
    referent.x + min * referent.zoom,
    referent.y - f(min) * referent.zoom
  );

  let i = min;
  while (
    referent.x + (i + step) * referent.zoom <
    referent.x + max * referent.zoom
  ) {
    i = i + step;
    graphics.lineTo(
      referent.x + i * referent.zoom,
      referent.y - f(i) * referent.zoom
    );
  }

  graphics.lineTo(
    referent.x + max * referent.zoom,
    referent.y - f(max) * referent.zoom
  );

  const basicText = new PIXI.Text(f.toString(), { fill: 0xffffff });
  basicText.x = referent.x + max * referent.zoom;
  basicText.y = referent.y - f(max) * referent.zoom;
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

  bob.x = referent.x + x * referent.zoom - 15;
  bob.y = referent.y - f(x) * referent.zoom - 40;
}

function upListener() {
  vx = 0.005;
}

window.addEventListener("keydown", downListener, false);
window.addEventListener("keyup", upListener, false);
