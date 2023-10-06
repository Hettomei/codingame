// console.log('monty hall problem');
// console.log("d'abord afficher la frequence pour du rand simple");

function getRandomInt(max) {
    return Math.floor(Math.random() * Math.floor(max));
}

const freq = (total, n) => (n * 100/total).toFixed(3)

// 1) On affiche les 3 possibilities
//
// 2) le spectateur choisi une porte
// 3) l'animateur montre une chevre
//
// 4) le spectateur fais un choix
//
// 4.1) "je change pas"
// 4.2) "je change"


// 1

// // C chevre
// // V voiture
const possibilities = [
    'CCV',
    'CVC',
    'VCC',
]

// expected output: 0, 1 or 2

function run() {
    return possibilities[getRandomInt(3)][getRandomInt(3)];
}


let winIfKeep = 0;
let winIfChange = 0;


for (let i = 1; i < 10000000000; i++) {
    if (run() === 'V') {
        winIfKeep++;
    } else {
        winIfChange++
    }

    if (i % 5000000 === 0) {
        // console.log({ winIfKeep, winIfChange });
      console.log("Si maintient(" + freq(i, winIfKeep) + "%, " + winIfKeep + ") " +
        "| Si change("+ freq(i, winIfChange) + ", " + winIfChange +")");
    }
}
