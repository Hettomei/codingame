(ns clj-ascii.core
  (:gen-class))

; (ns Solution
;   (:gen-class))

(defn clean [str]
  (clojure.string/replace (clojure.string/upper-case str) #"[^A-Z]" "?"))

(defn ascii [str-string start-positions width]
  (println (clojure.string/join (map #(subs str-string % (+ width %)) start-positions))))

(defn -main [& args]
  (let [width (read)
        height (read)
        _ (read-line)
        word (read-line)
        matrix (repeatedly height read-line)
        position (map #(if (= % -2) 26 %) (map #(- (int %) 65) (seq (char-array (clean word)))))
        position-width (map #(* % width) position)]
    (dotimes [n height] (ascii (nth matrix n) position-width width))
  ))
