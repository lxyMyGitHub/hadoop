package cn.itcast.hadoop.mr.flowsum;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FlowSumReducer extends Reducer<Text, FlowBean, Text, FlowBean>{

    //框架每传递一组数据，运行一次程序
    //<13823239393，{flowBean,flowBean,flowBean...}>
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {

        long up_flow_count = 0;
        long down_flow_count = 0 ;
        for (FlowBean bean : values) {
            up_flow_count += bean.getUp_flow();
            down_flow_count += bean.getD_flow();
        }
        
        context.write(key, new FlowBean(key.toString(),up_flow_count,down_flow_count));
    
    
    }
}
