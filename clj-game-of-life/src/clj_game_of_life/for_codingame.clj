(ns Solution
  (:gen-class))

(defn build-array [acc current]
  (let [line (read-line)
        cells (map #(Integer/parseInt (str %)) line)]
    (conj acc cells)))

(defn around-cell [x y cells]
  (letfn [(gett [i j]
            (nth (nth cells i '()) j 0))]
    (list
      (gett (dec x) (dec y))
      (gett (dec x)      y)
      (gett (dec x) (inc y))
      (gett      x  (dec y))
      (gett      x  (inc y))
      (gett (inc x) (dec y))
      (gett (inc x)      y)
      (gett (inc x) (inc y)))))

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
            (around-cell x y cells)))
        line))
    cells))

(defn -main [& args]
  (let [width (read)
        height (read)
        ; just to satisfy codingame input
        _ (read-line)
        p-result (reduce build-array '() (range height))
        result (reverse p-result)]
    (doseq [line (new-state result)]
      (println (apply str line)))))
