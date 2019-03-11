(ns clj-the-labyrinth2.core-test
  (:require [clojure.test :refer :all]
            [clj-the-labyrinth2.core :refer :all]))

(deftest test01
  (testing "can-see-commands?"
    (testing "when see commands"
      (is (= 0 1)))
    (testing "when not see commands"
      (is (= 0 1)))))
