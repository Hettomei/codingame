import * as PIXI from 'pixi.js'

const app = new PIXI.Application();

app.renderer.autoResize = true;
app.renderer.backgroundColor = 0x061639;
app.renderer.view.style.position = "absolute";
app.renderer.view.style.display = "block";
app.renderer.autoResize = true;
app.renderer.resize(window.innerWidth, window.innerHeight);

document.body.appendChild(app.view);
