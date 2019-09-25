package cn.itcast.spout;

import java.io.UnsupportedEncodingException;
import java.util.List;

import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class MessageScheme implements Scheme{

    private static final long serialVersionUID = 1145488038440789661L;

    @Override
    public List<Object> deserialize(byte[] bytes) {
        // TODO Auto-generated method stub
        try {
            String msg = new String(bytes,"UTF-8");
            return new Values(msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Fields getOutputFields() {
        // TODO Auto-generated method stub
        return new Fields("msg");
    }

}
