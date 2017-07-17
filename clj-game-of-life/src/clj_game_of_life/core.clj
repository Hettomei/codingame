(ns clj-game-of-life.core
  (:gen-class))

(defn str-to-arr [string])

(defn transform [listt]
   listt
  )

(defn start [width height & rest]
  (let [listt (map seq rest)]
    (println listt)
    (pprint (transform listt))
    (println "------------")
    )
  )


(defn flat [seqq]
  (letfn [
          (a
            ([b] (str b))
            ([b & c] (str b "-" b "||" c))
            )
          ]
    (pr-str (apply a '(3 4 5)))
    )
  )

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
   (start 4 4
          "0000"
          "1111"
          "0000"
          "0000")
  ; (flat '((1) 2 3 (333)))
  )
