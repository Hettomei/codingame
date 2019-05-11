(ns clj-create-puzzle-puzzle.core
  (:gen-class))

(defn compute [current-level xp-needed point-a-depenser]
  (let [next-level (inc current-level)]
    (if
      (<= xp-needed point-a-depenser)
      (compute
        next-level
        (int (* 10 next-level (Math/sqrt next-level)))
        (- point-a-depenser xp-needed))
      (list current-level (- xp-needed point-a-depenser)))))

(defn -main [& args]
  (let [current-level (read) xp-needed (read) n (read)]
    (println (clojure.string/join "\n"
                                  (compute current-level xp-needed (* n 300))))))
