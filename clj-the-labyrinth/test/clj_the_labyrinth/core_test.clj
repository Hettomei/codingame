(ns clj-the-labyrinth.core-test
  (:require [clojure.test :refer :all]
            [clj-the-labyrinth.core :refer :all]))

(deftest test-previous-movement
  (is (= 'RIGHT (previous-movement nil)))
  (is (= 'RIGHT (previous-movement 'RIGHT)))
  (is (= 'RIGHT (previous-movement 'UP)))
  (is (= 'UP    (previous-movement 'LEFT)))
  (is (= 'LEFT  (previous-movement 'BOTTOM))))

(deftest test-after-movement
  (is (= 'UP     (after-movement 'RIGHT)))
  (is (= 'LEFT   (after-movement 'UP)))
  (is (= 'BOTTOM (after-movement 'LEFT)))
  (is (= 'FAIL   (after-movement 'BOTTOM))))

(deftest test-add-position
  (is (= '(4 6)     (add-position '(4 5) 'RIGHT)))
  (is (= '(1 0)   (add-position '(0 0) 'BOTTOM))))

(deftest test-find-2d-string
  (is (= '(0 0) (find-2d-string \T '("T."))))
  (is (= '(1 2) (find-2d-string \T '("...." "..T." "....")))))

(deftest test-next-place
  (let [input '(,".U."
                 "LTR"
                 ".B.") ]
    (is (= \U (next-place input 'UP)))
    (is (= \B (next-place input 'BOTTOM)))
    (is (= \L (next-place input 'LEFT)))
    (is (= \R (next-place input 'RIGHT)))))

(deftest test-get-place-in-lab
  (let [lab '(,".U."
               "LTR"
               ".B.") ]
    (is (= \.  (get-place-in-lab lab '(0 0))))
    (is (= \U  (get-place-in-lab lab '(0 1))))
    (is (= \B  (get-place-in-lab lab '(2 1))))
    (is (= nil (get-place-in-lab lab '(20 10))))
    ))

(deftest test-next-move
  (let [input '("T.")]
    (is (= 'RIGHT (next-move input 'RIGHT)))
    (is (= 'FAIL (next-move input 'FAIL)))
    (is (= 'FAIL (next-move input 'LEFT)))
    (is (= 'FAIL (next-move input 'UP)))
    (is (= 'FAIL (next-move input 'BOTTOM))))
  (let [input '( "..."
                 ".T."
                 "...")]
    (is (= 'UP     (next-move input 'UP)))
    (is (= 'BOTTOM (next-move input 'BOTTOM)))
    (is (= 'LEFT   (next-move input 'LEFT)))
    (is (= 'RIGHT  (next-move input 'RIGHT)))))
