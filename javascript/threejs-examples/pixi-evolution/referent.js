const initX = 40;
const initY = window.innerHeight - 50;
const zoom = 50;

function placeX(x) {
  return initX + x * zoom;
}

function placeY(i) {
  return initY - i * zoom;
}

export { initX, initY, zoom, placeX, placeY };
