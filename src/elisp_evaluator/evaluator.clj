(ns elisp-evaluator.evaluator)

(declare eval-ast)

(defn empty-environment
  "Returns an empty environment."
  []
  {
   "+" (fn [env, ops] [env, (apply + ops)])
   "*" (fn [env, ops] [env, (apply * ops)])
   "defvar" (fn [env, [var, val]] [(assoc env var val), nil])
   })

(defn- eval-atom
  "Evaluates an atom in the given environment."
  [atom, env]
  (cond
    (vector? atom) (let [[env, result] (eval-ast atom env)] result)
    (number? atom) atom
    (contains? env atom) (get env atom)
    (string? atom) atom
    :else (throw (Exception. (str "elisp error: Unknown symbol " atom)))))

(defn eval-ast
  "Evaluates AST in the given environment."
  [ast-exp, env]
  (let [[operator & operands] (map #(eval-atom %1 env) ast-exp)]
    (operator env operands)))
