(ns Player
  (:gen-class))

; Help the Christmas elves fetch presents in a magical labyrinth!

; (binding [*out* *err*]
;   (println "Debug messages..."))


(defn -main [& args]
  (while true
    (let [turnType (read)]
      (loop [i 7]
        (when (> i 0)
          (loop [j 7]
            (when (> j 0)
              (let [tile (read)]
              (recur (dec j)))))
        (recur (dec i))))
    (loop [i 2]
      (when (> i 0)
        (let [numPlayerCards (read) playerX (read) playerY (read) playerTile (read)]
          ; numPlayerCards: the total number of quests for a player (hidden and revealed)
        (recur (dec i)))))
    (let [numItems (read)]
      ; numItems: the total number of items available on board and on player tiles
      (loop [i numItems]
        (when (> i 0)
          (let [itemName (read) itemX (read) itemY (read) itemPlayerId (read)]
          (recur (dec i)))))
      (let [numQuests (read)]
        ; numQuests: the total number of revealed quests for both players
        (loop [i numQuests]
          (when (> i 0)
            (let [questItemName (read) questPlayerId (read)]
            (recur (dec i)))))

        ; PUSH <id> <direction> | MOVE <direction> | PASS
        (println "PUSH 3 RIGHT"))))))
