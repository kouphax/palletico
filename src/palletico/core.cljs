(ns palletico.core
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :refer [GET]]))

(enable-console-print!)

(defn button [state]
  (let [visible? (:list-visible @state)]
    [:a.menu-button
      { :on-click #(swap! state assoc :list-visible (not visible?)) }
      (if visible? "↑" "↓")]))

(defn menu [state]
  (let [handler (fn [value] #(swap! state assoc :selected-value value :list-visible false)) ]
  [:ul { :style { :display (if (:list-visible @state) "inherit" "none") } }
    [:li { :on-click (handler "1") } "one"]
    [:li { :on-click (handler "2") } "two"]
    [:li { :on-click (handler "3") } "three"]
    [:li { :on-click (handler "4") } "four"]]))

(defn selected-value [state]
  [:div (:selected-value @state)])

(defn container []
  (let [state (atom { :list-visible false
                      :selected-value "" })]
    (fn []
      [:div
       [button state]
       [menu state]
       [selected-value state]])))

(GET "/palletes.json" { :handler (reagent/render-component
                                   [container]
                                   (.-body js/document))
                        :response-format :json
                        :error-handler println })





;(def selected-palette
;  (atom nil))
;
;(defn mini-palette [colours]
;  [:div.mini-palette
;    (for [colour colours]
;      [:div { :key colour
;              :style { :background-color  colour } }])])
;
;(defn mega-palette [colours]
;  [:div.mega-palette
;    (for [colour colours
;          :let [width     (/ 100 (count colours))
;                width-pct (str width "%")]]
;      [:div { :key colour
;              :style { :background-color  colour
;                       :width             width-pct
;                       :display           :inline-block
;                       :height            "100%" } }])])
;
;; use sidr
;(defn palette-list [palettes]
;  [:ul
;    (for [[name colours] palettes]
;      [:li { :key name }
;        [:a.palette-name { :on-click #(reset! selected-palette colours)
;                           :href "#" }  name]
;        [mini-palette colours]])])
;
;(defn application [pallettes]
;  [:div#application
;    [palette-list pallettes]
;    [mega-palette @selected-palette]])
;
;(defn render-application [pallettes]
;  (reagent/render-component
;    [application pallettes]
;    (.-body js/document)))
;
;(GET "/palletes.json" { :handler render-application
;                        :response-format :json
;                        :error-handler println })
;
