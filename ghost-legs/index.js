// const { data, solution } = require('./level1.js')
const { data, solution } = require('./level100.js')


function algo(entry, path) {
    let currentIndex = entry;

    for (let i = 0; i < path.length; i++) {
        const line = path[i];
        const char = line[currentIndex];

        if (char === 'R') {
            currentIndex++
        } else if (char === 'L') {
            currentIndex--
        }

    }

    return currentIndex;
}

function printSolution(mySolution) {
    console.log();
    console.log('----- result -----');
    console.log({ mySolution, solution });
    console.log('same result ?', solution.join(' ') === mySolution.join(' '));
}

function guessVal(left, right) {
    if (left === '-') {
        return 'L'
    }

    if (right === '-') {
        return 'R'
    }

    return '0';
}

function convert(legs) {
    const convertLegs = [];

    legs.forEach((a) => {
        // input:
        // |  |  |
        // |--|  |
        // |  |--|
        //
        // output:
        // 000
        // RL0
        // 0RL

        let b = ''

        for (let i = 0; i < a.length; i++) {
            char = a[i];
            if (char === '|') {
                b += guessVal(a[i-1], a[i+1])
            }
        }
        convertLegs.push(b);
    });

    return convertLegs;
}

function start() {
    // console.log(data);
    // console.log(solution);

    const dataToArray = data.trim().split('\n')
    const input = dataToArray[0].split('  ')
    const output = dataToArray[dataToArray.length - 1].split('  ')
    const legs = dataToArray.slice(1,dataToArray.length - 1)

    console.log('----- data -----');
    console.log(input.join('  '));
    console.log(legs.join('\n'));
    console.log(output.join('  '));
    console.log('----------------');

    const newLegs = convert(legs);
    console.log('new path :' );
    console.log(newLegs.join('\n'));

    const mySolution = input.map((char, index) => {
        const mySolution = algo(index, newLegs);
        console.log({index, mySolution});
        return `${char}${output[mySolution]}`
    });

    printSolution(mySolution);
}

start();


console.log('----------------');
console.log('----------------');
console.log('----------------');
console.log('----------------');
