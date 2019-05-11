(ns clj-the-labyrinth.core
  (:gen-class))

; (ns Player
;   (:gen-class))

(defn previous-movement [previous]
  (get {nil 'RIGHT
        'RIGHT 'RIGHT
        'UP 'RIGHT
        'LEFT 'UP
        'DOWN 'LEFT} previous))

(defn after-movement [mov]
  (get {'RIGHT 'UP
        'UP 'LEFT
        'LEFT 'DOWN
        'DOWN 'FAIL} mov))

(defn add-position [position mov]
  (let [add (get {'UP     '(-1  0)
                  'DOWN '( 1  0)
                  'LEFT   '( 0 -1)
                  'RIGHT  '( 0  1)} mov)
        x (first position)
        y (last  position)]
    (list (+ x (first add)) (+ y (last add)))))

(defn indices-of [f coll]
  (keep-indexed #(if (f %2) %1 nil) coll))

(defn pos-in-str [line a-char]
  (first (indices-of (partial = a-char) line)))

(defn get-place-in-lab [lab x-y]
  (nth (nth lab (first x-y) '()) (second x-y) nil))

(defn next-place [lab mov pos]
  (get-place-in-lab lab (add-position pos mov)))

(defn next-move [labyrinth move-to pos]
  (cond
    (= 'FAIL move-to) move-to
    (= \. (next-place labyrinth move-to pos)) move-to
    :else (next-move labyrinth (after-movement move-to) pos)))


(defn start [R last-m]
  (let [pos (list (read) (read)) _ (read-line)
        ROWS (repeatedly R read-line)
        out (next-move ROWS (previous-movement last-m) pos)]
      (binding [*out* *err*]
         (println (clojure.string/join "\n" ROWS))
         (println pos)
         )
   (println out)
   (start R out)))

(defn -main [& args]
  (let [R (read) __ (read-line)]
    (start R nil)))
