(ns elisp-evaluator.parser
  (:require [clojure.string :as str]))

(defn parse
  "Takes in stream of tokens, returns AST"
  ([]
   (throw (Exception. "Unexpected EOF.")))
  ([tokens]
   (let [[token & remaining] tokens]
     (cond
       (= "(" token) (let [ast []])
       (= ")" token) (throw (Exception. "Unexpected )"))
       :else (ast-atom token)))))

(defn- ast-atom
  "Numbers become numbers, everything else is a symbol"
  [token]
  (try
    (Integer/parseInt token)
    (catch java.lang.NumberFormatException ex
      (try
        (Double/parseDouble token)
        (catch java.lang.NumberFormatException ex
          token)))))

(defn tokenize
  "Takes in string of characters, returns list of tokens"
  [input-string]
  (let [tokens (-> input-string
                   (str/replace #"\(" " ( ")
                   (str/replace #"\)" " ) ")
                   (str/split #" "))]
    (filter (fn [x] (not (empty? x))) tokens)))
