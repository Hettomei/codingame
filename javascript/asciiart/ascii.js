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

function extractElegant(str, index, width) {
  return index.reduce((acc, i) => acc + str.slice(
    i * width,
    (i * width) + width,
  ), '');
}

module.exports = {
  toto,
  extract,
  extractElegant,
};
