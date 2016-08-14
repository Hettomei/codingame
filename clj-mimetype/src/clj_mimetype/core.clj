(ns clj-mimetype.core
  (:gen-class)
  (:require [clojure.string :as str]))


(defn -main
  [& args]
  (println "          ")
  (println "          ")
  (let [n-extension (read) n-file (read) _ (read-line)]
    (println n-extension n-file)
    (let [extensions (repeatedly n-extension read-line)
          map-ext (map #(str/split % #" ") extensions)
          down (map (fn [a] [(str/lower-case (first a)) (last a)]) map-ext)
          ]
      (prn extensions)
      (prn map-ext)
      (prn down)
      (println "-------------")
      (let [files (repeatedly n-file read-line)
            files-low (map (fn [f](str/lower-case f)) files)
            files-ext (map (fn [f](str/split f #"\.")) files-low)
            ]
        (prn files)
        (prn files-low)
        (prn files-ext)
        )
      )
    )
  (println 'fin))
