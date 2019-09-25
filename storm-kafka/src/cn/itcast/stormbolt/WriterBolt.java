package cn.itcast.stormbolt;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class WriterBolt extends BaseBasicBolt{

    /**
     * 
     */
    private static final long serialVersionUID = 7911188065495250227L;
    private FileWriter writer = null;
    
    @Override
    public void prepare(Map map, TopologyContext context) {
        try {
            writer = new FileWriter("d://sufei" + UUID.randomUUID().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        String line = tuple.getString(0);
        try {
            writer.write(line);
            writer.write("\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        
    }

}
