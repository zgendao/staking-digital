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

(def ws (new js/WebSocket
             ;(str "ws://localhost:8080/btc-usd")
             (str "ws://95.217.184.184:8080/btc-usd")
             ))

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
   [:ul.tabs.z-depth-1 {:id id}
    (map-indexed
      (fn [i {:keys [title]}]
        [:li.tab.col.s3 [:a.indigo-text {:href (str "#swipe-"id i)} title]])
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
                       :buy "teal lighten-5"
                       :sell "pink lighten-5"
                       ))
         :style {:position "relative"}} 
        [:h5.center 
         "$"(int (:price trade))
         ]
        [:p {:style {:position "absolute" :left "30px" :top "10px"}} 
         [:b (pprint/cl-format nil "~,2f" (:amount trade))"₿"]
         " at "(:exchange trade)
         ]
        [:div {:style {:position "absolute" :right "30px" :top "20px"}}
         (case (:side trade)
           :buy [:i.material-icons.teal "arrow_drop_up"]
           :sell [:i.material-icons.pink "arrow_drop_down"]
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
      ;:margin-bottom "20px"
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
   [:div.white-text.deep-purple.darken-1 {:style {:margin 0 :position "relative" :display "block"
                                  :padding-top "60px" :padding-bottom "60px"}}
    [:div.container
     [:h3 "Maintain Your Growth"]
     
     [:div.card.white-text.pink.darken-1
      {:style {:margin-top "40px" :padding-top "30px" :padding-bottom "40px"}}
      [:div.container.card-content
       [:span.card-title {:style {:margin-bottom "30px"}}
        "Compound digital assets by 5% - 100% annually through staking."
        ]
       
      [tabs "tabs1"
       [
        {:title "What is staking?"
         :text [
                [:p "Proof of Stake is replacing Proof of Work aka cryptocurrency mining. Investors of PoS currencies can participate in governance and collect staking rewards of $2.5 BN+ annually."]
                ]}
        {:title "How can I earn?"
         :text [
                [:p "Staking Digital automatically allocates your assets to the highest yielding opportunities."]
                [:p "We make it simple for investors to choose a specific basket of opportunities that best suits their risk profile, and then automatically allocates capital to maximize the yield."]]} 
        ]
       ]
       ]
      ]

     [:div.card.white-text.yellow.darken-2
      {:style {:margin-top "40px" :padding-top "30px" :padding-bottom "40px"}}
      [:div.container.card-content
       [:span.card-title {:style {:margin-bottom "30px"}}
        "Cryptocurrency exchanges integrated under a single dashboard."
        ]
       
      [tabs "tabs2"
       [
        {:title "Recap your gains" :text ""}
        {:title "Discover new assets" :text ""}
        {:title "Sell at the best time" :text ""}
        ]
       ]
       ]
      ]

     ]
    ]
   
   
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
    [:a.grey-text.text-lighten-4.right {:href "https://zgen.hu"} "Z Gen Kibernetika"]]]
  
     [:h6.center.indigo.darken-4.yellow-text {:style {:margin-bottom 0 :padding "17px"}}
      [:i {:style {:opacity 1}} "for Investors by Investors"]]
  ]
])


