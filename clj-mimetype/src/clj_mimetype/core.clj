(ns clj-mimetype.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn array-to-low [array]
  (map (fn [e](str/lower-case e)) array))

(defn array-to-split [array]
  (map (fn [f](str/split f #"\.")) array))

(defn array-to-ext-hash [array]
  (let [map-ext (map #(str/split % #" ") array)
        down (map (fn [a] [(str/lower-case (first a)) (last a)]) map-ext)
        ext-to-mime (into {} down)]
    ext-to-mime))

(defn -main
  [& args]
  (let [n-extension (read) n-file (read) _ (read-line)]
    (let [extensions (repeatedly n-extension read-line)
          ext-to-mime (array-to-ext-hash extensions) ]
      (let [files (repeatedly n-file read-line)
            files-low (array-to-low files)
            files-ext (array-to-split files-low)
            only-ext (map #(second %) files-ext)]
        (println (str/join "\n" (map #(ext-to-mime % "UNKNOWN") only-ext)))
        ))))
