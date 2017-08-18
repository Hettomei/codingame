(ns clj-game-of-life.core
  (:gen-class))

; (ns Solution
;   (:gen-class))

(defn print-2d-array [coll]
  (println '-------)
  (run! prn coll)
  (println '-------))


(defn proof-position [& {:keys [x y coll] }]
  (prn 'x x 'y y '--> (nth (nth coll x) y)))

(defn build-array [acc current]
  (let [line (read-line)
        coll (map #(Integer/parseInt (str %)) line)]
    (conj acc coll)))

(defn haut-gauche [& {:keys [x y coll] }]
  (nth (nth coll (dec x) '()) (dec y) 0))

(defn haut-haut [& {:keys [x y coll] }]
  (nth (nth coll (dec x) '()) y 0))

(defn haut-droite [& {:keys [x y coll] }]
  (nth (nth coll (dec x) '()) (inc y) 0))

(defn gauche [& {:keys [x y coll] }]
  (nth (nth coll x '()) (dec y) 0))

(defn droite [& {:keys [x y coll] }]
  (nth (nth coll x '()) (inc y) 0))

(defn bas-gauche [& {:keys [x y coll] }]
  (nth (nth coll (inc x) '()) (dec y) 0))

(defn bas-bas [& {:keys [x y coll] }]
  (nth (nth coll (inc x) '()) y 0))

(defn bas-droite [& {:keys [x y coll] }]
  (nth (nth coll (inc x) '()) (inc y) 0))

(defn around-cell [& {:keys [x y coll] }]
  (list (haut-gauche :x x :y y :coll coll)
        (haut-haut   :x x :y y :coll coll)
        (haut-droite :x x :y y :coll coll)
        (gauche      :x x :y y :coll coll)
        (droite      :x x :y y :coll coll)
        (bas-gauche  :x x :y y :coll coll)
        (bas-bas     :x x :y y :coll coll)
        (bas-droite  :x x :y y :coll coll)))

(defn next-state [current arround]
  (let [sum (apply + arround)]
    )); pattern current sum)

(defn -main [& args]
  (let [width (read-line)
        height (Integer/parseInt (read-line))
        p-result (reduce build-array '() (range height))
        result (reverse p-result)]
    (prn 'width width 'height height )
    (print-2d-array result)

    (proof-position :x 0 :y 0 :coll result)
    (prn 'arround (around-cell :x 0 :y 0 :coll result)
         'next-state (next-state '() (around-cell :x 0 :y 0 :coll result)))

    (proof-position :x 0 :y 1 :coll result)
    (prn 'arround (around-cell :x 0 :y 1 :coll result)
         'next-state (next-state '() (around-cell :x 0 :y 1 :coll result)))
    )
  (println)
  (println)
  )
