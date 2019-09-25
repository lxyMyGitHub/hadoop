package cn.itcast.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import cn.itcast.spout.MessageScheme;
import cn.itcast.stormbolt.WordSpliter;
import cn.itcast.stormbolt.WriterBolt;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

public class KafkaTopo {

    public static void main(String[] args) {

        String topic = "sufei";
        String zkRoot = "/kafka-storm";
        String spoutId = "KafkaSpout";
        ZkHosts brokerHosts = new ZkHosts("weekend109:2181,weekend110:2181,weekend111:2181");
        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, topic, zkRoot, spoutId);
        spoutConfig.forceFromStart = true;
        spoutConfig.scheme = new SchemeAsMultiScheme(new MessageScheme());
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout(spoutId, new KafkaSpout(spoutConfig));
        builder.setBolt("word-spilter", new WordSpliter()).shuffleGrouping(spoutId);
        builder.setBolt("writer", new WriterBolt(),4).fieldsGrouping("word-spilter",new Fields("word"));
        Config conf = new Config();
        conf.setNumWorkers(4);
//        conf.setNumAckers(0);
        conf.setDebug(false);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("sufei-topo", conf, builder.createTopology());
        
    }

}
