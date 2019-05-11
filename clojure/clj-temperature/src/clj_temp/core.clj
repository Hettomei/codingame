(ns clj-temp.core
  (:gen-class))

; (ns Solution
;   (:gen-class))

; 10
; -5 -4 -2 12 -40 4 2 18 11 5

; 6
; 42 5 12 21 -5 24

; 0

(defn positive [default other]
  (if (= (Math/abs default) (Math/abs other)) (max default other) default)
  )

(defn a-or-b-close-to-zero
  [a b]
  (if (> (Math/abs a) (Math/abs b)) (positive b a) (positive a b)))


(defn close-zero
  ([best temps]
   (println "TEMP : " best " TEMPS :" temps)
   (if (empty? temps)
     (println best)
     (close-zero (a-or-b-close-to-zero best (first temps)) (rest temps)))
   ))

; (defn -main
; [& args]
; (println "Hello, World!"))
(def temps '(-5 -4 -2 12 -40 4 2 18 11 5))
(def temps '(42 5 12 21 -5 24))

(defn -main [& args]
  (println 10)
  (println temps)
  (println "--------")
  ; (let [n (read) _ (read-line) tot (read-string (str "(" (read-line) ")"))]
  (let [n 4 tot temps]
    (if (= n 0)
      (println "0")
      (close-zero (first tot) (rest tot)))))
