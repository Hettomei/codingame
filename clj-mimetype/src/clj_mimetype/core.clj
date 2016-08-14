(ns clj-mimetype.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn array-to-low [array]
  (map (fn [e](str/lower-case e)) array))

(defn array-to-split [array]
  (map (fn [f](str/split f #"\.")) array))

(defn -main
  [& args]
  (println "          ")
  (let [n-extension (read) n-file (read) _ (read-line)]
    (println n-extension n-file)
    (let [extensions (repeatedly n-extension read-line)
          map-ext (map #(str/split % #" ") extensions)
          down (map (fn [a] [(str/lower-case (first a)) (last a)]) map-ext)
          ext-to-mime (into {} down)
          ]
      (prn ext-to-mime)
      (println "-------------")
      (let [files (repeatedly n-file read-line)
            files-low (array-to-low files)
            files-ext (array-to-split files-low)
            only-ext (map #(second %) files-ext)]
        (prn only-ext)
        (println (str/join "\n" (map #(ext-to-mime % "UNKNOWN") only-ext)))
        ))))
