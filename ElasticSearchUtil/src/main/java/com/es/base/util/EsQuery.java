package com.es.base.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.queryparser.flexible.core.builders.QueryTreeBuilder;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQuery.ZeroTermsQuery;
import org.elasticsearch.search.SearchHit;


public class EsQuery {
    private static String index = null;

    private static String type = null;

    private static EsFactory esFactory = null;

    private static TransportClient client = null;

    static {
        if (esFactory == null) {
            esFactory = new EsFactory();
            client = esFactory.getEsClient();
        }
    }

    public EsQuery() {
        if (client == null) {
            client = esFactory.getEsClient();
        }
    }

    public void getModel(String index, String type, String id) {
        GetResponse response = client.prepareGet(index, type, id).get();
        if (response.isExists()) {
            System.out.println("---start---");
            System.out.println("" + response);
            System.out.println("response.isExists():" + response.isExists());
            System.out.println("response.getIndex():" + response.getIndex());
            System.out.println("response.getType():" + response.getType());
            System.out.println("response.getId():" + response.getId());
            System.out.println("response.getVersion():" + response.getVersion());
            System.out.println("response.isSourceEmpty():" + response.isSourceEmpty());
            Map<String, Object> map = response.getSource();
            Set<String> set = map.keySet();
            for (String key : set) {
                System.out.println("\t\t" + key + " : " + map.get(key));
            }
        }

        // System.out.println("response.getSourceAsString():"+response.getSourceAsString());
        // System.out.println("response.getField(\"_id\"):"+response.getField("_id"));
        System.out.println("---end---");

    }

    public void putOneMsg(String index, String type, Map<String, Object> map) {
        System.out.println("put one message is start...");
        String id = StringUtil.genRandomNum(15);
        System.out.println(id);
        map.put("rowKey", id);
        IndexResponse response = client.prepareIndex(index, type, id).setSource(map).get();
        response.forcedRefresh();
        System.out.println("put one message is success...");
    }

    public void updateEs(String index, String type, String id, Map<String, Object> map) {
        try {
            UpdateRequestBuilder prepareUpdate = client.prepareUpdate(index, type, id);
            if (map != null && map.size() > 0) {
                XContentBuilder builder = XContentFactory.jsonBuilder().startObject();
                for (Map.Entry<String, Object> m : map.entrySet()) {
                    builder.field(m.getKey(), m.getValue());
                }
                prepareUpdate.setDoc(builder.endObject()).get();
                System.out.println("update successful !");
            }
        } catch (IOException e) {
            System.out.println("update failed :" + e);
        }
    }

    public int deleteEsById(String index, String type, String id) {
        try {
            DeleteResponse response = client.prepareDelete(index, type, id).execute().actionGet();
            response.forcedRefresh();
        } catch (Exception e) {
            System.out.println("delete failed :" + e);
            return 0;
        }
        return 1;
    }
    
    public Map<String,Object> queryAllMap(String felid,String value){
        Map<String,Object> dataMap = new HashMap<>();
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        SearchRequestBuilder requestBuilder = client.prepareSearch("testindex");
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery(felid, value));
        SearchResponse searchResponse = requestBuilder.setQuery(boolQuery).get();
        long totalHits = searchResponse.getHits().getTotalHits();
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : hits) {
            Map<String, Object> data = searchHit.getSourceAsMap();
            String id = searchHit.getId();
            data.put("id", id);
            listMap.add(data);
        }
        dataMap.put("total", totalHits);
        dataMap.put("data", listMap);
        return dataMap;
    }
    
    public Map<String,Object> queryAllPre(String felid,String value){
        Map<String,Object> dataMap = new HashMap<>();
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        SearchRequestBuilder requestBuilder = client.prepareSearch("eventindex");
        DisMaxQueryBuilder disMaxQuery = QueryBuilders.disMaxQuery();
        disMaxQuery.add(QueryBuilders.prefixQuery(felid, value));
        System.out.println(disMaxQuery.toString());
        SearchResponse searchResponse = requestBuilder.setQuery(disMaxQuery).setFrom(1).setSize(10).get();
        long totalHits = searchResponse.getHits().getTotalHits();
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : hits) {
            Map<String, Object> data = searchHit.getSourceAsMap();
            String id = searchHit.getId();
            data.put("id", id);
            listMap.add(data);
        }
        dataMap.put("total", totalHits);
        dataMap.put("data", listMap);
        return dataMap;
    }
    
    public Map<String,Object> queryDemo(String felid,String value){
        Map<String,Object> dataMap = new HashMap<>();
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        SearchRequestBuilder requestBuilder = client.prepareSearch("testindex");
        DisMaxQueryBuilder disMaxQuery = QueryBuilders.disMaxQuery();
        
        
        
        //------------------------------------------------------------
        MatchAllQueryBuilder queryBuilder1 = QueryBuilders.matchAllQuery().boost(11f);
        System.out.println(queryBuilder1.toString());
        MatchQueryBuilder zeroTermsQuery = QueryBuilders.matchQuery("gender", "男").operator(Operator.AND).zeroTermsQuery(ZeroTermsQuery.ALL);
        System.out.println(zeroTermsQuery.toString());
        
        
        //------------------------------------------------------------
        
        
        
        disMaxQuery.add(QueryBuilders.prefixQuery(felid, value));
        System.out.println(disMaxQuery.toString());
        SearchResponse searchResponse = requestBuilder.setQuery(disMaxQuery).setFrom(1).setSize(1).get();
        long totalHits = searchResponse.getHits().getTotalHits();
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : hits) {
            Map<String, Object> data = searchHit.getSourceAsMap();
            String id = searchHit.getId();
            data.put("id", id);
            listMap.add(data);
        }
        dataMap.put("total", totalHits);
        dataMap.put("data", listMap);
        return dataMap;
    }

    public void adminApi(String index) {
        ClusterAdminClient cluster = client.admin().cluster();
        ClusterHealthResponse healthResponse = cluster.prepareHealth(index).get();
        System.out.println(healthResponse.getClusterName().toString());
        System.out.println(healthResponse.getNumberOfNodes());
        System.out.println(healthResponse.getStatus().toString());
        System.out.println(healthResponse.getActiveShards());
        ClusterStateResponse clusterStateResponse = cluster.prepareState().get();
        System.out.println("---------------------------------------------");
        System.out.println(clusterStateResponse.getClusterName().toString());
        System.out.println(clusterStateResponse.remoteAddress().toString());
        System.out.println(clusterStateResponse.getState().toString());
        
    }
}
