(ns Solution
  (:gen-class))

(defn p [& mess]
  (binding [*out* *err*]
    (prn mess)))

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

(defn codin-output [cells]
  (println
    (apply str
           (interpose
             "\n"
             (map #(apply str %) cells)))))

(defn -main [& args]
  (let [width (read)
        height (read)
        ; just to satisfy codingame input
        _ (read-line)
        p-result (reduce build-array '() (range height))
        result (reverse p-result)]
    (doseq [line (new-state result)]
      (println (apply str line)))))
    ; (codin-output (new-state result))))
