#include "tools.h"

#include <array>
#include <chrono>
#include <cstdlib> // for EXIT_SUCCESS and EXIT_FAILURE
#include <iostream>
#include <random>

#include <SDL2/SDL.h>
#include <SDL2/SDL_ttf.h>

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

  SDL_Surface *text = TTF_RenderText_Blended(
      font, "Hello, World",
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
  bool isOpen{true};

  // Gestion de l'aléatoire
  std::default_random_engine generator{
      static_cast<unsigned int>(std::chrono::system_clock::now()
                                    .time_since_epoch()
                                    .count())}; // Générateur
  std::uniform_int_distribution distribution{
      0, WIDTHSCREEN<int>}; // Selon la loi mathématique Uniform

  std::array<SDL_Point, TOTAL_POINTS<int>> points;

  // Création des SDL_Point avec des coordonnée aléatoire
  for (auto &point : points)
    point = {distribution(generator), distribution(generator)};

  SDL_Rect rectangle1{20, 20, 100, 50};

  SDL_Rect position;

  SDL_QueryTexture(texture, nullptr, nullptr, &position.w,
                   &position.h); // Récupere la dimension de la texture

  // Centre la texture sur l'écran
  position.x = WIDTHSCREEN<int> / 2 - position.w / 2;
  position.y = HEIGHTSCREEN<int> / 2 - position.h / 2;

  SDL_FreeSurface(text);
  TTF_CloseFont(font);

  // Game loop
  while (isOpen) {
    // Input
    while (SDL_PollEvent(&events)) {
      switch (events.type) {
      case SDL_QUIT:
        isOpen = false;
        break;
      }
    }

    // Rendering
    SDL_SetRenderDrawColor(pRenderer, 0, 0, 0, 255); // Choisir la couleur noir
    SDL_RenderClear(pRenderer); // Colorier en noir toutes la fenêtre

    SDL_SetRenderDrawColor(pRenderer, 255, 255, 255,
                           255); // Choisir la couleur blanche

    SDL_RenderDrawPoints(pRenderer, &points[0],
                         points.size()); // Dessiner mon tableau de SDL_Point en
                                         // tous en couleur blanche.
    SDL_SetRenderDrawColor(pRenderer, 255, 255, 0, 0);
    SDL_RenderFillRect(pRenderer, &rectangle1);

    SDL_RenderDrawLine(pRenderer, 100, 200, 300, 400);
    SDL_RenderDrawLines(pRenderer, lines, POINTS_COUNT);
    SDL_RenderCopy(pRenderer, texture, nullptr, &position);

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
