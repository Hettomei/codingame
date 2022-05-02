(ns tangente-turing.core-test
  (:require [clojure.test :refer :all]
            [tangente-turing.core :refer :all]))

(deftest a-test
  (testing "full zero"
    (is (= (full-zero) '(0 0 0 40 0)))))
