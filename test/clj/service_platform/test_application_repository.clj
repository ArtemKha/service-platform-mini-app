(ns service-platform.test-application-repository
  (:require [clojure.set]
            [clojure.test :refer :all]
            [service-platform.service-db :as s-db]
            [service-platform.modules.application.repository :as r]))

(deftest test-application-repository

  (testing "Select all records"
    (let [initial-applications s-db/initial-applications
          request (r/get-applications-handler)]
      (is (= (:body request)
             initial-applications))))

  (testing "Add a new record"
    (let [new-record {:title "Title #2"
                      :description "Description #2"
                      :assignee "Assignee #2"
                      :applicant "Applicant #2"}
          request (r/create-application-handler new-record)]
      ;; Index is correct
      (is (= (get-in request [:body :id])
             2))
      ;; Contain all passed fields
      (is (clojure.set/superset? (into #{} (:body request))
                                 (into #{} new-record)))))
  (testing "Update a record"
    (let [record-id 2
          new-record {:title "Unique title #2"
                      :description "Description #2"
                      :assignee "Assignee #2"
                      :applicant "Applicant #2"
                      :date "10/03/2024"}
          update-request (r/update-application-handler record-id new-record)
          select-all-request (r/get-applications-handler)]
      ;; New title is correct
      (is (= (get-in update-request [:body :title])
             (:title new-record)))
      ;; DB contains the updated record
      (is (seq
           (filter #(= (:title new-record) (:title %))
                   (:body select-all-request))))))

  (testing "Delete a record"
    (let [record-id 2
          delete-request (r/delete-application-handler record-id)
          select-all-request (r/get-applications-handler)]
      (is (= (:body delete-request) nil))
      ;; DB does not contain the deleted record
      (is (empty?
           (filter #(= (:id record-id) (:id %))
                   (:body select-all-request)))))))
