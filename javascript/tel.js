// eslint-disable-next-line no-console
const l = console.log;
const pt = (ll, i = 0) => ll.forEach((e) => {
  l(Array(i).fill(' ').join('') + e.node);
  pt(e.children, i + 1);
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

// for (var i = 0; i < N; i++) {
  // const tel = readline().split('');
const tel1 = '01234'.split('');
const tel2 = '0123345'.split('');
const tel3 = '0223345'.split('');
place(all)(tel1);
place(all)(tel2);
place(all)(tel3);

function count(arr = []) {
  if (arr.length) {
    return arr.length + reduce((acc,node) => acc + count(arr.children));
  }

  return 0;
}

l(count(all));
