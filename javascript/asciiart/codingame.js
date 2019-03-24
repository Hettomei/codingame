const width = Number(readline());
const height = Number(readline());
const word = readline();
let lines = [];
for (let i = 0; i < height; i++) {
    lines.push(readline());
}

function extract(str, index, width) {
  let output = '';

  index.forEach((i) => {
    output += str.slice(
      i * width,
      (i * width) + width,
    );
  });

  return output;
}

function extractFromArray(lines, index, width) {
  return lines.map(line => extract(line, index, width));
}

function indexOfLetter(letter) {
  const result = letter.toLowerCase().charCodeAt() - 97;
  if (result < 0 || result > 25) {
    return 26;
  }
  return result;
}

function indexOfWord(word) {
  return [...word].map(indexOfLetter);
}

const index = indexOfWord(word);

const solutionInArray = extractFromArray(lines, index, width);
const solutionInString = solutionInArray.join('\n');
console.log(solutionInString);
