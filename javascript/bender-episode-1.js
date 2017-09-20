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
initGrid = initGrid.map(l => l.split(''));
initGrid.map(l => l.join('')).forEach(l => log(l));

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

function nextCase(grid, x, y, dir) {
  const newX = x + getDirX[dir];
  const newY = y + getDirY[dir];

  const result = grid[newY][newX];
  if (result === 'X' || result === '#') {
    return nextCase(grid, x, y, nextDir(dir));
  }

  return {
    newX,
    newY,
    newGrid: grid,
    newDir: dir,
  };
}

let startDir = 'S';

function next(grid, dir, x, y, moves) {
  console.log(moves);
  if (isOn('$', grid, x, y)) { return moves; }

  if (isOn('S', grid, x, y)) { startDir = 'S' ; }
  if (isOn('N', grid, x, y)) { startDir = 'N' ; }
  if (isOn('E', grid, x, y)) { startDir = 'E' ; }
  if (isOn('W', grid, x, y)) { startDir = 'W' ; }

  const { newGrid, newX, newY, newDir } = nextCase(grid, x, y, startDir);

  return next(newGrid, newDir, newX, newY, moves.concat(newDir));
}

const [x, y] = getStart(initGrid);
log(x, y);

const answers = next(initGrid, 'S', x, y, []);

const toText = {
  S: 'SOUTH',
  E: 'EAST',
  N: 'NORTH',
  W: 'WEST',
};

answers.forEach(letter => print(toText[letter]));
