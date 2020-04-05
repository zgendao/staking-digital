(ns app.components
  (:require
    [reagent.core :as r]
    [app.api :as api]
    [app.util :refer [memory storage exp]]
    ["lightweight-charts" :as charts]
    ["moment" :as moment]
    ["materialize-css" :as materialize]
    [cljs.pprint :as pprint]
    [alandipert.storage-atom :refer [local-storage]]
    [cognitect.transit :as t]
    ))




(defn tabs [id coll]
  (r/create-class
    {
     :component-did-mount 
     (fn []
       (materialize/Tabs.init (js/document.getElementById id) #js {})
       )
     :reagent-render
     (fn []
  [:div
   [:ul.tabs.z-depth-1.deep-purple.darken-3 {:id id}
    (map-indexed
      (fn [i {:keys [title]}]
        [:li.deep-purple.darken-3.yellow-text.tab.col.s3
         [:a.yellow-text {:href (str "#swipe-"id i)} title]])
      coll)
    ]
   (map-indexed
    (fn [i {:keys [text]}] 
      [:div.col.s12
       {:id (str "swipe-"id i) :style {:padding-top "30px"}}
    (seq text)
    ])
    coll)
   ]
  )}))
