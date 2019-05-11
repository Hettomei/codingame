(ns clj-la-bataille.core
  (:gen-class))

; Par exemple, si la distribution des cartes est la suivante :
; Joueur 1 : 10 9 8 K 7 5 6
; Joueur 2 : 10 7 5 Q 2 4 6
; Après une manche elle sera :
; Joueur 1 : 5 6 10 9 8 K 7 10 7 5 Q 2
; Joueur 2 : 4 6

; bataille: Chaque joueur défausse 3 cartes

; Si la partie a une fin :
; le numéro du gagnant (1 ou 2)
; et le nombre de manches jouées séparés par un espace.
; Une bataille ou une succession de batailles comptent pour une seule manche.

; Si les joueurs terminent ex aequo : PAT

; Si un des deux joueurs n'a plus assez de cartes pour jouer lors d'une bataille
; (pendant la phase de défausse ou pendant la phase de combat qui suit la défausse),
; alors les joueurs sont ex aequo.

(use '[clojure.string :only (join)])
(declare round)

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

(defn add-to-deck [& cards]
  (remove nil? (flatten cards)))

(defn bataille [manche
                card1 deck1 played-cards-1
                card2 deck2 played-cards-2]

  ; (println 'bataille 'manche manche)
  ; (println 'j1 'play card1 'in-deck deck1 'to-add played-cards-1)
  ; (println 'j2 'play card2 'in-deck deck2 'to-add played-cards-2)

  (cond
    (win card1 card2) (round
                        (inc manche)
                        (add-to-deck deck1 played-cards-1 card1 played-cards-2 card2)
                        deck2)
    (win card2 card1) (round
                        (inc manche)
                        deck1
                        (add-to-deck deck2 played-cards-1 card1 played-cards-2 card2))

    (or (< (count deck1) 4) (< (count deck2) 4)) ['PAT]

    (= card1 card2) (let [[d11 d12 d13 to-play-1] deck1
                          [d21 d22 d23 to-play-2] deck2]
                      (recur manche
                             to-play-1 (drop 4 deck1) (add-to-deck played-cards-1 card1 d11 d12 d13)
                             to-play-2 (drop 4 deck2) (add-to-deck played-cards-2 card2 d21 d22 d23)))))

(defn round [manche
             [card1 & deck1]
             [card2 & deck2]]
  ; (println "-------")
  ; (println 'manche manche)
  ; (println 'play card1 'stay deck1)
  ; (println 'play card2 'stay deck2)

  (cond

    (not card1) [2 (dec manche)]
    (not card2) [1 (dec manche)]

    ; BATAILLE
    (= card1 card2) (bataille manche
                              card1 deck1 []
                              card2 deck2 [])

    (win card1 card2) (do
                        ; (println 'j1 'win 'against 'j2)
                        (let [new-deck-1 (add-to-deck deck1 card1 card2)]
                          (recur (inc manche) new-deck-1 deck2)))

    (win card2 card1) (do
                        ; (println 'j2 'win 'against 'j1)
                        (let [new-deck-2 (add-to-deck deck2 card1 card2)]
                          (recur (inc manche) deck1 new-deck-2)))))

(defn -main [& args]
  (let [deck-j1 (prepare-deck (read-line))
        deck-j2 (prepare-deck (read-line))]
    ; (println 'j1 deck-j1)
    ; (println 'j2 deck-j2)
    (println (join " " (round 1 deck-j1 deck-j2)))))
