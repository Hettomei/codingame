import * as PIXI from "pixi.js";
import * as plan from "./plan";

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

addLineFunction((x) => x, 0, 4);
addLineFunction((x) => x + 1, 0, 3);
addLineFunction(() => 1 / 2, 1, 5);
addPointFunction((x) => Math.sqrt(x), 0, 5);
addPointFunction((x) => 1 / x, 0, 5);

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
}

app.ticker.add(() => {});
