(ns clj-panic.core-test
  (:require [clojure.test :refer :all]
            [clj-panic.core :refer :all]))

(deftest test-create-exits
  (testing "It create a set of map"
    (is (= #{{:floor 5 :pos 19}} (create-exits 5 19 '())))

    (is (= #{{:floor 3 :pos 22} {:floor 5 :pos 19}} (create-exits 5 19 '(3 22))))

    (is (= #{{:floor 0 :pos 17}
             {:floor 1 :pos 12}
             {:floor 2 :pos 10}
             {:floor 5 :pos 19}} (create-exits 5 19 '(2 10 0 17 1 12))))
    ))

(deftest test-find-exit
  (testing "return a map"
    (is (= nil (find-exit 5 #{{:floor 3 :pos 2}})))
    (is (= {:floor 5 :pos 2} (find-exit 5 #{{:floor 3 :pos 2} {:floor 5 :pos 2}})))
    ))

(deftest test-action
  (testing "action when wait"
    (is (= 'WAIT (action 'RIGHT {:floor 4 :pos 12} 12)))
    (is (= 'WAIT (action 'RIGHT {:floor 4 :pos 12} 11)))
    (is (= 'WAIT (action 'LEFT {:floor 4 :pos 4} 4)))
    (is (= 'WAIT (action 'LEFT {:floor 4 :pos 4} 5))))
  (testing "action when Block"
    (is (= 'BLOCK (action 'RIGHT {:floor 4 :pos 12} 13)))
    (is (= 'BLOCK (action 'LEFT {:floor 4 :pos 4} 3)))))
