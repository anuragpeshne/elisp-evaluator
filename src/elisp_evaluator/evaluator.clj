(ns elisp-evaluator.evaluator)

(defn empty-environment
  "Returns an empty environment."
  []
  {
   "+" (fn [ops] (apply + ops))
   "*" (fn [ops] (apply + ops))
   })

(defn eval-ast
  "Evaluates AST in the given environment."
  [ast-exp, env]
  (let [[operator & operands] ast-exp]
    (if (contains? env operator)
      ((get env operator) operands)
      (throw (Exception. (str "Unknown symbol " operator))))))
