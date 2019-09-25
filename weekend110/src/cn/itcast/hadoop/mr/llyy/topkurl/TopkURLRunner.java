package cn.itcast.hadoop.mr.llyy.topkurl;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider.Text;

import cn.itcast.hadoop.mr.flowsum.FlowBean;


public class TopkURLRunner extends Configured implements Tool{

    @Override
    public int run(String[] args) throws Exception {

        Configuration conf = new Configuration();
        Job job  = Job.getInstance(conf);
        job.setJarByClass(TopkURLRunner.class);
        
        job.setMapperClass(TopkURLMapper.class);
        job.setReducerClass(TopkURLReducer.class);
        
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
        
        
        return job.waitForCompletion(true)?0:1;
    }
    
    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new TopkURLRunner(),args);
        System.exit(res);
    }

}
