(defproject flambo-kafka-streaming-example "0.1.0-SNAPSHOT"
  :description "A basic example of using flambo with Spark 
                Streamings's Kafka interface"
  :url "https://github.com/joshrotenberg/flambo-kafka-streaming-example"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main flambo-kafka-streaming-example.core
  :auto-clean false
  :profiles {:uberjar {:aot :all}
             :provided {:dependencies
                        [[org.apache.spark/spark-core_2.10 "1.1.1"]]}}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [yieldbot/flambo "0.4.0"]
                 [org.apache.spark/spark-streaming_2.10 "1.1.1"]
                 [org.apache.spark/spark-streaming-kafka_2.10 "1.1.1"]
                 ;; flambo's streaming.clj expects flume to be there
                 ;; even though we aren't using it ... TODO: submit
                 ;; PR to break up the streaming ns into each type
                 [org.apache.spark/spark-streaming-flume_2.10 "1.1.1"]
                 [clj-kafka "0.2.8-0.8.1.1"]])
