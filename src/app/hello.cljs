(ns app.hello
  (:require
    [reagent.core :as r]
    [app.api :as api]
    [app.util :refer [memory storage exp mobile?]]
    [app.charts :refer [forecast-chart live-chart]]
    [app.stats :refer [trades]]
    [app.components :as component]
    ["lightweight-charts" :as charts]
    ["moment" :as moment]
    ["materialize-css" :as materialize]
    [cljs.pprint :as pprint]
    [alandipert.storage-atom :refer [local-storage]]
    [cognitect.transit :as t]
    ))




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
     [:h3 "Maintain portfolio growth"]
     
     [:div.card.white-text.deep-purple.darken-4
      {:style {:margin-top "40px" :padding-top "30px" :padding-bottom "40px"}}
      [:div.container.card-content
       [:span.card-title {:style {:margin-bottom "30px"}}
        "Compound digital assets by "[:b.yellow-text "3% - 30% annually"]" through staking."
        ]
       
      [component/tabs "tabs1"
       [
        {:title "What is staking?"
         :text [
                [:p "Proof of Stake is replacing Proof of Work aka cryptocurrency mining. Investors of PoS currencies can participate in governance and collect staking rewards of $2.5 BN+ annually."]
                ]}
        {:title "How can I earn?"
         :text [
                [:p "Staking Digital automatically allocates your assets to the highest yielding opportunities."]
                [:br]
                [:p "We make it simple for investors to choose a specific basket of opportunities that best suits their risk profile, and then automatically allocates capital to maximize the yield."]]} 
        ]
       ]
       ]
      ]

     [:div.card.white-text.deep-purple.darken-2
      {:style {:margin-top "40px" :padding-top "30px" :padding-bottom "40px"}}
      [:div.container.card-content
       [:span.card-title {:style {:margin-bottom "30px"}}
        "Cryptocurrency exchanges integrated under a single dashboard."
        ]
       
      [component/tabs "tabs2"
       [
        {:title "Recap your gains" :text [[:p "Always know exactly how much your portfolio worths in fiat and follow the progression of your invested assets."]]}
        {:title "Discover new assets" :text [[:p "Keeping track with the digital assets is hard. New opportunities will be recommended to you after first announcements."]]}
        {:title "Sell at the best time" :text [[:p "A smart recommendation system helps you make the decision about an exit."]]}
        ]
       ]
       ]
      ]

     ]
    ]
   
   [:div.white-text 
    {:style 
     {
      :background-color "#100841"
      :margin 0 :position "relative" :display "block"
                                  :padding-top "60px" :padding-bottom "60px"}}
    [:div.container
     [:h3.pink-text {:style {:text-align (when-not (mobile?) "center") :margin-bottom "60px"}} "Measure your potential"]
    

     [forecast-chart] 
     ]
    ]

[:div.black.darken-4 {:style {:margin "0px" :padding-bottom "60px" :padding-top "30px"}}
   [:div.container 
     [:h3.yellow-text {:style {:margin-bottom "60px"}} "Live Demo"]
         [:div.input-field.col.s12
    [:input#email.validate.yellow-text {:type "text"}]
    [:label.yellow-text {:for "email"} "Email Address"]]
      
      ]
     [:center 
     [:a.waves-effect.waves-light.btn-large.yellow.darken-1.pink-text
      {:on-click 
       #(let [
              
              ]
         )
       }
      [:i.material-icons.left "trending_up"]
    "Request Access"]]
     ]

    ;[live-chart] 
   [:div.deep-purple.darken-1 
    {:style {:position "relative" :min-height "1032px" :overflow "hidden" :padding-top "60px" :padding-bottom "60px"}}
    [:div.container
     [:h3.white-text "Whale Watch"]
     [:h6.grey-text "Live market data from the 17 leading cryptocurrency exchanges."]
    [:br]
     [trades]]
    [:div
     {
      :style {:z-index 0
              :position "absolute"
              :bottom 0
              :right "-50%"
              :width "100%"
              :display "flex"
              :align-items "flex-end"
              }
      }
     (map
       (fn [{:keys [price size]}]
         [:div.pink {:key (str "sell"price)
                     :style {:max-height "600px"
                        :width "10px" :height (str (* 10 size)"px")}}]
         )
      (sort-by :price 
       (mapv
         (fn [[price volumes]]
           {:price price :size (apply + (map :size volumes))}
           )
         (group-by :price
                 (mapv
                   (fn [x] (update x :price int ))
                   (apply concat
                        (mapv (fn [[k d]] (:asks d)) (:books @storage))
                        ))
                 ))
       ))
     ]


    [:div
     {
      :style {:z-index 0
              :position "absolute"
              :bottom 0
              :left "-50%"
              :width "100%"
              :display "flex"
              :align-items "flex-end"
              :flex-direction "row-reverse"
              }
      }
     (reverse
     (map
       (fn [{:keys [price size]}]
         [:div.teal {:key (str "bux"price)
                     :style {:max-height "600px"
                        :width "10px" :height (str (* 10 size)"px")}}]
         )
      (sort-by :price 
       (mapv
         (fn [[price volumes]]
           {:price price :size (apply + (map :size volumes))}
           )
         (group-by :price
                 (mapv
                   (fn [x] (update x :price int ))
                   (apply concat
                        (mapv (fn [[k d]] (:bids d)) (:books @storage))
                        ))
                 ))
       )))
     ]

    ]
   
[:footer.page-footer.black.darken-3
 [:div.container
   [:div.row
    [:div.col.l6.s12
     [:h5.white-text "Staking Digital"]
     [:p.grey-text.text-lighten-4
      "is a simple, but powerful digital asset management platform."
      ]]
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
  
;     [:h6.center.black.darken-4.yellow-text {:style {:margin-bottom 0 :padding "17px"}}
;      [:i {:style {:opacity 1}} "for Investors by Investors"]]
  ]
])


