/* global readline print */

/**
 * Bring data on patient samples from the diagnosis machine to the laboratory with enough molecules to produce medicine!
 **/
import { p, createPlayer, createAvailableMolecules, createSample } from './utils'

function voidd (e) { return e }

function onlyCloud (samples) {
  return samples.filter(s => s.carriedBy === -1)
}

function maxHealth (samples) {
  return samples.reduce(
    (precedente, enCour) => precedente.health > enCour.health ? precedente : enCour
  )
}

// always zero att start
const projectCount = parseInt(readline())
for (let i = 0; i < projectCount; i++) {
  const fsdfds = readline().split(' ')
  voidd(fsdfds)
  // const a = parseInt(inputs[0])
  // const b = parseInt(inputs[1])
  // const c = parseInt(inputs[2])
  // const d = parseInt(inputs[3])
  // const e = parseInt(inputs[4])
}

while (true) {
  const player1 = createPlayer(readline().split(' '))
  p(player1)
  const player2 = createPlayer(readline().split(' '))
  voidd(player2)

  // ignore it
  const availableMolecules = createAvailableMolecules(readline().split(' '))
  voidd(availableMolecules)

  const sampleCount = parseInt(readline())
  const samples = []
  for (let i = 0; i < sampleCount; i++) {
    samples.push(createSample(readline().split(' ')))
  }

  if (player1.target === 'START_POS') {
    // Si player1.target = START_POS alors aller chercher un DIAGNOSIS
    print('GOTO DIAGNOSIS')
  } else {
    // Dans DIAGNOSIS, ne prendre que les element du cloud :
    // sample.carriedBy = -1
    // strategie : prendre les plus haut point de health
    // En prendre 3

    const sample = maxHealth(onlyCloud(samples))
    print('CONNECT ' + sample.id)
  }

  // The complex is composed of 3 modules named DIAGNOSIS, MOLECULES and LABORATORY.
}
