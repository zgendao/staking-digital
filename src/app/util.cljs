(ns app.util
  (:require
    [reagent.core :as r]
    ["lightweight-charts" :as charts]
    ["moment" :as moment]
    ["materialize-css" :as materialize]
    [cljs.pprint :as pprint]
    [alandipert.storage-atom :refer [local-storage]]
    [cognitect.transit :as t]
    ))


(def memory (local-storage (atom {}) :prefs))

(def storage (r/atom {:trades [] :books {}}))

(def connections (r/atom {}))

(defn connect [path callback]
  (let [
        uri 
        (str 
          ;"ws://localhost:8080/"
          "ws://95.217.184.184:8080/"
          path)
        ws (new js/WebSocket uri)
        ]
    (swap! connections assoc path ws)
    (set!
      (.-onmessage ws)
      (fn [event]
        (let [data (t/read (t/reader :json) (.-data event))]
          (callback data))))))


(defn exp [x n]
     (if (zero? n) 1
         (* x (exp x (dec n)))))

(defn deep-merge [v & vs]
  (letfn [(rec-merge [v1 v2]
            (if (and (map? v1) (map? v2))
              (merge-with deep-merge v1 v2)
              v2))]
    (when (some identity vs)
      (reduce #(rec-merge %1 %2) v vs))))

(defn mobile? []
  (< (.-innerWidth js/window) 768))
