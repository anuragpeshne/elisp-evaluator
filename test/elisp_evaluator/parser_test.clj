(ns elisp-evaluator.parser-test
  (:require  [clojure.test :as t :refer :all]
             [elisp-evaluator.parser :refer :all]))

(deftest parser-test
  (testing "Parser test1."
    (is (= (parse "(+ 1 1)") '(+ 1 1)))))

(deftest tokenize-test
  (testing "Tokenize simple input."
    (let [input "(+ 1 1)"
          expected-output ["(", "+", "1", "1", ")"]]
      (is (= (tokenize input) expected-output)))
    (let [input " ( + 1  1)"
          expected-output ["(", "+", "1", "1", ")"]]
      (is (= (tokenize input) expected-output)))
    (is (= (tokenize "(def planet \"Earth\")") ["(", "def", "planet", "\"Earth\"", ")"]))))
