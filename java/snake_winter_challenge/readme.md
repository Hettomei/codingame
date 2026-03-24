# Start

```
./run.sh
```

# Comment envoyer sur codinGame ?

Tout copier sauf le nom du package tout en haut

# Fin du concours :

Arrivé 778 eme / 2382 participants

# Goal

Récupérez de l'énergie pour faire grandir vos robot-serpents et assurez-vous d'avoir les plus long robots en fin de jeu.

# Règles
Chaque joueur contrôle une équipe de snakebots.
À chaque tour, les snakebots se déplacent simultanément selon les commandes des joueurs.

# 🗺️ Carte
Les plateformes sont des cases infranchissables.

Chaque case de la grille peut contenir
- du vide
- une plateforme
- un segment du corps d'un snakebot
- de l'énergie.

# 🐍 Snakebot

Les snakebots sont affectés par la gravité.
Cela signifie qu'au moins une partie du corps doit se trouver au-dessus de quelque chose de solide, sinon ils vont tomber.

Les autres snakebots sont considérés comme solides, tout comme les plateformes et l'énergie .

# Déplacement

Les snakebots sont en mouvement permanent et, à chaque tour, continuent dans la dernière direction qu'ils suivaient,
sauf si le joueur leur donne une nouvelle direction.

La direction initiale est vers le haut.

Lors d'un déplacement, un snakebot avance sa tête dans la direction à laquelle il fait face, et le reste de son corps le suit.

## Cas 1 :
Si la cellule dans laquelle la tête du snakebot s'est déplacée contient une plateforme ou une autre partie de corps,
la tête du snakebot est détruite et la partie suivante de son corps devient la nouvelle tête.
Cela ne se produit que s'il reste au moins trois parties. Sinon, le snakebot entier est supprimé.

## Cas 2 :
Si la cellule dans laquelle le snakebot s'est déplacé contient de l'énergie,
le snakebot va la manger. Cela a deux effets :

    Le snakebot grandit : une nouvelle partie du corps apparaît à l'extrémité de son corps.
    L'énergie disparait et cette cellule n'est plus considérée comme solide.

Ces collisions sont résolues simultanément pour tous les snakebots.

## Cas spécial :
Si plusieurs têtes de snakebots entrent en collision sur une cellule contenant de l'énergie,
cette énergie est considérée comme mangée par chacun des snakebots !

Une fois les déplacements et suppressions résolus,
tous les snakebots tombent vers le bas jusqu'à ce qu'une partie de leur corps repose sur quelque chose de solide.

Il est possible d'étendre votre snakebot au-delà des limites de la grille.
Mais tomber hors de la zone de jeu entraînera la suppression du snakebot.

# 🎬 Actions

À chaque tour, les joueurs doivent fournir au moins une action sur la sortie standard.

Les actions doivent être séparées par un point-virgule ; et être l'une des suivantes.
Commandes pour déplacer le snakebot d'identifiant id :

id UP    : (x,y) = ( 0,-1)
id DOWN  : (x,y) = ( 0, 1)
id LEFT  : (x,y) = (-1, 0)
id RIGHT : (x,y) = ( 1, 0)

Toute action de déplacement peut être suivie d'un texte qui sera affiché au-dessus du snakebot concerné à des fins de débogage.

Commandes spéciales :

MARK x y : place un marqueur aux coordonnées spécifiées. Les marqueurs sont visibles dans le viewer à des fins de débogage.
WAIT : ne rien faire.


# ⛔ Fin de partie
La partie se termine si l'une des conditions suivantes est vraie à la fin d'un tour :

- Tous les snakebots d'un joueur ont été supprimés.
- Il ne reste plus d'énergie à manger.
- 200 tours se sont écoulés.

Conditions de victoire
Avoir plus de parties de corps au total sur l'ensemble de vos snakebots que votre adversaire à la fin de la partie.

Conditions de défaite
Votre programme ne fournit pas de commande dans le temps imparti ou l'une des commandes est invalide.


# Entrée d'initialisation

Ligne 1 : myId, un entier pour l'identification de votre joueur (0 ou 1).
Ligne 2 : width, un entier correspondant à la largeur de la grille.
Ligne 3 : height un entier correspondant à la hauteur de la grille.

Les height lignes suivantes : Une ligne de la grille contenant width caractères. Chaque caractère peut être :

```
# : une plateforme.
. : une case libre.
```

Ligne suivante : snakbotsPerPlayer un entier correspondant au nombre de snakebots contrôlés par chaque joueur.

Les snakbotsPerPlayer lignes suivantes : Un entier snakebotId pour chacun des snakebots contrôlés par vous.

Les snakbotsPerPlayer Lignes suivantes : Un entier snakebotId pour chacun des snakebots contrôlés par votre adversaire.

# Entrée pour un tour de jeu

Première ligne : powerSourceCount, un entier correspondant au nombre restant d'énergie sur la grille.

Les powerSourceCount lignes suivantes : Les 2 entiers suivants pour chaque énergie :

    x : coordonnée X
    y : coordonnée Y

Ligne suivante : snakebotCount, un entier correspondant au nombre restant de snakebots sur la grille.

Les snakebotCount lignes suivantes : Les 2 entrées suivantes pour chaque snakebot :

snakebotId : un entier correspondant à l'identifiant de ce snakebot
body : une chaîne de coordonnées séparées par des deux-points (:),
chaque coordonnée représentant la position d'une partie du corps sous la forme « x,y ».
La première coordonnée de la liste est la tête.

Exemple : « 0,1:1,1:2,1 » correspond à un snakebot avec une tête en x=0 et y=1 et deux autres parties du corps vers la droite.

Sortie

Une seule ligne, contenant au moins une action

1 LEFT;2 RIGHT;MARK 12 2

# Contraintes
Temps de réponse par tour ≤ 50ms
Temps de réponse pour le premier tour ≤ 1000ms
15 ≤ width ≤ 45
10 ≤ height ≤ 30
1 ≤ snakebotCount ≤ 8

ordre des coordonnées :

0,0  1,0  2,0  3,0
0,1  1,1  2,1  3,1
0,2  1,2  2,2  3,2
