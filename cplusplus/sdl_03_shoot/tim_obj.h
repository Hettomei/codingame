#pragma once
#include "SDL.h"

namespace tim_obj {
void getTriangle(SDL_Vertex *vert, SDL_Event *event);
void addVert(SDL_Vertex *vert, int i, SDL_Event *event);
} // namespace tim_obj