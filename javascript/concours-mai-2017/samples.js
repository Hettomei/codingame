export function create(array) {
  return {
    raw: array,
    id: parseInt(array[0], 10),
    carriedBy: parseInt(array[1], 10),
    rank: parseInt(array[2], 10),
    expertiseGain: array[3],
    health: parseInt(array[4], 10),
    costA: parseInt(array[5], 10),
    costB: parseInt(array[6], 10),
    costC: parseInt(array[7], 10),
    costD: parseInt(array[8], 10),
    costE: parseInt(array[9], 10),

    sumCost() { return this.costA + this.costB + this.costC + this.costD + this.costE },
  }
}

export function onlyCloud(samples) {
  return samples.filter(s => s.carriedBy === -1)
}

export function onlyMine(samples) {
  return samples.filter(s => s.carriedBy === 0)
}

const MAX_MOLECULE = 10
export function untraitable(samples) {
  return samples.filter(s => s.sumCost() > MAX_MOLECULE)
}

export function traitable(samples) {
  return samples.filter(s => s.sumCost() <= MAX_MOLECULE)
}

export function undiag(samples) {
  return samples.filter(s => s.health === -1)
}

export function diag(samples) {
  return samples.filter(s => s.health > 0)
}

export function maxHealth(samples) {
  return samples.reduce(
    (precedente, enCour) => {
      if (precedente.health > enCour.health) {
        return precedente
      }
      return enCour
    },
  )
}
