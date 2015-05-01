(ns kosuzu.core
  (:gen-class)
  (:require clojure.java.io
            [net.cgrand.enlive-html :as html]))

(defn fetch-url [url]
  (html/html-resource url))

(defn -main
  [& args]
  (if (seq args)
    (println (fetch-url (clojure.java.io/as-url (first args))))
    (throw (IllegalArgumentException.
             "Please enter a URL to a Touhou music album."))))
