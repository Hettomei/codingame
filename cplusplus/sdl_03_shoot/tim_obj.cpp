#include "SDL.h"
#include <random>

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
  std::random_device dev;
  std::mt19937 rng(dev());
  std::uniform_int_distribution<std::mt19937::result_type> dist6(
      0, 255); // distribution in range [1, 6]

  // center
  vert[i].position = vert[i - 2].position;
  vert[i].color = SDL_Color{dist6(rng), dist6(rng), dist6(rng), 255};

  vert[i + 1].position = vert[i - 1].position;
  vert[i + 1].color = SDL_Color{dist6(rng), dist6(rng), dist6(rng), 255};

  vert[i + 2].position = SDL_FPoint{event->motion.x, event->motion.y};
  vert[i + 2].color = SDL_Color{dist6(rng), dist6(rng), dist6(rng), 255};
}
} // namespace tim_obj