#include <cstdlib> // for EXIT_SUCCESS and EXIT_FAILURE
#include <iostream>
#include <random>

#include "SDL.h"
#include "SDL_ttf.h"
#include "tim_obj.h"
#include "tim_sdl.h"

// Définition des constante
template <typename T> constexpr T WIDTHSCREEN{1300};
template <typename T> constexpr T HEIGHTSCREEN{700};

int main(int argc, char *argv[]) {
  SDL_Log("Params :");

  for (int i = 0; i < argc; ++i) {
    SDL_Log("%s", argv[i]);
  }
  SDL_Log("------");

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
      15); // Crée un police avec la police "ariali.ttf" et de taille 18 pixels

  if (font == nullptr) {
    SDL_LogError(SDL_LOG_CATEGORY_APPLICATION, "[DEBUG] > %s", TTF_GetError());
    SDL_Quit();
    return EXIT_FAILURE;
  }
  // passage en full screen
  // SDL_SetWindowFullscreen(pWindow, SDL_WINDOW_FULLSCREEN);
  SDL_Surface *text = TTF_RenderText_Blended(
      font, "esc: quitter, q: quitter, espace: tirer",
      SDL_Color{255, 255, 255, 255}); // Crée un surface qui contient le texte

  if (text == nullptr) {
    SDL_LogError(SDL_LOG_CATEGORY_APPLICATION, "[DEBUG] > %s", TTF_GetError());
    SDL_Quit();
    return EXIT_FAILURE;
  }

  SDL_Texture *texture = SDL_CreateTextureFromSurface(
      pRenderer,
      text); // Crée la texture qu'on va afficher a partir de la surface

  if (texture == nullptr) {
    SDL_LogError(SDL_LOG_CATEGORY_APPLICATION, "[DEBUG] > %s", TTF_GetError());
    SDL_Quit();
    return EXIT_FAILURE;
  }

  SDL_Rect position;
  SDL_QueryTexture(texture, nullptr, nullptr, &position.w,
                   &position.h); // Récupere la dimension de la texture
  position.x = 10;
  position.y = 10;

  SDL_FreeSurface(text);
  TTF_CloseFont(font);

  SDL_Vertex vert[300];
  int i{0};
  //  tim_obj::getTriangle(vert);

  // Game loop
  SDL_Event event;
  bool isOpen{true};
  while (isOpen) {
    // Input
    while (SDL_PollEvent(&event)) {
      switch (event.type) {
      case SDL_QUIT:
        isOpen = false;
        break;
      case SDL_KEYDOWN:
        if (event.key.keysym.sym == SDLK_q ||
            event.key.keysym.sym == SDLK_ESCAPE) {
          isOpen = false;
        }
        break;
      case SDL_MOUSEBUTTONDOWN:
        SDL_Log("mouse x %d, mouse y %d", event.motion.x, event.motion.y);
        tim_obj::addVert(vert, i, &event);
        i++;
        SDL_Log("i %d  %d", i % 3, i - i % 3);

        break;
      }
    }

    // Rendering
    SDL_SetRenderDrawColor(pRenderer, 0, 0, 0, 255); // Choisir la couleur noir
    SDL_RenderClear(pRenderer); // Colorier en noir toutes la fenêtre

    SDL_RenderCopy(pRenderer, texture, nullptr, &position);

    SDL_SetRenderDrawColor(pRenderer, 255, 255, 255,
                           255); // Choisir la couleur blanche
    tim_sdl::RenderDrawCircle(pRenderer, 200, 200, 200);

    SDL_RenderGeometry(pRenderer, NULL, vert, i - i % 3, NULL, 0);

    SDL_RenderPresent(pRenderer);
  }

  // Libération des ressource en mémoire
  SDL_DestroyTexture(texture);
  SDL_DestroyRenderer(pRenderer);
  SDL_DestroyWindow(pWindow);
  TTF_Quit();
  SDL_Quit();

  return EXIT_SUCCESS;
}
