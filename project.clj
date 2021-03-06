(defproject kosuzu "0.1.0-SNAPSHOT"
  :description "Given a URL to a Touhou doujin album, this tool will generate a Touhou wiki article for it."
  :url "https://github.com/bvtsang/kosuzu"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [reaver "0.1.1"]
                 [selmer "0.8.2"]]
  :main ^:skip-aot kosuzu.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :jvm-opts ["-Dfile.encoding=utf8"])
