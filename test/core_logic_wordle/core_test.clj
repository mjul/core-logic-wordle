(ns core-logic-wordle.core-test
  (:require [clojure.test :refer :all]
            [clojure.core.logic :as l]
            [core-logic-wordle.core :as sut]))

(deftest specific-solve-test
  (testing "specific-solve will return at least one five-letter word"
    (let [actual (sut/specific-solve)]
      (is (every? string? actual))
      (is (every? #(= 5 (count %)) actual))
      (is (some #{"HUMOR"} actual) "HUMOR is the known solution"))))

(deftest knowno-test
  (testing "known binding"
    (testing "when the value is known (non-nil), knowno must bind the value"
      (let [actual (l/run* [q]
                           (l/fresh [a]
                                    (sut/knowno a "A")
                                    (l/== q a)))]
        (is (= ["A"] actual))))
    (testing "when the value is unknown known (nil), knowno must not bind the value"
      (let [actual (l/run* [q]
                           (l/fresh [a]
                                    (sut/knowno a nil)
                                    ;; this can only succeed if a is not ground by the above
                                    (l/== a :not-known)
                                    (l/== q a)))]
        (is (= [:not-known] actual))))))

(deftest simple-solve-test
  (testing "test solving a specific problem"
    (testing "sanity checks inputs"
      (testing "non-letters may not be in known list"
        (is (thrown? AssertionError (sut/simple-solve "A" "A...." ["" "" "" "" ""]))))
      (testing "non-letters may not be in not-in-this-position list"
        (is (thrown? AssertionError (sut/simple-solve "A" "....." ["" "A" "" "" ""])))))
    (testing "where every letter and position is known"
      (testing "and nothing else is known"
        (let [actual (sut/simple-solve "" "THINK" ["" "" "" "" ""])]
          (is (= 1 (count actual)))
          (is (= "THINK" (first actual)))))
      (testing "and compatible not-in-this-positions are known"
        (let [actual (sut/simple-solve "" "THINK" ["HI" "IN" "NK" "KT" "TH"])]
          (is (= 1 (count actual)))
          (is (= ["THINK"] actual)))))
    (testing "where some letters and positions are known"
      (testing "and compatible not-in-this-positions are known"
        (testing "and has single solution"
          (let [actual (sut/simple-solve "" "TH.NK" ["HI" "IN" "NK" "KT" "TH"])]
            (is (= 1 (count actual)))
            (is (= ["THINK"] actual))))
        (testing "and has multiple solutions"
          (let [actual (sut/simple-solve "BCDEFGJLMOPQRSUVWXYZ" "TH.NK" ["" "" "" "" ""])]
            (is (= #{"THTNK" "THHNK" "THINK" "THNNK" "THKNK" "THANK"} (set actual))))))))
  (testing "actual Wordle problems can be solved"
    (are [solution actual] (some #{solution} actual)
      "HUMOR" (sut/simple-solve "AIELDNTPYG" "....." ["" "RO" "" "UM" ""])
      "ULCER" (sut/simple-solve "AIDONTS" "...E." ["R" "RU" "L" "U" "L"])
      "CYNIC" (sut/simple-solve "ARELDOUTPGMKOS" ".YN.." ["" "I" "I" "" ""]))))