package cn.itcast.hadoop.mr.flowsort;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class FlowBean implements WritableComparable<FlowBean>{

    private String phoneNB;
    private long up_flow;
    private long d_flow;
    private long s_flow;
    
    
    
    public FlowBean(String phoneNB, long up_flow, long d_flow) {
        this.phoneNB = phoneNB;
        this.up_flow = up_flow;
        this.d_flow = d_flow;
        this.s_flow = d_flow + up_flow;
    }
    
    public void set(String phoneNB, long up_flow, long d_flow) {
        this.phoneNB = phoneNB;
        this.up_flow = up_flow;
        this.d_flow = d_flow;
        this.s_flow = d_flow + up_flow;
    }
    

    public FlowBean() {
        
    }


    public String getPhoneNB() {
        return phoneNB;
    }

    public void setPhoneNB(String phoneNB) {
        this.phoneNB = phoneNB;
    }

    public long getUp_flow() {
        return up_flow;
    }

    public void setUp_flow(long up_flow) {
        this.up_flow = up_flow;
    }

    public long getD_flow() {
        return d_flow;
    }

    public void setD_flow(long d_flow) {
        this.d_flow = d_flow;
    }

    public long getS_flow() {
        return s_flow;
    }

    public void setS_flow(long s_flow) {
        this.s_flow = s_flow;
    }

    @Override
    public String toString() {
        return "" + up_flow + "\t" + d_flow + "\t" + s_flow;
    }

    //将对象数据序列化到数据流中
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(phoneNB);
        out.writeLong(up_flow);
        out.writeLong(d_flow);
        out.writeLong(s_flow);
    }

    //从数据流中反序列化出对象的数据
    //从数据流中读出对象字段时，必须跟序列化顺序一致,先序列化什么就先读什么
    @Override
    public void readFields(DataInput in) throws IOException {
        phoneNB = in.readUTF();
        up_flow = in.readLong();
        d_flow = in.readLong();
        s_flow = in.readLong();
    }


    @Override
    public int compareTo(FlowBean flowBean) {
        return this.s_flow>flowBean.s_flow?-1:1;
    }

}
