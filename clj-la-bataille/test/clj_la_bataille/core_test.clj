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
    (is (= (add-to-deck ['a 'b 'c] 'd 'e) ['a 'b 'c 'd 'e]))))

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
           [2 56])))

  (testing "test04"
    (is (= (round
             1
             ["8" "K" "A" "Q" "2"]
             ["8" "2" "3" "4" "3"])
           [2 1])))

  (testing "test05"
    (is (= (round
             1
             ["10" "K" "6" "10" "8" "A" "Q" "3" "7" "K" "9" "2" "J" "K" "3" "2" "Q" "A" "J" "7" "K" "10" "4" "A" "5" "5"]
             ["2" "9" "8" "4" "5" "A" "J" "Q" "7" "5" "4" "6" "6" "Q" "9" "10" "4" "J" "6" "3" "8" "3" "7" "9" "8" "2"])
           [1 52])))

  (testing "test06"
    (is (= (round
             1
             ["8" "K" "A" "Q" "3" "K" "A" "Q" "6"]
             ["8" "2" "3" "4" "3" "2" "3" "4" "7"])
           [2 1])))

  (testing "test08"
    (is (= (round
             1
             ["5" "8" "10" "9" "4" "6" "Q" "6" "6" "9" "2" "7" "A" "5" "7" "9" "Q" "4" "3" "J" "2" "K" "10" "Q" "3" "8"]
             ["4" "J" "8" "10" "5" "7" "3" "A" "K" "10" "J" "6" "2" "K" "8" "9" "K" "3" "A" "J" "4" "7" "2" "Q" "5" "A"])
             ['PAT]))))
