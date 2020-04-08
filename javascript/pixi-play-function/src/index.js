import * as PIXI from "pixi.js";

const app = new PIXI.Application();
document.body.appendChild(app.view);

app.stage.interactive = true;

app.renderer.autoResize = true;
app.renderer.backgroundColor = 0x061639;
app.renderer.view.style.position = "absolute";
app.renderer.view.style.display = "block";
app.renderer.autoResize = true;
app.renderer.resize(window.innerWidth, window.innerHeight);

const plan = new PIXI.Graphics();
plan.lineStyle(2, 0xffffff, 1);

const zero = {
  x: 40,
  y: window.innerHeight - 50,
};

plan.moveTo(-10, zero.y);
plan.lineTo(window.innerWidth, zero.y);
plan.closePath();

plan.moveTo(zero.x, -200);
plan.lineTo(zero.x, window.innerHeight);
plan.closePath();

app.stage.addChild(plan);

const littleShapeMove = new PIXI.Graphics();
littleShapeMove.lineStyle(2, 0xffffff, 1);
littleShapeMove.moveTo(0, 0);
littleShapeMove.lineTo(20, 0);
littleShapeMove.lineTo(20, 20);
littleShapeMove.lineTo(0, 20);
littleShapeMove.closePath();

littleShapeMove.pivot.x = 10;
littleShapeMove.pivot.y = 10;

littleShapeMove.position.x = 20;
littleShapeMove.position.y = 20;

app.stage.addChild(littleShapeMove);

app.renderer.plugins.interaction.on("pointerdown", onPointerDown);

const graphics = new PIXI.Graphics();

app.stage.addChild(graphics);

function onPointerDown(a) {
  console.log("pointer down", a);

  add_function((x) => x, 0, 200);
  add_function(() => 200, 200, 400);
  add_function((x) => -x + 600, 200, 500);
  add_function_n((x) => Math.sqrt(x*100), 0, 1000, 2);
  add_function_n((x) => 1/x * 10000, 1, 1000, 2);
}

function add_function(f, min, max) {
  graphics.lineStyle(2, 0xffffff, 1);

  graphics.moveTo(zero.x + min, zero.y - f(min));
  graphics.lineTo(zero.x + max, zero.y - f(max));
}

function add_function_n(f, min, max, step) {
  graphics.lineStyle(2, 0xffffff, 1);

  graphics.moveTo(zero.x + min, zero.y - f(min));

  for (let i = min +1; i < max; i += step) {
    graphics.lineTo(zero.x + i, zero.y - f(i));
  }

  graphics.lineTo(zero.x + max, zero.y - f(max));
}

let count = 0;
app.ticker.add(() => {
  count += 0.001;
  littleShapeMove.rotation = count;
});
