(ns service_platform.db
  (:require [service-platform.common :as common]))

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
(defn delete! [model record-id]
  (logger "The record with id" record-id "was deleted (if ever existed).")
  (swap! service-db update-in [model] (common/filter-by-id record-id)))

;; UPDATE
(defn update! [model record-id new-record]
  (logger "The record was updated: " new-record)
  (swap! service-db update-in [model] (common/swap-by-id record-id new-record))
  new-record)

;; SELECT
(defn select [model]
  (get @service-db model))

(defn select-by-id [model id]
  (filter #(= id (:id %)) (get @service-db model)))