; (ns clj-the-labyrinth2.core
;   (:gen-class))
(ns Player
  (:gen-class))

(defn p [& args]
  (binding [*out* *err*]
    (println args)))

(defn mmap [row_n]
  (loop [i rows_n]
    (when (> i 0)
      (let [ROW (read)]
        (p (str ROW))
        ; ROW: column of the characters in '#.TC?' (i.e. one line of the ASCII maze).
        (recur (dec i))))))

(defn -main [& args]
  (let [rows_n (read) column (read) A (read)]
    ; R: number of rows.
    ; C: number of columns.
    ; A: number of rounds between the time the alarm countdown is activated and the time the alarm goes off.
    (while true
      (let [start_row (read)
            start_column (read)
            mmap (build-mmap rows_n)]
        ; KR: row where Kirk is located.
        ; KC: column where Kirk is located.
        (p mmap)


        ; Kirk's next move (UP DOWN LEFT or RIGHT).
        (println "RIGHT")))))
