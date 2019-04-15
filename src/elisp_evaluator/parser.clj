(ns elisp-evaluator.parser
  (:require [clojure.string :as str]))

(defn- parse-atom
  "Numbers become numbers, everything else is a symbol"
  [token]
  (try
    (Integer/parseInt token)
    (catch java.lang.NumberFormatException ex
      (try
        (Double/parseDouble token)
        (catch java.lang.NumberFormatException ex
          token)))))

(defn- parse-sexp
  [tokens]
  (loop [[token & remaining-tokens] tokens
         parsed-tokens []]
    (cond
      (nil? token) [parsed-tokens, remaining-tokens]
      (= token ")") [parsed-tokens, remaining-tokens]
      (= token "(")
      (let [[inner-parsed-tokens, remaining-tokens-after-parsing]
            (parse-sexp remaining-tokens)]
        (recur remaining-tokens-after-parsing (conj parsed-tokens inner-parsed-tokens)))
      :else
      (recur remaining-tokens (conj parsed-tokens (parse-atom token))))))

(defn parse
  "Takes in stream of tokens, returns AST"
  [tokens]
  (let [[parsed-tokens, remaining-tokens] (parse-sexp tokens)]
    (get parsed-tokens 0)))

(defn tokenize
  "Takes in string of characters, returns list of tokens"
  [input-string]
  (let [tokens (-> input-string
                   (str/replace #"\(" " ( ")
                   (str/replace #"\)" " ) ")
                   (str/split #" "))]
    (filter #(not (empty? %))  tokens)))
