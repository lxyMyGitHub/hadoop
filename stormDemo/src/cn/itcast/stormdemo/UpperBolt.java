package cn.itcast.stormdemo;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class UpperBolt extends BaseBasicBolt{

    
    //消息到来，重复调用
    //执行业务逻辑
    @Override
    public void execute(Tuple tuple, BasicOutputCollector collector) {
        //先取出上一个组件传递过来的数据
        String goodsName  = tuple.getString(0);
        //将商品名称转换成大写
        String goodsName_upper = goodsName.toUpperCase();
        
        //将转换完成的商品名称发送给下一个组件
        collector.emit(new Values(goodsName_upper));
    }

    //声明该bolt组件要发出去逇tuple数据
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("uppername"));
    }

}
