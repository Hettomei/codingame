/* global readline print */
/* eslint-disable no-restricted-globals */

const printErr = undefined;
const p = printErr;
const TRUE = true;
const readSplit = () => readline().split(' ').map(j => parseInt(j, 10));
const z = a => JSON.stringify(a, null, 1);
const pp = a => p(z(a));
const ppp = a => pp({
  x: a.x, y: a.y, vx: a.vx, vy: a.vy,
});

// const REAPER = 0;
// const DESTROYER = 1; // kill les tankers
// const DOOF = 2; // grenade
// const TANKER = 3; // Contient de l'eau
// const EPAVE = 4;

// const f = n => b => b.type === n;

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

function vitesse(car, acc) {
  return (acc / car.mass) * (1 - car.friction);
}

function emplacementFinal(car, direction, acc) {
  // return vitesse(car, acc);
  return { x: car.x, y: car.y - vitesse(car, acc) };
}

function moveReaper(me) {
  const newPoint = { x: me.x, y: me.y - 6000 };

  p('newpoint', z(newPoint), distance(me, newPoint));
  return `${newPoint.x} ${newPoint.y} ${300}`;
}

function moveDoof(me) {
  // const newPoint = { x: me.x, y: me.y - 6000 };

  // p('newpoint', z(newPoint), distance(me, newPoint));
  // return `${newPoint.x} ${newPoint.y} ${300}`;
  return 'WAIT';
}

// function moveDestroyer(destroyer, tankers) {
//   const closestOthers = tankers
//     .slice()
//     .sort((a, b) => distance(destroyer, a) - distance(destroyer, b));
//   const best = closestOthers[0];
//   return `${best.x} ${best.y} ${300}`;
// }

// function follow(me, other) {
//   return `${other.x} ${other.y} ${acc(me, other)}`;
// }

function main() {
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
    reaper.friction = 0.2;
    const doof = all[2];
    doof.friction = 0.25;
    // const destroyer = all[1];
    print(moveReaper(reaper));
    print(moveDoof(doof));

    // const epaves = all.filter(f(EPAVE));
    // if (epaves.length) {
    //   print(moveReaper(reaper, epaves));
    // } else {
    // print(follow(reaper, destroyer));
    // }

    // const tankers = all.filter(f(TANKER));
    // if (tankers.length) {
    //   print(moveDestroyer(destroyer, tankers));
    // } else {
    //   print('WAIT');
    // }

    print('WAIT');
  }
}

// main();

module.exports = {
  square,
  toObj,
  main,
  distance,
  moveDoof,
  vitesse,
  emplacementFinal,
};
