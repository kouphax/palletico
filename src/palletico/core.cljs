(ns palletico.core
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :refer [GET]]))

(enable-console-print!)

(defn button [state]
  (let [visible? (:list-visible @state)]
    [:button.menu-button
      { :on-click #(swap! state assoc :list-visible (not visible?)) }
      (if visible? "↑" "↓")]))

(defn mini-pallette [colours]
  [:div.mini-palette
    (for [colour colours]
      [:div { :key colour
              :style { :background-color  colour } }])])

(defn menu [state]
  (let [handler (fn [value] #(swap! state assoc :selected value :list-visible false)) ]
    [:ul { :style { :display (if (:list-visible @state) "inherit" "none") } }
      (for [[name pallette] (:pallettes @state)]
        [:li { :on-click (handler pallette) }
          [:div.pallette-name name]
          [mini-pallette pallette]])]))

(defn selected [state]
  [:div.mega-palette
   (let [colours (:selected @state)]
    (for [colour  colours
            :let [width     (/ 100 (count colours))
                  width-pct (str width "%")]]
        [:div { :key colour
                :style { :background-color  colour
                          :width            width-pct
                          :display          :inline-block
                          :height           "100%" } } ] ))])

(defn container [pallettes]
  (let [state (atom { :list-visible false
                      :selected     nil
                      :pallettes    pallettes })]
    (fn []
      [:div
       [:div.menu
         [button state]
         [menu state]]
       [selected state]])))

(GET "/palletes.json" { :handler #(reagent/render-component
                                    [container %]
                                    (.-body js/document))
                        :response-format :json
                        :error-handler println })
