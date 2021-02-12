(ns service-platform.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [service-platform.subs :as subs]))

(defn list-item [label]
  [:div label])

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
      [form-item "Date" {:pattern #"[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}"}]
      [:button "Submit"]]]))

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