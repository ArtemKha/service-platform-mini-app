(ns service-platform.modules.application.repository
  (:require [service_platform.db :as s-db]
            [ring.util.http-response :refer [ok not-found created]]
            [service-platform.modules.application.model :refer [Application]]))

;; TODO: move http-response to handler (proper layer separation)
;; Create
(defn id->created [application]
  (created (str "/applications/" (:id application)) application))

(defn create-application-handler [create-application-req]
  (-> (s-db/insert! Application create-application-req)
      id->created))

;; Get All
(defn get-applications-handler []
  (ok (s-db/select Application)))

;; Get By Id
(defn application->response [application]
  (if application
    (ok application)
    (not-found)))

(defn get-application-handler [application-id]
  (-> (s-db/select-by-id Application application-id)
      application->response))

;; Update
(defn update-application-handler [id update-application-req]
  (-> (s-db/update! Application id update-application-req)
      (ok)))

;; Delete
(defn delete-application-handler [application-id]
  (s-db/delete! Application application-id)
  (ok))
