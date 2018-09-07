(ns clj-la-bataille.core-test
  (:require [clojure.test :refer :all]
            [clj-la-bataille.core :refer :all]))

(deftest win-test
  (testing "win"
    (is (= (win "A" "K") true))
    (is (= (win "A" "2") true))
    (is (= (win "K" "A") false))
    (is (= (win "2" "A") false))))

(deftest add-to-deck-test
  (testing "ok"
    (is (= (add-to-deck 'd 'e ['a 'b 'c]) ['a 'b 'c 'd 'e]))))

