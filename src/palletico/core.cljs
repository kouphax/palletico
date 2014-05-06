(ns palletico.core
  (:require [reagent.core :as reagent :refer [atom]]))

(def palettes
  (atom { "greener" ["#FFFFFF" "#999999" "#333333" "#000000"]
          "lettuce" ["#BBBCCC" "#FDFDFD" "#CECEFE"]
          "snot"    ["#F33F3F" "#333999" "#3332233" "#030303"] }))

(def selected-palette
  (atom nil))

(defn mini-palette [colours]
  [:div.mini-palette
    (for [colour colours]
      [:div { :style { :background-color  colour } }])])


(defn mega-palette [colours]
  [:div.mega-palette
    (for [colour colours]
      [:div { :style { :background-color  colour
                       :width             (str (/ 100 (count colours)) "%")
                       :display           :inline-block
                       :height            "100%" } }])])

(defn palette-list [palettes]
  [:ul
    (for [[name colours] palettes]
      [:li
        [:a.palette-name { :on-click #(reset! selected-palette colours)
                           :href "#" }  name]
        [mini-palette colours]])])

(defn application []
  [:div#application
    [palette-list @palettes]
    [mega-palette @selected-palette]])

(reagent/render-component [application]
                          (.-body js/document))



