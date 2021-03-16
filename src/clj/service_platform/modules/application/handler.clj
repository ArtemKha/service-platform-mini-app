(ns service-platform.modules.application.handler
  (:require [compojure.route :as route]
            [compojure.core :refer [routes context GET POST PUT DELETE]]
            [ring.util.http-response :refer [bad-request]]
            [ring.util.response :refer [response content-type charset status]]
            [service-platform.modules.application.repository :as r]
            [service-platform.utils :refer [validate-application-params!]]
            [clojure.data.json :refer [json-str]]))

(defn json-response [data]
  (-> (json-str data)
      response
      (status (:status data))
      (content-type "application/json")
      (charset "UTF-8")))

;; TODO: find better approach to validation (more declarative)
(defn validation-exception-handler [e]
  (when (= :validation-exception (-> e ex-data :type))
    (json-response (bad-request (-> e ex-data :errors)))))

;; Routes
(defn application-routes [& args]
  (routes
   (context "/api" []
     (POST "/applications" req
       (let [create-application-req (:params req)]
         (try (validate-application-params! create-application-req)
              (json-response (r/create-application-handler create-application-req))
              (catch clojure.lang.ExceptionInfo e
                (validation-exception-handler e)))))
     (GET "/applications" []
       (json-response (r/get-applications-handler)))
     (GET "/applications/:id" [id]
       (json-response (r/get-application-handler (Integer/parseInt id))))
     (PUT "/applications/:id" req
       (let [id (Integer/parseInt (get-in req [:route-params :id]))
             update-application-req (:params req)]
         (try (validate-application-params! update-application-req)
              (json-response (r/update-application-handler id update-application-req))
              (catch clojure.lang.ExceptionInfo e
                (validation-exception-handler e)))))
     (DELETE "/applications/:id" [id]
       (json-response (r/delete-application-handler (Integer/parseInt id))))
     (route/not-found "404"))))
