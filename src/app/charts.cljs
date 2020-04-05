(ns app.charts
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


(defn forecast-chart []
  (r/create-class
    {:component-did-mount
     (fn []
       (let [

    render (js/document.getElementById "forecast-chart")
chart
  (charts/createChart
     render
     #js
     {:width (.-clientWidth render)
      :height 300,
      :priceScale #js {:mode 2},
      :timeScale #js {:borderColor "rgba(197, 203, 206, 0.4)"},
      :layout #js {:backgroundColor "#100841", :textColor "#ffffff"},
      :grid
        #js
         {:vertLines
            #js
             {:color "rgba(197, 203, 206, 0.4)",
              :style (.. charts -LineStyle -Dotted)},
          :horzLines
            #js
             {:color "rgba(197, 203, 206, 0.4)",
              :style (.. charts -LineStyle -Dotted)}}})

areaSeries
  (.addAreaSeries
    chart
    #js
     {:topColor "rgba(67, 83, 254, 0.7)",
      :bottomColor "rgba(67, 83, 254, 0.3)",
      :lineColor "rgba(67, 83, 254, 1)",
      :lineWidth 2})

extraSeries
  (.addAreaSeries
    chart
    #js
     {:topColor "rgba(255, 192, 0, 0.7)",
      :bottomColor "rgba(255, 192, 0, 0.3)",
      :lineColor "rgba(255, 192, 0, 1)",
      :lineWidth 2})
             ]

(.setData
  areaSeries
  #js
   [
    #js {:time "2018-12-24", :value 146.83}
    #js {:time "2018-12-26", :value 157.17}
    #js {:time "2018-12-27", :value 156.15}
    #js {:time "2018-12-28", :value 156.23}
    #js {:time "2018-12-31", :value 157.74}
    #js {:time "2019-01-02", :value 157.92}
    #js {:time "2019-01-03", :value 142.19}
    #js {:time "2019-01-04", :value 148.26}
    #js {:time "2019-01-07", :value 147.93}
    #js {:time "2019-01-08", :value 150.75}
    #js {:time "2019-01-09", :value 153.31}
    #js {:time "2019-01-10", :value 153.8}
    #js {:time "2019-01-11", :value 152.29} #js {:time "2019-01-14", :value 150}
    #js {:time "2019-01-15", :value 153.07}
    #js {:time "2019-01-16", :value 154.94}
    #js {:time "2019-01-17", :value 155.86}
    #js {:time "2019-01-18", :value 156.82}
    #js {:time "2019-01-22", :value 153.3}
    #js {:time "2019-01-23", :value 153.92}
    #js {:time "2019-01-24", :value 152.7}
    #js {:time "2019-01-25", :value 157.76}
    #js {:time "2019-01-28", :value 156.3}
    #js {:time "2019-01-29", :value 154.68}
    #js {:time "2019-01-30", :value 165.25}
    #js {:time "2019-01-31", :value 166.44}
    #js {:time "2019-02-01", :value 166.52}
    #js {:time "2019-02-04", :value 171.25}
    #js {:time "2019-02-05", :value 174.18}
    #js {:time "2019-02-06", :value 174.24}
    #js {:time "2019-02-07", :value 170.94}
    #js {:time "2019-02-08", :value 170.41}
    #js {:time "2019-02-11", :value 169.43}
    #js {:time "2019-02-12", :value 170.89}
    #js {:time "2019-02-13", :value 170.18}
    #js {:time "2019-02-14", :value 170.8}
    #js {:time "2019-02-15", :value 170.42}
    #js {:time "2019-02-19", :value 170.93}
    #js {:time "2019-02-20", :value 172.03}
    #js {:time "2019-02-21", :value 171.06}
    #js {:time "2019-02-22", :value 172.97}
    #js {:time "2019-02-25", :value 174.23}
    #js {:time "2019-02-26", :value 174.33}
    #js {:time "2019-02-27", :value 174.87}
    #js {:time "2019-02-28", :value 173.15}
    #js {:time "2019-03-01", :value 174.97}
    #js {:time "2019-03-04", :value 175.85}
    #js {:time "2019-03-05", :value 175.53}
    #js {:time "2019-03-06", :value 174.52}
    #js {:time "2019-03-07", :value 172.5}
    #js {:time "2019-03-08", :value 172.91}
    #js {:time "2019-03-11", :value 178.9}
    #js {:time "2019-03-12", :value 180.91}
    #js {:time "2019-03-13", :value 181.71}
    #js {:time "2019-03-14", :value 183.73}
    #js {:time "2019-03-15", :value 186.12}
    #js {:time "2019-03-18", :value 188.02}
    #js {:time "2019-03-19", :value 186.53}
    #js {:time "2019-03-20", :value 188.16}
    #js {:time "2019-03-21", :value 195.09}
    #js {:time "2019-03-22", :value 191.05}
    #js {:time "2019-03-25", :value 188.74}
    #js {:time "2019-03-26", :value 186.79}
    #js {:time "2019-03-27", :value 188.47}
    #js {:time "2019-03-28", :value 188.72}
    #js {:time "2019-03-29", :value 189.95}
    #js {:time "2019-04-01", :value 191.24}
    #js {:time "2019-04-02", :value 194.02}
    #js {:time "2019-04-03", :value 195.35}
    #js {:time "2019-04-04", :value 195.69} #js {:time "2019-04-05", :value 197}
    #js {:time "2019-04-08", :value 200.1}
    #js {:time "2019-04-09", :value 199.5}
    #js {:time "2019-04-10", :value 200.62}
    #js {:time "2019-04-11", :value 198.95}
    #js {:time "2019-04-12", :value 198.87}
    #js {:time "2019-04-15", :value 199.23}
    #js {:time "2019-04-16", :value 199.25}
    #js {:time "2019-04-17", :value 203.13}
    #js {:time "2019-04-18", :value 203.86}
    #js {:time "2019-04-22", :value 204.53}
    #js {:time "2019-04-23", :value 207.48}
    #js {:time "2019-04-24", :value 207.16}
    #js {:time "2019-04-25", :value 205.28}
    #js {:time "2019-04-26", :value 204.3}
    #js {:time "2019-04-29", :value 204.61}
    #js {:time "2019-04-30", :value 200.67}
    #js {:time "2019-05-01", :value 210.52}
    #js {:time "2019-05-02", :value 209.15}
    #js {:time "2019-05-03", :value 211.75}
])

(.setData
  extraSeries
  #js
   [
    #js {:time "2018-12-24", :value 36.65}
    #js {:time "2018-12-26", :value 38.06}
    #js {:time "2018-12-27", :value 37.73}
    #js {:time "2018-12-28", :value 38.13}
    #js {:time "2018-12-31", :value 37.92}
    #js {:time "2019-01-02", :value 38.59}
    #js {:time "2019-01-03", :value 38.81}
    #js {:time "2019-01-04", :value 40.03}
    #js {:time "2019-01-07", :value 40.16}
    #js {:time "2019-01-08", :value 40.03}
    #js {:time "2019-01-09", :value 40.36} #js {:time "2019-01-10", :value 40.7}
    #js {:time "2019-01-11", :value 40.24}
    #js {:time "2019-01-14", :value 40.42}
    #js {:time "2019-01-15", :value 40.24}
    #js {:time "2019-01-16", :value 40.12}
    #js {:time "2019-01-17", :value 40.13}
    #js {:time "2019-01-18", :value 40.76}
    #js {:time "2019-01-22", :value 40.08}
    #js {:time "2019-01-23", :value 40.12}
    #js {:time "2019-01-24", :value 40.11}
    #js {:time "2019-01-25", :value 40.11}
    #js {:time "2019-01-28", :value 39.57} #js {:time "2019-01-29", :value 40.2}
    #js {:time "2019-01-30", :value 40.67}
    #js {:time "2019-01-31", :value 41.12}
    #js {:time "2019-02-01", :value 41.34}
    #js {:time "2019-02-04", :value 41.39}
    #js {:time "2019-02-05", :value 42.82}
    #js {:time "2019-02-06", :value 43.04} #js {:time "2019-02-07", :value 42.7}
    #js {:time "2019-02-08", :value 42.49}
    #js {:time "2019-02-11", :value 42.21}
    #js {:time "2019-02-12", :value 42.42}
    #js {:time "2019-02-13", :value 42.64}
    #js {:time "2019-02-14", :value 41.87}
    #js {:time "2019-02-15", :value 42.29}
    #js {:time "2019-02-19", :value 42.38}
    #js {:time "2019-02-20", :value 42.48}
    #js {:time "2019-02-21", :value 42.29}
    #js {:time "2019-02-22", :value 42.46}
    #js {:time "2019-02-25", :value 42.51}
    #js {:time "2019-02-26", :value 42.52}
    #js {:time "2019-02-27", :value 42.84}
    #js {:time "2019-02-28", :value 42.65}
    #js {:time "2019-03-01", :value 42.58}
    #js {:time "2019-03-04", :value 42.64}
    #js {:time "2019-03-05", :value 42.74} #js {:time "2019-03-06", :value 42.7}
    #js {:time "2019-03-07", :value 42.63}
    #js {:time "2019-03-08", :value 42.25}
    #js {:time "2019-03-11", :value 42.33}
    #js {:time "2019-03-12", :value 42.46}
    #js {:time "2019-03-13", :value 43.83}
    #js {:time "2019-03-14", :value 43.95}
    #js {:time "2019-03-15", :value 43.87}
    #js {:time "2019-03-18", :value 44.24}
    #js {:time "2019-03-19", :value 44.47}
    #js {:time "2019-03-20", :value 44.53}
    #js {:time "2019-03-21", :value 44.53}
    #js {:time "2019-03-22", :value 43.95}
    #js {:time "2019-03-25", :value 43.53}
    #js {:time "2019-03-26", :value 43.82}
    #js {:time "2019-03-27", :value 43.59}
    #js {:time "2019-03-28", :value 43.63}
    #js {:time "2019-03-29", :value 43.72}
    #js {:time "2019-04-01", :value 44.09}
    #js {:time "2019-04-02", :value 44.23}
    #js {:time "2019-04-03", :value 44.23}
    #js {:time "2019-04-04", :value 44.15}
    #js {:time "2019-04-05", :value 44.53}
    #js {:time "2019-04-08", :value 45.23}
    #js {:time "2019-04-09", :value 44.99}
    #js {:time "2019-04-10", :value 45.04}
    #js {:time "2019-04-11", :value 44.87}
    #js {:time "2019-04-12", :value 44.67}
    #js {:time "2019-04-15", :value 44.67}
    #js {:time "2019-04-16", :value 44.48}
    #js {:time "2019-04-17", :value 44.62}
    #js {:time "2019-04-18", :value 44.39}
    #js {:time "2019-04-22", :value 45.04}
    #js {:time "2019-04-23", :value 45.02}
    #js {:time "2019-04-24", :value 44.13}
    #js {:time "2019-04-25", :value 43.96}
    #js {:time "2019-04-26", :value 43.31}
    #js {:time "2019-04-29", :value 43.02}
    #js {:time "2019-04-30", :value 43.73}
    #js {:time "2019-05-01", :value 43.08}
    #js {:time "2019-05-02", :value 42.63}
    #js {:time "2019-05-03", :value 43.08}
])


         ))
     :reagent-render
     (fn []
       [:div

         [:div.input-field.col.s12
    [:input#initial.validate.white-text {:type "number"}]
    [:label {:for "initial"} "Initial investment in EUR"]]

         [:form {:style {:display "flex"}}
    [:p.z-depth-3
     {:style {:margin "30px" :padding "30px"}}
     [:label
      [:input
       {:type "radio", :name "group1"}]
      [:span.white-text "I am a conservative investor or represent an institutional fund"]
      [:br]
      [:b "Only AAA cryptocurrency protocols will be involved in your portfolio."]
      ]]
    [:p.z-depth-3
     {:style {:margin "30px" :padding "30px"}}
     [:label
      [:input {:type "radio", :name "group1"}]
      [:span.white-text "I take risk occasionally"]
      [:br]
      [:b "Your portfolio will be expanded to new cryptocurrency stars with estabilished ecosystems."]
      ]]
    [:p.z-depth-3
     {:style {:margin "30px" :padding "30px"}}
     [:label
      [:input.with-gap {:type "radio", :name "group1"}]
      [:span.white-text "Include high-risk assets in my portfolio"]
      [:br]
      [:b "Diversify to the hidden gems, carefully selected by our experts."]
      ]]
    ]




        [:div#forecast-chart]])}))



(defn live-chart []
  (r/create-class
    {:component-did-mount
     (fn []
       (let [
    render (js/document.getElementById "live-chart")
       chart
  (charts/createChart
    render
     #js
     {:width (.-clientWidth render)
      :height 300,
      :priceScale
        #js {:scaleMargins #js {:top 0.3, :bottom 0.25}, :borderVisible false},
      :layout #js {:backgroundColor "#000000", :textColor "#d1d4dc"},
      :grid
        #js
         {:vertLines #js {:color "rgba(42, 46, 57, 0)"},
          :horzLines #js {:color "rgba(42, 46, 57, 0.6)"}}})

areaSeries
  (.addAreaSeries
    chart
    #js
     {:topColor "rgba(38,198,218, 0.56)",
      :bottomColor "rgba(38,198,218, 0.04)",
      :lineColor "rgba(38,198,218, 1)",
      :lineWidth 2})

volumeSeries
  (.addHistogramSeries
    chart
    #js
     {:color "#26a69a",
      :lineWidth 2,
      :priceFormat #js {:type "volume"},
      :overlay true,
      :scaleMargins #js {:top 0.8, :bottom 0}})
             ]

(.setData
  areaSeries
  #js
   [#js {:time "2018-10-19", :value 54.90}
    #js {:time "2018-10-22", :value 54.98}
    #js {:time "2018-10-23", :value 57.21}
    #js {:time "2018-10-24", :value 57.42}
    #js {:time "2018-10-25", :value 56.43}
    #js {:time "2018-10-26", :value 55.51}
    #js {:time "2018-10-29", :value 56.48}
    #js {:time "2018-10-30", :value 58.18}
    #js {:time "2018-10-31", :value 57.09}
    #js {:time "2018-11-01", :value 56.05}
    #js {:time "2018-11-02", :value 56.63}
    #js {:time "2018-11-05", :value 57.21}
    #js {:time "2018-11-06", :value 57.21}
    #js {:time "2018-11-07", :value 57.65}
    #js {:time "2018-11-08", :value 58.27}
    #js {:time "2018-11-09", :value 58.46}
    #js {:time "2018-11-12", :value 58.72}
    #js {:time "2018-11-13", :value 58.66}
    #js {:time "2018-11-14", :value 58.94}
    #js {:time "2018-11-15", :value 59.08}
    #js {:time "2018-11-16", :value 60.21}
    #js {:time "2018-11-19", :value 60.62}
    #js {:time "2018-11-20", :value 59.46}
    #js {:time "2018-11-21", :value 59.16}
    #js {:time "2018-11-23", :value 58.64}
    #js {:time "2018-11-26", :value 59.17}
    #js {:time "2018-11-27", :value 60.65}
    #js {:time "2018-11-28", :value 60.06}
    #js {:time "2018-11-29", :value 59.45}
    #js {:time "2018-11-30", :value 60.30}
    #js {:time "2018-12-03", :value 58.16}
    #js {:time "2018-12-04", :value 58.09}
    #js {:time "2018-12-06", :value 58.08}
    #js {:time "2018-12-07", :value 57.68}
    #js {:time "2018-12-10", :value 58.27}
    #js {:time "2018-12-11", :value 58.85}
    #js {:time "2018-12-12", :value 57.25}
    #js {:time "2018-12-13", :value 57.09}
    #js {:time "2018-12-14", :value 57.08}
    #js {:time "2018-12-17", :value 55.95}
    #js {:time "2018-12-18", :value 55.65}
    #js {:time "2018-12-19", :value 55.86}
    #js {:time "2018-12-20", :value 55.07}
    #js {:time "2018-12-21", :value 54.92}
    #js {:time "2018-12-24", :value 53.05}
    #js {:time "2018-12-26", :value 54.44}
    #js {:time "2018-12-27", :value 55.15}
    #js {:time "2018-12-28", :value 55.27}
    #js {:time "2018-12-31", :value 56.22}
    #js {:time "2019-01-02", :value 56.02}
    #js {:time "2019-01-03", :value 56.22}
    #js {:time "2019-01-04", :value 56.36}
    #js {:time "2019-01-07", :value 56.72}
    #js {:time "2019-01-08", :value 58.38}
    #js {:time "2019-01-09", :value 57.05}
    #js {:time "2019-01-10", :value 57.60}
    #js {:time "2019-01-11", :value 58.02}
    #js {:time "2019-01-14", :value 58.03}
    #js {:time "2019-01-15", :value 58.10}
    #js {:time "2019-01-16", :value 57.08}
    #js {:time "2019-01-17", :value 56.83}
    #js {:time "2019-01-18", :value 57.09}
    #js {:time "2019-01-22", :value 56.99}
    #js {:time "2019-01-23", :value 57.76}
    #js {:time "2019-01-24", :value 57.07}
    #js {:time "2019-01-25", :value 56.40}
    #js {:time "2019-01-28", :value 55.07}
    #js {:time "2019-01-29", :value 53.28}
    #js {:time "2019-01-30", :value 54.00}
    #js {:time "2019-01-31", :value 55.06}
    #js {:time "2019-02-01", :value 54.55}
    #js {:time "2019-02-04", :value 54.04}
    #js {:time "2019-02-05", :value 54.14}
    #js {:time "2019-02-06", :value 53.79}
    #js {:time "2019-02-07", :value 53.57}
    #js {:time "2019-02-08", :value 53.95}
    #js {:time "2019-02-11", :value 54.05}
    #js {:time "2019-02-12", :value 54.42}
    #js {:time "2019-02-13", :value 54.48}
    #js {:time "2019-02-14", :value 54.03}
    #js {:time "2019-02-15", :value 55.16}
    #js {:time "2019-02-19", :value 55.44}
    #js {:time "2019-02-20", :value 55.76}
    #js {:time "2019-02-21", :value 56.15}
    #js {:time "2019-02-22", :value 56.92}
    #js {:time "2019-02-25", :value 56.78}
    #js {:time "2019-02-26", :value 56.64}
    #js {:time "2019-02-27", :value 56.72}
    #js {:time "2019-02-28", :value 56.92}
    #js {:time "2019-03-01", :value 56.96}
    #js {:time "2019-03-04", :value 56.24}
    #js {:time "2019-03-05", :value 56.08}
    #js {:time "2019-03-06", :value 55.68}
    #js {:time "2019-03-07", :value 56.30}
    #js {:time "2019-03-08", :value 56.53}
    #js {:time "2019-03-11", :value 57.58}
    #js {:time "2019-03-12", :value 57.43}
    #js {:time "2019-03-13", :value 57.66}
    #js {:time "2019-03-14", :value 57.95}
    #js {:time "2019-03-15", :value 58.39}
    #js {:time "2019-03-18", :value 58.07}
    #js {:time "2019-03-19", :value 57.50}
    #js {:time "2019-03-20", :value 57.67}
    #js {:time "2019-03-21", :value 58.29}
    #js {:time "2019-03-22", :value 59.76}
    #js {:time "2019-03-25", :value 60.08}
    #js {:time "2019-03-26", :value 60.63}
    #js {:time "2019-03-27", :value 60.88}
    #js {:time "2019-03-28", :value 59.08}
    #js {:time "2019-03-29", :value 59.13}
    #js {:time "2019-04-01", :value 59.09}
    #js {:time "2019-04-02", :value 58.53}
    #js {:time "2019-04-03", :value 58.87}
    #js {:time "2019-04-04", :value 58.99}
    #js {:time "2019-04-05", :value 59.09}
    #js {:time "2019-04-08", :value 59.13}
    #js {:time "2019-04-09", :value 58.40}
    #js {:time "2019-04-10", :value 58.61}
    #js {:time "2019-04-11", :value 58.56}
    #js {:time "2019-04-12", :value 58.74}
    #js {:time "2019-04-15", :value 58.71}
    #js {:time "2019-04-16", :value 58.79}
    #js {:time "2019-04-17", :value 57.78}
    #js {:time "2019-04-18", :value 58.04}
    #js {:time "2019-04-22", :value 58.37}
    #js {:time "2019-04-23", :value 57.15}
    #js {:time "2019-04-24", :value 57.08}
    #js {:time "2019-04-25", :value 55.85}
    #js {:time "2019-04-26", :value 56.58}
    #js {:time "2019-04-29", :value 56.84}
    #js {:time "2019-04-30", :value 57.19}
    #js {:time "2019-05-01", :value 56.52}
    #js {:time "2019-05-02", :value 56.99}
    #js {:time "2019-05-03", :value 57.24}
    #js {:time "2019-05-06", :value 56.91}
    #js {:time "2019-05-07", :value 56.63}
    #js {:time "2019-05-08", :value 56.38}
    #js {:time "2019-05-09", :value 56.48}
    #js {:time "2019-05-10", :value 56.91}
    #js {:time "2019-05-13", :value 56.75}
    #js {:time "2019-05-14", :value 56.55}
    #js {:time "2019-05-15", :value 56.81}
    #js {:time "2019-05-16", :value 57.38}
    #js {:time "2019-05-17", :value 58.09}
    #js {:time "2019-05-20", :value 59.01}
    #js {:time "2019-05-21", :value 59.50}
    #js {:time "2019-05-22", :value 59.25}
    #js {:time "2019-05-23", :value 58.87}
    #js {:time "2019-05-24", :value 59.32}
    #js {:time "2019-05-28", :value 59.57}])

(.setData
  volumeSeries
  #js
   [#js
     {:time "2018-10-19", :value 19103293.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-10-22", :value 21737523.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-10-23", :value 29328713.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-10-24", :value 37435638.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2018-10-25", :value 25269995.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2018-10-26", :value 24973311.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2018-10-29", :value 22103692.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-10-30", :value 25231199.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2018-10-31", :value 24214427.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2018-11-01", :value 22533201.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2018-11-02", :value 14734412.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-11-05", :value 12733842.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-11-06", :value 12371207.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-11-07", :value 14891287.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-11-08", :value 12482392.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-11-09", :value 17365762.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-11-12", :value 13236769.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2018-11-13", :value 13047907.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2018-11-14", :value 18288710.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-11-15", :value 17147123.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-11-16", :value 19470986.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-11-19", :value 18405731.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2018-11-20", :value 22028957.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2018-11-21", :value 18482233.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2018-11-23", :value 7009050.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2018-11-26", :value 12308876.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-11-27", :value 14118867.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2018-11-28", :value 18662989.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2018-11-29", :value 14763658.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2018-11-30", :value 31142818.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2018-12-03", :value 27795428.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2018-12-04", :value 21727411.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2018-12-06", :value 26880429.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2018-12-07", :value 16948126.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2018-12-10", :value 16603356.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-12-11", :value 14991438.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2018-12-12", :value 18892182.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2018-12-13", :value 15454706.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2018-12-14", :value 13960870.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2018-12-17", :value 18902523.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2018-12-18", :value 18895777.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2018-12-19", :value 20968473.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2018-12-20", :value 26897008.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2018-12-21", :value 55413082.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2018-12-24", :value 15077207.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2018-12-26", :value 17970539.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-12-27", :value 17530977.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-12-28", :value 14771641.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2018-12-31", :value 15331758.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-01-02", :value 13969691.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-01-03", :value 19245411.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-01-04", :value 17035848.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-01-07", :value 16348982.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-01-08", :value 21425008.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-01-09", :value 18136000.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-01-10", :value 14259910.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-01-11", :value 15801548.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-01-14", :value 11342293.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-01-15", :value 10074386.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-01-16", :value 13411691.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-01-17", :value 15223854.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-01-18", :value 16802516.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-01-22", :value 18284771.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-01-23", :value 15109007.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-01-24", :value 12494109.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-01-25", :value 17806822.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-01-28", :value 25955718.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-01-29", :value 33789235.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-01-30", :value 27260036.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-01-31", :value 28585447.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-02-01", :value 13778392.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-02-04", :value 15818901.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-02-05", :value 14124794.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-02-06", :value 11391442.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-02-07", :value 12436168.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-02-08", :value 12011657.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-02-11", :value 9802798.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-02-12", :value 11227550.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-02-13", :value 11884803.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-02-14", :value 11190094.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-02-15", :value 15719416.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-02-19", :value 12272877.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-02-20", :value 11379006.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-02-21", :value 14680547.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-02-22", :value 12534431.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-02-25", :value 15051182.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-02-26", :value 12005571.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-02-27", :value 8962776.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-02-28", :value 15742971.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-03-01", :value 10942737.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-03-04", :value 13674737.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-03-05", :value 15749545.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-03-06", :value 13935530.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-03-07", :value 12644171.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-03-08", :value 10646710.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-03-11", :value 13627431.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-03-12", :value 12812980.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-03-13", :value 14168350.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-03-14", :value 12148349.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-03-15", :value 23715337.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-03-18", :value 12168133.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-03-19", :value 13462686.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-03-20", :value 11903104.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-03-21", :value 10920129.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-03-22", :value 25125385.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-03-25", :value 15463411.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-03-26", :value 12316901.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-03-27", :value 13290298.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-03-28", :value 20547060.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-03-29", :value 17283871.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-04-01", :value 16331140.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-04-02", :value 11408146.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-04-03", :value 15491724.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-04-04", :value 8776028.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-04-05", :value 11497780.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-04-08", :value 11680538.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-04-09", :value 10414416.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-04-10", :value 8782061.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-04-11", :value 9219930.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-04-12", :value 10847504.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-04-15", :value 7741472.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-04-16", :value 10239261.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-04-17", :value 15498037.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-04-18", :value 13189013.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-04-22", :value 11950365.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-04-23", :value 23488682.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-04-24", :value 13227084.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-04-25", :value 17425466.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-04-26", :value 16329727.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-04-29", :value 13984965.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-04-30", :value 15469002.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-05-01", :value 11627436.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-05-02", :value 14435436.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-05-03", :value 9388228.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-05-06", :value 10066145.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-05-07", :value 12963827.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-05-08", :value 12086743.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-05-09", :value 14835326.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-05-10", :value 10707335.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-05-13", :value 13759350.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-05-14", :value 12776175.00, :color "rgba(255,82,82, 0.8)"}
    #js
     {:time "2019-05-15", :value 10806379.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-05-16", :value 11695064.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-05-17", :value 14436662.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-05-20", :value 20910590.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-05-21", :value 14016315.00, :color "rgba(0, 150, 136, 0.8)"}
    #js {:time "2019-05-22", :value 11487448.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-05-23", :value 11707083.00, :color "rgba(255,82,82, 0.8)"}
    #js {:time "2019-05-24", :value 8755506.00, :color "rgba(0, 150, 136, 0.8)"}
    #js
     {:time "2019-05-28", :value 3097125.00, :color "rgba(0, 150, 136, 0.8)"}])
  
         ))
     :reagent-render
     (fn []
       [:div#live-chart]
       )
     })
  )
