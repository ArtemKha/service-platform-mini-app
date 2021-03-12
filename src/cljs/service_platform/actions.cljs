(ns service-platform.actions
  (:require [ajax.core :as ajax]
            [day8.re-frame.http-fx]
            [service-platform.events :as events]
            [re-frame.core :refer [reg-event-fx]]))

(def application-path "http://localhost:10555/api/applications")

(reg-event-fx
 :get-all-apllications
 (fn
   [{db :db} _]
   {:http-xhrio {:method          :get
                 :uri             application-path
                 :format          (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [::events/on-get-all-apllications]
                 :on-failure      [::events/on-bad-response]}
    :db  (assoc db :loading? true)}))
