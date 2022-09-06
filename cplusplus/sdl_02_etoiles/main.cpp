#include <array>
#include <chrono>
#include <cstdlib> // for EXIT_SUCCESS and EXIT_FAILURE
#include <iostream>
#include <random>

#include <SDL.h>
#include <SDL_ttf.h>

// Définition des constante
template <typename T> constexpr T WIDTHSCREEN{900};

template <typename T> constexpr T HEIGHTSCREEN{700};

template <typename T> constexpr T TOTAL_POINTS{1000};

#define POINTS_COUNT 4

static SDL_Point lines[POINTS_COUNT] = {
    {320, 200}, {300, 240}, {340, 240}, {320, 200}};

int main(int argc, char *argv[]) {
  // Chargement du module vidéo de la SDL
  if (SDL_Init(SDL_INIT_VIDEO) < 0) {
    SDL_LogError(SDL_LOG_CATEGORY_APPLICATION, "[DEBUG] > %s", SDL_GetError());
    return EXIT_FAILURE;
  }

  if (TTF_Init() < 0) {
    SDL_LogError(SDL_LOG_CATEGORY_APPLICATION, "[DEBUG] > %s", TTF_GetError());
    return EXIT_FAILURE;
  }

  // Ressource : Fenêtre et rendu
  SDL_Window *pWindow{nullptr};
  SDL_Renderer *pRenderer{nullptr};

  // Création d'une fenêtre, en 800x600, et visible
  if (SDL_CreateWindowAndRenderer(WIDTHSCREEN<unsigned int>,
                                  HEIGHTSCREEN<unsigned int>, SDL_WINDOW_SHOWN,
                                  &pWindow, &pRenderer) < 0) {
    SDL_LogError(SDL_LOG_CATEGORY_APPLICATION, "[DEBUG] > %s", SDL_GetError());
    SDL_Quit();
    return EXIT_FAILURE;
  }

  SDL_RendererInfo infoRenderer;
  SDL_GetRendererInfo(pRenderer, &infoRenderer);

  if (infoRenderer.flags & SDL_RENDERER_ACCELERATED) {
    SDL_Log("Le rendu est gérer par SDL_RENDERER_ACCELERATED.");
  }
  if (infoRenderer.flags & SDL_RENDERER_SOFTWARE) {
    SDL_Log("Le rendu est gérer par SDL_RENDERER_SOFTWARE.");
  }

  if (infoRenderer.flags & SDL_RENDERER_TARGETTEXTURE) {
    SDL_Log("Le rendu est autorisé sur des textures.");
  }

  TTF_Font *font = TTF_OpenFont(
      "Miglia.ttf",
      18); // Crée un police avec la police "ariali.ttf" et de taille 18 pixels

  if (font == nullptr) {
    SDL_LogError(SDL_LOG_CATEGORY_APPLICATION, "[DEBUG] > %s", TTF_GetError());
  }
  //passage en full screen
  //SDL_SetWindowFullscreen(pWindow, SDL_WINDOW_FULLSCREEN);
  SDL_Surface *text = TTF_RenderText_Blended(
      font, "esc, q: quitter     espace: les etoiles bougent",
      SDL_Color{0, 255, 0, 255}); // Crée un surface qui contient le texte

  if (text == nullptr) {
    SDL_LogError(SDL_LOG_CATEGORY_APPLICATION, "[DEBUG] > %s", TTF_GetError());
  }

  SDL_Texture *texture = SDL_CreateTextureFromSurface(
      pRenderer,
      text); // Crée la texture qu'on va afficher a partir de la surface

  if (texture == nullptr) {
    SDL_LogError(SDL_LOG_CATEGORY_APPLICATION, "[DEBUG] > %s", TTF_GetError());
  }

  SDL_Event events;

  // Gestion de l'aléatoire
  std::default_random_engine generator{
      static_cast<unsigned int>(std::chrono::system_clock::now()
                                    .time_since_epoch()
                                    .count())}; // Générateur

  std::uniform_int_distribution distribution_x{
      0, WIDTHSCREEN<int>}; // Selon la loi mathématique Uniform
                            //
  std::uniform_int_distribution distribution_y{
      0, HEIGHTSCREEN<int>}; // Selon la loi mathématique Uniform

  std::uniform_int_distribution distribution_move{
      0, 2}; // Selon la loi mathématique Uniform

  std::array<SDL_Point, TOTAL_POINTS<int>> points;

  // Création des SDL_Point avec des coordonnée aléatoire
  for (auto &point : points)
    point = {distribution_x(generator), distribution_y(generator)};

  SDL_Rect rectangle1{20, 20, 100, 50};

  SDL_Rect position;

  SDL_QueryTexture(texture, nullptr, nullptr, &position.w,
                   &position.h); // Récupere la dimension de la texture

  // Centre la texture sur l'écran
  position.x = WIDTHSCREEN<int> / 2 - position.w / 2;
  position.y = 50;

  SDL_FreeSurface(text);
  TTF_CloseFont(font);

  int x;
  int y;
  // Game loop
  bool isOpen{true};
  while (isOpen) {
    // Input
    while (SDL_PollEvent(&events)) {
      switch (events.type) {
      case SDL_QUIT:
        isOpen = false;
        break;
      case SDL_KEYDOWN:
        if (events.key.keysym.sym == SDLK_q ||
            events.key.keysym.sym == SDLK_ESCAPE) {
          isOpen = false;
          break;
        }

        for (auto &point : points) {
          x = point.x + distribution_move(generator);
          y = point.y + distribution_move(generator);
          if (x > WIDTHSCREEN<int>)
            x = 0;
          if (y > HEIGHTSCREEN<int>)
            y = 0;
          point = {x, y};
        }
        SDL_SetRenderDrawColor(pRenderer, 255, 255, 255,
                               255); // Choisir la couleur blanche
        SDL_RenderDrawPoints(
            pRenderer, &points[0],
            points.size()); // Dessiner mon tableau de SDL_Point en
                            // tous en couleur blanche.

        SDL_RenderPresent(pRenderer); // Mise à jour de la fenêtre
      }
    }

    // Rendering
    SDL_SetRenderDrawColor(pRenderer, 0, 0, 0, 255); // Choisir la couleur noir
    SDL_RenderClear(pRenderer); // Colorier en noir toutes la fenêtre

    SDL_SetRenderDrawColor(pRenderer, 255, 255, 0, 0); // jaune
    SDL_RenderFillRect(pRenderer, &rectangle1);

    SDL_RenderDrawLine(pRenderer, 100, 200, 300, 400);
    SDL_RenderDrawLines(pRenderer, lines, POINTS_COUNT);
    SDL_RenderCopy(pRenderer, texture, nullptr, &position);

    SDL_SetRenderDrawColor(pRenderer, 255, 255, 255,
                           255); // Choisir la couleur blanche
    SDL_RenderDrawPoints(pRenderer, &points[0],
                         points.size()); // Dessiner mon tableau de SDL_Point en
                                         // tous en couleur blanche.

    SDL_RenderPresent(pRenderer); // Mise à jour de la fenêtre
  }

  // Libération des ressource en mémoire
  SDL_DestroyTexture(texture);
  SDL_DestroyRenderer(pRenderer);
  SDL_DestroyWindow(pWindow);
  TTF_Quit();
  SDL_Quit();

  return EXIT_SUCCESS;
}
