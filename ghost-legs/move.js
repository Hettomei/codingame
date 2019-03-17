const tests = [
    // require('./level1.js'),
    // require('./level2.js'),
    // require('./level3.js'),
    require('./level100.js'),
]

const log = console.log

function resolveAfterMs(ms) {
  return new Promise(resolve => {
    setTimeout(() => {
      resolve('resolved');
    }, ms);
  });
}

const man = '┼';

async function printX(currentChar, lineIndex, legs) {
    await resolveAfterMs(200);
    process.stdout.write('\033c');
    const newLegs = [...legs];
    const line = newLegs[lineIndex];
    const toA = [line.slice(0,currentChar), man, line.slice(currentChar + 1, line.length)]
    newLegs[lineIndex] = toA.join('');
    log(newLegs.join('\n'));
    log();
}

async function walkLegs(entry, legs) {
    let currentIndex = entry * 3;

    for (let i = 0; i < legs.length; i++) {
        await printX(currentIndex, i, legs);
        const line = legs[i];
        const charLeft = line[currentIndex - 1];
        const charRight = line[currentIndex + 1];

        if (charRight === '-') {
            await printX(currentIndex + 1, i, legs);
            await printX(currentIndex + 2, i, legs);
            await printX(currentIndex + 3, i, legs);
            currentIndex += 3
        } else if (charLeft === '-') {
            await printX(currentIndex - 1, i, legs);
            await printX(currentIndex - 2, i, legs);
            await printX(currentIndex - 3, i, legs);
            currentIndex -= 3
        }
    }
}

async function run({ data, solution }) {
    const dataToArray = data.trim().split('\n')
    const input = dataToArray[0].split('  ')
    const output = dataToArray[dataToArray.length - 1];
    const legs = dataToArray.slice(1,dataToArray.length - 1)

    await walkLegs(15, legs);
    console.log(man + ' WIN');
}

tests.forEach(run);
