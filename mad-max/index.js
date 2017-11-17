/* global readline printErr print */
/* eslint-disable no-restricted-globals */
/* eslint-disable no-unused-vars */

// Write an action using print()
const p = printErr;
const TRUE = true;
const readSplit = _ => readline().split(' ').map(j => parseInt(j, 10));
const pp = a => p(JSON.stringify(a, null, 1));

const REAPER = 0;
const EPAVE = 4;

function toObj(array) {
  const [
    unitId,
    unitType, // 0 (Reaper), 4 (Épave)
    player, // id du joueur pour les Reapers, -1 sinon
    mass, // Masse du véhicule, -1 pour les Épaves
    radius, x, y,
    vx, vy,
    water, // La quantité d'Eau disponible dans une Épave, -1 sinon
    extra2, // tjrs -1
  ] = array;

  return {
    unitId, unitType, player, mass, radius, x, y, vx, vy, water, extra2,
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
  const v = distance({ x: 0, y: 0 }, { x: me.vx, y: me.vy });
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

while (TRUE) {
  const myScore = parseInt(readline(), 10);
  const enemyScore1 = parseInt(readline(), 10);
  const enemyScore2 = parseInt(readline(), 10);

  const myRage = parseInt(readline(), 10);
  const enemyRage1 = parseInt(readline(), 10);
  const enemyRage2 = parseInt(readline(), 10);

  const unitCount = parseInt(readline(), 10);
  const all = new Array(unitCount)
    .fill(null)
    .map(readSplit)
    .map(toObj);

  all.forEach(m => p(...m));
  const me = all[0];
  // pp(me);

  const repears = all.slice(1).filter(b => b.unitType === REAPER);
  const epaves = all.filter(b => b.unitType === EPAVE);

  const closestEpaves = epaves.slice().sort((a, b) => distance(me, a) - distance(me, b));
  const best = closestEpaves[0];
  print(`${best.x} ${best.y} ${acc(me, best)}`);
  print('WAIT');
  print('WAIT');
}
