set -eux

if [ "$OSTYPE" = "msys" ]; then
    echo "For windows"
    
    
    PATH=/mingw64/bin/:$PATH
    
    cp Miglia.ttf x86_64-w64-mingw32/bin/
    
    g++ main.cpp tim_sdl.cpp tim_obj.cpp \
        -std=c++20 -g \
        -Wall -Wextra \
        -m64 \
        -Ix86_64-w64-mingw32/include/SDL2 -Dmain=SDL_main \
        -Lx86_64-w64-mingw32/lib \
        -lmingw32 -lSDL2main -lSDL2 -lSDL2_ttf \
        -mwindows -msse3 \
        -o x86_64-w64-mingw32/bin/main
        
    ./x86_64-w64-mingw32/bin/main.exe $@
    
else
    echo "Not supported"
fi