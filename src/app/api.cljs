(ns app.api
  (:require
    [app.util :refer [connect memory storage exp]]
    ["axios" :as axios]
    ["uuid" :as uuid]
    ))


(connect 
  "btc-usd"
  (fn [data]
    (case (:name data)
      :trade (swap! storage update :trades conj data)
      :book (swap! storage assoc-in [:books (:exchange data)] data)
      nil)))
