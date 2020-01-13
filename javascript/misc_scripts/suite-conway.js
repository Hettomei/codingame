function next(list) {
  // 1 2 3
  // -> 1 1 1 2 1 3
  let current = list[0];
  let count = 0;
  const tot = [];

  list.forEach((a) => {
    if (a === current) {
      count += 1;
    } else {
      tot.push(count);
      tot.push(current);
      current = a;
      count = 1;
    }
  });

  tot.push(count);
  tot.push(current);
  return tot;
}

function go(seed, tour) {
  const tot = [[seed]];

  for (let i = 1; i < tour; i += 1) {
    tot.push(next(tot[tot.length - 1]));
  }
  return tot;
}

go(1, 11).forEach(a => console.log(a));
