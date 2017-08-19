(ns clj-create-puzzle-puzzle.core-test
  (:require [clojure.test :refer :all]
            [clj-create-puzzle-puzzle.core :refer :all]))

(deftest test-compute
  (testing "all"
    (is (= '(10 1) (compute 10 300 299)))
    (is (= '(10 300) (compute 10 300 0)))
    (is (= '(11 364) (compute 10 300 300)))
    (is (= '(11 287) (compute 1 10 1500)))
    (is (= '(28 883) (compute 23 250 (* 20 300))))
    (is (= '(27 628) (compute 27 928 (* 1 300))))
    (is (= '(30 1) (compute 30 1 (* 0 300))))
    ))
