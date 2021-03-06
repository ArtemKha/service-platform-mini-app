(ns service-platform.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::applications
 (fn [db _]
   (:applications db)))

(re-frame/reg-sub
 ::panel
 (fn [db _]
   (:panel db)))