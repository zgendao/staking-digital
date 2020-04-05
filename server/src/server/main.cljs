(ns server.main
  (:require
    ["ccxt" :as ccxt]
    ["ccxws" :as ccxws]
    ["ws" :as ws]
    ["pikola" :as pikola]
    [brave.swords :as x]
    [cognitect.transit :as t]
    ))

(defn ws-subscribe [xc pair callback]
  (let [
        exchange
        (case xc
          :bibox (ccxws/bibox.)
          :binance (ccxws/binance.)
          :binanceje (ccxws/binanceje.)
          :binanceus (ccxws/binanceus.)
          :bitfinex (ccxws/bitfinex.)
          :bitflyer (ccxws/bitflyer.)
          :bitmex (ccxws/bitmex.)
          :bitstamp (ccxws/bitstamp.)
          :bittrex (ccxws/bittrex.)
          :cex (ccxws/cex.)
          :coinbase (ccxws/coinbasepro.)
          :coinex (ccxws/coinex.)
          ;:ethfinex (ccxws/ethfinex.)
          :ftx (ccxws/ftx.)
          :gateio (ccxws/gateio.)
          :gemini (ccxws/gemini.)
          :hitbtc (ccxws/hitbtc2.)
          :huobi (ccxws/huobipro.)
          :kucoin (ccxws/kucoin.)
          :kraken (ccxws/kraken.)
          :okex (ccxws/okex3.)
          :poloniex (ccxws/poloniex.)
          :upbit (ccxws/upbit.)
          :zb (ccxws/zb.)
          nil)
        market
        (case pair
          :btc-gbp
          (case xc
            :binanceje (clj->js {:id "BTCGBP", :base "BTC", :quote "GBP"})
            )
          :btc-eur
          (case xc
            :binanceje (clj->js {:id "BTCEUR", :base "BTC", :quote "EUR"})
            :bitstamp (clj->js {:id "btceur", :base "BTC", :quote "EUR"})
            ;:bitflyer (clj->js {:id "BTC_USD", :base "BTC", :quote "USD"})
            )
          :btc-usd
          (case xc
            :bibox (clj->js {:id "BTC_USDT", :base "BIX_BTC", :quote "BIX_USDT"})
            :binance (clj->js {:id "BTCUSDT", :base "BTC", :quote "USDT"})
            :binanceus (clj->js {:id "BTCUSDT", :base "BTC", :quote "USDT"})
            :bitfinex (clj->js {:id "BTCUSD", :base "BTC", :quote "USDT"})
            :bitmex (clj->js {:id "XBTUSD", :base "XBT", :quote "USD"})
            :bitstamp (clj->js {:id "btcusd", :base "BTC", :quote "USD"})
            :bittrex (clj->js {:id "USD-BTC", :base "USD", :quote "BTC"})
            :coinbase (clj->js {:id "BTC-USD", :base "BTC", :quote "USD"})
            :coinex (clj->js {:id "BTCUSDT", :base "BTC", :quote "USDT"})
            :ftx (clj->js {:id "BTC-PERP", :base "BTC", :quote "PERP"})
            :gemini (clj->js {:id "BTCUSD", :base "BTC", :quote "USD"})
            :huobi (clj->js {:id "btcusdt", :base "btc", :quote "usdt"})
            :kucoin (clj->js {:id "BTC-USDT", :base "BTC", :quote "USDT"})
            :okex (clj->js {:id "BTC-USDT", :base "BTC", :quote "USDT"})
            :poloniex (clj->js {:id "USDT_BTC", :base "USDT", :quote "BTC"})
            :upbit (clj->js {:id "USDT-BTC", :base "USDT", :quote "BTC"})
            :zb (clj->js {:id "btc_usdt", :base "BTC", :quote "USDT"})
            ;Needs API
            ;:cex (clj->js {:id "BTC/USD", :base "BTC", :quote "USD"})
            ;:gateio (clj->js {:id "BTC_USD", :base "BTC", :quote "USD"})
            ;:hitbtc (clj->js {:id "BTC_USDT", :base "BTC", :quote "USDT"})
            ;:kraken (clj->js {:id "XBT/USD", :base "XBT", :quote "USD"})
            
            ;:ethfinex (clj->js {:id "BTCUSDT", :base "BTC", :quote "USDT"})
          (clj->js {}))
          (clj->js {}))
        ]
    (.on
      exchange
      "trade" 
      (fn [market]
        (let [market (x/obj->clj market)
              data {
                    :name :trade
                    :pair pair
                    :side (if (= (get market "side") "buy") :buy :sell)
                    :amount 
                    (cond
                      (= (get market "exchange") "BitMEX")
                      (double (/ (x/safe-read (get market "amount"))
                                 (x/safe-read (get market "price"))))
                      :else (x/safe-read (get market "amount")))
                    :price (x/safe-read (get market "price"))
                    :exchange (get market "exchange")
                    :unix (get market "unix")
                    }]
          (callback data))))

    (.subscribeTrades exchange market)
  
    (when-not (#{:bittrex :gemini} xc) 
      (.on 
        exchange
        "l2snapshot"
        (fn [snapshot]
          (let [
                snapshot (x/obj->clj snapshot)
                data 
                {
                 :name :book
                 :pair pair
                 :asks (mapv (fn [x] (let [x (x/obj->clj x)] {:price (x/safe-read (get x "price")) :size (x/safe-read (get x "size"))})) (get snapshot "asks"))
                 :bids (mapv (fn [x] (let [x (x/obj->clj x)] {:price (x/safe-read (get x "price")) :size (x/safe-read (get x "size"))})) (get snapshot "bids")) 
                 :exchange (get snapshot "exchange")
                 :unix (get snapshot "timestampMs")
                 }
                ]
            (callback data))))
      (.subscribeLevel2Snapshots exchange market)
      )
    ) 
  )

(defonce storage (atom []))

(defn main! []
  ;(println "Available CCXT REST APIs:")
  ;(println (str ccxt/exchanges))
  (println "Start..")
  
  (let [wss (new ws/Server #js {:port 8080 :path "/btc-usd"})]
    (.on
      wss "connection"
      (fn connection [ws]
        (.on
          ws "message" 
          (fn incoming [message] 
            (.log js/console "received: %s" message)
            ))
        (.send ws (t/write (t/writer :json) {:teszt "Üzenet"}))
        ))

    (let
      [trigger
       (->
         (pikola)
         (.EverySecond 2)
         (.OnMillisecond 333)
         (.OnFire 
           (fn []
             (.forEach 
               (.-clients wss)
               (fn [client]
                 (.send client
                        (t/write (t/writer :json) {:teszt "Broadcast!"})
                        )))
             )
           ))]
      (.Start trigger)
      )


  
    (doseq [xc [:bibox :binance :binanceus :bitfinex :bitmex :bitstamp
              :bittrex :coinbase :coinex :ftx :gemini
              :huobi :kucoin :okex :poloniex :upbit :zb]]
    (ws-subscribe
      xc :btc-usd 
      (fn [data] 
        (case (:name data)
          :trade
          (when (< 1 (:amount data))
          (.forEach 
            (.-clients wss)
            (fn [client]
              (.send client
                     (t/write (t/writer :json) data)
                     ))))
          :book
          (.forEach 
            (.-clients wss)
            (fn [client]
              (.send client
                     (t/write (t/writer :json) data)
                     )))
        ))
      ))

    
    ;(set!
    ;  (.-broadcast wss)
    ;  (fn broadcast
    ;    [msg]
    ;    (.log js/console msg)
    ;    (.forEach 
    ;      (.-clients wss)
    ;      (fn [client] (.send client msg)))))

    )
  )

(defn reload! []
  (println "Code updated.")
  )


