(ns server-reagent
  (:require
    ["express$default" :as express]
    [reagent.dom.server :refer [render-to-static-markup]]))

(defn homepage []
  [:<>
   [:h1 "Homepage"]
   [:p "Hello world."]
   [:button {:on-click #(js/alert "Hello")} "Say hello"]])

(defn serve-homepage [_req res]
  (-> res
      (.send (render-to-static-markup [homepage]))))

(let [server (express)]
  (.get server "/" serve-homepage)
  (.listen server 8000)
  (print "Server running on port 8000."))
