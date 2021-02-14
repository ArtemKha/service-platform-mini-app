(ns service-platform.views
  (:require [re-frame.core :as re-frame]
            [service-platform.components.form-panel :refer [form-panel]]
            [service-platform.subs :as subs]))

(defn list-item [label]
  [:div label])


(defn list-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div {:class "background"}
       [:div {:class "wrap"}
        [:h1 "Service Platform"]
        [:h3 "Your way to create Szechuan sauce"]
        (list-item "Title")
        [:a {:href "#/form"}
         [:button "Create application"]]]])))


(defn- panels [panel-name]
  (case panel-name
    :home [list-panel]
    :form [form-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/panel])]
    [show-panel @active-panel]))