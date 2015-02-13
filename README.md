# flambo-kafka-streaming-example

Spark. Kafka. Streaming. Flambo. Clojure. etc.

I've been wanting to toy with this stuff for a while. I'm a big fan of
simple but useful self contained examples and I couldn't find one, so
here it is, for posterity if nothing else. Hopefully this gets you
rolling. If you see anything weird or if it doesn't work, please let
me know and I'll try to fix it for future generations.

Also have a look at [streaming
tweets](https://gist.github.com/arnaudsj/f1967a7d66609c094447).

This is mostly analogous to the Spark Streaming Kafka example, but
implemented (obviously) in Clojure using
[flambo](https://github.com/yieldbot/flambo). I also took the liberty
of including [clj-kafka](https://github.com/pingles/clj-kafka) to
automatically publish some stuff to Kafka so you don't have to.

Here is what you'll need:

* Have a look at the
[source](https://github.com/joshrotenberg/flambo-kafka-streaming-example/blob/master/src/flambo_kafka_streaming_example/core.clj). Clojure's
thread-first macro combined with flambo's Spark API wrappers make the
actual processing pretty easy to build up gradually, and you can
easily comment out steps and see what would happpen without changing
much. Clojure ftw.

* Download and run Kafka (and zookeeper) if you don't already have a zookeeper/Kafka
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

* Download and unpack a pre built
[Spark](http://spark.apache.org/). You won't need to run anything but
you will run this self-contained example with bin/spark-submit.

* Clone this repo, cd in and build an uberjar:
```
> git clone https://github.com/joshrotenberg/flambo-kafka-streaming-example.git
> cd flambo-kafka-streaming-example/
> lein uberjar
```

* Now run the example with spark-submit and point it at the class and jar to run:
```
<path to spark>/bin/spark-submit --class  flambo_kafka_streaming_example.core \
target/flambo-kafka-streaming-example-0.1.0-SNAPSHOT-standalone.jar
```

* Be amazed at the amount of stuff scrolling by. To see some of the
word count output, look for the lines that look like:

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
