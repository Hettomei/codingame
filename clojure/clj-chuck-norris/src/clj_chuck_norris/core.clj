(ns clj-chuck-norris.core
  (:gen-class))

; Prenons un exemple simple avec un message constitué d'un seul caractère :
; C majuscule.
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

(defn convert-to-zero-and-space [acc current]
  (str
    acc
    (if (= \1 (first current)) "0" "00")
    " "
    (apply str (repeat (count current) "0"))
    " "))

(defn force-7-char [string]
  (let [new-str (str "0000000" string)
        c (count new-str)]
    (subs new-str (- c 7) c)))

(defn chuck-encode [message]
  (let [only-int (map int message)
        array-bin (map #(force-7-char (Integer/toBinaryString %)) only-int)
        full-str-bin (apply str (map force-7-char array-bin))
        group-msg (partition-by identity full-str-bin)
        only-zero (reduce convert-to-zero-and-space "" group-msg)
        final (clojure.string/trim only-zero)
        ]
    final))

(defn -main [& args]
  (let [message (read-line)]
    (println (chuck-encode message))))
