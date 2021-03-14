(ns service-platform.components.form-panel
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [service-platform.actions :as actions]
   [service-platform.subs :as subs]))

(defn form-item2 [label attr edit?]
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

(defn form-item [_ _ _ edit?]
(let [is-active (reagent/atom edit?)]
  (fn [form label item-key]
    [:div
     [:label {:for label
              :class (if @is-active "active" "")} label]
     [:input {:id label
              :type :text
              :name item-key
              :required true
              :value (item-key @form)
              :on-change (fn [e]
                           (swap! form assoc item-key (-> e .-target .-value)))
              :on-focus (fn []
                          (swap! is-active (fn [] true)))
              :on-blur (fn []
                         (swap! is-active (fn [] true)))}]])))

(defn form-panel []
  (let [panel (re-frame/subscribe [::subs/panel])
        applications (re-frame/subscribe [::subs/applications])
        id (get-in @panel [:params :id])
        application (select-by-id @applications id)
        form (reagent/atom (or application {}))]
    (fn []
      [:div {:class "background"}
       [:div {:class "wrap"}
        [:h1 "Service Platform"]
        [:h3 (str "Please, " (if id (str "edit application #" id) "create a new application"))]
        [:form {:on-submit (fn [e]
                             (.preventDefault e)
                             (if id
                               #(re-frame/dispatch [:update-apllication id (dissoc application :id)])
                               #(re-frame/dispatch [::actions/create-apllication application])))}
         [form-item form "Title" :title id]
         [form-item form "Description" :description id]
         [form-item form "Assignee" :assignee id]
         [form-item form "Applicant" :applicant id]
         (if id [form-item form "Date" :date id] [:div])
         [:a {:href "#/"} [:button {:type :button} "Back"]] 
         [:button {:type :submit} "Save"]]]])))