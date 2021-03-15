(ns service-platform.server
  (:gen-class)
  (:require [com.stuartsierra.component :as component]
            [service-platform.components.server-info :refer [server-info]]
            [system.components.endpoint :refer [new-endpoint]]
            [system.components.handler :refer [new-handler]]
            [system.components.middleware :refer [new-middleware]]
            [service-platform.modules.application.handler :refer [application-routes]]
            [system.components.jetty :refer [new-web-server]]
            [service-platform.routes :refer [home-routes]]
            [service-platform.config :refer [config]]))


(def swagger-config
  {:ui "/swagger"
   :spec "/swagger.json"
   :options {:ui {:validatorUrl nil}
             :data {:info {:version "1.0.0", :title "Service platform – Restful API"}}}})

(defn app-system [config]
  (component/system-map
   :site-middleware (new-middleware {:middleware (:site-middleware config)})
   :api-middleware (new-middleware {:middleware (:api-middleware config)})
   :site-routes (component/using (new-endpoint home-routes) [:site-middleware])
   :api-routes (component/using (new-endpoint application-routes) [:api-middleware])
   :handler (component/using (new-handler) [:api-routes :site-routes :middleware])
   :http (-> (new-web-server (:http-port config))
             (component/using [:handler]))
   :server-info (server-info (:http-port config))))

(defn -main [& _]
  (let [config (config)]
    (-> config
        app-system
        component/start)))

