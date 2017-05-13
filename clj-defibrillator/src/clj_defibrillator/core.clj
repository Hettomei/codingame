(ns clj-defibrillator.core
  (:gen-class))

; (ns Solution
;   (:gen-class))
(require '[clojure.pprint :as pprint :refer [pprint print-table]])

(defn str-to-num [stra]
  (read-string (clojure.string/replace stra #"," "."))
  )

(defn distance [longA latA longB latB]
  (let [x (* (- longB longA) (Math/cos (/ (+ latA latB) 2)))
        y (- latB latA)]
    (+ (* x x) (* y y))
    ))

(defn -main [& args]
  (let [LON (str-to-num (read-line)) LAT (str-to-num (read-line)) N (str-to-num (read-line))]
    (let [DEFIB (repeatedly N #(clojure.string/split (read-line) #";"))
          DEFIB2 (map (fn[a][(second a) (str-to-num (get a 4)) (str-to-num (get a 5))]) DEFIB )]
      (let [DEFIB3 (map (fn[a] [ (first a) (distance LON LAT (second a) (last a)) ]) DEFIB2 )]
        (println (first (apply min-key last DEFIB3)))))))
