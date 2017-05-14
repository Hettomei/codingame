/* global print readline */

import { p, createPlayer, createAvailableMolecules, createSample } from './utils'

const n = true

function voidd(e) { return e }
voidd(p)

// function onlyCloud(samples) {
//   return samples.filter(s => s.carriedBy === -1)
// }

function onlyMine(samples) {
  return samples.filter(s => s.carriedBy === 0)
}

function lessOrEqThan(samples, max) {
  return samples.filter(s => s.sumCost() <= max)
}

function undiag(samples) {
  return samples.filter(s => s.health === -1)
}

function diag(samples) {
  return samples.filter(s => s.health > 0)
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

class StateManager {

  constructor() {
    this.currentState = new GoSample(this)
  }

  change(state) {
    this.currentState = state
  }

  execute(player, samples) {
    this.currentState.execute(player, samples)
  }

}

class BaseState {
  constructor(stateManager) {
    this.stateManager = stateManager
  }
}

class GoSample extends BaseState {
  execute() {
    print('GOTO SAMPLES')
    this.stateManager.change(new TakeUndiagnosedSample(this.stateManager))
  }
}

class TakeUndiagnosedSample extends BaseState {
  execute(player, samples) {
    print('CONNECT 3')
    const undiagSamples = undiag(onlyMine(samples))

    if ((undiagSamples.length + 1) === 3) {
      this.stateManager.change(new GoDiagnosis(this.stateManager))
    }
  }
}

class GoDiagnosis extends BaseState {
  execute() {
    print('GOTO DIAGNOSIS')
    this.stateManager.change(new ManageSample(this.stateManager))
  }
}

class ManageSample extends BaseState {
  execute(player, samples) {
    const mine = onlyMine(samples)
    const undiagSamples = undiag(mine)

    // diagnose all
    if (undiagSamples.length > 0){
      const sample = undiagSamples[0]
      print(`CONNECT ${sample.id}`)
      // keep only sample with <= 10 molecules
    } else if (lessOrEqThan(undiagSamples, 10).length > 0){
      lessOrEqThan()
    } else {

      const sample = undiagSamples[0]
      print(`CONNECT ${sample.id}`)

      if ((undiagSamples.length - 1) === 0) {
        this.stateManager.change(new GoMolecules(this.stateManager))
      }
    }
    // si 0 sample -> aller en rechercher
    //
    // si > 0 sample, collecter
  }
}


class GoMolecules extends BaseState {
  execute() {
    print('GOTO MOLECULES')
    this.stateManager.change(new TakeMolecules(this.stateManager))
  }
}

class TakeMolecules extends BaseState {
  execute(player, samples) {
    const bestSample = maxHealth(lessOrEqThan(onlyMine(samples), 10))
    p(bestSample.raw)
    const moleculeId = takeMolecule(player, bestSample)
    print(`CONNECT ${moleculeId}`)

    // Si y a 10 molecule, stop
    // Si sample complet, stop
    if (sampleComplete(player, bestSample, 1) || maxCarring(player, 1)) {
      this.stateManager.change(new GoLaboratory(this.stateManager))
    }
  }
}

class GoLaboratory extends BaseState {
  execute() {
    print('GOTO LABORATORY')
    this.stateManager.change(new SendToLaboratory(this.stateManager))
  }
}

class SendToLaboratory extends BaseState {
  execute(player, samples) {
    const toCure = lessOrEqThan(onlyMine(samples), 10)
    const bestSample = maxHealth(toCure)
    print(`CONNECT ${bestSample.id}`)

    if (toCure.length - 1 === 0) {
      this.stateManager.change(new GoSample(this.stateManager))
    } else {
      this.stateManager.change(new GoMolecules(this.stateManager))
    }
  }
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

/* ////////////////////////////////////////////////// */
/* ////////////////////////////////////////////////// */
/* ////////////////////////////////////////////////// */

const sm = new StateManager()

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
  // sort now to always pic same best/undiagnose...
  samples.sort((a, b) => a.value - b.value)

  sm.execute(player1, samples)
}
