(ns Solution
  (:gen-class))

(defn to-chuck
  [str-1-or-0]
  (let [only-0 (clojure.string/replace str-1-or-0 #"1" "0")]
    (if (= \1 (first str-1-or-0))
      (str "0" " " only-0)
      (str "00" " " only-0))))

(defn -main [& args]
  (println '(r sr re rs))
  (let [MESSAGE (read-line)
        ascii (map int MESSAGE)
        str-binary (apply str (map #(Integer/toBinaryString %) ascii))
        seq-binary (re-seq #"0+|1+" str-binary)
        ]
    (println (clojure.string/join " " (map to-chuck seq-binary)))
    ))
