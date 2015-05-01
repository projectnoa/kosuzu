(ns kosuzu.parser.eastnewsound
  (:require kosuzu.parser))

; http://e-ns.net/discography/ens0036.html
(defrecord EastNewSoundParser [html url]
  kosuzu.parser/Parser
  (can-parse-album? [this] false)
  (parse-album [this] {}))

(defn get-parser [html url] (EastNewSoundParser. html url))
