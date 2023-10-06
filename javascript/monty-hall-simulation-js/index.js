// On affiche les 3 possibilities
//
// le spectateur choisi une porte
// l'animateur montre une chevre
//
// le spectateur fais un choix
//
// soit "je change pas"
// soit "je change"
//
// C => Chevre
// V => Voiture

const possibilities = ["CCV", "CVC", "VCC"];

const getRandomInt = (max) => Math.floor(Math.random() * max);
const freq = (total, n) => ((n * 100) / total).toFixed(3);
const chooseSession = () => possibilities[getRandomInt(3)];

function whenKeepWin(game, firstChoice) {
  // Comme il ne va pas changer, si il prend la voiture, il gagne
  return game[firstChoice] == "V";
}

function whenChangeWin(game, firstChoice) {
  // Pourquoi ?
  // Si le premier choix c est la voiture
  // l animateur montre une chevre
  // il changera pour la seconde chevre.
  //
  // Si le premier choix est une chevre,
  // l'animateur montre la deuxieme chevre
  // il changera tjrs pour la voiture
  return game[firstChoice] == "C";
}

function format(title, total, countWin) {
  return `${title} ${freq(total, countWin)}%, ${countWin}`;
}
function printResult(total, winIfKeep, winIfChange) {
  console.log(
    format("Si maintient", total, winIfKeep),
    "|",
    format("Si change", total, winIfChange),
  );
}

function run(total, steps) {
  let winIfKeep = 0;
  let winIfChange = 0;

  let game;
  let firstChoice;

  for (let i = 1; i <= total; i++) {
    game = chooseSession();
    firstChoice = getRandomInt(3);
    if (whenKeepWin(game, firstChoice)) {
      winIfKeep++;
    }
    if (whenChangeWin(game, firstChoice)) {
      winIfChange++;
    }

    // LE code du haut est TOTALEMENT equivalent à celui ci
    // mais c'est contre intuitif, donc on le laisse :
    //
    // if (game[firstChoice] == "V") {
    //   winIfKeep++;
    // } else {
    //   winIfChange++;
    // }

    if (i % steps === 0) {
      printResult(i, winIfKeep, winIfChange);
    }
  }
  printResult(total, winIfKeep, winIfChange);
}

const total = 50000000;
const steps = 5000000;
run(total, steps);
