(ns service-platform.config
  (:require [environ.core :refer [env]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults site-defaults]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]]
            [ring.middleware.logger :refer [wrap-with-logger]]))

(defn config []
  {:http-port  (Integer. (or (env :port) 10555))
   :site-middleware [[wrap-defaults site-defaults]]
   :api-middleware [[wrap-defaults api-defaults]
                    wrap-reload
                    wrap-multipart-params
                    wrap-with-logger
                    wrap-gzip]})
