https://www.codingame.com/ide/puzzle/game-of-life

1. Une cellulle vivante qui a moins de deux voisins meurt, pour cause de sous-population.
2. Une cellule vivante qui a deux ou trois voisins reste en vie
3. Une cellule vivante qui a plus de trois voisins meurt, pour cause de sur-population
4. Une cellule morte qui a exactement trois voisins retrouve la vie, grâce à la reproduction des cellules


Entrée
3 3
000
111
000

Sortie
010
010
010

cat input.test | lein run
ls src/clj_game_of_life/core.clj | entr -s 'echo go && cat input2.t
est | lein run'
