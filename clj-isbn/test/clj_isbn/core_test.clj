(ns clj-isbn.core-test
  (:require [clojure.test :refer :all]
            [clj-isbn.core :refer :all]))

(deftest valid-test
  (testing "valid"
    (is (= (valid "9780306406157") true))
    (is (= (valid "9780470124512") true))
    (is (= (valid "9781118737637") true))
    (is (= (valid "9781548751555") true))
    (is (= (valid "0306406152") true))
    (is (= (valid "0470371722") true))
    (is (= (valid "1118105354") true))
    (is (= (valid "0387372350") true))))

(deftest invalid-test
  (testing "invalid"
    (is (= (valid "12345678910") false))
    (is (= (valid "123456789") false))
    (is (= (valid "aa") false))
    (is (= (valid "1145185215X") false))
    (is (= (valid "9780306406154") false))
    (is (= (valid "9781119247792") false))
    (is (= (valid "11190495550") false))
    (is (= (valid "978193981677") false))
    (is (= (valid "154875155X") false))
    (is (= (valid "978043551907X") false))))
