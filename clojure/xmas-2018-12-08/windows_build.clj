(ns Player
  (:use [clojure.string :only (split)])
  (:use clojure.pprint)
  (:gen-class))

; ppprint is only for codingame
; (defn ppprint [& arg]
;   (binding [*out* *err*]
; (doseq [i arg] (pprint i))
; (println "-----------")))

; (defn ppprint [& arg]
;   (doseq [i arg] (pprint i))
  ; (println "-----------"))

; doall is a workaround to ensure repeatedly + read-line have done all work
(defn build-area []
  (let [a (repeatedly 7 #(split (read-line) #" "))]
    (dorun 7 a)
    a))

;numPlayerCards, playerX, playerY, playerTile:
(defn fetch-player [line]
  (let [[numPlayerCards x y playerTile] (split line #" ")]
    {
     :restingQuests (Integer/parseInt numPlayerCards)
     :playerTile playerTile
     :x (Integer/parseInt x)
     :y (Integer/parseInt y)
     }))

(defn build-item [line]
  (let [[itemName x y playerId] (split line #" ")]
    {
     :itemName itemName
     :playerId (Integer/parseInt playerId)
     :x (Integer/parseInt x)
     :y (Integer/parseInt y)
     }))

; doall is a workaround to ensure repeatedly + read-line have done all work
(defn build-items [number]
  (let [a (repeatedly number #(build-item (read-line)))]
    (doall a)
    a))

(defn build-quest [line]
  (let [[itemName playerId] (split line #" ")]
    {
     :itemName itemName
     :playerId (Integer/parseInt playerId)
     }))

; doall is a workaround to ensure repeatedly + read-line have done all work
(defn build-quests [number]
  (let [a (repeatedly number #(build-quest (read-line)))]
    (doall a)
    a))

(defn push []
  ; (ppprint "PUSH")
  (let [area (build-area)
        player1 (fetch-player (read-line))
        player2 (fetch-player (read-line))
        items (build-items (Integer/parseInt (read-line)))
        resting-quests (build-quests (Integer/parseInt (read-line)))]

    ; (ppprint area)
    ; (ppprint "player1" player1)
    ; (ppprint "player2" player2)
    ; (ppprint "items" items)
    ; (ppprint "resting-quests" resting-quests)
  "PUSH 0 UP"
    )
    )

(defn move []
  "MOVE"
  "PUSH 0 UP")

(defn -main [& args]
  (let [all (repeatedly 16 read-line)]
    (doall all)
    (let [area (build-area all)])
  )))
  ; (while true
  ;   (println (if (= "1" (read-line)) (push) (move)))))
