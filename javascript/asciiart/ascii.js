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

// utiliser 'reduce' c'est la élégant
// 'reduce' reduit un tableau en moins d element
//
// function extractElegant(str, index, width) {
//   return index.reduce((acc, i) => acc + str.slice(
//       i * width,
//       (i * width) + width,
//     ) , '');
// }

function extractFromArray(lines, index, width) {
  return lines.map(line => extract(line, index, width));
}

function indexOfLetter(letter) {
  return 0;
}

module.exports = {
  toto,
  extract,
  extractFromArray,
  indexOfLetter,
};

// const lines = [
// " #  ##   ## ##  ### ###  ## # # ###  ## # # #   # # ###  #  ##   #  ##   ## ### # # # # # # # # # # ### ### ",
// "# # # # #   # # #   #   #   # #  #    # # # #   ### # # # # # # # # # # #    #  # # # # # # # # # #   #   # ",
// "### ##  #   # # ##  ##  # # ###  #    # ##  #   ### # # # # ##  # # ##   #   #  # # # # ###  #   #   #   ## ",
// "# # # # #   # # #   #   # # # #  #  # # # # #   # # # # # # #    ## # #   #  #  # # # # ### # #  #  #       ",
// "# # ##   ## ##  ### #    ## # # ###  #  # # ### # # # #  #  #     # # # ##   #  ###  #  # # # #  #  ###  #  ",
// ]

// console.log(extractFromArray(lines, [7, 4, 11, 11, 14], 4).join('\n'));
