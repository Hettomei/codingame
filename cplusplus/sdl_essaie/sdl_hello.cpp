#include <SDL.h>
#include <iostream>



const int WIDTH = 1100, HEIGHT = 900;

int main(int argc, char *args[]) {
  std::cout << "Hello World!";

  if (SDL_Init(SDL_INIT_EVERYTHING) < 0) {
    SDL_LogError(SDL_LOG_CATEGORY_APPLICATION, "[DEBUG] > %s", SDL_GetError());
    return EXIT_FAILURE;
  }

  SDL_Window *window =
      SDL_CreateWindow("GAME", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED,
                       WIDTH, HEIGHT, SDL_WINDOW_ALLOW_HIGHDPI);

  if (window == NULL) {
    SDL_LogError(SDL_LOG_CATEGORY_APPLICATION, "[DEBUG] > window == null %s",
                 SDL_GetError());
    SDL_Quit();
    return EXIT_FAILURE;
  }

  SDL_Renderer *renderer = SDL_CreateRenderer(window, -1, 0);
  SDL_SetRenderDrawColor(renderer, 10, 10, 10, 255);
  SDL_RenderClear(renderer);

  SDL_RenderPresent(renderer);

  SDL_RendererInfo infoRenderer;
  SDL_GetRendererInfo(renderer, &infoRenderer);

  if (infoRenderer.flags & SDL_RENDERER_ACCELERATED) {
    SDL_Log("Le rendu est gérer par SDL_RENDERER_ACCELERATED.");
  }
  if (infoRenderer.flags & SDL_RENDERER_SOFTWARE) {
    SDL_Log("Le rendu est gérer par SDL_RENDERER_SOFTWARE.");
  }

  if (infoRenderer.flags & SDL_RENDERER_TARGETTEXTURE) {
    SDL_Log("Le rendu est autorisé sur des textures.");
  }

  SDL_Event event;
  while (1) {
    if (SDL_PollEvent(&event)) {
      if (event.type == SDL_QUIT) {
        break;
      }
      if (event.type == SDL_KEYDOWN ) {
          SDL_Log("Physical %s key acting as %s key",
            SDL_GetScancodeName(event.key.keysym.scancode),
            SDL_GetKeyName(event.key.keysym.sym));
      }

      if (event.type == SDL_KEYDOWN && event.key.keysym.sym == SDLK_q) {
        SDL_Log("You pressed q");
        break;
      }
    }
  }

  SDL_DestroyRenderer(renderer);
  SDL_DestroyWindow(window);

  SDL_Quit();
  return EXIT_SUCCESS;
}
