(ns service-platform.events
  (:require [re-frame.core :as re-frame]
            [service-platform.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::on-get-all-apllications
 (fn [db [_ applications]]
   (assoc db :applications applications)))

(re-frame/reg-event-db
 ::on-bad-response (fn [db [_ _]]
                    (assoc db :applications [])))

(re-frame/reg-event-db
 ::set-panel
 (fn [db [_ id params]]
   (assoc db :panel {:id id :params params})))