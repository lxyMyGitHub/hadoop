package cn.itcast.hadoop.rpc;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Builder;
import org.apache.hadoop.ipc.RPC.Server;

public class Starter {

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        Configuration conf = new Configuration();
        Builder builder = new RPC.Builder(conf);
        builder.setBindAddress("weekend110").setPort(10000).setInstance(new LoginServiceImpl());
        builder.setProtocol(LoginServiceInterface.class);
        Server server = builder.build();
        server.start();
    }

}
