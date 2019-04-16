(ns elisp-evaluator.evaluator-test
  (:require  [clojure.test :as t :refer :all]
             [elisp-evaluator.parser :refer :all]
             [elisp-evaluator.evaluator :refer :all]))

(deftest empty-environment-test
  (testing "If empty-environment returns a map."
    (is (= (type (empty-environment)) clojure.lang.PersistentArrayMap))))

(deftest eval-test
  (testing "eval test."
    (let [input-output-list ['("(+ 1 2)" 3)
                             '("(* 2 2)" 4)
                             '("(+ 2 (* 2 3))" 8)]]
      (loop [i-o input-output-list]
        (if (not (empty? i-o))
          (let [[input expected-output] (first i-o)
                output (-> input
                           tokenize
                           parse
                           (eval-ast (empty-environment)))]
            (is (= expected-output output))
            (recur (rest i-o))))))))
