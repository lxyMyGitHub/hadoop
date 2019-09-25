package cn.itcast.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

public class ConsumerDemo {

    private static final String topic = "order";
    private static final Integer threads = 1;
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("zookeeper.connect", "weekend113:2181,weekend114:2181,weekend115:2181");
        props.put("group.id", "1111");
        props.put("auto.offset.reset", "smallest");
        ConsumerConfig config = new ConsumerConfig(props);
        ConsumerConnector consumerConn = Consumer.createJavaConsumerConnector(config);
        HashMap<String, Integer> topicCountMap = new HashMap<String,Integer>();
        topicCountMap.put(topic, threads);
        topicCountMap.put("order", threads);
        topicCountMap.put("mysons", threads);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConn.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
        for (final KafkaStream<byte[], byte[]> kafkaStream : streams) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    for (MessageAndMetadata<byte[], byte[]> mm : kafkaStream) {
                        String msg = new String(mm.message());
                        System.out.println(msg);
                    }
                }}).start();
        }
    }
}
