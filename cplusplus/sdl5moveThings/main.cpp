#include <cmath>
#include <iostream>
#include <string>
#include <time.h>
#include <vector>

#include "SDL.h"
#include "SDL_image.h"
#include "SDL_ttf.h"

using namespace std;
// thanks to https://en.cppreference.com/w/cpp/numeric/math/round

// Gravity in pixels per second squared
const float GRAVITY = 750.0f;
const int BOTTOM = 800;

bool init();
void kill();
void loop();
void debug(Uint64);
void renderText(string text, SDL_Rect dest);

SDL_Window *window;
SDL_Renderer *renderer;
SDL_Texture *box;
TTF_Font *font;

struct square {
  float x, y, w, h, xvelocity, yvelocity;
  Uint32 born, lastUpdate;
};

int main(int argc, char **args) {

  if (!init()) {
    system("pause");
    return 1;
  }

  loop();

  kill();
  return 0;
}

void loop() {

  srand(time(NULL));

  // Physics squares
  vector<square> squares;
  vector<square> squaresNoMoves;

  bool running = true;
  while (running) {
    Uint64 startTicks = SDL_GetTicks64();

    SDL_Event e;

    SDL_SetRenderDrawColor(renderer, 255, 255, 255, 255);
    SDL_RenderClear(renderer);

    // Event loop
    while (SDL_PollEvent(&e) != 0) {
      switch (e.type) {
      case SDL_QUIT:
        running = false;
        break;
      case SDL_MOUSEBUTTONDOWN:
        for (int i = 0; i < 5; i++) {
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
        }
        break;
      }
    }

    // Physics loop
    for (int index = 0; index < squares.size(); index++) {
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
      SDL_Rect dest = {std::round(s.x), std::round(s.y), std::round(s.w),
                       std::round(s.h)};
      SDL_RenderCopy(renderer, box, NULL, &dest);
    }
    for (const square &s : squares) {
      SDL_Rect dest = {std::round(s.x), std::round(s.y), std::round(s.w),
                       std::round(s.h)};
      SDL_RenderCopy(renderer, box, NULL, &dest);
    }

    SDL_Delay(20);

    debug(startTicks);
    // Display window
    SDL_RenderPresent(renderer);
  }
}

void debug(Uint64 startTicks) {

  // End frame timing
  Uint64 endTicks = SDL_GetTicks64();
  Uint64 endPerf = SDL_GetPerformanceCounter();
  float frameTime = (endTicks - startTicks) / 1000.0f;

  // Strings to display
  string fps = "Current FPS: " + to_string(1.0f / frameTime);

  // Display strings
  SDL_Rect dest = {10, 10, 0, 0};
  renderText(fps, dest);
  dest.y += 24;
}

void renderText(string text, SDL_Rect dest) {
  SDL_Color fg = {0, 0, 0};
  SDL_Surface *surf = TTF_RenderText_Solid(font, text.c_str(), fg);

  dest.w = surf->w;
  dest.h = surf->h;

  SDL_Texture *tex = SDL_CreateTextureFromSurface(renderer, surf);

  SDL_RenderCopy(renderer, tex, NULL, &dest);
  SDL_DestroyTexture(tex);
  SDL_FreeSurface(surf);
}

bool init() {
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

  window =
      SDL_CreateWindow("Example", SDL_WINDOWPOS_UNDEFINED,
                       SDL_WINDOWPOS_UNDEFINED, 1800, BOTTOM, SDL_WINDOW_SHOWN);
  if (!window) {
    cout << "Error creating window: " << SDL_GetError() << endl;
    return false;
  }

  renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);
  if (!renderer) {
    cout << "Error creating renderer: " << SDL_GetError() << endl;
    return false;
  }

  SDL_Surface *buffer = IMG_Load("box.jpg");
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

  font = TTF_OpenFont("font.ttf", 24);
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
  window = NULL;
  renderer = NULL;

  TTF_Quit();
  IMG_Quit();
  SDL_Quit();
}
