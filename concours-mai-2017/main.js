/**
 * Bring data on patient samples from the diagnosis machine to the laboratory with enough molecules to produce medicine!
 **/

var projectCount = parseInt(readline());
for (var i = 0; i < projectCount; i++) {
    var inputs = readline().split(' ');
    var a = parseInt(inputs[0]);
    var b = parseInt(inputs[1]);
    var c = parseInt(inputs[2]);
    var d = parseInt(inputs[3]);
    var e = parseInt(inputs[4]);
}

// game loop
while (true) {
    for (var i = 0; i < 2; i++) {
        var inputs = readline().split(' ');
        var target = inputs[0];
        var eta = parseInt(inputs[1]);
        var score = parseInt(inputs[2]);
        var storageA = parseInt(inputs[3]);
        var storageB = parseInt(inputs[4]);
        var storageC = parseInt(inputs[5]);
        var storageD = parseInt(inputs[6]);
        var storageE = parseInt(inputs[7]);
        var expertiseA = parseInt(inputs[8]);
        var expertiseB = parseInt(inputs[9]);
        var expertiseC = parseInt(inputs[10]);
        var expertiseD = parseInt(inputs[11]);
        var expertiseE = parseInt(inputs[12]);
    }
    var inputs = readline().split(' ');
    var availableA = parseInt(inputs[0]);
    var availableB = parseInt(inputs[1]);
    var availableC = parseInt(inputs[2]);
    var availableD = parseInt(inputs[3]);
    var availableE = parseInt(inputs[4]);
    var sampleCount = parseInt(readline());
    for (var i = 0; i < sampleCount; i++) {
        var inputs = readline().split(' ');
        var sampleId = parseInt(inputs[0]);
        var carriedBy = parseInt(inputs[1]);
        var rank = parseInt(inputs[2]);
        var expertiseGain = inputs[3];
        var health = parseInt(inputs[4]);
        var costA = parseInt(inputs[5]);
        var costB = parseInt(inputs[6]);
        var costC = parseInt(inputs[7]);
        var costD = parseInt(inputs[8]);
        var costE = parseInt(inputs[9]);
    }

    // Write an action using print()
    // To debug: printErr('Debug messages...');

    print('GOTO DIAGNOSIS');
}
