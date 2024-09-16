(ns index
  (:require
   ;[MyComponent :as MyComponent]
   ["react-dom/client" :as rdom]
   ["react" :refer [useState]]))

(defn TestComponent []
  (let [[state setState] (useState 0)]
    #jsx [:div "You clicked " state "times"
          [:button {:onClick #(setState (inc state))}
           "Click me"]]))

(let [root (rdom/createRoot (js/document.getElementById "app"))]
  (.render root #jsx [TestComponent]))
