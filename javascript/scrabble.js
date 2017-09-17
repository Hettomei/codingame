// eslint-disable-next-line
const N = parseInt(readline());
const dic = [];
// eslint-disable-next-line
for (let i = 0; i < N; i++) {
  // eslint-disable-next-line
  dic.push(readline());
}
// eslint-disable-next-line
const LETTERS = readline().split('');

// Write an action using print()
// To debug: printErr('Debug messages...');

const valids = dic.filter((word) => {
  let restWord = word;
  LETTERS.forEach((l) => {
    restWord = restWord.replace(l, '');
  });

  return restWord.length === 0;
});

const point = {
  a: 1,
  b: 3,
  c: 3,
  d: 2,
  e: 1,
  f: 4,
  g: 2,
  h: 4,
  i: 1,
  j: 8,
  k: 5,
  l: 1,
  m: 3,
  n: 1,
  o: 1,
  p: 3,
  q: 10,
  r: 1,
  s: 1,
  t: 1,
  u: 1,
  v: 4,
  w: 4,
  x: 8,
  y: 4,
  z: 10,
};
function count(word) {
  return word
    .split('')
    .reduce((acc, letter) => acc + point[letter], 0);
}
// count point
const withPoints = valids.map(word => [word, count(word)]);
let max = 0;
withPoints.forEach(([, p]) => {
  if (p > max) { max = p; }
});

const maxPoints = withPoints.filter(([, p]) => p === max);

print(maxPoints[0][0]);
