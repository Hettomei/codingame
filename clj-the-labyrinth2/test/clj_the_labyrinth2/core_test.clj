(ns clj-the-labyrinth2.core-test
  (:require [clojure.test :refer :all]
            [clj-the-labyrinth2.core :refer :all]))

(deftest test01
  (testing "can-see-commands?"
    (testing "when see commands"
      (is (= true (can-see-commands? '("aaa" "bbb" "eeC")))))
    (testing "when not see commands"
      (is (nil? (can-see-commands? '("aaa" "bbb" "eee")))))))
