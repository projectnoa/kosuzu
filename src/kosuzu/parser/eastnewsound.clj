(ns kosuzu.parser.eastnewsound
  (:require kosuzu.parser
            [kosuzu.util :as util]
            [net.cgrand.enlive-html :as html]))

(def ^:private url-pattern
  (re-pattern "/discography/ens(\\d{4})(?:\\.html)?"))

(defn- get-titleen [html]
  (let [titleen-pattern (re-pattern "ENS-\\d{4} / (.*)::EastNewSound Official Site::")
        title (-> (html/select html [:title])
                  first :content first)]
    (last (re-find titleen-pattern title))))

(defn- get-released [html]
  (let [date-pattern (re-pattern "(\\d{4})/(\\d{2})/(\\d{2})")
        release-date
        (re-find date-pattern
                 (-> (html/select
                       html
                       {[:a#sal] [:span]})
                     first
                     (nth 3)
                     :content
                     first))]
    (str (second release-date)
         "-"
         (nth release-date 2)
         "-"
         (last release-date))))

(defn- get-tracks [html]
  (count (filter (every-pred string?
                             (fn [line] (.contains line "◆")))
                 (-> (html/select html {[:a#track] [:span]})
                     first
                     (nth 3)
                     :content))))

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
     :released (get-released html)
     :convention (util/date->convention (get-released html))
     :tracks (get-tracks html)
     :catalogno (get-catalogno url)
     :website (str "[" url " Link]")
     :image (str (get-catalogno url) ".png")
     :banner (str (get-catalogno url) "_banner.png")}))

(defn get-parser [html url] (EastNewSoundParser. html url))
