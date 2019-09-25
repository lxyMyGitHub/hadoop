package cn.itcast.hadoop.mr.ii;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class InverseIndexStepTwo {

    
    
    //mapper 取出每条信息，分类发送
    public static class StepTwoMapper extends Mapper<LongWritable, Text, Text, Text>{
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();
            String[] fields = line.split("\t");
            String message = fields[0];
            String counter = fields[1];
            String[] messages = message.split("-->");
            String keyWord = messages[0];
            String fileName = messages[1];
            context.write(new Text(keyWord), new Text(fileName + "-->" + counter));
        }
    }
    //<hello {a.txt-->3,b.txt-->3,c.txt-->3}>
    //reduce 合并统计每条信息
    public static class StepTwoReducer extends Reducer<Text, Text, Text, Text>{
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            
            String message = "";
            for (Text value : values) {
                message = message + " " + value.toString();
            }
            context.write(key, new Text(message));
        }
    }
    
    //jobRunner
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(InverseIndexStepTwo.class);
        job.setMapperClass(StepTwoMapper.class);
        job.setReducerClass(StepTwoReducer.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        Path output = new Path(args[1]);
        FileSystem fs = FileSystem.get(conf);
        if(fs.exists(output)) {
            fs.delete(output,true);
        }
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true)?0:1);
    }
    
}
