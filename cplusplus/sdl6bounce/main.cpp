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

using namespace std;
// thanks to https://en.cppreference.com/w/cpp/numeric/math/round

// Gravity in pixels per second squared
const float GRAVITY = 750.0f;
const int BOTTOM = 800;
const int WINDOW_WIDTH = 800;
const int WINDOW_HEIGHT = 500;

bool init();
void kill();
void loop();

SDL_Window *window;
SDL_Renderer *renderer;
SDL_Texture *box;
TTF_Font *font;

struct square {
  float x, y, w, h, xvelocity, yvelocity;
  Uint32 born, lastUpdate;
};

int main(int argc, char **args) {
  cout << "phrase test cout" << endl;
  cerr << "phrase test cerr" << endl;
  cout << "argc: " << argc << " args: " << args << endl;

  if (!init()) {
    system("pause");
    return 1;
  }

  srand(time(NULL));

  loop();

  kill();
  return 0;
}

void loop() {
  // Physics squares
  vector<square> squares;
  vector<square> squaresNoMoves;

  bool running = true;
  SDL_SetRenderDrawColor(renderer, 255, 255, 255, 255);
  while (running) {
    Uint64 startTicks = SDL_GetTicks64();
    SDL_RenderClear(renderer);

    // Event loop
    SDL_Event e;
    while (SDL_PollEvent(&e) != 0) {
      switch (e.type) {
      case SDL_QUIT:
        running = false;
        break;
      case SDL_MOUSEBUTTONDOWN:
        square s;
        s.x = e.button.x;
        s.y = e.button.y;
        s.w = rand() % 50 + 25;
        s.h = rand() % 50 + 25;
        s.yvelocity = -500;
        s.xvelocity = rand() % 500 - 250;
        s.lastUpdate = SDL_GetTicks64();
        s.born = SDL_GetTicks64();

        squares.push_back(s);
        break;
      case SDL_KEYDOWN:
        if (e.key.keysym.sym == SDLK_q || e.key.keysym.sym == SDLK_ESCAPE) {
          running = false;
          break;
        }
      }
    }

    // Physics loop
    for (long long unsigned int index = 0; index < squares.size(); index++) {
      square &s = squares[index];

      Uint64 time = SDL_GetTicks64();
      float dT = (time - s.lastUpdate) / 1000.0f;

      s.yvelocity += dT * GRAVITY;
      s.y += s.yvelocity * dT;
      s.x += s.xvelocity * dT;

      if (s.y > BOTTOM - s.h) {
        s.y = BOTTOM - s.h;
        s.xvelocity = 0;
        s.yvelocity = 0;
      }

      s.lastUpdate = time;
      if (s.lastUpdate > s.born + 5000) {
        squares.erase(squares.begin() + index);
        index--;
        squaresNoMoves.push_back(s);
      }
    }

    // Render loop
    for (const square &s : squaresNoMoves) {
      SDL_Rect dest = {(int)std::round(s.x), (int)std::round(s.y),
                       (int)std::round(s.w), (int)std::round(s.h)};
      SDL_RenderCopy(renderer, box, NULL, &dest);
    }
    for (const square &s : squares) {
      SDL_Rect dest = {(int)std::round(s.x), (int)std::round(s.y),
                       (int)std::round(s.w), (int)std::round(s.h)};
      SDL_RenderCopy(renderer, box, NULL, &dest);
    }

    SDL_Delay(20);

    tim_debug::display_fps(renderer, startTicks, font);
    // Display window
    SDL_RenderPresent(renderer);
  }
}

bool init() {
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

  // SDL_Surface *buffer = IMG_Load(projectPath + "resources/images/box.jpg");
  SDL_Surface *buffer = IMG_Load(
      (resources_path / "images" / "box.jpg").generic_string().c_str());
  if (!buffer) {
    cout << "Error loading image box.jpg: " << SDL_GetError() << endl;
    return false;
  }

  box = SDL_CreateTextureFromSurface(renderer, buffer);
  SDL_FreeSurface(buffer);
  buffer = NULL;
  if (!box) {
    cout << "Error creating texture: " << SDL_GetError() << endl;
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

void kill() {
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
