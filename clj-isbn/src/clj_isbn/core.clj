(ns clj-isbn.core
  (:gen-class))
; (ns Solution
;   (:gen-class))


; ISBN-13 check digit is calculated by Modulus 10
; with alternate weights of 1 and 3 on the first 12 digits.

; Example: 978030640615?
; 9×1 + 7×3 + 8×1 + 0×3 + 3×1 + 0×3 + 6×1 + 4×3 + 0×1 + 6×3 + 1×1 + 5×3 = 93.
; 93 / 10 = 9 remainder 3.

; Check digit is the value needed to add to the sum
; to make it dividable by 10. So the check digit is 7.
; The valid ISBN is 9780306406157.
(defn is-valid-13 [isbn]
  (let [clean (apply str (re-seq #"[0-9]" isbn))
        n (count clean)]
    (cond
      (= n 13) true
      :else false)))

; ISBN-10 check digit is calculated by Modulus 11
; with decreasing weights on the first 9 digits.

; Example: 030640615?
; 0×10 + 3×9 + 0×8 + 6×7 + 4×6 + 0×5 + 6×4 + 1×3 + 5×2 = 130.

; 130 / 11 = 11 remainder 9.

; Check digit is the value needed to add to the sum to make it dividable
; by 11. In this case it is 2.

; So the valid ISBN is 0306406152.

; In case 10 being the value needed to add to the sum,
; we use X as the check digit instead of 10.
(defn is-valid-10 [isbn]
  (let [clean (apply str (re-seq #"[0-9]{9,10}X?$" isbn))
        n (count clean)]
    (cond
      (= n 10) true
      :else false)))

(defn valid [isbn]
  (let [n (count isbn)]
    (cond
      (= n 10) (is-valid-10 isbn)
      (= n 13) (is-valid-13 isbn)
      :else false)))

(defn -main [& args]
  (let [N        (read)
        _        (read-line)
        isbns    (repeatedly N read-line)
        valids   (filter valid isbns)
        invalids (filter (complement valid) isbns)]

    (println 'valids)
    (doseq [i valids] (println i))
    (println '--------)

    (println (count invalids) 'invalid)
    (doseq [i invalids] (println i))))
