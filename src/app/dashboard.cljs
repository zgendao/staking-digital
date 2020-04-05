(ns app.dashboard
  (:require
    [reagent.core :as r]
    [app.api :as api]
    ["lightweight-charts" :as charts]
    ["moment" :as moment]
    ["materialize-css" :as materialize]
    [cljs.pprint :as pprint]
    [alandipert.storage-atom :refer [local-storage]]
    [cognitect.transit :as t]
    ))


(defn landing []
  [:div
   [:nav
    [:div.nav-wrapper
    [:a.brand-logo "Incognito Market"]
     [:ul.right.hide-on-med-and-down
    [:li
     [:a
      {:href "sass.html"}
      [:i.material-icons.left "search"]
      "Link with Left Icon"]]
    [:li
     [:a
      {:href "badges.html"}
      [:i.material-icons.right "view_module"]
      "Link with Right Icon"]]]
    ]]
   ]
  )
