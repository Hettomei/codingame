(ns clj-game-of-life.core-test
  (:require [clojure.test :refer :all]
            [clj-game-of-life.core :refer :all]))

(deftest arround-cell1
  (testing "around-cell"
    (let [input '((1))]
      (is (= '(0 0 0 0 0 0 0 0) (around-cell 0 0 input))))
    (let [input '((1 2 3)
                  (4 5 6)
                  (7 8 9))]
      (is (= '(0 1 2 0 5 0 7 8) (around-cell 1 0 input)))
      (is (= '(1 2 3 4 6 7 8 9) (around-cell 1 1 input))))
    ))

(deftest new-state-1
  (testing "new-state"
    (testing "simple form"
      (is (= '((0)) (new-state '((1)))))
      (is (= '((0)) (new-state '((0))))))

    (testing "some forms"
      (let [input '((1 1 1)
                    (0 0 0)
                    (0 0 0))
            expected '((0 1 0)
                       (0 1 0)
                       (0 0 0))]
        (is (= expected (new-state input)))))

    (testing "oscillators"
      (let [input '((0 0 0)
                    (1 1 1)
                    (0 0 0))
            expected '((0 1 0)
                       (0 1 0)
                       (0 1 0))]
        (is (= expected (new-state input))))

      (let [input '((0 1 0)
                    (0 1 0)
                    (0 1 0))
            expected '((0 0 0)
                       (1 1 1)
                       (0 0 0))]
        (is (= expected (new-state input))))

      (let [input '((0 0 0 0)
                    (0 1 1 1)
                    (1 1 1 0)
                    (0 0 0 0))
            expected '((0 0 1 0)
                       (1 0 0 1)
                       (1 0 0 1)
                       (0 1 0 0))]
        (is (= expected (new-state input))))

      (let [input '((0 0 1 0)
                    (1 0 0 1)
                    (1 0 0 1)
                    (0 1 0 0))
            expected '((0 0 0 0)
                       (0 1 1 1)
                       (1 1 1 0)
                       (0 0 0 0))]
        (is (= expected (new-state input)))))
    (testing "spaceships oscillator"
      (let [input '((0 1 0 0 0)
                    (0 0 1 0 0)
                    (1 1 1 0 0)
                    (0 0 0 0 0)
                    (0 0 0 0 0))

            step1 '((0 0 0 0 0)
                    (1 0 1 0 0)
                    (0 1 1 0 0)
                    (0 1 0 0 0)
                    (0 0 0 0 0))

            step2 '((0 0 0 0 0)
                    (0 0 1 0 0)
                    (1 0 1 0 0)
                    (0 1 1 0 0)
                    (0 0 0 0 0))

            step3 '((0 0 0 0 0)
                    (0 1 0 0 0)
                    (0 0 1 1 0)
                    (0 1 1 0 0)
                    (0 0 0 0 0))

            step4 '((0 0 0 0 0)
                    (0 0 1 0 0)
                    (0 0 0 1 0)
                    (0 1 1 1 0)
                    (0 0 0 0 0))

            step5 '((0 0 0 0 0)
                    (0 0 0 0 0)
                    (0 1 0 1 0)
                    (0 0 1 1 0)
                    (0 0 1 0 0))

            step6 '((0 0 0 0 0)
                    (0 0 0 0 0)
                    (0 0 0 1 0)
                    (0 1 0 1 0)
                    (0 0 1 1 0))

            step7 '((0 0 0 0 0)
                    (0 0 0 0 0)
                    (0 0 1 0 0)
                    (0 0 0 1 1)
                    (0 0 1 1 0))

            step8 '((0 0 0 0 0)
                    (0 0 0 0 0)
                    (0 0 0 1 0)
                    (0 0 0 0 1)
                    (0 0 1 1 1))]
        (is (= step1 ((apply comp (repeat 1 new-state)) input)))
        (is (= step2 ((apply comp (repeat 2 new-state)) input)))
        (is (= step3 ((apply comp (repeat 3 new-state)) input)))
        (is (= step4 ((apply comp (repeat 4 new-state)) input)))
        (is (= step5 ((apply comp (repeat 5 new-state)) input)))
        (is (= step6 ((apply comp (repeat 6 new-state)) input)))
        (is (= step7 ((apply comp (repeat 7 new-state)) input)))
        (is (= step8 ((apply comp (repeat 8 new-state)) input)))
        ))))
