(ns service-platform.utils
  (:require [validateur.validation :as vr]
            [service-platform.common :as common]))


(defn range-validator [param]
  (vr/length-of param :within (range 1 250)))

(def application-validator
  (vr/validation-set
   (range-validator :title)
   (range-validator :description)
   (range-validator :applicant)
   (range-validator :assignee)
   (vr/validate-when #(contains? % :date) (vr/format-of :date :format common/date-pattern))))

(defn validate-application-params! [params]
  (when (vr/invalid? application-validator params)
    (throw (ex-info "Validation exception" {:type :validation-exception
                                            :errors (application-validator params)}))))

