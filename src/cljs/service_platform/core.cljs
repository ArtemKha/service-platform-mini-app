(ns service-platform.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [service-platform.events]
            [service-platform.subs]
            [service-platform.actions :as actions]
            [service-platform.routes :as routes]
            [service-platform.views :as views]
            [service-platform.config :as config]))

(enable-console-print!)

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn render []
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (re-frame/dispatch-sync [::actions/get-all-apllications])
  (dev-setup)
  (mount-root))
