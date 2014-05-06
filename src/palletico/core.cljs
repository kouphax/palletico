(ns palletico.core
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :refer [GET]]))

(enable-console-print!)

(def selected-palette
  (atom nil))

(defn mini-palette [colours]
  [:div.mini-palette
    (for [colour colours]
      [:div { :key colour
              :style { :background-color  colour } }])])

(defn mega-palette [colours]
  [:div.mega-palette
    (for [colour colours
          :let [width     (/ 100 (count colours))
                width-pct (str width "%")]]
      [:div { :key colour
              :style { :background-color  colour
                       :width             width-pct
                       :display           :inline-block
                       :height            "100%" } }])])

; use sidr
(defn palette-list [palettes]
  [:ul
    (for [[name colours] palettes]
      [:li { :key name }
        [:a.palette-name { :on-click #(reset! selected-palette colours)
                           :href "#" }  name]
        [mini-palette colours]])])

(defn application [pallettes]
  [:div#application
    [palette-list pallettes]
    [mega-palette @selected-palette]])

(defn render-application [pallettes]
  (reagent/render-component
    [application pallettes]
    (.-body js/document)))

(GET "/palletes.json" { :handler render-application
                        :response-format :json
                        :error-handler println })

