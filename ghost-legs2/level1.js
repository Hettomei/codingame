const DOWN = 'DOWN';
const RIGHT = 'RIGHT';
const LEFT = 'LEFT';

const data = `
ABC
║║║
╠╣║
║╠╣
║╠╣
║║║
`

// Un retour à la ligne se traduit par "\n"
// console.log("ligne 1\nbonjour\nligne2")
// https://theasciicode.com.ar/extended-ascii-code/box-drawings-double-line-vertical-left-character-ascii-code-185.html


// On veut
//
// const representation = [
//     ["A"   , "B"   , "C" ],
//     [DOWN  , DOWN  , DOWN],
//     [RIGHT , LEFT  , DOWN],
//     [DOWN  , RIGHT , LEFT],
//     [DOWN  , RIGHT , LEFT],
//     [DOWN  , DOWN  , DOWN],
// ]
