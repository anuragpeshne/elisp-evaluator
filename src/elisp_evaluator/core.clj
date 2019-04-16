(ns elisp-evaluator.core
  (:require [elisp-evaluator.core :refer :all]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
