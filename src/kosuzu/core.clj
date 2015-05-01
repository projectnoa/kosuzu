(ns kosuzu.core
  (:gen-class)
  (:require clojure.java.io
            kosuzu.parser
            [net.cgrand.enlive-html :as html]))

;; TODO: Allow user to customize this
(def output-file "output.txt")

(defn -main
  [& args]
  (if (seq args)
    (let [url (clojure.java.io/as-url (first args))
          html (html/html-resource url)
          wiki-content (kosuzu.parser/generate-content html url)]
      (spit output-file wiki-content))
    (throw (IllegalArgumentException.
             "Please enter a URL to a Touhou music album."))))
