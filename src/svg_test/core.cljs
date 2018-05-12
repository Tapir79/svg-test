(ns svg-test.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(defonce app-state (atom {:text "Svg test!" :fill-number 0}))

(def color1 "blue")
(def color2 "yellow")

(defn svg-test []
  [:div
   [:h1 (:text @app-state)]
   [:h3 "Click the circle!"]
   [:div
    [:svg {:width 100 :height 100}
     [:circle {:cx 50
               :cy 50
               :r 40
               :stroke "green"
               :stroke-width 4
               :fill (if (zero? (get-in @app-state [:fill-number]))
                       color1
                       color2)
               :on-click (fn circle-click [e]
                           (if (zero? (get-in @app-state [:fill-number]))
                             (do
                               (swap!
                                 app-state update-in [:fill-number]
                                 inc)
                               (swap!
                                 app-state assoc-in [:text]
                                 (str "Changed to " color2 "!")))
                             ;this is printed into the console.
                             ; If you click the circle when it is color2 this is printed out.
                             (println "Not zero")
                             ))}]]]
   [:div
    [:button {:on-click (fn reset-click [e]          
                          (if (== (get-in @app-state [:fill-number]) 1)
                            (do
                              (swap! app-state update-in [:fill-number]
                                     dec)
                              (swap! app-state assoc-in [:text]
                                     (str "Back to " color1 "!")))))}
     (str "Reset to color " color1)]]
   ])

(reagent/render-component [svg-test]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  )