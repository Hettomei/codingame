set -eux

PATH=/mingw64/bin/:$PATH

g++ sdl_hello.cpp \
    -std=c++20 -g \
    -Wall -Wextra \
    -m64 \
    -Ix86_64-w64-mingw32/include/SDL2 -Dmain=SDL_main \
    -Lx86_64-w64-mingw32/lib \
    -lmingw32 -lSDL2main -lSDL2 \
    -mwindows -msse3 \
    -o x86_64-w64-mingw32/bin/main
    
 ./x86_64-w64-mingw32/bin/main.exe