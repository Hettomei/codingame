const SCREEN_WIDTH = 250;
const SCREEN_HEIGHT = 60;

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
      str += has_point(width, height, points) ? "#" : " ";
    }
    console.log(str);
  }
}

function linestoPoints(lines) {
  return lines.map(lineToPoint).flat();
}

function lineToPoint(l) {
  p = [];
  // 1er cas : nous connaissons les coordonnées de deux points distincts de la droite.
  // Par exemple A(-3;9) et B(4;-5).
  // Nous pouvons déterminer le coefficient directeurd de la droite,
  // puis l'équation réduite de la droite :
  // coefficient directeur = ( −5 − 9 ) / ( 4 − (−3) ) = −14 / 7 = −2
  // On obtient alors : y = −2x + k, avec k constante réelle à déterminer.
  // Les coordonnées du point A, qui appartient à la droite, doivent vérifier l'équation. D'où :
  // 9 = −2× (−3) + k et de là k = 9 − 6 = 9 − 6 = 3.

  // y = coef * x + k
  // l.y1 = coef * l.x1 + k
  const coef = (l.y2 - l.y1) / (l.x2 - l.x1);
  const k = l.y1 - coef * l.x1;
  for (let x = l.x1; x <= l.x2; x++) {
    p.push(point(x, Math.floor(coef * x + k)));
  }
  return p;
}

function coeff(l) {
  const coef = (l.y2 - l.y1) / (l.x2 - l.x1);
  const k = l.y1 - coef * l.x1;
  return [coef, k];
}
function bezierToPoint(l1, l2) {
  p = [];

  const [coef1, k1] = coeff(l1);
  const [coef2, k2] = coeff(l2);

  const lines = [];
  // Probleme, la longueur
  let xx = 0;
  for (let x = l1.x1; x <= l1.x2; x++) {
    const p1 = point(x, Math.floor(coef1 * x + k1));
    const p2 = point(l2.x1 + xx, Math.floor(coef2 * (l2.x1 + xx) + k2));
    xx++;
    // p.push(p1);
    // p.push(p2);
    const l3 = line(p1.x, p1.y, p2.x, p2.y);
    lines.push(l3);
  }

  lines.forEach((l3, x) => {
    const [coef3, k3] = coeff(l3);
    p.push(point(x * 2, Math.floor(coef3 * 2 * x + k3)));
  });
  return p;
}

function main() {
  points = [point(1, 1), point(3, 1), point(5, 1)];

  lines = [
    line(5, 5, 30, 10),
    line(6, 10, 22, 3),
    // aaaaaaaaaaaaaaaaaaaaaaaaa
    // line(2, 58, 100, 5),
    // line(100, 5, 200, 50),
  ];

  l1 = line(2, 58, 100, 5);
  l2 = line(100, 5, 200, 50);
  drawPoints(points.concat(linestoPoints(lines)).concat(bezierToPoint(l1, l2)));
}

main();
