(ns service-platform.events
  (:require [service-platform.common :as common]
            [re-frame.core :as re-frame]
            [service-platform.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::on-get-all-apllications
 (fn [db [_ response]]
   (assoc db :applications (:body response))))

(re-frame/reg-event-fx
 ::on-bad-response (fn [_ [_ response]]
                     (js/alert (str
                                "Bad request: "
                                (get-in response [:response :body] "Unknown error.")))))

(re-frame/reg-event-db
 ::on-create-apllication
 (fn [db [_ response]]
   (update-in db [:applications] conj (:body response))))

(re-frame/reg-event-db
 ::on-update-apllication
 (fn [db [_ id response]]
   (update-in db [:applications] (common/swap-by-id id (:body response)))))

(re-frame/reg-event-db
 ::on-delete-apllication
 (fn [db [_ id]]
   (update-in db [:applications] (common/filter-by-id id))))

(re-frame/reg-event-db
 ::set-panel
 (fn [db [_ id params]]
   (assoc db :panel {:id id :params params})))