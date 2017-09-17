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

function next(grid, dir, x, y) {
  if (isOn('$', grid, x, y)) { return true; }

  return next(newGrid, newDir, newX, newY);
}

const [x, y] = getStart(initGrid);
log(x, y);

const answers = [];

next(initGrid, 'S', x, y);

answers.forEach(print);
