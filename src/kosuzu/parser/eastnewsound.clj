(ns kosuzu.parser.eastnewsound
  (:require kosuzu.parser
            [kosuzu.util :as util]
            [clojure.string :as string]
            [reaver :refer [select text]]))

(def ^:private url-pattern
  (re-pattern "/discography/ens(\\d{4})(?:\\.html)?"))

(defn- get-titleen [html]
  (let [titleen-pattern (re-pattern "ENS-\\d{4} / (.*)::EastNewSound Official Site::")]
    (last (re-find titleen-pattern (.title html)))))

(defn- get-released [html]
  (let [date-pattern (re-pattern "(\\d{4})/(\\d{2})/(\\d{2})")
        release-date (re-find date-pattern
                              (text (select html "a#sal + strong + span")))]
    (str (second release-date)
         "-"
         (nth release-date 2)
         "-"
         (last release-date))))

(defn- get-tracks [html]
  (let [tracks (text (select html "a#track + strong + span"))]
    ((frequencies tracks) \◆)))

(defn- get-catalogno [url]
  (str "ENS-" (last (re-find url-pattern (.getPath url)))))

(defn- get-arranger [html]
  (let [; Grabbing a#staff's parent p to be absolutely sure we've got the staff
        staff-parent-p (-> (select html "a#staff") first .parent)
        staff-text (map (fn [txt] (string/trim (.text txt)))
                        (.textNodes staff-parent-p))
        arrangers
        (take-while (complement (fn [txt] (.contains txt "・")))
                    (drop 1
                          (drop-while
                            (complement (fn [txt] (.contains txt "Arranger:")))
                            staff-text)))
        bracket-pattern (re-pattern "(.*)\\[([^]]+)\\]")
        wrap-in-parens (fn [text]
                         (let [regex-result (re-find bracket-pattern text)]
                           (if (nil? regex-result)
                             text
                             (str (second regex-result)
                                  "("
                                  (string/trim (last regex-result))
                                  ")"))))]
    (string/join "\n: " (map util/wrap-jp-text
                             (map wrap-in-parens arrangers)))))

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
     :banner (str (get-catalogno url) "_banner.png")
     :arranger (get-arranger html)}))

(defn get-parser [html url] (EastNewSoundParser. html url))
