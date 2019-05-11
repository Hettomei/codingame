//////////////////////////////// beekeeperkeeper
////////////////////////////////

let [, h] = readline().split(' ');
let [top, ...rungs] = [...Array(--h)].map(readline);
let bottom = readline().split("  ");

rungs.reverse().forEach(val =>
    val.split('|').forEach((v, i) => {
        if (v === "--") [bottom[i - 1], bottom[i]] = [bottom[i], bottom[i - 1]];
    })
);

print(top.split("  ").map((val, i) => val + bottom[i]).join('\n'));



//////////////////////////////// puma17
////////////////////////////////

r = readline; [w,h]=r().split(' ').map(x=>+x);
l = [...Array(h)].map(x=>r().split(''));

for (i=0; i<w; i+=3) {
    for (y=1,x=i; y<h-1; y++)
        x += (x<w-1 && l[y][x+1]=='-')*3 - (x>0 && l[y][x-1]=='-')*3;
    print(l[0][i]+l[h-1][x]);
}


//////////////////////////////// DPAmar
////////////////////////////////

var [w,h] = readline().split(' ').map(Number);
var t=[];
for(var i=0; i<h; i++) t.push(readline());
for(var i=0; i<w; i++)
{
    var start = t[0][i];
    if(start == ' ') continue;
    var column = i;
    for(var j=0; j<h; j++)
    {
        if((t[j][column-1]||'') == '-')
            while((t[j][--column]||'') == '-');
        else if((t[j][column+1]||'') == '-')
            while((t[j][++column]||'') == '-');
    }
    print(start + t[j-1][column]);
}
