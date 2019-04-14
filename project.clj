(defproject elisp-evaluator "0.1.0-SNAPSHOT"
  :description "An Emacs Lisp evaluator"
  :url "http://github.com/anuragpeshne/elisp-evaluator"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :repl-options {:init-ns elisp-evaluator.core})
