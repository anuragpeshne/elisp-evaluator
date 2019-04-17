(ns elisp-evaluator.evaluator)

(declare eval-ast)

(defn empty-environment
  "Returns an empty environment."
  []
  {
   "+" (fn [ops] (apply + ops))
   "*" (fn [ops] (apply * ops))
   })

(defn- eval-atom
  "Evaluates an atom in the given environment."
  [atom, env]
  (if (vector? atom)
    (eval-ast atom env)
    (cond
      (number? atom) atom
      (contains? env atom) (get env atom)
      :else (throw (Exception. (str "Unknown symbol " atom))))))

(defn eval-ast
  "Evaluates AST in the given environment."
  [ast-exp, env]
  (let [[operator & operands] (map #(eval-atom % env) ast-exp)]
    (operator operands)))
