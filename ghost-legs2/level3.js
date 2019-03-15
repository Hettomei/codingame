// A  B  C
// |  |  |
// |--|  |
// |  |--|
// |  |--|
// |  |  |

// Rules
// A0 --> DOWN = A1
// A0 --> LEFT = ERROR
// A1 --> RIGHT = B2
// B2 --> LEFT = A3

const DOWN = 'DOWN';
const RIGHT = 'RIGHT';
const LEFT = 'LEFT';

const columns = [
    { A: [DOWN, RIGHT, DOWN , DOWN , DOWN] }, //0
    { B: [DOWN, LEFT , RIGHT, RIGHT, DOWN] }, //1
    { C: [DOWN, DOWN , LEFT , LEFT , DOWN] }, //2
]


// Example
// A0 --> DOWN = A1
// A1 --> RIGHT = B2
// B2 --> RIGHT = C3
// C3 --> LEFT = B4
// B4 --> DOWN = B5
// B5 n'existe pas. Donc la réponse est B.
