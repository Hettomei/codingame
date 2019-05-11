; (ns Player
;   (:gen-class)
(ns clj-the-labyrinth2.core
  (:gen-class)
  (:require [clojure.string :as str]))

(def counter (atom ()))

(defn p [& args]
  (binding [*out* *err*]
    (apply prn args)))

(defn p-world [args]
  (run! p args))

(defn p-place [world kr kc]
  (p (nth (nth world kr) kc)))

(defn can-see-commands? [world]
  (some #(str/includes? % "C") world))

(defn reach-command [world]
  (p "reach-command"))

(defn find-command [world]
  (p "find-command"))

(defn toto [world]
  (swap! counter conj world)
  (prn @counter)
  true)

(defn -main [& args]
  (let [rows_n (read) _ (read) __ (read)]
    ; (while true
      (let [KR (read) ; row where Kirk is located.
            KC (read) ; column where Kirk is located.
            _  (read-line) ; needed to change line. Same on codingame ?
            world (repeatedly rows_n read-line)]
        (p KR KC)
        (p-world world)
        (p-place world KR KC)
        (if (can-see-commands? world)
            (reach-command world)
            (find-command world))
        ; Kirk's next move (UP DOWN LEFT or RIGHT).
        ; (println "RIGHT"))))
        ; )
        )))
