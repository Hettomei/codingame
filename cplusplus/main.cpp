#include "tools.h"
#include <iostream>
#include <cstdlib> // for EXIT_SUCCESS and EXIT_FAILURE
#include <SDL2/SDL.h>
// Inclusion des bibliothèque standard C++
#include <random>
#include <chrono>
#include <array>

// Définition des constante
template<typename T>
constexpr T WIDTHSCREEN{ 800 };

template<typename T>
constexpr T HEIGHTSCREEN{ 600 };

template<typename T>
constexpr T TOTAL_POINTS{ 5000 };

int main(int argc, char* argv[])
{
    // Chargement du module vidéo de la SDL
    if (SDL_Init(SDL_INIT_VIDEO) < 0)
    {
        SDL_LogError(SDL_LOG_CATEGORY_APPLICATION, "[DEBUG] > %s", SDL_GetError());
        return EXIT_FAILURE;
    }

    // Ressource : Fenêtre et rendu
    SDL_Window* pWindow{ nullptr };
    SDL_Renderer* pRenderer{ nullptr };

    // Création d'une fenêtre, en 800x600, et visible
    if (SDL_CreateWindowAndRenderer(WIDTHSCREEN<unsigned int>, HEIGHTSCREEN<unsigned int>, SDL_WINDOW_SHOWN, &pWindow, &pRenderer) < 0)
    {
        SDL_LogError(SDL_LOG_CATEGORY_APPLICATION, "[DEBUG] > %s", SDL_GetError());
        SDL_Quit();
        return EXIT_FAILURE;
    }


    SDL_Event events;
    bool isOpen{ true };

    // Gestion de l'aléatoire
    std::default_random_engine generator{ static_cast<unsigned int>(std::chrono::system_clock::now().time_since_epoch().count()) }; // Générateur
    std::uniform_int_distribution distribution{ 0, WIDTHSCREEN<int>}; // Selon la loi mathématique Uniform

    std::array<SDL_Point, TOTAL_POINTS<int>> points; // Tableau des 5000 SDL_Point

    // Création des SDL_Point avec des coordonnée aléatoire
    for (auto& point : points)
        point = { distribution(generator), distribution(generator) };

    // Game loop
    while (isOpen)
    {
        // Input
        while (SDL_PollEvent(&events))
        {
            switch (events.type)
            {
            case SDL_QUIT:
                isOpen = false;
                break;
            }
        }


        // Rendering
        SDL_SetRenderDrawColor(pRenderer, 0, 0, 0, 255); // Choisir la couleur noir
        SDL_RenderClear(pRenderer); // Colorier en noir toutes la fenêtre

        SDL_SetRenderDrawColor(pRenderer, 255, 255, 255, 255); // Choisir la couleur blanche

        SDL_RenderDrawPoints(pRenderer, &points[0], points.size()); // Dessiner mon tableau de SDL_Point en tous en couleur blanche.

        SDL_RenderPresent(pRenderer); // Mise à jour de la fenêtre
    }

    // Libération des ressource en mémoire
    SDL_DestroyRenderer(pRenderer);
    SDL_DestroyWindow(pWindow);
     SDL_Quit();

    return EXIT_SUCCESS;

}
