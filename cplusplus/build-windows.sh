set -eux

rm -f main

#clang-format-11 -i main.cpp tools.cpp tools.h

g++ -Werror src/main.cpp -o bin/main -I include -L lib -lmingw32 -lSDL2main -lSDL2_ttf -lSDL2

# g++-11 -Werror tools.cpp `sdl-config --cflags`
# g++-11 -Werror -Wextra main.cpp `sdl-config --cflags`
# g++-11 -o main main.o tools.o `sdl-config --libs`
