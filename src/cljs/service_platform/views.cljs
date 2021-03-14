(ns service-platform.views
  (:require [re-frame.core :as re-frame]
            [service-platform.actions]
            [service-platform.components.form-panel :refer [form-panel]]
            [service-platform.subs :as subs]))

(defn list-item [application]
  [:div {:key (:id application)}
   [:a { :href (str "#/form/" (:id application)) :class "list-item"} 
    [:span.list-item-attr [:strong "Title: "] (:title application)]
    [:span.list-item-attr [:strong "Description: "] (:description application)]
    [:span.list-item-attr [:strong "Assignee: "] (:assignee application)]
    [:span.list-item-attr [:strong "Applicant: "] (:applicant application)]
    [:span.list-item-attr [:strong "Date: "] (:date application)]
    ]])


(defn list-panel []
  (let [applications (re-frame/subscribe [::subs/applications])]
    (fn []
      [:div {:class "background"}
       [:div {:class "wrap"}
        [:h1 "Service Platform"]
        [:h3 "The most unique way to manage applications"]
        (if (> (count @applications) 0)
          (map list-item @applications)
          [:div "No applications yet. Feel free to add a new one."])
        [:a {:href "#/form"}
         [:button "Create application"]]]])))


(defn- panels [panel-id]
  (case panel-id
    :home [list-panel]
    :form [form-panel]
    [:div]))

(defn show-panel [panel]
  [panels (:id panel)])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/panel])]
    [show-panel @active-panel]))