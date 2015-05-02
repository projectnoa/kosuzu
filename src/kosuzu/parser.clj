(ns kosuzu.parser
  (:require kosuzu.template))

(defprotocol Parser
  (can-parse-album? [this])
  (parse-album [this]))

;; TODO: Figure out how to load parsers automatically
(def ^:private parsers
  (list "eastnewsound"))

(defn generate-content [html url]
  (loop [current-handler (first parsers)
         remaining-handlers (rest parsers)]
    (if (nil? current-handler)
      (throw (Exception. "No known handlers can handle this URL!"))
      (do
        (load (str "kosuzu/parser/" current-handler))
        (let [current-ns (the-ns (symbol (str "kosuzu.parser." current-handler)))
              current-parser ((ns-resolve current-ns (symbol "get-parser"))
                              html url)]
          (if (can-parse-album? current-parser)
            (kosuzu.template/fill-template (parse-album current-parser))
            (recur (first remaining-handlers) (rest remaining-handlers))))))))
