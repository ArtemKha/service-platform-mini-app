(ns service-platform.modules.application.repository
  (:require [toucan.db :as db]
            [ring.util.http-response :refer [ok not-found created]]
            [service-platform.modules.application.model :refer [Application]]
            [service-platform.utils :refer [length-in-range?]]))

(defn valid? [str]
  (length-in-range? 1 255 str))

;; Create
(defn id->created [id]
  (created (str "/applications/" id) {:id id}))

(defn create-application-handler [create-application-req]
  (-> (db/insert! Application create-application-req)
      :id
      id->created))

;; Get All
(defn get-applications-handler []
  (ok (db/select Application)))

;; Get By Id
(defn application->response [application]
  (if application
    (ok application)
    (not-found)))

(defn get-application-handler [application-id]
  (-> (Application application-id)
      application->response))

;; Update
(defn update-application-handler [id update-application-req]
  (db/update! Application id update-application-req)
  (ok))

;; Delete
(defn delete-application-handler [application-id]
  (db/delete! Application :id application-id)
  (ok))
