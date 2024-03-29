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
  Uint32 totalFrameTicks = 0;
  Uint32 totalFrames = 0;
  while (running) {

    // Start frame timing
    totalFrames++;
    Uint32 startTicks = SDL_GetTicks();
    Uint64 startPerf = SDL_GetPerformanceCounter();

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
      case SDL_MOUSEMOTION:

        for (int i = 0; i < 100; i++) {
          square s;
          s.x = e.button.x;
          s.y = e.button.y;
          s.w = rand() % 50 + 25;
          s.h = rand() % 50 + 25;
          s.yvelocity = -500;
          s.xvelocity = rand() % 500 - 250;
          s.lastUpdate = SDL_GetTicks();
          s.born = SDL_GetTicks();
          squares.push_back(s);
        }
        break;
      }
    }

    // Physics loop
    for (int index = 0; index < squares.size(); index++) {
      square &s = squares[index];

      Uint32 time = SDL_GetTicks();
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
    for (const square &s : squares) {
      SDL_Rect dest = {std::round(s.x), std::round(s.y), std::round(s.w),
                       std::round(s.h)};
      SDL_RenderCopy(renderer, box, NULL, &dest);
    }
    for (const square &s : squaresNoMoves) {
      SDL_Rect dest = {std::round(s.x), std::round(s.y), std::round(s.w),
                       std::round(s.h)};
      SDL_RenderCopy(renderer, box, NULL, &dest);
    }

    // Delay for a random number of ticks - this makes the frame rate
    // variable, demonstrating that the physics is independent of the frame
    // rate.
    SDL_Delay(30);

    // End frame timing
    Uint32 endTicks = SDL_GetTicks();
    Uint64 endPerf = SDL_GetPerformanceCounter();
    Uint64 framePerf = endPerf - startPerf;
    float frameTime = (endTicks - startTicks) / 1000.0f;
    totalFrameTicks += endTicks - startTicks;

    // Strings to display
    string fps = "Current FPS: " + to_string(1.0f / frameTime);
    string avg = "Average FPS: " +
                 to_string(1000.0f / ((float)totalFrameTicks / totalFrames));
    string perf = "Current Perf: " + to_string(framePerf);

    // Display strings
    SDL_Rect dest = {10, 10, 0, 0};
    renderText(fps, dest);
    dest.y += 24;
    renderText(avg, dest);
    dest.y += 24;
    renderText(perf, dest);
    dest.y += 24;
    renderText(to_string(squares.size()), dest);
    dest.y += 24;
    renderText(to_string(squaresNoMoves.size()), dest);

    // Display window
    SDL_RenderPresent(renderer);
  }
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
