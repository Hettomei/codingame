#include <cmath>
#include <filesystem>
#include <iostream>
#include <string>
#include <time.h>
#include <vector>

#include "SDL.h"
#include "SDL_image.h"
#include "SDL_ttf.h"

namespace tim_debug {
void renderText(SDL_Renderer *renderer, std::string text, SDL_Rect *dest,
                TTF_Font *font) {
  SDL_Color fg = {0, 0, 0, 0};
  SDL_Surface *surf = TTF_RenderText_Solid(font, text.c_str(), fg);

  dest->w = surf->w;
  dest->h = surf->h;

  SDL_Texture *tex = SDL_CreateTextureFromSurface(renderer, surf);

  SDL_RenderCopy(renderer, tex, NULL, dest);
  SDL_DestroyTexture(tex);
  SDL_FreeSurface(surf);
}
void display_fps(SDL_Renderer *renderer, Uint64 startTicks, TTF_Font *font) {
  Uint64 endTicks = SDL_GetTicks64();
  float frameTime = (endTicks - startTicks) / 1000.0f;

  std::string fps = "Current FPS: " + std::to_string(1.0f / frameTime);

  SDL_Rect dest = {10, 10, 0, 0};
  renderText(renderer, fps, &dest, font);
}

} // namespace tim_debug
