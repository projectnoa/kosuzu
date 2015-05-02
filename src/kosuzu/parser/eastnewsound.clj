(ns kosuzu.parser.eastnewsound
  (:require kosuzu.parser))

(def ^:private url-pattern
  (re-pattern "/discography/ens(\\d{4})\\.html"))

(defn- get-catalogno [url]
  (str "ENS-" (last (re-find url-pattern (.getPath url)))))

(defrecord EastNewSoundParser [html url]
  kosuzu.parser/Parser
  (can-parse-album?
    [this]
    (and (= (.getHost url) "e-ns.net")
         (re-find url-pattern (.getPath url))))

  (parse-album
    [this]
    {:group "[[EastNewSound]]"
     :groupCat "EastNewSound"
     :catalogno (get-catalogno url)}))

(defn get-parser [html url] (EastNewSoundParser. html url))
