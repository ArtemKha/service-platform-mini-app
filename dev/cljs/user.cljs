(ns cljs.user
  (:require [service-platform.core]
            [service-platform.system :as system]))

(def go system/go)
(def reset system/reset)
(def stop system/stop)
(def start system/start)
