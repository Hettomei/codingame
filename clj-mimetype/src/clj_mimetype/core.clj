(ns clj-mimetype.core
  (:gen-class))

(def a '(4
         7
         png image/png
         TIFF image/TIFF
         css text/css
         TXT text/plain
         example.TXT
         referecnce.txt
         strangename.tiff
         resolv.CSS
         matrix.TiFF
         lanDsCape.Png
         extract.cSs))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "write")
  (let [n (read) extension (repeatedly n (fn []([(read) (read)])))]
    (let [clean-ext (map [] extension)]
    (println extension)))
    (println 'fin))
