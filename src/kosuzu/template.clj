(ns kosuzu.template
  (:require selmer.parser))

(defn fill-template [data]
  ; The double curly braces interferes with selmer.parser,
  ; so we're prepending it here
  (str "{{MusicArticle\n"
       (selmer.parser/render-file "template.txt" data)))
