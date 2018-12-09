(ns Player
  (:gen-class))

; Help the Christmas elves fetch presents in a magical labyrinth!

(defn ppp [& arg]
  (binding [*out* *err*]
    (println arg)))


; (defn -main [& args]
;   (while true
;     (let [turnType (read)]
;       (ppp "turnType" (if (= 0 turnType) "PUSH" "MOVE") turnType)
;       (loop [i 7]
;         (when (> i 0)
;           (let [tiles (repeatedly 7 read)]
;             (ppp "tiles" tiles)
;             (recur (dec i))))
;         ))
;     (printnl "PUSH 3 UP")
;     ))

(defn -main [& args]
  (while true
    (let [a (read-line)]
      (ppp a)
      )))
