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

let x = 0;
let i = 0;
let vx = 0.020;
// app.ticker.add((a) => { console.log(a);});

function downListener(event) {
  if (event.key === "ArrowLeft") {
  } else if (event.key === "ArrowRight") {
  } else if (event.key === "ArrowUp") {
  } else if (event.key === "ArrowDown") {
  }
}

function upListener() {
}

window.addEventListener("keydown", downListener, false);
window.addEventListener("keyup", upListener, false);
