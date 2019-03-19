function toto() {
  return 'toto';
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

module.exports = {
  toto,
  extract,
  extractFromArray,
  indexOfLetter,
};
