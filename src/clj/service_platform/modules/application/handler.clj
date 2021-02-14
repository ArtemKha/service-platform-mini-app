(ns service-platform.modules.application.handler
  (:require [compojure.route :as route]
            [compojure.core :refer [routes GET POST PUT DELETE]]
            [ring.util.response :refer [response content-type charset]]
            [service-platform.modules.application.repository :as r]
            [clojure.data.json :refer [write-str]]))

(defn as-date-string [date]
  (str date))

(defn date-aware-value-writer [key value]
  (if (= key :created_at) (as-date-string value) value))

(defn json-response [data]
  (-> (write-str data :value-fn date-aware-value-writer)
      response
      (content-type "application/json")
      (charset "UTF-8")))

;; Routes
(defn application-routes [& args]
  (routes
   (POST "/applications" req
     (json-response (r/create-application-handler (:params req))))
   (GET "/applications" []
     (json-response (r/get-applications-handler)))
   (GET "/applications/:id" [id]
     (json-response (r/get-application-handler (Integer/parseInt id))))
   (PUT "/applications/:id" req
     (let [id (Integer/parseInt (get-in req [:route-params :id]))
           update-application-req (:params req)]
       (json-response (r/update-application-handler id update-application-req))))
   (DELETE "/applications/:id" [id]
     (json-response (r/delete-application-handler (Integer/parseInt id))))
   (route/not-found "404")))
