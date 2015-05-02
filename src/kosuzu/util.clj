(ns kosuzu.util)

(def ^:private date->convention-map
  {"2008-11-02" "Touhou Kouroumu 4"
   "2008-12-29" "Comiket 75"
   "2009-03-08" "Reitaisai 6"
   "2009-05-05" "M3-23"
   "2009-08-15" "Comiket 76"
   "2009-10-11" "Touhou Kouroumu 5"
   "2009-12-30" "Comiket 77"
   "2010-03-14" "Reitaisai 7"
   "2010-08-14" "Comiket 78"
   "2010-12-30" "Comiket 79"
   "2011-03-13" "Reitaisai 8"
   "2011-08-13" "Comiket 80"
   "2011-10-16" "Touhou Kouroumu 7"
   "2011-12-30" "Comiket 81"
   "2012-05-27" "Reitaisai 9"
   "2012-08-11" "Comiket 82"
   "2012-12-30" "Comiket 83"
   "2013-05-26" "Reitaisai 10"
   "2013-08-12" "Comiket 84"
   "2013-12-30" "Comiket 85"
   "2014-05-11" "Reitaisai 11"
   "2014-08-16" "Comiket 86"
   "2014-12-29" "Comiket 87"
   "2015-05-10" "Reitaisai 12"})

(defn date->convention [date]
  (get date->convention-map date ""))
