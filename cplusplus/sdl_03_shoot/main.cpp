#include <cstdlib> // for EXIT_SUCCESS and EXIT_FAILURE
#include <iostream>
#include <random>

#include "tim_sdl.h"
#include <SDL.h>
#include <SDL_ttf.h>


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

  SDL_Vertex vert[3];

  // center
  vert[0].position.x = 400;
  vert[0].position.y = 150;
  vert[0].color.r = 255;
  vert[0].color.g = 255;
  vert[0].color.b = 255;
  vert[0].color.a = 255;
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

  // Game loop
  SDL_Event events;
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
      }
    }

    // Rendering
    SDL_SetRenderDrawColor(pRenderer, 0, 0, 0, 255); // Choisir la couleur noir
    SDL_RenderClear(pRenderer); // Colorier en noir toutes la fenêtre

    SDL_RenderCopy(pRenderer, texture, nullptr, &position);

    SDL_SetRenderDrawColor(pRenderer, 255, 255, 255,
                           255); // Choisir la couleur blanche
    tim_sdl::DrawCircle(pRenderer, 200, 200, 200);
    SDL_RenderGeometry(pRenderer, NULL, vert, 3, NULL, 0);

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
