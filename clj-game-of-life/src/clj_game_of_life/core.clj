(ns clj-game-of-life.core
  (:gen-class))

; (ns Solution
;   (:gen-class))

(defn print-2d-array [array]
  (println '-------)
  (run! prn array)
  (println '-------))


(defn proof-position [& {:keys [x y cells] }]
  (prn 'x x 'y y '--> (nth (nth cells x) y)))

(defn build-array [acc current]
  (let [line (read-line)
        cells (map #(Integer/parseInt (str %)) line)]
    (conj acc cells)))

(defn haut-gauche [& {:keys [x y cells] }]
  (nth (nth cells (dec x) '()) (dec y) 0))

(defn haut-haut [& {:keys [x y cells] }]
  (nth (nth cells (dec x) '()) y 0))

(defn haut-droite [& {:keys [x y cells] }]
  (nth (nth cells (dec x) '()) (inc y) 0))

(defn gauche [& {:keys [x y cells] }]
  (nth (nth cells x '()) (dec y) 0))

(defn droite [& {:keys [x y cells] }]
  (nth (nth cells x '()) (inc y) 0))

(defn bas-gauche [& {:keys [x y cells] }]
  (nth (nth cells (inc x) '()) (dec y) 0))

(defn bas-bas [& {:keys [x y cells] }]
  (nth (nth cells (inc x) '()) y 0))

(defn bas-droite [& {:keys [x y cells] }]
  (nth (nth cells (inc x) '()) (inc y) 0))

(defn around-cell [& {:keys [x y cells] }]
  (list (haut-gauche :x x :y y :cells cells)
        (haut-haut   :x x :y y :cells cells)
        (haut-droite :x x :y y :cells cells)
        (gauche      :x x :y y :cells cells)
        (droite      :x x :y y :cells cells)
        (bas-gauche  :x x :y y :cells cells)
        (bas-bas     :x x :y y :cells cells)
        (bas-droite  :x x :y y :cells cells)))

(defn state-when-alive [nb-cell]
    (if (or (= 2 nb-cell) (= 3 nb-cell)) 1 0))

(defn state-when-dead [nb-cell]
  (if (= 3 nb-cell) 1 0))

(defn next-state [current arround]
  (let [sum (apply + arround)]
    (if (= 1 current)
      (state-when-alive sum)
      (state-when-dead sum))))

(defn new-state [cells]
  (map-indexed
    (fn [x line]
      (map-indexed
        (fn [y cell]
          (next-state
            (nth (nth cells x) y)
            (around-cell :x x :y y :cells cells)))
        line))
    cells))

(defn -main [& args]
  (let [width (read-line)
        height (Integer/parseInt (read-line))
        p-result (reduce build-array '() (range height))
        result (reverse p-result)]
    (prn 'width width 'height height )
    (print-2d-array result)

    (proof-position :x 0 :y 0 :cells result)
    (prn 'arround (around-cell :x 0 :y 0 :cells result)
         'next-state (next-state (nth (nth result 0) 0) (around-cell :x 0 :y 0 :cells result)))

    (proof-position :x 0 :y 1 :cells result)
    (prn 'arround (around-cell :x 0 :y 1 :cells result)
         'next-state (next-state (nth (nth result 0) 1) (around-cell :x 0 :y 1 :cells result)))

    (proof-position :x 1 :y 1 :cells result)
    (prn 'arround (around-cell :x 1 :y 1 :cells result)
         'next-state (next-state (nth (nth result 1) 1) (around-cell :x 1 :y 1 :cells result)))

    (print-2d-array (new-state result))
    (run! #(println (apply str %)) (new-state result)))
  (println)
  (println))
