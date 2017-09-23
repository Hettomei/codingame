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
]
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

const invertDir = 0;

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
    S: 'W',
    W: 'N',
    N: 'E',
    E: 'S',
  }[dir];
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

function nextCase(grid, x, y, dir) {
  const newX = x + getDirX[dir];
  const newY = y + getDirY[dir];

  const result = grid[newY][newX];
  if (canPass(result)) {
    return {
      newX,
      newY,
      newNewDir: dir,
    };
  }

  return nextCase(grid, x, y, nextDir(dir));
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

function next(grid, dir, x, y, moves) {
  log('length', moves.length, dir, x, y, moves[moves.length - 1]);
  pprint(grid, x, y);
  if (moves.length > 30) { return ['L']; }

  let newGrid = grid;
  let newDir = dir;
  if (findExit(grid, x, y)) {
    return moves;
  } else if (changeDir(grid, x, y)) {
    newDir = changeDir(grid, x, y);
  } else if (drinkBeer(grid, x, y)) {
    changePass();
  } else if (onX(grid, x, y)) {
    newGrid = replace(grid, x, y, ' ');
  }


  const { newX, newY, newNewDir } = nextCase(newGrid, x, y, newDir);

  return next(newGrid, newNewDir, newX, newY, moves.concat(newNewDir));
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
