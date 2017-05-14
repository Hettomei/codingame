/* global print readline */

import { p, createPlayer, createProject, createAvailableMolecules } from './utils'
import * as s from './samples'

const n = true

function voidd(e) { return e }
voidd(p)

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

function sampleComplete(player, sample) {
  return player.sumStorage() === sample.sumCost()
}

class StateManager {

  constructor() {
    this.currentState = new GoSample(this)
  }

  change(state) {
    this.currentState = state
    return this
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
    const undiagSamples = s.undiag(s.onlyMine(samples))

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
    const mines = s.onlyMine(samples)
    const undiagSamples = s.undiag(mines)
    const diagSamples = s.diag(mines)
    const untraitableSamples = s.untraitable(diagSamples)
    const traitableSamples = s.traitable(diagSamples)

    // diagnose all
    if (undiagSamples.length > 0) {
      const sample = undiagSamples[0]
      print(`CONNECT ${sample.id}`)

      // keep only sample with <= 10 molecules
    } else if (untraitableSamples.length > 0) {
      print(`CONNECT ${untraitableSamples[0].id}`)

      // si > 0 sample, collecter
    } else if (traitableSamples.length > 0) {
      this.stateManager
        .change(new GoMolecules(this.stateManager))
        .execute()

      // on a plus rien, retour debut
    } else {
      this.stateManager
        .change(new GoSample(this.stateManager))
        .execute()
    }
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
    const bestSample = s.maxHealth(s.traitable(s.onlyMine(samples), 10))
    const moleculeId = takeMolecule(player, bestSample)
    player.addMolecule(moleculeId)
    print(`CONNECT ${moleculeId}`)

    if (sampleComplete(player, bestSample)) {
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
    const traitables = s.traitable(s.onlyMine(samples), 10)
    const bestSample = s.maxHealth(traitables)
    print(`CONNECT ${bestSample.id}`)

    if (traitables.length - 1 === 0) {
      this.stateManager.change(new GoSample(this.stateManager))
    } else {
      this.stateManager.change(new GoMolecules(this.stateManager))
    }
  }
}

const sm = new StateManager()

const projectCount = parseInt(readline(), 10)
const projects = []
for (let i = 0; i < projectCount; i += 1) {
  projects.push(createProject(readline().split(' ')))
}
p(projects)

/* ////////////////////////////////////////////////// */
/* ////////////////////////////////////////////////// */
/* ////////////////////////////////////////////////// */

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
    samples.push(s.create(readline().split(' ')))
  }
  // sort now to always pic same best/undiagnose...
  samples.sort((a, b) => a.value - b.value)

  sm.execute(player1, samples)
}
