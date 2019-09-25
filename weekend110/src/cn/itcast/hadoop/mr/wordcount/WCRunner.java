package cn.itcast.hadoop.mr.wordcount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 用来描述一个特定的作业 一个job
 * @author CFCA
 *
 */
public class WCRunner {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job =Job.getInstance(conf);
        //设置整个job所用的那些类在那个job下
        job.setJarByClass(WCRunner.class);
        
        job.setMapperClass(WCMapper.class);
        job.setReducerClass(WCReducer.class);
        
        
        //针对mapper和reduce的设置，二者都有效
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        
        
        //针对mapper的设置
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        
        //指定源原始数据放在哪里
        FileInputFormat.setInputPaths(job, new Path("/wc/srcdata/"));
        //处理结果的输出数据存放路径
        FileOutputFormat.setOutputPath(job, new Path("/wc/output/"));
        //将job提交给集群运行 true 打印进度 false 不打印
        job.waitForCompletion(true);
    }

}
