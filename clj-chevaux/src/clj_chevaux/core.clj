(ns clj-chevaux.core
  (:gen-class))

; (ns Solution
;   (:gen-class))

(defn distance [alist]
  (Math/abs (- (first alist) (second alist))))

(defn result
  ([alist] (result (distance alist) (rest alist)))
  ([min-ecart alist]
  (if (= (count alist) 1)
   min-ecart
   (result (min min-ecart (distance alist)) (rest alist)))))

(defn -main [& args]
  ; (let [N (read)]
  ; (let [Pi (repeatedly N read)]
  (let [Pi (apply sorted-set (repeatedly 10 #(int (rand 300))))]
    (println Pi)
    (println (result Pi))
    ))
