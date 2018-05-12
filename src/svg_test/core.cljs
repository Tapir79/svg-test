(ns svg-test.core
  (:require [reagent.core :as reagent :refer [atom]]))

;console writing
(enable-console-print!)

; the variables of the atom can change
(defonce app-state (atom {:text "Svg test!" :fill-number 0}))

; these variables are immutable
(def color1 "blue")
(def color2 "yellow")

; the main code
(defn svg-test []
  [:div
   ;calling the text variable in the app-state atom
   [:h1 (:text @app-state)]
   [:h3 "Click the circle!"]
   [:div
    ; svg inside the div
    [:svg {:width 100 :height 100}
     ; circle inside the svg
     [:circle {:cx 50
               :cy 50
               :r 40
               :stroke "green"
               :stroke-width 4
               ;filling the circle with
               ; color1 if fill-number is zero
               ; else with color2
               :fill (if (zero? (get-in @app-state [:fill-number]))
                       color1
                       color2)
               ;when clicking the circle
               ;if fill-number is zero change fill-number and text
               ; else print to console "Not zero"
               :on-click (fn circle-click [e]
                           (if (zero? (get-in @app-state [:fill-number]))
                             (do
                               (swap!
                                 app-state update-in [:fill-number]
                                 inc)
                               (swap!
                                 app-state assoc-in [:text]
                                 (str "Changed to " color2 "!")))
                             (println "Not zero")
                             ))}]]]
   [:div
    ; Reset button.
    ; If fill-number equals 1 decrease is by 1
    ; else print to console "Not one"
    [:button {:on-click (fn reset-click [e]          
                          (if (== (get-in @app-state [:fill-number]) 1)
                            (do
                              (swap! app-state update-in [:fill-number]
                                     dec)
                              (swap! app-state assoc-in [:text]
                                     (str "Back to " color1 "!")))
                            (println "Not one")
                            ))}
     (str "Reset to color " color1)]]
   ])

; we pass svg-test function to the reagent render-component
(reagent/render-component [svg-test]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  )