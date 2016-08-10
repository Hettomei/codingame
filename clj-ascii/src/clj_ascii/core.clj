(ns clj-ascii.core
  (:gen-class))

; (ns Solution
;   (:gen-class))

; # # ### ### # # ### ### ### ### ###
; ###   # # # # #   #  #   #    # # #
; ###  ## # # ###  ##  #   #   ## # #
; # #     # # # #      #   #      # #
; # #  #  # # # #  #   #   #   #  # #

(defn clean [str]
  (clojure.string/replace (clojure.string/upper-case str) #"[^A-Z]" "?"))

(defn find-letter [matrix row width letter]
  (let [pos (- (int letter) 65) pos-in (* width (if (= -2 pos) 26 pos ))]
    (println pos)
    (println pos-in)
    (println "row " row)
    (println (take row matrix))
    (subs (apply str (take row matrix)) 4 5)))

(defn ascii [str-string start-positions width]
  (println (subs str-string (first start-positions) (+ width (first start-positions)))))

(defn ascii [str-string start-positions width]
  (println (clojure.string/join (map #(subs str-string % (+ width %)) start-positions))))

(defn -main [& args]
  (let [width (read) height (read) _ (read-line) word (read-line) matrix (repeatedly height read-line)
        position (map #(if (= % -2) 26 %) (map #(- (int %) 65) (seq (char-array (clean word)))))
        position-width (map #(* % width) position)]
    (println "word " word)
    (println "better word " (clean word))
    (println "position " position)
    (println "position 2 " position-width)
    (dotimes [n height] (println (nth matrix n)))
    (dotimes [n height] (ascii (nth matrix n) position-width width))
  ))
