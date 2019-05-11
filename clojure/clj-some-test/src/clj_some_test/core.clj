(ns clj-some-test.core
  (:gen-class))

(require '[clojure.pprint :as pprint :refer [pprint print-table]])

(defn str-to-num [stra]
  (read-string (clojure.string/replace stra #"," ".")))

(defn distance [longA latA longB latB]
  (let [x (* (- longB longA) (Math/cos (/ (+ latA latB) 2)))
        y (- latB latA)]
    (+ (* x x) (* y y))))

(defn aaa [DEFIB line LON LAT]
  (->> DEFIB
       (map #(clojure.string/split % #";") ,,,)
       (map (fn[line] [ (second line) (str-to-num (get line 4)) (str-to-num (get line 5)) ] ) ,,, )
       (map (fn[line] [ (first line) (distance LON LAT (second line) (last line)) ]) ,,, )
       (apply min-key last ,,,)
       first))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [LON (str-to-num (read-line)) LAT (str-to-num (read-line)) N (str-to-num (read-line))]
    (let [ DEFIB (repeatedly N read-line) ]
      (pprint (->> DEFIB
                   (map #(clojure.string/split % #";") ,,,)
                   (map (fn[line] [ (second line) (str-to-num (get line 4)) (str-to-num (get line 5)) ] ) ,,, )
                   (map (fn[line] [ (first line) (distance LON LAT (second line) (last line)) ]) ,,, )
                   (apply min-key last ,,,)
                   first
                   )))))
