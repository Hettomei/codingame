(ns clj-the-labyrinth.core-test
  (:require [clojure.test :refer :all]
            [clj-the-labyrinth.core :refer :all]))

(deftest test-previous-movement
  (is (= 'RIGHT (previous-movement nil)))
  (is (= 'RIGHT (previous-movement 'RIGHT)))
  (is (= 'RIGHT (previous-movement 'UP)))
  (is (= 'UP (previous-movement 'LEFT)))
  (is (= 'LEFT (previous-movement 'BOTTOM))))

(deftest test-after-movement
  (is (= 'UP (after-movement 'RIGHT)))
  (is (= 'LEFT (after-movement 'UP)))
  (is (= 'BOTTOM    (after-movement 'LEFT)))
  (is (= nil  (after-movement 'BOTTOM))))

(deftest test-find-2d-string
  (is (= '(0 0) (find-2d-string \T '("T."))))
  (is (= '(1 2) (find-2d-string \T '("...." "..T." "....")))))

(deftest test-can-go?
  (testing "go right"
    (let [input '("...T.")]
      (is (= true (can-go? input 'RIGHT)))
      )
    )
  )

(deftest test-next-move
  (testing "go right"
    (let [input '("T.")]
      ; (is (= 'RIGHT (next-move input 'RIGHT)))
      )
    )
  )

