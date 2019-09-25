package cn.itcast.stormdemo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class SuffixBolt extends BaseBasicBolt{
    
    private FileWriter fileWriter ;
    //在bolt组件运行过程中只会被调用一次
    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        try {
            fileWriter = new FileWriter("/home/hadoop/stormdata/"+UUID.randomUUID());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    //改bolt组件的核心处理逻辑
    //每收到一个tuple消息，就会被调用一次
    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {

        //先拿到上一个组件的信息
        String upperName = tuple.getString(0);
        //为上一个组件发来的商品名称添加后缀
        String suffix_Name = upperName + "_itisok";
        
        try {
            fileWriter.write(suffix_Name);
            fileWriter.write("\n");
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }

    //声明发送的tuple字段名称
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        //不往下发信息了，所以不声明字段了。
    }

}
