(ns app.client
  (:require
    [reagent.core :as r]
    [reagent.dom :as rdom]
    [app.common :refer [common-component]]))

(defonce state (r/atom {:val 0}))

(defn app []
  [:div
   [:h1 "Hello world"]
   [:p "Value: " [:code (:val @state)]]
   [:button {:on-click #(swap! state update :val dec)} "Down"]
   [:button {:on-click #(swap! state update :val inc)} "Up"]
   [common-component]
   [:p [:a {:href "/testpage"
            :target "_BLANK"}
        "Server rendered /testpage"]]])

(defn init []
  (rdom/render [app]
               (.getElementById js/document "app")))

(defn ^:export reload []
  (init))
