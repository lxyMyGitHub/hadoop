package cn.itcast.stormdemo;

import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class RandomWordSpout extends BaseRichSpout{
    
    private SpoutOutputCollector collector;
    
    //模拟数据
    String[] words = {"iphone","huawei","xiaomi","sony","mate","sumsung","meizu","ztl","oppo","vivo"};
    

    //怎么发出下一个Tuple
    //不断的往下一个组件发送tuple消息
    //这里面是spout组件的核心逻辑
    @Override
    public void nextTuple() {
        
        //可以送kafka消息队列中拿到数据，此处模拟数据从words数组随机获取
        Random random = new Random();
        int index = random.nextInt(words.length);
        
        //随机获取一个商品名称
        String goodsName = words[index];
        
        //将商品封装成tuple，发送个一下一个消息组件
        collector.emit(new Values(goodsName));
        
        //每发送一条消息，休眠0.5秒
        Utils.sleep(500);
    }

    //初始化方法，当前类第一次实例化的时候调用一次open方法
    //初始化方法，在spout组件实例化时调用一次
    @Override
    public void open(Map map, TopologyContext context, SpoutOutputCollector collector) {

        this.collector = collector;
    }

    //声明Tuple里面的变量
    //声明本spout组件发送出去的tuple中的数据的字段名称
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        declarer.declare(new Fields("orignname"));
    }

}
