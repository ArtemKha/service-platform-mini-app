(ns service-platform.common)

(defn filter-by-id [record-id]
  (fn [records] (filter #(not= (:id %) record-id) records)))

(defn swap-by-id [record-id new-record]
  (fn [records] (map #(if (= (:id %) record-id) (assoc new-record :id (:id %)) %) records)))