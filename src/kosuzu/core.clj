(ns kosuzu.core
  (:gen-class)
  (:require clojure.java.io
            kosuzu.parser
            [reaver :refer [parse]]))

;; TODO: Allow user to customize this
(def output-file "output.txt")

(defn -main
  [& args]
  (if (seq args)
    (let [url (clojure.java.io/as-url (first args))
          html (parse (slurp url))
          wiki-content (kosuzu.parser/generate-content html url)]
      (spit output-file wiki-content))
    (throw (IllegalArgumentException.
             "Please enter a URL to a Touhou music album."))))
