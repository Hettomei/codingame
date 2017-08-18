(ns clj-game-of-life.core
  (:gen-class))
; (use 'clojure.tools.trace)
; (trace-ns clj-game-of-life.core)

(defn str-to-arr [string])

(defn transform [listt]
   listt
  )

(defn start [width height & rest]
  (let [listt (map seq rest)]
    (println listt)
    ; (pprint (transform listt))
    (println "------------")
    )
  )


(defn flat [seqq]
  (letfn [
          (a-func
            ([b c] (conj b c))
            )
          ]
     (reduce a-func '() seqq)
    )
  )

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
   ; (start 4 4
   ;        "0000"
          ; "1111"
          ; "0000"
          ; "0000")
  (flat '((1) 2 3 (333)))
  )

(use 'clojure.tools.trace)
(trace-vars clj-game-of-life.core/flat)
