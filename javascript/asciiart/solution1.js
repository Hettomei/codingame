// eslint-disable no-console

const fs = require('fs');

const input = fs.readFileSync('./level1.txt', 'utf8');
// const input = fs.readFileSync('./level2.txt', 'utf8')
// const input = fs.readFileSync('./level3.txt', 'utf8')
// const input = fs.readFileSync('./level4.txt', 'utf8')
// const input = fs.readFileSync('./level5.txt', 'utf8')

const a = input.split('\n');
const width = Number(a[0]);
const height = Number(a[1]);
const toPrint = [...(a[2].toLowerCase())].map(e => e.charCodeAt() - 97);
const mixedLetters = a.slice(3, height + 3);

const letters = [];

mixedLetters.forEach((line) => {
  let j = 0;
  for (let i = 0; i < line.length; i += width) {
    if (!letters[j]) {
      letters[j] = '';
    }

    if (letters[j]) {
      letters[j] += '\n';
    }

    letters[j] += `${line.slice(i, i + width)}`;
    j += 1;
  }
});

letters.forEach(l => console.log(`${l}\n`));
// console.log('toprin', toPrint);

// toPrint.forEach((i) => {
//   let j = i;
//   if (i < 0 || i > 25) {
//     j = 26;
//   }
//   console.log(letters[j]);
// });
