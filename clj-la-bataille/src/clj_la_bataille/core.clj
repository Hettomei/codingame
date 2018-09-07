(ns clj-la-bataille.core
  (:gen-class))

; (ns Solution
;   (:gen-class))

; Par exemple, si la distribution des cartes est la suivante :
; Joueur 1 : 10D 9S 8D KH 7D 5H 6S
; Joueur 2 : 10H 7H 5C QC 2C 4H 6D
; Après une manche elle sera :
; Joueur 1 : 5H 6S 10D 9S 8D KH 7D 10H 7H 5C QC 2C
; Joueur 2 : 4H 6D

; Si la partie a une fin :
; le numéro du gagnant (1 ou 2)
; et le nombre de manches jouées séparés par un espace.
; Une bataille ou une succession de batailles comptent pour une seule manche.

; Si les joueurs terminent ex aequo : PAT

(defn clean [card]
  (clojure.string/join "" (drop-last card)))

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
    (println 'card1 a)
    (println 'card2 b)
    (if (> a b) true false)))

(defn add-to-deck [j-card other-j-card deck]
  (concat deck [j-card] [other-j-card]))

(defn round [manche
             [card1 & deck1]
             [card2 & deck2]]
  (println "-------")
  (println 'manche manche)
  (println 'play card1 'stay deck1)
  (println 'play card2 'stay deck2)

  (if (win card1 card2)
    (do
      (println 'j1 'win 'against 'j2)
      (let [xx (add-to-deck card1 card2 deck1)]
        (println 'AAAAAAAAAAAAAAA xx)
        ))
    (do
      (println 'j2 'win 'against 'j1)
      (add-to-deck card2 card1 deck2)))

  (if (and deck1 deck2)
    (round (inc manche) deck1 deck2)
    [manche (if deck1 1 2)]
    ))

(defn result [j1 j2]
  (println 'j1 j1)
  (println 'j2 j2)
  (let [result (round 1 j1 j2)]
    result))

(defn -main [& args]
  (println 'result (result
                     (prepare-deck (read-line))
                     (prepare-deck (read-line)))))
