/* global printErr */

const arrayContructor = [].constructor
const objectConstructor = {}.constructor

export function p (obj) {
  if (obj.constructor === objectConstructor) {
    printErr('{')
    Object.entries(obj).forEach(([k, v]) => printErr('  ' + k + ': ' + v))
    printErr('}')
  } else if (obj.constructor === arrayContructor) {
    printErr('[ ' + obj + ' ]')
  } else {
    printErr(obj)
  }
}

export function createPlayer (array) {
  return {
    all: array,
    target: array[0],
    eta: parseInt(array[1]),
    score: parseInt(array[2]),
    storageA: parseInt(array[3]),
    storageB: parseInt(array[4]),
    storageC: parseInt(array[5]),
    storageD: parseInt(array[6]),
    storageE: parseInt(array[7]),
    // ignore at start
    expertiseA: parseInt(array[8]),
    expertiseB: parseInt(array[9]),
    expertiseC: parseInt(array[10]),
    expertiseD: parseInt(array[11]),
    expertiseE: parseInt(array[12])
  }
}

export function createAvailableMolecules (array) {
  return {
    all: array,
    a: parseInt(array[0]),
    b: parseInt(array[1]),
    c: parseInt(array[2]),
    d: parseInt(array[3]),
    e: parseInt(array[4])
  }
}

export function createSample (array) {
  return {
    id: parseInt(array[0]),
    carriedBy: parseInt(array[1]),
    rank: parseInt(array[2]),
    expertiseGain: array[3],
    health: parseInt(array[4]),
    costA: parseInt(array[5]),
    costB: parseInt(array[6]),
    costC: parseInt(array[7]),
    costD: parseInt(array[8]),
    costE: parseInt(array[9])
  }
}
