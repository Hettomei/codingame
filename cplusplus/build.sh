set -eux

rm -f main
g++-11 -Werror main.cpp -o main -lSDL2main -lSDL2

./main

# g++-11 -Werror tools.cpp `sdl-config --cflags`
# g++-11 -Werror -Wextra main.cpp `sdl-config --cflags`
# g++-11 -o main main.o tools.o `sdl-config --libs`
# ./main
