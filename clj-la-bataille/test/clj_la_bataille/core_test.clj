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

(deftest final
  (testing "test01"
    (is (= (round 1 ["A" "K" "Q"] ["K" "Q" "J"]) [1 3])))

  (testing "test02"
    (is (= (round
             1
             ["5" "3" "2" "7" "8" "7" "5" "5" "6" "5" "4" "6" "6" "3" "3" "7" "4" "4" "7" "4" "2" "6" "8" "3" "2" "2"]
             ["A" "9" "K" "K" "K" "K" "10" "10" "9" "Q" "J" "10" "8" "Q" "J" "A" "J" "A" "Q" "A" "J" "10" "9" "8" "Q" "9"])
           [2 26])))

  (testing "test03"
    (is (= (round
             1
             ["6" "7" "6" "Q" "7" "8" "6" "5" "6" "Q" "4" "3" "7" "3" "4" "5" "Q" "5" "3" "3" "8" "4" "4" "Q" "5" "7"]
             ["J" "A" "K" "A" "9" "2" "2" "J" "10" "K" "10" "J" "J" "9" "9" "K" "A" "K" "10" "8" "2" "10" "8" "A" "2" "9"])
           [2 56]))))
