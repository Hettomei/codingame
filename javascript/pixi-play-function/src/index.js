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


const graphics = new PIXI.Graphics();
graphics.lineStyle(2, 0xFFFFFF, 1);
graphics.moveTo(0, 0);
graphics.lineTo(100, 200);
graphics.lineTo(200, 200);
graphics.lineTo(240, 100);

graphics.position.x = 600;
graphics.position.y = 300;

app.stage.addChild(graphics);

const littleShapeMove = new PIXI.Graphics();
littleShapeMove.lineStyle(2, 0xFFFFFF, 1);
littleShapeMove.moveTo(0, 0);
littleShapeMove.lineTo(20, 0);
littleShapeMove.lineTo(20, 20);
littleShapeMove.lineTo(0, 20);
littleShapeMove.lineTo(0, 0);

littleShapeMove.pivot.x = 10;
littleShapeMove.pivot.y = 10;

littleShapeMove.position.x = 20;
littleShapeMove.position.y = 20;

app.stage.addChild(littleShapeMove);

app.renderer.plugins.interaction.on("pointerdown", onPointerDown);

function onPointerDown(a) {
  console.log("pointer down",a);
}

let count = 0
app.ticker.add(() => {
  count += 0.01;
  littleShapeMove.rotation = count;
});
