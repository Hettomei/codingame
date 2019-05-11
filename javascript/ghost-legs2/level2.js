const data = `
ABC
║║║
╠╣║
║╠╣
║╠╣
║║║
`

const DOWN = 'DOWN';
const RIGHT = 'RIGHT';
const LEFT = 'LEFT';

// On represente le dessin
// const representation = [
//     ["A"     , "B"    , "C"   ]
//     [DOWN  , DOWN  , DOWN]
//     [RIGHT , LEFT  , DOWN]
//     [DOWN  , RIGHT , LEFT]
//     [DOWN  , RIGHT , LEFT]
//     [DOWN  , DOWN  , DOWN]
// ]

// On veut savoir à quel endroit il atterit (l'index : 0..n)
