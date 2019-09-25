package cn.itcast.stormdemo;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;

/**
 * 组织各个组件，行程一个完整的处理流程，就是所谓的topology（类似于MapReduce的job）
 * 并且将改topology提交给storm集群去运行
 * @author CFCA
 *
 */
public class TopoMain {

    
    
    public static void main(String[] args) throws Exception {
        
        TopologyBuilder builder = new TopologyBuilder();
        //将我买的spout组件设置到topology中
        builder.setSpout("randomspout", new RandomWordSpout(),4);
        //将大写的bolt组件设置到topology中，并设置接收消息的分组id
        builder.setBolt("upperbolt", new UpperBolt(),4).shuffleGrouping("randomspout");
        //将添加后缀的组件设置到topology,并设置接收消息的分组id
        builder.setBolt("suffixbolt", new SuffixBolt(),4).shuffleGrouping("upperbolt");
       
        //用builder创建一个topology
        StormTopology demotopo = builder.createTopology();
        
        //配置一些topology在集群中运行的参数
        Config conf = new Config();
        conf.setNumWorkers(4);
        conf.setDebug(true);
        conf.setNumAckers(0);
        
        //将这个topology提交给storm集群运行
        StormSubmitter.submitTopology("demotopo", conf, demotopo);
        
        
    }

}
