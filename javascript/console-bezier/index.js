const SCREEN_WIDTH = 140;
const SCREEN_HEIGHT = 30;

function point(x, y) {
  return { x, y };
}

function line(x1, y1, x2, y2) {
  return { x1, y1, x2, y2 };
}

function has_point(width, height, points) {
  return points.find((p) => p.x == width && p.y == height);
}

function drawPoints(points) {
  for (let height = 0; height < SCREEN_HEIGHT; height++) {
    let str = "";

    for (let width = 0; width < SCREEN_WIDTH; width++) {
      str += has_point(width, height, points) ? "X" : " ";
    }
    console.log(str);
  }
}

function toPoints(lines) {
  return lines.map(toPoint).flat();
}

function toPoint(l) {
  p = [];
  // Pas bon du tout, faut faire des maths ici
  for (let x = l.x1; x <= l.x2; x++) {
    for (let y = l.y1; y <= l.y2; y++) {
      p.push(point(x, y));
    }
  }
  return p;
}

function main() {
  points = [point(10, 10), point(11, 11), point(12, 12)];

  lines = [line(5, 5, 20, 5), line(5, 5, 20, 20)];
  drawPoints(points.concat(toPoints(lines)));
}

main();
