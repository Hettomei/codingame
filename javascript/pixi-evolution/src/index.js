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

// let x = 0;
// let i = 0;
// let vx = 0.02;
// app.ticker.add((a) => { console.log(a);});

const JUNCTION = 0;
const STEEL_BAR = 1;
const state = {
  x: 500,
  y: 500,
  junction: null,
  steelBar: null,
  status: STEEL_BAR,
  list: [],
};

function drawJunction(x, y, radius) {
  let circle = new PIXI.Graphics();
  circle.lineStyle(4, 0xffffff, 1);
  circle.drawCircle(0, 0, radius);
  circle.x = x;
  circle.y = y;
  app.stage.addChild(circle);

  let line = new PIXI.Graphics();
  line.lineStyle(4, 0xffffff, 1);
  line.moveTo(0, 0);
  line.lineTo(radius, 0);
  line.x = x;
  line.y = y;
  app.stage.addChild(line);
  state.junction = line;

  return line;
}

function drawSteelBar(x, y, radius) {
  let container = new PIXI.Container();

  let rectangle = new PIXI.Graphics();
  rectangle.lineStyle(3, 0xffffff, 1);
  rectangle.drawRect(0, 0, 64, radius);
  container.addChild(rectangle);

  let line = new PIXI.Graphics();
  line.lineStyle(3, 0xffffff, 1);
  line.moveTo(0, 0);
  line.lineTo(64, radius);
  container.addChild(line);

  container.x = x;
  container.y = y;
  app.stage.addChild(container);

  return container;
}

function nextState(_state) {
  if (_state.status === JUNCTION) {
    _state.steelBar = drawSteelBar(_state.x, _state.y, 30);
    _state.list.push(_state.steelBar);
    _state.status = STEEL_BAR;

    window.removeEventListener("keydown", junctionListener);
    window.addEventListener("keydown", steelBarListener, false);
  } else {
    _state.junction = drawJunction(_state.x, _state.y, 30);
    _state.list.push(_state.junction);
    _state.status = JUNCTION;

    window.removeEventListener("keydown", steelBarListener);
    window.addEventListener("keydown", junctionListener, false);
  }

  console.log(_state);
}

function junctionListener(event) {
  let speed = 0.1;
  if (event.shiftKey) {
    speed += 0.3;
  }
  const line = state.junction;
  // if (event.key === "ArrowLeft") {
  if (event.keyCode === 37) {
    line.rotation = line.rotation - speed;
    // } else if (event.key === "ArrowRight") {
  } else if (event.keyCode === 39) {
    line.rotation = line.rotation + speed;
    // space
  } else if (event.keyCode === 32) {
    nextState(state);
  } else {
    debug(event);
  }
}

function steelBarListener(event) {
  let speed = 3;
  if (event.shiftKey) {
    speed += 10;
  }
  const line = state.steelBar;
  // if (event.key === "ArrowUP") {
  if (event.keyCode === 38) {
    line.width = line.width + speed;
    // } else if (event.key === "ArrowDown") {
  } else if (event.keyCode === 40) {
    line.width = line.width - speed;
    if (line.width < 25) {
      line.width = 25;
    }
    // SPACE
  } else if (event.keyCode === 32) {
    nextState(state);
    console.log(state);
  } else {
    debug(event);
  }
}

function upListener() {}

window.addEventListener("keyup", upListener, false);

nextState(state);
