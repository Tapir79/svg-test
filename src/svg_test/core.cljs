(ns svg-test.core
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "First test!" :fill-number 0}))

(defn make-zero[x]
  (println x)
  (println (- x x))
  (- x x))

(defn reset-color []
  (make-zero (get-in @app-state [:fill-number])))

(defn fill-number []
  (get-in @app-state [:fill-number]))

  ;(reset! app-state ([:fill-number] 1 [:text] "It changed!"))     
  ;(reset! app-state ([:text] "It changed!"))                      

(defn hello-world []
  [:div
   [:h1 (:text @app-state)]
   [:h3 "Click the circle!"]
   [:button {:on-click (fn button-click [e]
                         (js/alert "You clicked me!"))
             } "Js/alert Button" ]
   [:div
    [:svg {:width 100 :height 100}
     [:circle {:cx 50
               :cy 50
               :r 40
               :stroke "green"
               :stroke-width 4
               :fill (if (zero? (get-in @app-state [:fill-number]))
                       "blue"
                       "yellow")
               :on-click (fn circle-click [e]
                           (if (zero? (get-in @app-state [:fill-number]))
                                 (do ((prn (swap! app-state update-in [:fill-number] inc))
                                            (prn (swap! app-state assoc-in [:text] "Changed!"))
                                            (println (get-in @app-state [:fill-number]))))
                                 (println "Not zero")
                                          ))}]]]
   [:div
    [:button {:on-click (fn reset-click [e]          
                          (if (== (get-in @app-state [:fill-number]) 1)
                              (swap! app-state update-in [:fill-number] dec)
                              (println (get-in @app-state [:fill-number]))))} "Reset color"]]
   ])
;(swap! player update :score + 5)
(reagent/render-component [hello-world]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
