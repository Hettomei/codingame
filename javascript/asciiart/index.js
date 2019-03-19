const fs = require('fs');
const ascii = require('./ascii');

// const input = fs.readFileSync('./level1.txt', 'utf8')
// const input = fs.readFileSync('./level2.txt', 'utf8')
// const input = fs.readFileSync('./level3.txt', 'utf8')
// const input = fs.readFileSync('./level4.txt', 'utf8')
const input = fs.readFileSync('./level5.txt', 'utf8');

const toArray = input.split('\n').filter(e => e);
const width = Number(toArray[0]);
const height = Number(toArray[1]);
const index = ascii.indexOfWord(toArray[2]);
const lines = toArray.slice(3, 3 + height);

// Pourquoi ca ne fonctionnait pas ?
// car width et height était des String au moment de l'extraction des input
// il fallait les convertir en nombre (d'ou le Number)

const solutionInArray = ascii.extractFromArray(lines, index, width);
const solutionInString = solutionInArray.join('\n');
console.log(solutionInString);
