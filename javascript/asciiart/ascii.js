function toto() {
  return 'toto';
}

function extract(str, index, width) {
  return str.slice(
    index * width,
    (index * width) + width,
  );
}


module.exports = {
  toto,
  extract,
};
