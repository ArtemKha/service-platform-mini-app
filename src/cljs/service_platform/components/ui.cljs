(ns service-platform.components.ui
  (:require [com.stuartsierra.component :as component]
            [service-platform.core :refer [render]]))

(defrecord UIComponent []
  component/Lifecycle
  (start [component]
    (render)
    component)
  (stop [component]
    component))

(defn new-ui-component []
  (map->UIComponent {}))
