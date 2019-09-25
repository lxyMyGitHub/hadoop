package com.es;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.es.base.util.EsQuery;

public class EsTest {

    @Test
    public void testEsQuery() {
        EsQuery query = new EsQuery();
        System.out.println(query);
//         getModexTest(query);
        // testDel(query);
        // putOneMsg(query);
        // testUpdate(query);
         testQueryPre(query);
//        adminApi(query);
    }

    public void putOneMsg(EsQuery query) {
        Map<String, Object> map = new HashMap<>();
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
        Map<String, Object> map = new HashMap<>();
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
