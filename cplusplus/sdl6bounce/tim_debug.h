#pragma once

#include "SDL.h"
#include "SDL_ttf.h"

#include <string>

namespace tim_debug {
void display_fps(SDL_Renderer *renderer, Uint64 startTicks, TTF_Font *font);
} // namespace tim_debug
