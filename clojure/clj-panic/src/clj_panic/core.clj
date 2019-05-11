(ns clj-panic.core
  (:gen-class))

(defn p [& mess]
  (binding [*out* *err*]
    (apply prn mess)))
; Auto-generated code below aims at helping you parse
; the standard input according to the problem statement.

(defn create-exits [exitFloor exitPos elevators]
  (let [map-exit {:floor exitFloor :pos exitPos}
        map-elevators (map #(zipmap '(:floor :pos) %) (partition 2 elevators))]
    (set (list* map-exit map-elevators))))

(defn find-exit [floor coll]
  (first (filter #(= (:floor %) floor) coll)))

; action: WAIT or BLOCK
(defn action [direction next-exit clonePos]
  (if (or
        (and (= direction 'RIGHT) (>= (:pos next-exit) clonePos))
        (and (= direction 'LEFT) (<= (:pos next-exit) clonePos)))
    'WAIT
    'BLOCK))

(defn -main [& args]
  (let [nbFloors (read) ; unused
        width (read) ; unused
        nbRounds (read) ; unused
        exitFloor (read)
        exitPos (read)
        nbTotalClones (read) ; unused
        nbAdditionalElevators (read) ; unused
        nbElevators (read)
        ; nbElevators lignes suivantes : un couple d'entiers elevatorFloor elevatorPos donnant respectivement l'étage et la position d'un ascenseur.
        exits (create-exits exitFloor exitPos (repeatedly (* 2 nbElevators) read))]
    (while true
      (let [cloneFloor (read) ; floor of the leading clone
            clonePos (read) ; position of the leading clone on its floor
            direction (read) ; direction of the leading clone: LEFT or RIGHT
            next-exit (find-exit cloneFloor exits)]
        (println (action direction next-exit clonePos))))))
