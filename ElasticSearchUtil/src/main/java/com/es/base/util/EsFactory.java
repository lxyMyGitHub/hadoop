package com.es.base.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;


public class EsFactory {
    private static String clusterName = "elasticsearch";
    private static String esServers = "127.0.0.1:9300";
    public static TransportClient client = null;

    static {
        FileInputStream stream = null;
        Properties properties = new Properties();
        try {
            stream = new FileInputStream("E:\\CFCA\\xfraud\\00-Config\\esinit.properties");
            properties.load(stream);
            clusterName = properties.getProperty("clusterName");
            esServers = properties.getProperty("esServers");
            System.out.println("clusterName is : "+clusterName);
            System.out.println("esServers is : "+esServers);
        }catch(Exception e) {
            System.out.println("读取配置文件失败。。。");
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                System.out.println("关闭数据流失败。。。");
                System.out.println(e);
            }
        }
        
    }
    

    public  TransportClient getEsClient() {
        if(client==null) {
            try {

                Settings settings = Settings.builder().put("cluster.name", clusterName).put("client.transport.sniff", true)
                        .put("transport.type", "netty3").put("http.type", "netty3").build();
                client = new PreBuiltTransportClient(settings);
                String[] addresses = esServers.split(",");
                for (String address : addresses) {
                    String[] hostInfo = address.split(":");
                    client.addTransportAddress(
                            new InetSocketTransportAddress(InetAddress.getByName(hostInfo[0]), Integer.valueOf(hostInfo[1])));
                }
            } catch (Exception e) {
                System.out.println("create elasticsearch client fail : " + e);
                shutdown();
                client = null;
            }
        }
        System.out.println("create client is successed" + client);
        return client;
    }
    
    private void shutdown() {
        try {
            if (client != null) {
                client.close();
            }
        } catch (Exception e) {
           System.out.println("close EsClient is error..."+e);
        }
    }
    
    @Test
    public void Test() {
        // TODO Auto-generated method stub
        client = getEsClient();
        shutdown();
    }
}
