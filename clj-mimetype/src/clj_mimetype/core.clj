(ns clj-mimetype.core
  (:gen-class))

(defn -main
  [& args]
  (println "          ")
  (println "          ")
  (let [n-extension (read) n-file (read) _ (read-line)]
    (println n-extension n-file)
    (let [extensions (repeatedly n-extension read-line)
          map-ext (map #(clojure.string/split % #" ") extensions)
          down (map (fn [a] [(clojure.string/lower-case (first a)) (last a)]) map-ext)
          ]
      (prn extensions)
      (prn map-ext)
      (prn down)
      (println "-------------")
      (let [files (repeatedly n-file read-line)
            files-low (map (fn [f](clojure.string/lower-case f)) files)
            files-ext (map (fn [f](clojure.string/split f #".")) files-low)
            ]
        (prn files)
        (prn files-low)
        (prn files-ext)
        )
      )
    )
  (println 'fin))
