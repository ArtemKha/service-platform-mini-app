(ns service-platform.components.form-panel
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [service-platform.actions :as actions]
   [service-platform.subs :as subs]))

(defn form-item [edit?]
  (let [is-active (reagent/atom edit?)]
    (fn [_ form label item-key attributes]
      [:div
       [:label {:for label
                :class (if @is-active "active" "")} label]
       [:input (conj attributes {:id label
                                 :type :text
                                 :name item-key
                                 :required true
                                 :max-length 250
                                 :value (item-key @form)
                                 :on-change (fn [e]
                                              (swap! form assoc item-key (-> e .-target .-value)))
                                 :on-focus (fn []
                                             (swap! is-active (fn [] true)))
                                 :on-blur (fn []
                                            (swap! is-active (fn [] true)))})]])))

(defn handle-submit [e id form]
  (.preventDefault e)
  (if id
    (re-frame/dispatch [::actions/update-apllication id (dissoc @form :id)])
    (re-frame/dispatch [::actions/create-apllication @form]))
  (.assign js/location "#/"))

(defn handle-delete [id]
  (re-frame/dispatch [::actions/delete-apllication id])
  (.assign js/location "#/"))

(defn select-by-id [applications id]
  (first
   (filter (fn [it] (= (:id it) id)) applications)))

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
        [:form {:on-submit #(handle-submit % id form)}
         [form-item id form "Title" :title {}]
         [form-item id form "Description" :description {}]
         [form-item id form "Assignee" :assignee {}]
         [form-item id form "Applicant" :applicant {}]
         (when id
           [form-item id form "Execution date" :date {:placeholder "dd/mm/yyyy"
                                                      :pattern  "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/20[0-2][0-9]"}])
         [:a {:href "#/"} [:button {:type :button} "Back"]]
         (when id [:button {:type :button :on-click #(handle-delete id)} "Remove"])
         [:button {:type :submit} "Save"]]]])))