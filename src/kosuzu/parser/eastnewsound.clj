(ns kosuzu.parser.eastnewsound
  (:require kosuzu.parser
            [net.cgrand.enlive-html :as html]))

(def ^:private url-pattern
  (re-pattern "/discography/ens(\\d{4})\\.html"))

(defn- get-titleen [html]
  (let [titleen-pattern (re-pattern "ENS-\\d{4} / (.*)::EastNewSound Official Site::")
        title (-> (html/select html [:title])
                  first :content first)]
    (last (re-find titleen-pattern title))))

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
    {:titleen (get-titleen html)
     :group "[[EastNewSound]]"
     :groupCat "EastNewSound"
     :catalogno (get-catalogno url)}))

(defn get-parser [html url] (EastNewSoundParser. html url))
