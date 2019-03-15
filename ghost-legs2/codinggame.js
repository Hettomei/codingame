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

var inputs = readline().split(' ');
const W = parseInt(inputs[0]);
const H = parseInt(inputs[1]);
let data = ''
for (let i = 0; i < H; i++) {
    data += "\n";
    data += readline();
}
data += "\n";

const dataToArray = data.trim().split('\n')
const input = dataToArray[0].split('  ')
const output = dataToArray[dataToArray.length - 1].split('  ')
const legs = dataToArray.slice(1,dataToArray.length - 1)

const newLegs = convert(legs);

input.map((char, index) => {
    const mySolution = algo(index, newLegs);
    console.log(`${char}${output[mySolution]}`);
});
