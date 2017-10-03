// eslint-disable-next-line no-console
const l = console.log;
const pt = ll => i => ll.forEach((e) => {
  l(Array(i).fill(' ').join('') + e.node);
  pt(e.children)(i + 1);
});

// var N = parseInt(readline());

const all = [];

const place = tree => ([number, ...tel]) => {
  if (number === undefined) { return tree; }

  const found = tree.find(e => e.node === number);

  if (found) {
    return place(found.children)(tel);
  }

  const node = { node: number, children: [] };
  tree.push(node);
  return place(node.children)(tel);
};

const tel1 = '01234567'.split('');
const tel2 = '0123345'.split('');
const tel3 = '0223345'.split('');
place(all)(tel1);
place(all)(tel2);
place(all)(tel3);

pt(all)(0);
const flatten = arr => arr.reduce((a, b) => a.concat(b.node).concat(flatten(b.children)), []);

l(flatten(all).join());
l(flatten(all).length);
