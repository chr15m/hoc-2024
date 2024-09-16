(ns app.server
  (:require
    ["express" :as express]
    [reagent.dom.server :refer [render-to-static-markup]]
    [app.common :refer [common-component]]))

(defn homepage []
  [:<>
   [:h1 "Homepage"]
   [:p "Hello world."]
   [:button {:on-click #(js/alert "Hello")} "Say hello"]
   [common-component]])

(defn serve-homepage [_req res]
  (-> res
      ; (.send "Hello world")  
      (.send (render-to-static-markup [homepage]))))

(defn init []
  (let [server (express)]
    (.get server "/testpage" #(serve-homepage %1 %2))
    (.use server "/" (.static express "public"))
    (.listen server 8000)
    (print "Server running on port 8000.")))

