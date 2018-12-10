(ns xmas.core
  (:use [clojure.string :only (split)])
  (:use clojure.pprint)
  (:gen-class))

; ppprint is only for codingame
; (defn ppprint [& arg]
;   (binding [*out* *err*]
; (doseq [i arg] (pprint i))
; (println "-----------")))

(defn ppprint [& arg]
  (doseq [i arg] (pprint i))
  (println "-----------"))

(defn build-area []
  (into [] (repeatedly 7 (fn [] (split (read-line) #" ")))))

;numPlayerCards, playerX, playerY, playerTile:
(defn fetch-player []
  (let [b (read-line)
        a (split b #" ")
        [numPlayerCards x y playerTile] a]
    {
     :restingQuests (Integer/parseInt numPlayerCards)
     :playerTile playerTile
     :x (Integer/parseInt x)
     :y (Integer/parseInt y)
     }))

(defn build-item []
  (let [b (read-line)
        a (split b #" ")
        [itemName x y playerId] a]
    {
     :itemName itemName
     :playerId (Integer/parseInt playerId)
     :x (Integer/parseInt x)
     :y (Integer/parseInt y)
     }))

(defn build-items [number]
  (repeatedly number build-item))

(defn build-quest []
  (let [b (read-line)
        a (split b #" ")
        [itemName playerId] a]
    {
     :itemName itemName
     :playerId playerId
     }))

(defn build-quests [number]
  (ppprint number)
  (repeatedly number build-quest))

(defn push []
  (pprint "PUSH")
  (let [area (build-area)
        player1 (fetch-player)
        player2 (fetch-player)
        items (build-items (Integer/parseInt (read-line)))
        ; restingQuests (build-quests (Integer/parseInt (read-line)))
        ]

    (ppprint area)
    (ppprint "player1" player1)
    (ppprint "player2" player2)
    (ppprint "items" items)
    (ppprint "items a" (Integer/parseInt (read-line)))
    (doseq [x [1 2]] (ppprint (read-line)))
    ; (ppprint "restingQuests" restingQuests)
    )
  (System/exit 0))

(defn move []
  "MOVE"
  "PUSH 0 UP"
  (System/exit 0))

(defn -main [& args]
  (while true
    (println (if (= "0" (read-line)) (push) (move)))))
