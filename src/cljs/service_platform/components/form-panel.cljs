(ns service-platform.components.form-panel
  (:require 
    [reagent.core :as reagent]
    [re-frame.core :as re-frame]
    [service-platform.subs :as subs]))

(defn form-item [label attr edit?]
  (let [is-active (reagent/atom edit?)]
    (fn [label attr]
      [:div
       [:label {:for label
                :class (if @is-active "active" "")} label]
       [:input (conj (or attr  {}) {:id label
                                    :type "text"
                                    :on-focus (fn []
                                                (swap! is-active (fn [] true)))
                                    :on-blur (fn []
                                               (swap! is-active (fn [] true)))})]])))

(defn select-by-id [applications id] 
    (first
     (filter (fn [it] (= (:id it) id)) applications)))

(defn form-panel [id]
  (let [panel (re-frame/subscribe [::subs/panel])
        applications (re-frame/subscribe [::subs/applications])
        id (get-in @panel [:params :id])
        application (select-by-id @applications id)]
    (fn []
      [:div {:class "background"}
      [:div {:class "wrap"}
        [:h1 "Service Platform"]
        [:h3 (str "Please, " (if id (str "edit application #" id) "create a new application"))]
        [form-item "Title" {:value (:title application "")} id]
        [form-item "Description" {:value (:description application "")} id]
        [form-item "Assignee" {:value (:assignee application "")} id]
        [form-item "Applicant" {:value (:applicant application "")} id]

        (if id [form-item "Date" {:value (:date application) 
                                  :pattern #"[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}"} id] [:div])
        [:a {:href "#/"} [:button "Back"]
        [:button "Save"]]]])))