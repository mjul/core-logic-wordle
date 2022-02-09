(ns core-logic-wordle.core-test
  (:require [clojure.test :refer :all]
            [core-logic-wordle.core :as sut]))

(deftest solve-test
  (testing "solve will return at least one five-letter word"
    (let [a (first (sut/solve))]
      (is (string? a))
      (is (= 5 (count a))))))
