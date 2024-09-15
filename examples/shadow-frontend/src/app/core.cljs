(ns app.core
  (:require
    [reagent.core :as r]
    [reagent.dom :as rdom]))

(defonce state (r/atom {:val 0}))

(defn app []
  [:div
   [:h1 "Hello world"]
   [:p "Value: " [:code (:val @state)]]
   [:button {:on-click #(swap! state update :val dec)} "Down"]
   [:button {:on-click #(swap! state update :val inc)} "Up"]])

(defn init []
  (rdom/render [app]
               (.getElementById js/document "app")))

(defn ^:export reload []
  (init))
