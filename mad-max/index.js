/* global readline printErr print */
/* eslint-disable no-restricted-globals */

// Write an action using print()
const p = printErr;
const TRUE = true;
const readSplit = () => readline().split(' ').map(j => parseInt(j, 10));
const pp = a => p(JSON.stringify(a, null, 1));

// const REAPER = 0;
// const DESTROYER = 1; // kill les tankers
const TANKER = 3; // Contient de l'eau
const EPAVE = 4;

const f = n => b => b.type === n;

function toObj(array) {
  const [
    id,
    type, // 0 (Reaper), 4 (Épave)
    player, // id du joueur pour les Reapers, -1 sinon
    mass, // Masse du véhicule, -1 pour les Épaves
    radius, x, y,
    vx, vy,
    water, // La quantité d'Eau disponible dans une Épave, -1 sinon
    extra2, // tjrs -1
  ] = array;

  return {
    id, type, player, mass, radius, x, y, vx, vy, water, extra2,
  };
}

function square(a) {
  return a * a;
}

function distance(a, b) {
  return Math.sqrt(square(a.x - b.x) + square(a.y - b.y));
}

// 0 <= acc <= 300
function acc(me, b) {
  const d = distance(me, b);
  // const v = distance({ x: 0, y: 0 }, { x: me.vx, y: me.vy });
  if (d > 800) {
    return 300;
  } else if (d > 700) {
    return 150;
  } else if (d > 300) {
    return 60;
  } else if (d > 200) {
    return 50;
  }
  return 0;
}

function moveReaper(me, others) {
  const closestOthers = others.slice().sort((a, b) => distance(me, a) - distance(me, b));
  const best = closestOthers[0];
  return `${best.x} ${best.y} ${acc(me, best)}`;
}

function moveDestroyer(destroyer, tankers) {
  const closestOthers = tankers
    .slice()
    .sort((a, b) => distance(destroyer, a) - distance(destroyer, b));
  const best = closestOthers[0];
  return `${best.x} ${best.y} ${300}`;
}

function follow(me, other) {
  return `${other.x} ${other.y} ${acc(me, other)}`;
}

while (TRUE) {
  const myScore = parseInt(readline(), 10);
  const enemyScore1 = parseInt(readline(), 10);
  const enemyScore2 = parseInt(readline(), 10);
  const myRage = parseInt(readline(), 10);
  const enemyRage1 = parseInt(readline(), 10);
  const enemyRage2 = parseInt(readline(), 10);
  p(myScore, enemyScore1, enemyScore2, myRage, enemyRage1, enemyRage2);

  const unitCount = parseInt(readline(), 10);
  const all = new Array(unitCount)
    .fill(null)
    .map(readSplit)
    .map(toObj);

  const reaper = all[0];
  const destroyer = all[1];
  // pp(reaper);
  // pp(destroyer);

  const epaves = all.filter(f(EPAVE));
  if (epaves.length) {
    print(moveReaper(reaper, epaves));
  } else {
    print(follow(reaper, destroyer));
  }

  const tankers = all.filter(f(TANKER));
  if (tankers.length) {
    print(moveDestroyer(destroyer, tankers));
  } else {
    print('WAIT');
  }

  print('WAIT');
}
