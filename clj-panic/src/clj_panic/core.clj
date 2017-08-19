(ns clj-panic.core
  (:gen-class))

(defn p [& mess]
  (binding [*out* *err*]
    (apply prn mess)))
; Auto-generated code below aims at helping you parse
; the standard input according to the problem statement.

(defn -main [& args]
  (let [nbFloors (read) ; unused
        width (read) ; unused
        nbRounds (read)
        exitFloor (read)
        exitPos (read)
        nbTotalClones (read)
        nbAdditionalElevators (read)
        nbElevators (read)]
    ; nbFloors: number of floors
    ; width: width of the area
    ; nbRounds: maximum number of rounds
    ; exitFloor: floor on which the exit is found
    ; exitPos: position of the exit on its floor
    ; nbTotalClones: number of generated clones
    ; nbAdditionalElevators: ignore (always zero)
    ; nbElevators: number of elevators
    (loop [i nbElevators]
      (when (> i 0)
        (let [elevatorFloor (read) elevatorPos (read)]
          ; elevatorFloor: floor on which this elevator is found
          ; elevatorPos: position of the elevator on its floor
          (recur (dec i)))))
    (while true
      (let [cloneFloor (read) clonePos (read) direction (read)]
        ; cloneFloor: floor of the leading clone
        ; clonePos: position of the leading clone on its floor
        ; direction: direction of the leading clone: LEFT or RIGHT


        ; action: WAIT or BLOCK
        (println "WAIT")))))
