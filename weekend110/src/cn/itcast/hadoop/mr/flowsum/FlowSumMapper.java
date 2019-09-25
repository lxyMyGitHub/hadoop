package cn.itcast.hadoop.mr.flowsum;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
/**
 * FlowBean是自定义的数据类型，要在hadoop各节点之间传输，应该遵循hadoop的序列化机制
 * 就必须实现hadoop相应的序列化接口
 * @author CFCA
 *
 */
public class FlowSumMapper extends Mapper<LongWritable, Text, Text, FlowBean>{

    @Override
    protected void map(LongWritable key, Text value,Context context) throws IOException, InterruptedException {

        String line = value.toString();
        String[] fields = StringUtils.split(line, "\t");
        FlowBean flowBean = new FlowBean(fields[1],Long.valueOf(fields[7]),Long.valueOf(fields[8]));
        context.write(new Text(fields[1]), flowBean);
    
    
    }
}
