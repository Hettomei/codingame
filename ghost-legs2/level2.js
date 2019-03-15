// A  B  C
// |  |  |
// |--|  |
// |  |--|
// |  |--|
// |  |  |
//

const A = 'A';
const B = 'B';
const C = 'C';

const DOWN = 'DOWN';
const RIGHT = 'RIGHT';
const LEFT = 'LEFT';

// On represente le dessin
const representation = [
    [A     , B     , C   ]
    [DOWN  , DOWN  , DOWN]
    [RIGHT , LEFT  , DOWN]
    [DOWN  , RIGHT , LEFT]
    [DOWN  , RIGHT , LEFT]
    [DOWN  , DOWN  , DOWN]
]

// On veut traduire 'representation' en un array de la form :
// [
//     { A: [DOWN, RIGHT, DOWN , DOWN , DOWN] },
//     { B: [DOWN, LEFT , RIGHT, RIGHT, DOWN] },
//     { C: [DOWN, DOWN , LEFT , LEFT , DOWN] },
// ]



