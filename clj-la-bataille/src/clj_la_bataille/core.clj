(ns clj-la-bataille.core
  (:gen-class))

(use '[clojure.string :only (join split)])

; (ns Solution
;   (:gen-class))

; Par exemple, si la distribution des cartes est la suivante :
; Joueur 1 : 10D 9S 8D KH 7D 5H 6S
; Joueur 2 : 10H 7H 5C QC 2C 4H 6D
; Après une manche elle sera :
; Joueur 1 : 5H 6S 10D 9S 8D KH 7D 10H 7H 5C QC 2C
; Joueur 2 : 4H 6D

; bataille: Chaque joueur défausse 3 cartes

; Si la partie a une fin :
; le numéro du gagnant (1 ou 2)
; et le nombre de manches jouées séparés par un espace.
; Une bataille ou une succession de batailles comptent pour une seule manche.

; Si les joueurs terminent ex aequo : PAT

; Si un des deux joueurs n'a plus assez de cartes pour jouer lors d'une bataille
; (pendant la phase de défausse ou pendant la phase de combat qui suit la défausse),
; alors les joueurs sont ex aequo.

(defn clean [card]
  (join "" (drop-last card)))

(defn prepare-deck [n]
  ; read-string convert n to integer
  (loop [i (read-string n) result []]
    (if (> i 0)
      (let [card (read-line)]
        (recur (dec i) (conj result (clean card))))
      result)))

(def force* ["2" "3" "4" "5" "6" "7" "8" "9" "10" "J" "Q" "K" "A"])


(defn win [card1 card2]
  (let [a (.indexOf force* card1)
        b (.indexOf force* card2)]
    (> a b)))

(defn add-to-deck [j-card other-j-card deck]
  (concat deck [j-card] [other-j-card]))

(defn bataille [card1 deck1 card2 deck2]
  ['bataille])

(defn round [manche
             [card1 & deck1]
             [card2 & deck2]]
  (println "-------")
  (println 'manche manche)
  (println 'play card1 'stay deck1)
  (println 'play card2 'stay deck2)

  (cond

    (not card1) [2 (dec manche)]
    (not card2) [1 (dec manche)]

    (win card1 card2) (do
                        (println 'j1 'win 'against 'j2)
                        (let [new-deck-1 (add-to-deck card1 card2 deck1)]
                          (round (inc manche) new-deck-1 deck2)))

    (win card2 card1) (do
                        (println 'j2 'win 'against 'j1)
                        (let [new-deck-2 (add-to-deck card1 card2 deck2)]
                          (round (inc manche) deck1 new-deck-2)))
    ; BATAILLE
    :else (bataille card1 deck1 card2 deck2)))

(defn -main [& args]
  (let [deck-j1 (prepare-deck (read-line))
        deck-j2 (prepare-deck (read-line))]
    (println 'j1 deck-j1)
    (println 'j2 deck-j2)
    (println (join " " (round 1 deck-j1 deck-j2)))))
