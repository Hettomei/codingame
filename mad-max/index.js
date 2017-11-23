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

// 0 <= acc <= 300
// function acc(me, b) {
//   const d = distance(me, b);
//   const v = distance({ x: 0, y: 0 }, { x: me.vx, y: me.vy });
//   if (d - v > 800) {
//     return 300;
//   } else if (d - v > 700) {
//     return 150;
//   } else if (d - v > 600) {
//     return 60;
//   } else if (d - v > 500) {
//     return 50;
//   } else if (d - v < 400) {
//     return 50;
//   }
//   return 0;
// }

function moveReaper(me) {
  const newPoint = { x: me.x, y: me.y - 6000 };

  // Vos unités ont un rayon de 400 et accélèrent de ACC/masse.
  // ##########
  // ACC=100
  // masse=0.5
  // acc de ACC/masse
  // acc = 100/0.5 = 200
  //
  // v = acc*(1-friction)
  // v = 200 * (1-0.2)
  // v = 160
  // fric = 0.2
  // ##########
  // ACC=300
  // masse=0.5
  // acc de ACC/masse
  // acc = 300/0.5 = 600
  //
  // v = acc*(1-friction)
  // fric = 0.2
  // v = 600 * (1-0.2)
  // v = 480
  //
  // et donc y = y - 480


  p('newpoint', z(newPoint), distance(me, newPoint));
  return `${newPoint.x} ${newPoint.y} ${300}`;
}

function moveDoof(me) {
  const newPoint = { x: me.x, y: me.y - 6000 };


  p('newpoint', z(newPoint), distance(me, newPoint));
  return `${newPoint.x} ${newPoint.y} ${300}`;
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
    const doof = all[2];
    // const destroyer = all[1];
    ppp(reaper);
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
};
