/* global print readline */

import { p, createPlayer, createAvailableMolecules, createSample } from './utils'

const steps = {
  GO_SAMPLES: {},
  SEAK_UNDIAGNOS: {},
  GO_DIAGNOSIS: {},
  CONNECT_SAMPLE: {},
  GO_MOLECULES: {},
  TAKE_MOLECULES: {},
  GO_LABORATORY: {},
  CONNECT_LABORATORY: {},
}

let step = steps.GO_SAMPLES

const n = true

function voidd(e) { return e }
voidd(p)

// function onlyCloud(samples) {
//   return samples.filter(s => s.carriedBy === -1)
// }

function onlyMine(samples) {
  return samples.filter(s => s.carriedBy === 0)
}

function lessThan(samples, max) {
  return samples.filter(s => s.sumCost() <= max)
}

function undiag(samples) {
  return samples.filter(s => s.health === -1)
}

function maxHealth(samples) {
  return samples.reduce(
    (precedente, enCour) => {
      if (precedente.health > enCour.health) {
        return precedente
      }
      return enCour
    },
  )
}

function takeMolecule(player, sample) {
  if (player.storageA < sample.costA) {
    return 'A'
  } else if (player.storageB < sample.costB) {
    return 'B'
  } else if (player.storageC < sample.costC) {
    return 'C'
  } else if (player.storageD < sample.costD) {
    return 'D'
  }
  return 'E'
}

function sampleComplete(player, sample, startAt) {
  return player.sumStorage() + startAt === sample.sumCost()
}

function maxCarring(player, startAt) {
  return player.sumStorage() + startAt === 10
}

// always zero att start
const projectCount = parseInt(readline(), 10)
for (let i = 0; i < projectCount; i += 1) {
  const fsdfds = readline().split(' ')
  voidd(fsdfds)
  // const a = parseInt(inputs[0])
  // const b = parseInt(inputs[1])
  // const c = parseInt(inputs[2])
  // const d = parseInt(inputs[3])
  // const e = parseInt(inputs[4])
}

while (n) {
  const player1 = createPlayer(readline().split(' '))
  const player2 = createPlayer(readline().split(' '))
  voidd(player2)

  // ignore it
  const availableMolecules = createAvailableMolecules(readline().split(' '))
  voidd(availableMolecules)

  const sampleCount = parseInt(readline(), 10)
  const samples = []
  for (let i = 0; i < sampleCount; i += 1) {
    samples.push(createSample(readline().split(' ')))
  }

  switch (step) {
    case steps.GO_SAMPLES:
      print('GOTO SAMPLES')
      step = steps.SEAK_UNDIAGNOS
      break

    case steps.SEAK_UNDIAGNOS: {
      print('CONNECT 3')
      const undiagSamples = undiag(onlyMine(samples))

      if ((undiagSamples.length + 1) < 3) {
        step = steps.SEAK_UNDIAGNOS
      } else {
        step = steps.GO_DIAGNOSIS
      }
      break
    }

    case steps.GO_DIAGNOSIS:
      print('GOTO DIAGNOSIS')
      step = steps.CONNECT_SAMPLE
      break

      // Dans DIAGNOSIS, ne prendre que les element du cloud :
      // sample.carriedBy = -1
      // strategie : prendre les plus haut point de health
      // En prendre 3
    case steps.CONNECT_SAMPLE: {
      const undiagSamples = undiag(onlyMine(samples))

      const sample = undiagSamples[0]
      print(`CONNECT ${sample.id}`)
      if ((undiagSamples.length - 1) > 0) {
        step = steps.CONNECT_SAMPLE
      } else {
        step = steps.GO_MOLECULES
      }
      break
    }

    case steps.GO_MOLECULES:
      print('GOTO MOLECULES')
      step = steps.TAKE_MOLECULES
      break

    case steps.TAKE_MOLECULES: {
      const bestSample = maxHealth(lessThan(onlyMine(samples), 10))
      p(bestSample)
      const moleculeId = takeMolecule(player1, bestSample)
      print(`CONNECT ${moleculeId}`)

      // Si y a 10 molecule, stop
      // Si sample complet, stop
      if (sampleComplete(player1, bestSample, 1) || maxCarring(player1, 1)) {
        step = steps.GO_LABORATORY
      }
      break
    }

    case steps.GO_LABORATORY:
      print('GOTO LABORATORY')
      step = steps.CONNECT_LABORATORY
      break

    case steps.CONNECT_LABORATORY: {
      const bestSample = maxHealth(onlyMine(samples))
      print(`CONNECT ${bestSample.id}`)
      if (onlyMine(samples).length - 1 < 1) {
        step = steps.GO_SAMPLES
      } else {
        step = steps.GO_MOLECULES
      }
      break
    }

    default:
      break
  }
}
