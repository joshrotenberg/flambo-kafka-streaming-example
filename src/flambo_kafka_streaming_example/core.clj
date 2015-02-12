(ns flambo-kafka-streaming-example.core
  (:require [flambo.conf :as conf]
            [flambo.api :as f]
            [flambo.streaming :as fs])
  (:require [clj-kafka.producer :as p]
            [clj-kafka.zk :as zk])
  (:require [clojure.string :as s])
  (:gen-class))

(def master "local[*]")
(def app-name "fkse")
(def conf {})
(def env {
           "spark.executor.memory" "1G",
           "spark.files.overwrite" "true"})

(defn produce-lines
  "Publishes lines from the text to kafka, keyed on a random word in the line with the full line as
  the value."
  [frequency]
  (let [brokers (zk/brokers {"zookeeper.connect" "localhost:2181"})
        broker-list (zk/broker-list brokers)
        producer (p/producer {"metadata.broker.list" broker-list})
        topic "test"
        lines (s/split (slurp "resources/data.txt") #"\n")]
    (loop []
      (p/send-messages producer
                       (map #(p/message topic
                                        (.getBytes (rand-nth (s/split % #" ")))
                                        (.getBytes %)) lines))
      (Thread/sleep frequency)
      (recur))))


(defn -main
  [& args]
  (let [c (-> (conf/spark-conf)
              (conf/master master)
              (conf/app-name "adapters")
              (conf/set "spark.akka.timeout" "300")
              (conf/set-executor-env env))
        ssc (fs/streaming-context c 2000)
        stream (fs/kafka-stream :streaming-context ssc
                                :zk-connect "localhost:2181"
                                :group-id "word-count"
                                :topic-map {"test" 1})]

    ;; in a separate thread, pull lines from data.txt and randomly publish them into kafka
    (future (produce-lines 1000))
    
    (-> stream
        (fs/map (memfn _2))
        (fs/flat-map (f/fn [l] (s/split l #" ")))
         (fs/map (f/fn [w] [w 1]))
         (fs/reduce-by-key-and-window (f/fn [x y] (+ x y)) (* 10 60 1000) 2000)
         (fs/print))

    (.start ssc)
    (.awaitTermination ssc)))
