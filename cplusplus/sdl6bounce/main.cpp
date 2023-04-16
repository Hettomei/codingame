#include <cmath>
#include <filesystem>
#include <iostream>
#include <string>
#include <time.h>
#include <vector>

#include "SDL.h"
#include "SDL_image.h"
#include "SDL_ttf.h"
#include "tim_debug.h"

using namespace std; // todo remove this
// thanks to https://en.cppreference.com/w/cpp/numeric/math/round

const int WINDOW_WIDTH = 1200;
const int WINDOW_HEIGHT = 780;

const float GRAVITY = 0.08f;

const int BOTTOM = WINDOW_HEIGHT;

bool init();
void loop();
void cleanup_before_exit();

SDL_Window *window;
SDL_Renderer *renderer;
SDL_Texture *box;
TTF_Font *font;

struct square {
  float x, y, w, h, xvelocity, yvelocity;
  int bounce;
  bool is_moving;
};

int main(int argc, char **args) {
  cout << "phrase test cout" << endl;
  cerr << "phrase test cerr" << endl;
  cout << "argc: " << argc << " args: " << args << endl;

  if (!init()) {
    system("pause");
    return 1;
  }

  loop();

  cleanup_before_exit();
  return 0;
}

void loop() {
  // Physics squares
  vector<square> squares;

  bool running = true;
  SDL_SetRenderDrawColor(renderer, 255, 255, 255, 255);
  Uint64 loop_last_ticks = SDL_GetTicks64();
  Uint64 loop_start_ticks;
  SDL_FRect tiles[2];

  while (running) {
    loop_start_ticks = SDL_GetTicks64();
    SDL_SetRenderDrawColor(renderer, 255, 255, 255, 255);
    SDL_RenderClear(renderer);

    // Event loop
    SDL_Event e;
    while (SDL_PollEvent(&e) != 0) {
      switch (e.type) {
      case SDL_QUIT:
        running = false;
        break;
      case SDL_MOUSEBUTTONDOWN:
        SDL_LogDebug(SDL_LOG_CATEGORY_APPLICATION, "Click");
        square s;
        s.w = rand() % 50 + 25;
        s.h = rand() % 50 + 25;

        s.x = e.button.x - s.w / 2;
        s.y = e.button.y - s.h / 2;

        s.yvelocity = -40;
        s.xvelocity = rand() % 6; // rand % 20 + 5 => de 5 à 20+5;

        s.bounce = 0;
        s.is_moving = true;
        tiles[0] = {s.x, s.y, s.w, s.h};

        squares.push_back(s);
        break;
      case SDL_KEYDOWN:
        if (e.key.keysym.sym == SDLK_q || e.key.keysym.sym == SDLK_ESCAPE) {
          running = false;
          break;
        }
      }
    }

    float dTms = loop_start_ticks - loop_last_ticks;

    // Physics loop
    for (square &s : squares) {
      if (!s.is_moving) {
        continue;
      }
      s.yvelocity += dTms * GRAVITY;
      s.y += s.yvelocity;
      s.x += s.xvelocity;
      SDL_LogDebug(SDL_LOG_CATEGORY_APPLICATION,
                   "moves dTms: %f, s.y: %f, yvel: %f", dTms, s.y, s.yvelocity);

      if (s.y + s.h > BOTTOM) {
        s.yvelocity = -s.yvelocity / 1.3;
        s.y = BOTTOM - s.h;
        s.bounce += 1;
        SDL_LogDebug(SDL_LOG_CATEGORY_APPLICATION, "stop moves s.y: %f", s.y);
      }

      if (s.bounce > 15) {
        s.is_moving = false;
      }

      if (s.x > WINDOW_WIDTH) {
        s.is_moving = false;
        SDL_LogDebug(SDL_LOG_CATEGORY_APPLICATION, "stop moves s.y: %f", s.y);
      }
      tiles[0].x = s.x;
      tiles[0].y = s.y;
    }

    SDL_SetRenderDrawColor(renderer, 0, 0, 0, 255);
    SDL_RenderDrawRectsF(renderer, tiles, 1 /* tiles size */);
    tim_debug::display_fps(renderer, loop_last_ticks, font);
    // Display window
    SDL_RenderPresent(renderer);
    loop_last_ticks = loop_start_ticks;
    SDL_Delay(20);
  }
}

bool init() {
  srand(time(NULL));
  SDL_LogSetAllPriority(SDL_LOG_PRIORITY_DEBUG);

  std::string projectPath;
  char *charPath = SDL_GetBasePath();
  projectPath = charPath;
  SDL_free(charPath);

  cout << "bin dans : " << projectPath << endl;
  const std::filesystem::path project_path = projectPath;
  const std::filesystem::path resources_path = project_path / "resources";
  cout << "bin in : " << project_path << endl;
  cout << "resources in : " << resources_path << endl;

  if (SDL_Init(SDL_INIT_EVERYTHING) < 0) {
    cout << "Error initializing SDL: " << SDL_GetError() << endl;
    return false;
  }

  if (IMG_Init(IMG_INIT_JPG) < 0) {
    cout << "Error initializing SDL_image: " << IMG_GetError() << endl;
    return false;
  }

  if (TTF_Init() < 0) {
    cout << "Error initializing SDL_ttf: " << TTF_GetError() << endl;
    return false;
  }

  window = SDL_CreateWindow("Example", SDL_WINDOWPOS_UNDEFINED,
                            SDL_WINDOWPOS_UNDEFINED, WINDOW_WIDTH,
                            WINDOW_HEIGHT, SDL_WINDOW_SHOWN);
  if (!window) {
    cout << "Error creating window: " << SDL_GetError() << endl;
    return false;
  }

  renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);
  if (!renderer) {
    cout << "Error creating renderer: " << SDL_GetError() << endl;
    return false;
  }

  font = TTF_OpenFont(
      (resources_path / "fonts" / "font.ttf").generic_string().c_str(), 24);
  if (!font) {
    cout << "Error loading font: " << TTF_GetError() << endl;
    return false;
  }

  return true;
}

void cleanup_before_exit() {
  TTF_CloseFont(font);
  SDL_DestroyTexture(box);
  font = NULL;
  box = NULL;

  SDL_DestroyRenderer(renderer);
  SDL_DestroyWindow(window);
  renderer = NULL;
  window = NULL;

  TTF_Quit();
  IMG_Quit();
  SDL_Quit();
}
