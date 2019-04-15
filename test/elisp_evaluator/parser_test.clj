(ns elisp-evaluator.parser-test
  (:require  [clojure.test :as t :refer :all]
             [elisp-evaluator.parser :refer :all]))

(deftest parser-test
  (testing "Parser test1."
    (let [simple-input-token (tokenize "(+ 1 1)")
          nested-input-token (tokenize "(+ 1 (* 2 2) 1)")
          nested-2-level-token (tokenize "(* 4 (* 3.14 (* r r)))")]
      (is (= (parse simple-input-token) ["+" 1 1]))
      (is (= (parse nested-input-token) ["+" 1 ["*" 2 2] 1]))
      (is (= (parse nested-2-level-token) ["*" 4 ["*" 3.14 ["*" "r" "r"]]])))))

(deftest tokenize-test
  (testing "Tokenize simple input."
    (let [input "(+ 1 1)"
          expected-output ["(", "+", "1", "1", ")"]]
      (is (= (tokenize input) expected-output)))
    (let [input " ( + 1  1)"
          expected-output ["(", "+", "1", "1", ")"]]
      (is (= (tokenize input) expected-output)))
    (is (= (tokenize "(def planet \"Earth\")") ["(", "def", "planet", "\"Earth\"", ")"]))))
