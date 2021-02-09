(ns service-platform.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]))

(defn list-item [label]
  [:div label])

(defn form-item [label]
  (let [is-active (reagent/atom false)]
    (fn [lable]
      [:div
       [:label {:for label
                :class (if @is-active "active" "")} label]
       [:input {:id label :type "text"
                :on-focus (fn []
                            (swap! is-active (fn [] true)))
                :on-blur (fn []
                           (swap! is-active (fn [] false)))}]])))

(defn main-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div {:class "background"}
       [:div {:class "wrap"}
        [:h1 "Service Platform"]
        [:h3 "Your way to create tasks"]
        [form-item "Title"]
        [form-item "Description"]
        [form-item "Assignee"]
        [form-item "Applicant"]
        [form-item "Date"]
        [:button "Submit"]]])))

(defn list-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div {:class "background"}
       [:div {:class "wrap"}
        [:h1 "Service Platform"]
        [:h3 "Your way to create tasks"]
        (list-item "Title")
        [:button "Create application"]]])))
