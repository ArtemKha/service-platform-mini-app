(ns service-platform.utils
  (:require [clojure.string :as str]))

(def non-blank? (complement str/blank?))

(defn min-length? [length text]
  (>= (count text) length))

(defn max-length? [length text]
  (<= (count text) length))

(defn length-in-range? [min-length max-length text]
  (and (min-length? min-length text) (max-length? max-length text)))
