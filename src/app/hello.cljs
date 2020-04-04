(ns app.hello
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


(def memory (local-storage (atom {}) :prefs))

(def storage (r/atom {:trades []}))

(def ws (new js/WebSocket (str "ws://95.217.184.184:8080/btc-usd")))

(set!
  (.-onmessage ws)
  (fn [event]
    (let [data (t/read (t/reader :json) (.-data event))]
      
      (case (:name data)
        :trade (swap! storage update :trades conj data)
        nil)
      
      )))


(defn exp [x n]
     (if (zero? n) 1
         (* x (exp x (dec n)))))


(defn statistics []
  (r/create-class
    {
     :component-did-mount 
     (fn []
(let [
      currentCurrency "PRV-pUSDT"
      currentInterval 21600
      URL (.-location js/document)
      params (.-searchParams URL)

      options
  #js
   {:localization
      #js
       {:timeFormatter
          (fn [businessDayOrTimestamp]
            (.format (moment (* businessDayOrTimestamp 1000)) "MMM Do hh a"))}}


   watermarkDefault
  #js
   {:color "rgba(11, 94, 29, 0.4)",
    :visible true,
    :fontSize 20,
    :horzAlign "left",
    :vertAlign "top"}

 candles (.getElementById js/document "candles")

candlesChart
  (charts/createChart
    candles
    (js/Object.assign
      #js {}
      options
      #js {:width (.-clientWidth candles)}
      #js {:height 400}))

candlestickSeries (.addCandlestickSeries candlesChart)

_
(.applyOptions
  candlestickSeries
  #js {:priceFormat #js {:precision 6, :minMove 0.000001}})

data
      (clj->js
        [])
      ]
     
      (.setData candlestickSeries data)
      (.applyOptions
        candlesChart
        (js->clj
         {
		:layout {
			:backgroundColor "#2B2B43"
			:lineColor "#2B2B43"
			:textColor "#D9D9D9"
		}
		:crosshair {
			:color "#758696"
		}
		:grid {
			:vertLines {
				:color "#2B2B43"
			}
			:horzLines {
				:color "#363C4E"
			}
		}
          :watermark
            (js/Object.assign
              (clj->js {})
              watermarkDefault
              #js {:text currentCurrency})})))

   )
     
     :reagent-render (fn []
 
   [:div.container.z-depth-4
    {:style {:border-radius "10px" :margin-bottom "50px" :padding "10px" :background "white"}}
    [:div [:div#candles]]
    ]
                       
                       )}
    ) )


(defn tabs []
  (r/create-class
    {
     :component-did-mount 
     (fn []
       (materialize/Tabs.init (js/document.getElementById "tabs") #js {})
       )
     :reagent-render
     (fn []
  [:div.container
   [:ul#tabs.tabs.z-depth-1
    [:li.tab.col.s3 [:a.indigo-text {:href "#test-swipe-1"} "My nodes"]]
    [:li.tab.col.s3 [:a {:href "#test-swipe-2"} "Market data"]]
    ]
   [:div#test-swipe-1.col.s12
    
    ]
   [:div#test-swipe-2.col.s12
    
    ]]
  )}))

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

(defn trades []
  [:ul.collection.z-depth-1
   (map
     (fn [trade]
       [:li
        {:class (str "collection-item "
                     (case (:side trade)
                       :buy "green lighten-5"
                       :sell "red lighten-5"
                       ))
         :style {:position "relative"}} 
        [:p.center 
         "$ "(int (:price trade))
         ]
        [:p {:style {:position "absolute" :left "30px" :top "10px"}} 
         [:b (pprint/cl-format nil "~,2f" (:amount trade))"₿"]
         " at "(:exchange trade)
         ]
        [:div {:style {:position "absolute" :right "30px" :top "20px"}}
         (case (:side trade)
           :buy [:i.material-icons.green "arrow_drop_up"]
           :sell [:i.material-icons.red "arrow_drop_down"]
           nil)]
        ])
     (reverse (take-last 10 (:trades @storage)))
     )
   ]
  )

(defn hello []
  [:<> 
   
   [:div.banner
    {:style 
     {:opacity 1
      :margin-bottom "20px"
      :background "url(city.jpeg)"
      :background-size "cover" :background-position "bottom"
      :height "800px" :width "100%"
      :position "relative"
      :display "flex"
      :justify-content "center"
      :align-items "center"
      :flex-direction "column"
      }}
    [:div.indigo.darken-3 {:style {:z-index 0 :width "100%" :height "100%" :position "absolute" :top 0 :left 0
                   :opacity 0.8
                                   ;:background-color "rgba(0,0,0,0.4)"
                   }}]
    [:h1 {:style {:z-index 1 :color "white"}} "Staking Digital"]
    [:h4 {:style {:z-index 1 :color "white" :margin 0 :opacity 0.8}}"accessible. simple. powerful."]
    [:h4 {:style {:z-index 1 :color "white" :margin 0}} "High-efficiency crypto services"]
    ]
    
     
   ;[statistics]
   [:div.container 
    {:style {:margin-top "60px" :margin-bottom "60px"}}
    [:h3 "Whale Watch"]
    [trades]]
   
   ;[tabs]
[:footer.page-footer.indigo.darken-3
  [:div.container
   [:div.row
    [:div.col.l6.s12
     [:h5.white-text "Staking Digital"]
     [:p.grey-text.text-lighten-4
      "is your long-term partner for digital asset statistics & staking services."]]
    ;[:div.col.l4.offset-l2.s12
    ; [:h5.white-text "Links"]
    ; [:ul
    ;  [:li [:a.grey-text.text-lighten-3 {:href "#!"} "Link 1"]]
    ;  [:li [:a.grey-text.text-lighten-3 {:href "#!"} "Link 2"]]
    ;  [:li [:a.grey-text.text-lighten-3 {:href "#!"} "Link 3"]]
    ;  [:li [:a.grey-text.text-lighten-3 {:href "#!"} "Link 4"]]]]
    ]]
  [:div.footer-copyright
   [:div.container
    "\n            © 2020 All rights reserved.\n            "
    [:a.grey-text.text-lighten-4.right {:href "https://zgen.hu"} "Z Gen Kibernetika"]]]]
])


