# flambo-kafka-streaming-example

Spark. Kafka. Streaming. Flambo. Clojure. etc.

I'd been wanting to toy with this stuff for a while. Lots of moving parts, so here is a basic example for posterity.

This is mostly analogous to the Spark Streaming Kafka example, but
implemented (obviously) in Clojure using
[flambo](https://github.com/yieldbot/flambo). Here is what you'll need

1. Download and run Kafka (and zookeeper) if you don't already have a zookeeper/kafka
installation to point at. Follow the
[quickstart](http://kafka.apache.org/documentation.html#quickstart)
through step 3 and you should be ready (this example uses the 'test'
topic).
```
> tar -xzf kafka_2.10-0.8.2.0.tgz
> cd kafka_2.10-0.8.2.0
> bin/zookeeper-server-start.sh config/zookeeper.properties
# in another shell
> bin/kafka-server-start.sh config/server.properties
# and in yet another shell
> bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
> bin/kafka-topics.sh --list --zookeeper localhost:2181
```

2. Download and unpack a pre built [Spark](http://spark.apache.org/). You won't need to run anything but you will
run this self-contained example with bin/spark-submit.

3. Clone this repo, cd in and build an uberjar:
```
> git clone https://github.com/joshrotenberg/flambo-kafka-streaming-example.git
> cd flambo-kafka-streaming-example/
> lein uberjar
```

4. Now run the example with spark-submit and point it at the class and jar to run:
```
<path to spark>/bin/spark-submit --class  flambo_kafka_streaming_example.core \
target/flambo-kafka-streaming-example-0.1.0-SNAPSHOT-standalone.jar
```

5. Be amazed at the amount of stuff scrolling by. To see some of the word count output, look for the lines that look like:
```
-------------------------------------------
Time: 1423783528000 ms
-------------------------------------------
...
```

## License

Copyright © 2015 Josh Rotenberg

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
