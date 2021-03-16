(ns service-platform.common)

(def date-pattern #"(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/20[0-2][0-9]")

(defn filter-by-id [record-id]
  (fn [records] (filter #(not= (:id %) record-id) records)))

(defn swap-by-id [record-id new-record]
  (fn [records] (map #(if (= (:id %) record-id) (assoc new-record :id (:id %)) %) records)))