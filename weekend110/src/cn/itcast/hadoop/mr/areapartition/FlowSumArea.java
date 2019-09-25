package cn.itcast.hadoop.mr.areapartition;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import cn.itcast.hadoop.mr.flowsum.FlowBean;

/**
 * 流量汇总分区
 * @author CFCA
 *
 */
public class FlowSumArea {

    
    
    /**
     * 对流量原始日志进行流量统计，将不同省份的用户统计结果输出到不同文件
     * 需要自定义改造俩个机制
     * 1、改造分区的逻辑，自定义一个partitioner
     * 2、自定义reducer task的并发任务数
     * @author CFCA
     *
     */
    public static class FlowSumAreaMapper extends Mapper<LongWritable , Text, Text, FlowBean>{
        @Override
        protected void map(LongWritable key, Text value,Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] fields = StringUtils.split(line, "\t");
            FlowBean flowBean = new FlowBean(fields[1],Long.valueOf(fields[7]),Long.valueOf(fields[8]));
            context.write(new Text(fields[1]), flowBean);
        }
    }
    
    public static class FlowSumAreaReducer extends Reducer<Text, FlowBean, Text, FlowBean>{
        
        @Override
        protected void reduce(Text key, Iterable<FlowBean> values, Context context)
                throws IOException, InterruptedException {

            long up_flow_counter = 0 ;
            long d_flow_counter = 0;
            for (FlowBean bean : values) {
                up_flow_counter += bean.getUp_flow();
                d_flow_counter += bean.getD_flow();
            }
            context.write(key,new FlowBean(key.toString(),up_flow_counter,d_flow_counter));
        }
    }
    
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(FlowSumArea.class);
        job.setMapperClass(FlowSumAreaMapper.class);
        job.setReducerClass(FlowSumAreaReducer.class);
        job.setPartitionerClass(AreaPartitioner.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);
        job.setNumReduceTasks(6);
        
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true)?0:1);
    }
}
