(ns server
  (:require ["express$default" :as express]))

(defn component-homepage [req]
  #html
  [:html
   [:body "Hello "
    (or (:name req.query)
        "unknown")]
   [:ul
    [:li 1]
    [:li 2]
    [:li 3]
    (map (fn [i]
           #html [:li (inc i)]) [3 4 5])]])

(defn serve-homepage [req res]
  (.send res (str (component-homepage req))))

(let [app (express)]
  (.get app "/$" #(serve-homepage %1 %2))
  (.use app "/" (.static express "frontend/public"))
  (.listen app 8000)
  (println "Server running on port 8000."))
