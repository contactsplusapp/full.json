(ns full.time
  (:require [clj-time.core :as tt]
            [clj-time.format :as tf]
            [clj-time.coerce :as tc]
            [full.core.log :as log]))


;;; date time helpers ;;;


(def iso-ts-formatter (tf/formatters :date-time))
(def iso-ts-formatter-no-ms (tf/formatters :date-time-no-ms))
(def iso-ts-formatter-no-ms-tz (tf/formatters :date-hour-minute-second))
(def iso-d-formatter (tf/formatters :date))
(def iso-year-month-formatter (tf/formatters :year-month))
(def iso-year-formatter (tf/formatters :year))

(defn dt<-iso-ts [ts]
  (when ts
    (try
      (tf/parse iso-ts-formatter ts)
      (catch Exception _
        (try
          (tf/parse iso-ts-formatter-no-ms ts)
          (catch Exception _
            (try
              (tf/parse iso-ts-formatter-no-ms-tz ts)
              (catch Exception e
                (log/error "Error parsing timestamp1" ts (str e))))))))))

(defn dt->iso-ts
  "Creates a DateTime object from ISO timestamp."
  [dt]
  (when dt (tf/unparse iso-ts-formatter dt)))

(defn d<-iso-d [ts]
  (when ts
    (try
      (cond
        (= ts "0000-00") nil  ;; special handling for bad data
        ; --mm-dd
        (.startsWith ts "--") (tf/parse-local-date iso-d-formatter (str "0000" (.substring ts 1)))
        ; mm-dd
        (= 5 (.length ts)) (tf/parse-local-date iso-d-formatter (str "0000-" ts))
        ; yyyy
        (= 4 (.length ts)) (tf/parse-local-date iso-year-formatter ts)
        ; yyyy-mm
        (= 7 (.length ts)) (tf/parse-local-date iso-year-month-formatter ts)
        ; yyyy-mm-dd
        :else (tf/parse-local-date iso-d-formatter ts))
      (catch Exception e
        (log/error "Error parsing timestamp" ts (str e))))))

(defn d->iso-d [ts]
  (when ts (tf/unparse-local-date iso-d-formatter ts)))

(defn dt<-epoch
  "Creates a DateTime object from a (long) unix timestamp"
  [epoch]
  (tc/from-long epoch))

(defn epoch<-dt
  "Creates a DateTime object from a (long) unix timestamp"
  [dt]
  (tc/to-long dt))

(def now-utc tt/now)

(defn dt-between [dt from to]
  (tt/within? (tt/interval from to) dt))