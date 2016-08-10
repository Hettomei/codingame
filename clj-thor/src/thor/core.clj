(ns thor.core
  (:gen-class))
; (ns Player
;   (:gen-class))

(defn updown [init, final]
  (cond
    (< init final) "S"
    (> init final) "N"))

(defn calc [init, final]
  (cond
    (< init final) (inc init)
    (> init final) (dec init)
    :else init))

(defn leftright [init, final]
  (Thread/sleep 100)
  (cond
    (< init final) "E"
    (> init final) "W"))

(defn all [inity finaly initx finalx]
  (println (str (updown inity finaly) (leftright initx finalx)))
  (all (calc inity finaly) finaly (calc initx finalx) finalx))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]

  ; Thor position = (5,4). Light position = (31,4). Energy = 100

  ; (let [lightX (read) lightY (read) initialTX (read) initialTY (read)]
  (let [lightX 31 lightY 4 initialTX 5 initialTY 4]
    ; (read)
    (all initialTY lightY initialTX lightX)))
