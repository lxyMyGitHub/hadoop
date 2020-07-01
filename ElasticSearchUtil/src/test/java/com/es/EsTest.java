package com.es;

import java.text.DecimalFormat;
import java.util.*;

import com.es.base.util.DateUtil;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.junit.Test;

import com.es.base.util.EsQuery;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class EsTest {

    @Test
    public void testEsQuery() {


        EsQuery query = new EsQuery();
//        System.out.println(query);
//         getModexTest(query);
        // testDel(query);
        // putOneMsg(query);
        // testUpdate(query);
        System.out.println("testGet run begain");
        testGet(query);
//        adminApi(query);
    }
    @Test
    public void testJedis(){
        HashSet<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
        hostAndPorts.add(new HostAndPort("192.168.44.154",6379));
        hostAndPorts.add(new HostAndPort("192.168.44.154",6380));
        JedisCluster jc = new JedisCluster(hostAndPorts);
        long xfraudTradeTime4RA = System.currentTimeMillis();
        String key = "keyTest0605";
        jc.zadd(key,xfraudTradeTime4RA-5*3600000,xfraudTradeTime4RA+5*3600000+"");
        System.out.println(xfraudTradeTime4RA-5*3600000);
        jc.expire(key,3*60*60);
        Set<String> times = jc.zrangeByScore(key, xfraudTradeTime4RA - 3 * 3600000, xfraudTradeTime4RA);
        if(times!=null && times.size()>0){
            for (String time:times) {
                System.out.println(time);
            }
        }
        jc.zremrangeByScore(key,0,xfraudTradeTime4RA-3*3600000);


    }

    public void testGet(EsQuery query){
//        String endTime = "20200513191911";
//        String startTime = "20200525173566";
//        System.out.println(startTime.substring(0, 10)+"0000");
//        System.out.println("testGet run startTime" + startTime);
//        System.out.println("testGet run endTime" + endTime);
//        int size = 0;
//        query.getTradeSunAmountForTime("tradeindex", "tradeType", "tradeTime", startTime, endTime, size);
//        query.getMaxAmountLast72h("tradeindex", "tradeType", "tradeTime");
//        query.getdebit24h("tradeindex", "tradeType", "tradeTime");
//        query.getCurrCountryHistoryType72Hours_("tradeindex","tradeType",0);
        query.getHabitTradeDevice("tradeindex");

    }
    public void putOneMsg(EsQuery query) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "wyf");
        map.put("hobby", "rab dance and basktable");
        map.put("age", 24);
        map.put("gender", "男");
        map.put("say", "chicken you are very beautiful!");
        map.put("trigger", "black_cust");
        map.put("black", false);
        query.putOneMsg("testindex", "testType", map);
    }

    public void getModexTest(EsQuery query) {
        query.getModel("testindex", "testType", "13");
    }

    public void testUpdate(EsQuery query) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hobby", "rap song");
        map.put("say", "ai ai ni kan zhe ge wan.");
        query.updateEs("testindex", "testType", "47RHFT6EEN3WQ7Q", map);
    }

    public void testDel(EsQuery query) {
        int result = query.deleteEsById("testindex", "testType", "12");
        if (result == 1) {
            System.out.println("删除成功！");
            return;
        }
        System.out.println("删除失败！");
    }

    public void testQuery(EsQuery query) {
        Map<String, Object> map = query.queryAllMap("trigger", "cust");
        long total = Long.valueOf(map.get("total").toString());
        System.out.println(total);
        if (total > 0) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("data");
            for (Map<String, Object> data : list) {
                System.out.println("--------------------------------------------");
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    System.out.println(entry.getKey() + " : " + entry.getValue());
                }
            }
        }
    }
    
    public void testQueryPre(EsQuery query) {
        Map<String, Object> map = query.queryAllPre("ruleTrigger", "cust");
        long total = Long.valueOf(map.get("total").toString());
        System.out.println(total);
        if (total > 0) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("data");
            for (Map<String, Object> data : list) {
                System.out.println("--------------------------------------------");
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    System.out.println(entry.getKey() + " : " + entry.getValue());
                }
            }
        }
    }
    
    public void adminApi(EsQuery query) {
        query.adminApi("testindex");
    }
}
