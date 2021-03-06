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
      (loop [i-o input-output-list
             env (empty-environment)]
        (if (not (empty? i-o))
          (let [[input expected-output] (first i-o)
                [env, output] (-> input
                                  tokenize
                                  parse
                                  (eval-ast env))]
            (is (= expected-output output))
            (recur (rest i-o) env)))))))

(deftest eval-test-defvar
  (testing "defvar test."
    (let [input-output-list ['("(defvar a 12)" "a" 12 nil)]]
      (loop [i-o input-output-list
             env (empty-environment)]
        (if (not (empty? i-o))
          (let [[input key value expected-output] (first i-o)
                [env, output] (-> input
                                  tokenize
                                  parse
                                  (eval-ast env))]
            (is (and (= expected-output output)
                     (contains? env key)
                     (= (get env key) value)))
            (recur (rest i-o) env)))))))
