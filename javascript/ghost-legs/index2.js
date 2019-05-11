const tests = [
    require('./level1.js'),
    require('./level2.js'),
    require('./level3.js'),
    require('./level100.js'),
]

const log = console.log

function walkLegs(entry, legs) {
    let currentIndex = entry * 3;

    for (let i = 0; i < legs.length; i++) {
        const line = legs[i];
        const charLeft = line[currentIndex - 1];
        const charRight = line[currentIndex + 1];

        if (charRight === '-') {
            currentIndex += 3
        } else if (charLeft === '-') {
            currentIndex -= 3
        }
    }

    return currentIndex;
}

function printSolution(mySolution, solution) {
    log({
        mySolution: mySolution.join(' '),
        solution: solution.join(' '),
        same: solution.join(' ') === mySolution.join(' '),
    });
}

function run({ data, solution }) {
    const dataToArray = data.trim().split('\n')
    const input = dataToArray[0].split('  ')
    const output = dataToArray[dataToArray.length - 1];
    const legs = dataToArray.slice(1,dataToArray.length - 1)

    log(input.join('  '));
    log(legs.join('\n'));
    log(output);

    const mySolution = input.map((char, index) => {
        const endIndex = walkLegs(index, legs);
        return `${char}${output[endIndex]}`
    });

    log();
    printSolution(mySolution, solution);

    log('----------------');
}

tests.forEach(run);
