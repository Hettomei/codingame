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
  return letter.toLowerCase().charCodeAt() - 97;
}

module.exports = {
  toto,
  extract,
  extractFromArray,
  indexOfLetter,
};
