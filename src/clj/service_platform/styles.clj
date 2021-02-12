(ns service-platform.styles
  (:require [garden-watcher.def :refer [defstyles]]))

(defstyles style
  [:body {::font-family "'Lato', sans-serif"
          ::color "white"
          ::background-color "teal"}]

  [:h1 {:font-size "60px"
        :margin "0px"
        :padding "0px"}]

  [:h3 {:margin "0px"
        :padding "0px"
        :color "white"}]

  [:.wrap {:width "500px"
           :position "absolute"
           :top "50%"
           :left "50%"
           :transform "translate(-50%, -50%)"
           :vertical-align "middle"}]

  [".wrap div" {:position "relative"
                :margin "50px 0"}]

  [:label {:position "absolute"
           :top "0"
           :font-size "24px"
           :margin "10px"
           :padding "0 10px"
           :background-color "teal"
           :-webkit-transition "top .2s ease-in-out, font-size .2s ease-in-out"
           :transition "top .2s ease-in-out, font-size .2s ease-in-out"}]

  [:.active {:top "-25px"
             :font-size "20px"}]

  ["input[type=text]" {:width "100%"
                       :padding "15px"
                       :border "1px solid white"
                       :font-size "16px"
                       :background-color "teal"
                       :color "white"}]

  ["input[type=text]" :focus {:outline "none"}]

  [:button {:font-size "20px"
            :width "200px"
            :color "white"
            :shadow "none"
            :border "1px dotted white"
            :outline "none !important"
            :background-color "transparent"}])