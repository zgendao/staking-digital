(ns app.stats
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


(defn trades []
  [:ul.collection.z-depth-1.deep-purple-text.darken-4 {:style {:border-width "0px"}}
   (map
     (fn [trade]
       [:li 
        {:key (rand-int 300000) 
         :class (str "collection-item "
                     (case (:side trade)
                       :buy "teal lighten-5"
                       :sell "pink lighten-5"
                       ))
         :style {:position "relative" :opacity 1}} 
        [:h6.center 
         [:b "$"(int (:price trade))
         ]]
        [:p {:style {:position "absolute" :left "30px" :top "3px"}} 
         [:b (pprint/cl-format nil "~,2f" (:amount trade))"₿"]
         " at "(:exchange trade)
         ]
        [:div {:style {:position "absolute" :right "30px" :top "20px"}}
         (case (:side trade)
           :buy [:i.material-icons.teal.white-text "arrow_drop_up"]
           :sell [:i.material-icons.pink.white-text "arrow_drop_down"]
           nil)]
        ])
     (reverse (take-last 12 (:trades @storage)))
     )
   ]
  )
