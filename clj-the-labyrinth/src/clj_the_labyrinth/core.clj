(ns clj-the-labyrinth.core
  (:gen-class))

(defn previous-movement [previous]
  (get {nil 'RIGHT
        'RIGHT 'RIGHT
        'UP 'RIGHT
        'LEFT 'UP
        'BOTTOM 'LEFT} previous)
  )

(defn after-movement [mov]
  (get {'RIGHT 'UP
        'UP 'LEFT
        'LEFT 'BOTTOM} mov)
  )

(defn indices-of [f coll]
  (keep-indexed #(if (f %2) %1 nil) coll))

(defn pos-in-str [line a-char]
  (first (indices-of (partial = a-char) line)))

(defn find-2d-string [a-char lab-2d]
  (reduce
    (fn [idx line]
      (let [pos (pos-in-str line a-char)]
        (if pos
          (reduced (list idx pos))
          (inc idx))
        ))
    0
    lab-2d))

(defn can-go? [lab mov]
  ; find T
  ; then find next point (up down ...)
  ; return next point is a '.'
  (let [T (find-2d-string \T lab)]
    T))

(defn next-move [labyrinth move-to]
  (if (can-go? labyrinth move-to)
    move-to
    (next-move labyrinth (after-movement move-to))
    ))

(defn -main [& args]
  (next-move '(("####..T..#")) (previous-movement nil)))
