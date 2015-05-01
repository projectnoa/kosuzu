(ns kosuzu.core
  (:gen-class)
  (:require [clojure.java.io]))

(defn -main
  [& args]
  (if (seq args)
    (println (clojure.java.io/as-url (first args)))
    (throw (IllegalArgumentException.
             "Please enter a URL to a Touhou music album."))))
