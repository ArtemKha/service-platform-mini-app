(ns service-platform.actions
  (:require [ajax.core :as ajax]
            [day8.re-frame.http-fx]
            [service-platform.events :as events]
            [re-frame.core :refer [reg-event-fx]]))

(def application-path "http://localhost:8080/api/applications")

(defn generate-form-data [params]
  (let [form-data (js/FormData.)]
    (doseq [[key value] params]
      (.append form-data (name key) value))
    form-data))

(reg-event-fx
 ::get-all-apllications
 (fn
   [{db :db} _]
   {:http-xhrio {:method          :get
                 :uri             application-path
                 :format          (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [::events/on-get-all-apllications]
                 :on-failure      [::events/on-bad-response]}
    :db  (assoc db :loading? true)}))

(reg-event-fx
 ::create-apllication
 (fn
   [{db :db} [_ applicaton]]
   {:http-xhrio {:method          :post
                 :uri             application-path
                 :body            (generate-form-data applicaton)
                 :format          (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [::events/on-create-apllication]
                 :on-failure      [::events/on-bad-response]}
    :db  (assoc db :loading? true)}))

(reg-event-fx
 ::update-apllication
 (fn
   [{db :db} [_ id applicaton]]
   {:http-xhrio {:method          :put
                 :uri             (str application-path "/" id)
                 :body            (generate-form-data applicaton)
                 :format          (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [::events/on-update-apllication id]
                 :on-failure      [::events/on-bad-response]}
    :db  (assoc db :loading? true)}))

(reg-event-fx
 ::delete-apllication
 (fn
   [{db :db} [_ id]]
   {:http-xhrio {:method          :delete
                 :uri             (str application-path "/" id)
                 :format          (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success      [::events/on-delete-apllication id]
                 :on-failure      [::events/on-bad-response]}
    :db  (assoc db :loading? true)}))