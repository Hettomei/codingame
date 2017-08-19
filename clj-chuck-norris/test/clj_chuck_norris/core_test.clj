(ns clj-chuck-norris.core-test
  (:require [clojure.test :refer :all]
            [clj-chuck-norris.core :refer :all]))

(deftest a-test
  (testing "Full test"
    (is (= "0 0 00 0000 0 00" (chuck-encode "C")))
    (is (= "0 0 00 0000 0 000 00 0000 0 00" (chuck-encode "CC")))
    (is (= "00 0 0 0 00 00 0 0 00 0 0 0" (chuck-encode "%")))
    ))

(deftest a-test-2
  (testing "force-7-char"
    (is (= "0000001" (force-7-char "1")))
    (is (= "0000011" (force-7-char "11")))
    (is (= "0111111" (force-7-char "111111")))
    (is (= "1111111" (force-7-char "1111111")))
    ))
