#include "SDL.h"

namespace tim_obj {

void getTriangle(SDL_Vertex *vert, SDL_Event *event) {
  // right
  vert[0].position = SDL_FPoint{event->motion.x, event->motion.y};
  vert[0].color = SDL_Color{0, 0, 0, 255};
  // left up
  vert[1].position = SDL_FPoint{event->motion.x - 50, event->motion.y - 40};
  vert[1].color.r = 0;
  vert[1].color.g = 0;
  vert[1].color.b = 255;
  vert[1].color.a = 255;
  // left down
  vert[2].position = SDL_FPoint{event->motion.x - 50, event->motion.y + 40};
  vert[2].color.r = 0;
  vert[2].color.g = 255;
  vert[2].color.b = 0;
  vert[2].color.a = 255;
}

void addVert(SDL_Vertex *vert, int i, SDL_Event *event) {
  // center
  vert[i].position = vert[i - 2].position;
  vert[i].color =
      SDL_Color{event->motion.x % 255, 255, event->motion.y % 255, 255};

  vert[i + 1].position = vert[i - 1].position;
  vert[i + 1].color =
      SDL_Color{event->motion.x % 255, 255, event->motion.y % 255, 255};

  vert[i + 2].position = SDL_FPoint{event->motion.x, event->motion.y};
  vert[i + 2].color =
      SDL_Color{event->motion.x % 255, 255, event->motion.y % 255, 255};
}
} // namespace tim_obj