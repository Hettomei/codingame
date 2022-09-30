#include "SDL.h"

namespace tim_obj {

void getTriangle(SDL_Vertex *vert) {
  // center
  vert[0].position = SDL_FPoint{400, 150};
  vert[0].color = SDL_Color{0, 0, 0, 255};
  // left
  vert[1].position.x = 200;
  vert[1].position.y = 450;
  vert[1].color.r = 0;
  vert[1].color.g = 0;
  vert[1].color.b = 255;
  vert[1].color.a = 255;
  // right
  vert[2].position.x = 600;
  vert[2].position.y = 450;
  vert[2].color.r = 0;
  vert[2].color.g = 255;
  vert[2].color.b = 0;
  vert[2].color.a = 255;
}
} // namespace tim_obj