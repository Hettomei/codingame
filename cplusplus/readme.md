https://devopssec.fr/article/bases-fondamentales-de-laffichage-graphique-sdl2

```
sudo apt-get install libsdl2-dev

./build.sh
./main
```


Sur https://doc.ubuntu-fr.org/sdl il y a : 

```
gcc -o executable fichier1.c fichier2.c fichier3.c ...  `sdl-config --cflags --libs` 
```

gcc peut etre remplacé par g++ si vous programmez en C++.

Pour compiler plusieurs fichiers séparément puis les linker, utilisez :

```
gcc -c -Wall -Wextra main.c `sdl-config --cflags`
gcc -c -Wall -Wextra fichier1.c `sdl-config --cflags`
gcc -o executable main.o fichier1.o `sdl-config --libs`
```
