package cn.itcast.kafka;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class ProducerDemo {

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("zk.connect", "weekend113:2181,weekend114:2181,weekend115:2181");
        props.put("metadata.broker.list", "weekend113:9091,weekend114:9091,weekend115:9091");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);

        // 发送业务消息
        // 读取文件 读取文件内存数据 读socket端口
        for (int i = 1; i <= 10; i++) {
            Thread.sleep(500);
            producer.send(new KeyedMessage<String,String>("order","order ABC "+i+" times."));
        }
    }
}
