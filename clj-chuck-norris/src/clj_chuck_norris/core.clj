(ns clj-chuck-norris.core
  (:gen-class))

; Prenons un exemple simple avec un message constitué d'un seul caractère : C majuscule.
; C en binaire vaut 1000011 ce qui donne avec la technique de Chuck Norris :

; 0 0 (la première série composée d'un seul 1)
; 00 0000 (la deuxième série composée de quatre 0)
; 0 00 (la troisième série composée de deux 1)

; C vaut donc : 0 0 00 0000 0 00


; Deuxième exemple, nous voulons encoder le message CC (soit les 14 bits 10000111000011) :

;     0 0 (un seul 1)
;     00 0000 (quatre 0)
;     0 000 (trois 1)
;     00 0000 (quatre 0)
;     0 00 (deux 1)

; CC vaut donc : 0 0 00 0000 0 000 00 0000 0 00

(defn to-chuck
  [str-1-or-0]
  (let [only-0 (clojure.string/replace str-1-or-0 #"1" "0")]
    (if (= \1 (first str-1-or-0))
      (str "0" " " only-0)
      (str "00" " " only-0))))

(defn to-7-bit
  [ascii]
  (let [str-bin (Integer/toBinaryString ascii)]
    (if (count str-bin < 7
  )

(defn -main [& args]
  (let [MESSAGE (read-line)
        ascii (map int MESSAGE)
        str-binary (apply str (map to-7-bit ascii))
        seq-binary (re-seq #"0+|1+" str-binary)
        ]
    (prn str-binary)
    (println (clojure.string/join " " (map to-chuck seq-binary)))
    ))
