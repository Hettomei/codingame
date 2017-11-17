(ns Player
  (:gen-class))

(defn abs [n] (max n (- n)))

(defn -main [& args]
  (while true
    (let [x (read)
          y (read)
          nextCheckpointX (read); nextCheckpointX: x position of the next check point
          nextCheckpointY (read); nextCheckpointY: y position of the next check point
          nextCheckpointDist (read); nextCheckpointDist: distance to the next checkpoint
          nextCheckpointAngle (read); nextCheckpointAngle: angle between your pod orientation and the direction of the next checkpoint
          opponentX (read)
          opponentY (read)
          thrust (if (> (abs nextCheckpointAngle) 60) 0 100)]

      ; (binding [*out* *err*]
      ;   (println "Debug messages..."))

      ; You have to output the target position
      ; followed by the power (0 <= thrust <= 100)
      ; i.e.: "x y thrust"
      (println nextCheckpointX " " nextCheckpointY " " thrust))))
