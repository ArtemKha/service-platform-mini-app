(ns service-platform.components.form-panel
  (:require [reagent.core :as reagent]))

(defn form-item []
  (let [is-active (reagent/atom false)]
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

(defn form-panel [id]
  (fn []
    [:div {:class "background"}
     [:div {:class "wrap"}
      [:h1 "Service Platform"]
      [:h3 (str "Pleese, " (if id (str "edit task #" id) "create a new task"))]
      [form-item "Title"]
      [form-item "Description"]
      [form-item "Assignee"]
      [form-item "Applicant"]
      (if id [form-item "Date" {:pattern #"[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}"}] nil)
      [:a {:href "#/"} [:button "Back"]
       [:button "Save"]]]]))