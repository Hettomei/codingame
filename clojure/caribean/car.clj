(ns Player
  (:gen-class))

(defn -main [& args]
  (while true
    (let [myShipCount (read) entityCount (read)]
      ; myShipCount : the number of remaining ships
      ; entityCount : the number of entities (e.g. ships, mines or cannonballs)
      (loop [i entityCount]
        (when (> i 0)
          (let [entityId (read) entityType (read) x (read) y (read) arg1 (read) arg2 (read) arg3 (read) arg4 (read)]
          (recur (dec i)))))
      (loop [i myShipCount]
        (when (> i 0)

          (binding [*out* *err*]
            (println "Debug messages..."))

          ; Any valid action, such as "WAIT" or "MOVE x y"
          (print "MOVE")
          (print " ")
          (print "11")
          (print " ")
          (print "10")
          (println "")
      (recur (dec i)))))))
