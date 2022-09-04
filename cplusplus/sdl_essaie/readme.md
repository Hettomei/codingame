# Pour compiler sous windows : 

installer myngw via https://www.msys2.org/

apres faire
```
pacman -Syu
pacman -S base-devel gcc vim cmake git
pacman -S --needed base-devel mingw-w64-x86_64-toolchain
pacman -S mingw-w64-x86_64-gcc
pacman -S mingw64/mingw-w64-x86_64-cmake make
```

```
g++  --version
g++ (GCC) 11.3.0
```

Ensuite, telecharger SDL pour mingw : 
https://www.libsdl.org/ > release > SDL2-devel-2.24.0-mingw.zip

Copier : x86_64-w64-mingw32 à la racine du projet.

# Pour compiler du C++ sous windows : 

```
g++ sdl_hello.cpp  -std=c++14 -g -Wall -m64 -Ix86_64-w64-mingw32/include/SDL2 -Dmain=SDL_main -Lx86_64-w64-mingw32/lib -lmingw32 -lSDL2main -lSDL2 -mwindows -msse3 -o x86_64-w64-mingw32/bin/main

# run
./x86_64-w64-mingw32/bin/main.exe
```
