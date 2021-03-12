(ns service-platform.views
  (:require [re-frame.core :as re-frame]
            [service-platform.actions]
            [service-platform.components.form-panel :refer [form-panel]]
            [service-platform.subs :as subs]))

(defn list-item [application]
  [:div {:key (:id application)}
   [:a { :href (str "#/form/" (:id application)) :class "list-item"} 
    [:span (str application)]]])


(defn list-panel []
  (let [applications (re-frame/subscribe [::subs/applications])]
    (fn []
      [:div {:class "background"}
       [:div {:class "wrap"}
        [:h1 "Service Platform"]
        [:h3 "Your way to create Szechuan sauce"]
        (map list-item @applications)
        [:a {:href "#/form"}
         [:button "Create application"]]]])))


(defn- panels [panel-id]
  (case panel-id
    :home [list-panel]
    :form [form-panel]
    [:div]))

(defn show-panel [panel]
  (println (str "panel" panel))
  [panels (:id panel)])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/panel])]
    [show-panel @active-panel]))