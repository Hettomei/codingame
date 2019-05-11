(ns clj-mimetype.core
  (:gen-class)
  (:require [clojure.string :as str]))

(defn array-to-low [array]
  (map #(str/lower-case %) array))

(defn array-to-split [array]
  (map #(str/split % #"\.") array))

(defn array-to-ext-hash [array]
  (let [map-ext (map #(str/split % #" ") array)
        down (map (fn [a] [(str/lower-case (first a)) (last a)]) map-ext)]
    (into {} down)))

(defn -main
  [& args]
  (let [n-extension (read) n-file (read) _ (read-line)
        extensions (repeatedly n-extension read-line)
        ext-to-mime (array-to-ext-hash extensions) ]
    (let [files (repeatedly n-file read-line)
          files-low (array-to-low files)
          files-replace (map (fn [a] (str/replace a #"\.$" "-")) files-low)
          files-ext (array-to-split files-replace)
          only-ext (map #(last (rest %)) files-ext)]
      (println (str/join "\n" (map #(ext-to-mime % "UNKNOWN") only-ext)))
      )))
