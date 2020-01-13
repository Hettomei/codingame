// const [L, C] = readline().split(' ').map(parseInt);
// cont grid = [];
// for (let i = 0; i < L; i++) {
//   grid.push(readline());
// }

// eslint-disable-next-line
const log = console.log;
const print = log;

let initGrid = [
  '#####',
  '#@  #',
  '#   #',
  '#  $#',
  '#####',
];

initGrid = [
  '########',
  '# @    #',
  '#     X#',
  '# XXX  #',
  '#   XX #',
  '#   XX #',
  '#     $#',
  '########',
];

initGrid = [
  '##########',
  '#        #',
  '#  S   W #',
  '#        #',
  '#  $     #',
  '#        #',
  '#@       #',
  '#        #',
  '#E     N #',
  '##########',
];

initGrid = [
  '##########',
  '# @      #',
  '# B      #',
  '#XXX     #',
  '# B      #',
  '#    BXX$#',
  '#XXXXXXXX#',
  '#        #',
  '#        #',
  '##########',
];

initGrid = [
  '##########',
  '#    I   #',
  '#        #',
  '#       $#',
  '#       @#',
  '#        #',
  '#       I#',
  '#        #',
  '#        #',
  '##########',
];

initGrid = [
  '##########',
  '#    T   #',
  '#        #',
  '#        #',
  '#        #',
  '#@       #',
  '#        #',
  '#        #',
  '#    T  $#',
  '##########',
];

initGrid = [
  '###############',
  '#      IXXXXX #',
  '#  @          #',
  '#E S          #',
  '#             #',
  '#  I          #',
  '#  B          #',
  '#  B   S     W#',
  '#  B   T      #',
  '#             #',
  '#         T   #',
  '#         B   #',
  '#N          W$#',
  '#        XXXX #',
  '###############',
];

initGrid = initGrid.map(l => l.split(''));

function getStart(grid) {
  const y = grid.findIndex(line => line.includes('@'));
  const x = grid[y].findIndex(char => char === '@');
  return [x, y];
}

function isOn(char, grid, x, y) {
  return grid[y][x] === char;
}

const getDirX = {
  S: 0,
  E: 1,
  N: 0,
  W: -1,
};

const getDirY = {
  S: 1,
  E: 0,
  N: -1,
  W: 0,
};

let invertDir = 0;

function nextDir(dir) {
  if (invertDir % 2 === 0) {
    return {
      S: 'E',
      E: 'N',
      N: 'W',
      W: 'S',
    }[dir];
  }

  return {
    W: 'N',
    N: 'E',
    E: 'S',
    S: 'W',
  }[dir];
}

function defaultDirObstacle() {
  if (invertDir % 2 === 0) {
    return 'S';
  }

  return 'W';
}

const canPassAll = result => result !== '#';
const canPassNormal = result => result !== '#' && result !== 'X';

let canPass = canPassNormal;

function changePass() {
  if (canPass === canPassAll) {
    canPass = canPassNormal;
  } else {
    canPass = canPassAll;
  }
}

function nextCase(grid, x, y, dir, count) {
  const newX = x + getDirX[dir];
  const newY = y + getDirY[dir];

  const result = grid[newY][newX];
  if (canPass(result)) {
    return [
      newX,
      newY,
      dir,
    ];
  }

  // obstacle, premiere foi, force sud ou ouest;
  if (count === 0) {
    return nextCase(grid, x, y, defaultDirObstacle(), count + 1);
    // a security
  } else if (count > 10) {
    return [null, null, null, true];
  }
  return nextCase(grid, x, y, nextDir(dir), count + 1);
}

function findExit(grid, x, y) {
  return isOn('$', grid, x, y);
}

function drinkBeer(grid, x, y) {
  return isOn('B', grid, x, y);
}

function onX(grid, x, y) {
  return isOn('X', grid, x, y);
}

function onTeleporter(grid, x, y) {
  return isOn('T', grid, x, y);
}

function onInverser(grid, x, y) {
  return isOn('I', grid, x, y);
}

function changeDir(grid, x, y) {
  return ['S', 'N', 'E', 'W'].find(dir => isOn(dir, grid, x, y));
}

function replace(grid, x, y, char) {
  const g = grid.map(a => a.slice());
  g[y][x] = char;
  return g;
}

function pprint(grid, x, y) {
  replace(grid, x, y, '@').map(l => l.join('')).forEach(l => log(l));
}

function changePrio() {
  invertDir += 1;
}

function changeTeleport(grid, x, y) {
  const result = [];

  grid.forEach((line, yy) => {
    line.forEach((char, xx) => {
      if (char === 'T') {
        result.push([xx, yy]);
      }
    });
  });

  const [x1, y1] = result[0];
  if (x1 === x && y1 === y) {
    return result[1];
  }

  return result[0];
}

function next(grid, dir, x, y, moves) {
  log('length', moves.length, dir, x, y, moves[moves.length - 1]);
  // pprint(grid, x, y);
  // log('');
  // log('');
  // log('');
  // log('');
  // log('');
  // log('');
  // log('');
  // log('');
  // log('');
  // log('');
  // log('');

  if (moves.length > 200) { return ['L']; }

  if (findExit(grid, x, y)) {
    return moves;
  }

  let newGrid = grid;
  let newDir = dir;

  let newX = x;
  let newY = y;
  if (changeDir(grid, x, y)) {
    newDir = changeDir(grid, x, y);
  } else if (onInverser(grid, x, y)) {
    changePrio();
  } else if (drinkBeer(grid, x, y)) {
    changePass();
  } else if (onX(grid, x, y)) {
    newGrid = replace(grid, x, y, ' ');
  } else if (onTeleporter(grid, x, y)) {
    [newX, newY] = changeTeleport(grid, x, y);
  }


  const [newnewX, newnewY, newNewDir, loop] = nextCase(newGrid, newX, newY, newDir, 0);
  if (loop) { return ['L']; }

  return next(newGrid, newNewDir, newnewX, newnewY, moves.concat(newNewDir));
}

const [x, y] = getStart(initGrid);
log(x, y);

const answers = next(initGrid, 'S', x, y, []);

const toText = {
  S: 'SOUTH',
  E: 'EAST',
  N: 'NORTH',
  W: 'WEST',
  L: 'LOOP',
};

answers.forEach(letter => print(toText[letter]));
