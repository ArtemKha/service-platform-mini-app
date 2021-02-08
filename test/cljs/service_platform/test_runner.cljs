(ns service-platform.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [service-platform.core-test]
   [service-platform.common-test]))

(enable-console-print!)

(doo-tests 'service-platform.core-test
           'service-platform.common-test)
