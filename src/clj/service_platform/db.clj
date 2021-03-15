(ns service_platform.db)

(def initial-applications [{:id 1
                            :title "Title #1"
                            :description "Description #1"
                            :assignee "Assignee #1"
                            :applicant "Applicant #1"
                            :date "13/02/2000"}])

(def service-db-index (atom {:applications (count initial-applications)}))
(def service-db (atom {:applications initial-applications}))

(defn logger [& args]
  (println "Logger –" args))

;; INSERT
(defn insert! [model record]
  (swap! service-db-index update-in [model] inc)
  (let [new-record (merge record
                          {:id (get @service-db-index model)
                           :date (.format (java.text.SimpleDateFormat. "dd/MM/yyyy") (new java.util.Date))})]
    (logger "A new record created: " new-record)
    (swap! service-db update-in [model] conj new-record)
    new-record))

;; DELETE
(defn filter-by-id [record-id]
  (fn [records] (filter #(not= (:id %) record-id) records)))
(defn delete! [model record-id]
  (logger "The record with id" record-id "was deleted (if ever existed).")
  (swap! service-db update-in [model] (filter-by-id record-id)))

;; UPDATE
(defn swap-by-id [record-id new-record]
  (fn [records] (map #(if (= (:id %) record-id) (assoc new-record :id (:id %)) %) records)))
(defn update! [model record-id new-record]
  (logger "The record was updated: " new-record)
  (swap! service-db update-in [model] (swap-by-id record-id new-record)))

;; SELECT
(defn select [model]
  (get @service-db model))

(defn select-by-id [model id]
  (filter #(= id (:id %)) (get @service-db model)))