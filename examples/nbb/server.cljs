(ns server
  (:require
    ["express$default" :as express]))

(let [server (express)]
  (.get server "/" (fn [_req res] (.send res "Hello world!")))
  (.listen server 8000)
  (print "Server running on port 8000."))
