const left = {
  N: 'W',
  W: 'S',
  S: 'E',
  E: 'N',
};

const right = {
  N: 'E',
  E: 'S',
  S: 'W',
  W: 'N',
};

const fr = {
  N: 'haut',
  S: 'bas',
  W: 'gauche',
  E: 'droite',
};

const dx = {
  N: 0,
  S: 0,
  W: -1,
  E: 1,
};

const dy = {
  N: -1,
  S: 1,
  W: 0,
  E: 0,
};

function nextDirection(str, direction) {
  if (str === '.') {
    return left[direction];
  }
  return right[direction];
}

function invert(grid, x, y) {
  let replace = '.';
  if (grid[y][x] === replace) {
    replace = '#';
  }

  const newGrid = grid.slice();
  newGrid[y][x] = replace;

  return newGrid;
}

function move(direction, x, y) {
  return [x + dx[direction], y + dy[direction]];
}

function next(grid, x, y, direction, tours) {
  // eslint-disable-next-line no-console
  console.log([x, y], fr[direction], tours);
  // eslint-disable-next-line no-console
  console.log(grid.map(e => e.join(' ')).join('\n'));
  if (tours === 0) { return grid; }

  // elle tourne de 90° vers la gauche si elle est sur une case blanche,
  // sinon de 90° vers la droite ;
  // elle inverse la couleur de la case sur laquelle elle se situe ;
  // elle avance d'une case.

  const newDirection = nextDirection(grid[y][x], direction);
  const newGrid = invert(grid, x, y);
  const [newX, newY] = move(newDirection, x, y);

  return next(newGrid, newX, newY, newDirection, tours - 1);
}

const grid = [
  ['.', '.', '.', '.', '.'],
  ['.', '.', '.', '.', '.'],
  ['.', '.', '.', '.', '.'],
  ['.', '.', '.', '.', '.'],
  ['.', '.', '.', '.', '.'],
];
const x = 2;
const y = 2;
const direction = 'N';
const tours = 10;

next(grid, x, y, direction, tours);

// W, H,
// 5 5
// x, y,
// 2 2
// direction, tours
// N 10
// .....
// .....
// .....
// .....
// .....
// dir : N E S W

// .....
// ..##.
// .##..
// .##..
// .....
