package cn.itcast.hadoop.mr.wordcount;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
/**
 * Mapper<输入类型Key，输入类型value,输出类型key,输出类型value>
 * @author CFCA
 *
 */
public class WCMapper extends Mapper<LongWritable, Text, Text, LongWritable>{
    
    //mapreducer框架每读一行数据，就调用一次这个方法
    @Override
    protected void map(LongWritable key, Text value,Context context) throws IOException, InterruptedException {
        // TODO Auto-generated method stub
        //具体业务逻辑就写在这个方法中，而且我们业务要求处理的数据已经被框架传递进来，在方法参数中key-value
        //key是这一行数据的起始便宜量 value是这一行的文本内容
        String line = value.toString();
        String[] words = StringUtils.split(line," ");
        for (String word : words) {
            context.write(new Text(word), new LongWritable(1));
        }
    }
}
