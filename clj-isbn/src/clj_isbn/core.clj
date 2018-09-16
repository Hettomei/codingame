(ns clj-isbn.core
  (:gen-class))

; (defn -main
;   "I don't do a whole lot ... yet."
;   [& args]
;   (println "Hello, World!"))

; (ns Solution
;   (:gen-class))

; Auto-generated code below aims at helping you parse
; the standard input according to the problem statement.

(defn is-valid-13 [isbn]
  true)

(defn is-valid-10 [isbn]
  false)

(defn valid [isbn]
  (let [n (count isbn)]
    (cond
      (= n 10) (is-valid-10 isbn)
      (= n 13) (is-valid-13 isbn)
      :else false)))

(defn -main [& args]
  (let [N (read)
        _ (read-line)
        isbns (repeatedly N read-line)
        invalids (filter (complement valid) isbns)]

    (println (count invalids) 'invalid)
    (doseq [i invalids] (println i))))
