#include <iostream>
#include <SDL.h>

const int WIDTH = 1100, HEIGHT = 900;

int main(int argc, char* args[]) {
  std::cout << "Hello World!";
  if(SDL_Init(SDL_INIT_EVERYTHING) < 0) {
    return 1;
  }

  SDL_Window* window = SDL_CreateWindow("GAME",
                                        SDL_WINDOWPOS_UNDEFINED,
                                        SDL_WINDOWPOS_UNDEFINED,
                                        WIDTH, HEIGHT,
                                        SDL_WINDOW_ALLOW_HIGHDPI);

  if(window == NULL) {
    return 1;
  }
  
  SDL_Renderer* renderer = SDL_CreateRenderer(window, -1, 0);
  SDL_SetRenderDrawColor(renderer, 255, 0, 0, 255);
  SDL_RenderClear(renderer);

  SDL_RenderPresent(renderer);
  
  SDL_Event event;
  while(1) {
    if(SDL_PollEvent(&event)) {
      if(event.type == SDL_QUIT) {
        break;
      }
    }
  }

  SDL_DestroyWindow(window);

  SDL_Quit();
  return 0;
}
