/* global printErr */

const arrayContructor = [].constructor
const objectConstructor = {}.constructor

export function p(obj) {
  if (!obj) {
    printErr(obj)
  } else if (obj.constructor === objectConstructor) {
    printErr('{')
    Object.entries(obj).forEach(([k, v]) => printErr(`  ${k}: ${v}`))
    printErr('}')
  } else if (obj.constructor === arrayContructor) {
    printErr(`[ ${obj} ]`)
  } else {
    printErr(obj)
  }
}

export function createPlayer(array) {
  return {
    raw: array,
    target: array[0],
    eta: parseInt(array[1], 10),
    score: parseInt(array[2], 10),
    storageA: parseInt(array[3], 10),
    storageB: parseInt(array[4], 10),
    storageC: parseInt(array[5], 10),
    storageD: parseInt(array[6], 10),
    storageE: parseInt(array[7], 10),
    // ignore at start
    expertiseA: parseInt(array[8], 10),
    expertiseB: parseInt(array[9], 10),
    expertiseC: parseInt(array[10], 10),
    expertiseD: parseInt(array[11], 10),
    expertiseE: parseInt(array[12], 10),

    sumStorage() {
      return this.storageA + this.storageB + this.storageC + this.storageD + this.storageE
    },

    addMolecule(molId) {
      this[`storage${molId}`] = this[`storage${molId}`] + 1
    },
  }
}

export function createAvailableMolecules(array) {
  return {
    raw: array,
    a: parseInt(array[0], 10),
    b: parseInt(array[1], 10),
    c: parseInt(array[2], 10),
    d: parseInt(array[3], 10),
    e: parseInt(array[4], 10),
  }
}

export function createProject(array) {
  return {
    raw: array,
    a: parseInt(array[0], 10),
    b: parseInt(array[1], 10),
    c: parseInt(array[2], 10),
    d: parseInt(array[3], 10),
    e: parseInt(array[4], 10),
  }
}
