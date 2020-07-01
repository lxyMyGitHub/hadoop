package com.es.base.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.http.client.utils.DateUtils;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.util.concurrent.CountDown;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.search.MatchQuery.ZeroTermsQuery;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.support.IncludeExclude;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.elasticsearch.search.aggregations.metrics.sum.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.joda.time.DateTime;


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
        Map<String,Object> dataMap = new HashMap<String,Object>();
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
        Map<String,Object> dataMap = new HashMap<String,Object>();
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
        Map<String,Object> dataMap = new HashMap<String,Object>();
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

    public SearchHits getTradeMsgForTime(String tradeIndex, String tradeType,String field ,String startTime, String endTime, int size) {
        //时间查询区间
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.rangeQuery(field).gte(startTime).lte(endTime));
        SearchResponse searchResponse = client.prepareSearch(tradeIndex)
                .setQuery(boolQueryBuilder)
                .setSize(size)
                .get();
        return searchResponse.getHits();
    }

    /**
     * 近30天商户订单金额之和
     * @param tradeIndex
     * @param tradeType
     * @param field
     * @param startTime
     * @param endTime
     * @param size
     */
    public void getTradeSunAmountForTime(String tradeIndex, String tradeType, String field , String startTime, String endTime, int size) {
        // 误差范围
        double factor = 0.9;
        long acctSize = 0;
        long min_docs = 1;
        int span_days = 30;
        int terms_size = 10000;
        //时间查询区间
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("tradeTime").gte(startTime).lte(endTime));
        boolQueryBuilder.must(QueryBuilders.matchQuery("merchantNumber", "merchantNumber1531689"));
        // Cardinality agg
        CardinalityAggregationBuilder cardAgg = AggregationBuilders
                .cardinality("card_merchantNumber")
                .field("merchantNumber")
                .precisionThreshold(40000);
        SearchResponse card_response = client
                .prepareSearch(tradeIndex)
                .setQuery(boolQueryBuilder)
                .setSize(0)
                .addAggregation(cardAgg)
                .get();
        long dataSize = card_response.getHits().getTotalHits();
        Cardinality card_result = card_response.getAggregations().get("card_merchantNumber");
        long acct_count = card_result.getValue();
        // 估算分区数
        int numPartitions = 1 + (int) (acct_count /(terms_size*factor));
        System.out.println("****** account cardinality is "+acct_count+" terms size is "+terms_size+" numPartitions is "+numPartitions);
        // scroll aggregation
        for (int i = 0; i < numPartitions; i++) {
            long startFor = System.currentTimeMillis();
            // partition params
            IncludeExclude includeExclude = new IncludeExclude(i, numPartitions);
            TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("agg_terms").field("merchantNumber").size(terms_size).includeExclude(includeExclude);
            SumAggregationBuilder sumAgg = AggregationBuilders.sum("sum_amount").field("amount");

            SearchResponse searchResponse = client.prepareSearch(tradeIndex)
                    .setQuery(boolQueryBuilder)
                    .setSize(size)
                    .addAggregation(termsAggregationBuilder.subAggregation(sumAgg))
                    .get();
            Terms terms = searchResponse.getAggregations().get("agg_terms");
            for (Terms.Bucket bucket : terms.getBuckets()) {
                String merchantNumber = bucket.getKeyAsString();
                long docCount = bucket.getDocCount();
                Sum stats = bucket.getAggregations().get("sum_amount");
                DecimalFormat format = new DecimalFormat("#0.00");
                String valueStr = format.format(stats.getValue());
                double value = Double.parseDouble(valueStr);
                acctSize++;
                if(value>=0){
                    System.out.println("merchantNumber is  ："+merchantNumber +" sum :"+valueStr);
                }
            }
        }
    }

    /**
     * 近30天日均交易笔数
     * @param tradeIndex
     * @param tradeType
     * @param field
     * @param startTime
     * @param endTime
     * @param size
     */
    public void getTradeAvgCountForTime(String tradeIndex, String tradeType, String field , String startTime, String endTime, int size) {
        // 误差范围
        double factor = 0.9;
        long acctSize = 0;
        long min_docs = 1;
        int span_days = 30;
        int terms_size = 10000;
        //时间查询区间
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("tradeTime").gte(startTime).lte(endTime));
        // Cardinality agg
        CardinalityAggregationBuilder cardAgg = AggregationBuilders
                .cardinality("card_merchantNumber")
                .field("merchantNumber")
                .precisionThreshold(40000);
        SearchResponse card_response = client
                .prepareSearch(tradeIndex)
                .setQuery(boolQueryBuilder)
                .setSize(0)
                .addAggregation(cardAgg)
                .get();
        long dataSize = card_response.getHits().getTotalHits();
        Cardinality card_result = card_response.getAggregations().get("card_merchantNumber");
        long acct_count = card_result.getValue();
        // 估算分区数
        int numPartitions = 1 + (int) (acct_count /(terms_size*factor));
        System.out.println("****** account cardinality is "+acct_count+" terms size is "+terms_size+" numPartitions is "+numPartitions);
        // scroll aggregation
        for (int i = 0; i < numPartitions; i++) {
            long startFor = System.currentTimeMillis();
            // partition params
            IncludeExclude includeExclude = new IncludeExclude(i, numPartitions);
            TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("agg_terms").field("merchantNumber").size(terms_size).includeExclude(includeExclude);
            ValueCountAggregationBuilder countAgg = AggregationBuilders.count("count_city").field("city");
            SearchResponse searchResponse = client.prepareSearch(tradeIndex)
                    .setQuery(boolQueryBuilder)
                    .setSize(size)
                    .addAggregation(termsAggregationBuilder.subAggregation(countAgg))
                    .get();
            Terms terms = searchResponse.getAggregations().get("agg_terms");
            for (Terms.Bucket bucket : terms.getBuckets()) {
                String accountNumber = bucket.getKeyAsString();
                long docCount = bucket.getDocCount();
                Sum sum_amount = bucket.getAggregations().get("sum_amount");
                double value = sum_amount.getValue();
                if(value>1){
                    System.out.println("merchantNumber :"+accountNumber+" docCount : "+docCount);
                    System.out.println("merchantNumber :"+accountNumber+" ValueCount : "+value);
                }
            }
        }
    }


    /**
     * 24小时之内最后一次还款时间
     * @param tradeIndex
     * @param tradeType
     * @param field
     */
    public void getLastReimbursement24h(String tradeIndex, String tradeType, String field) {
        // 误差范围
        double factor = 0.9;
        long acctSize = 0;
        long min_docs = 1;
        int span_hours = 24;
        int terms_size = 10000;
        int size = 0;
        String endTime = "20200526000000";
        String startTime = "20200525000000";
        System.out.println("testGet run startTime" + startTime);
        System.out.println("testGet run endTime" + endTime);
        DecimalFormat format = new DecimalFormat("#0");
        //时间查询区间
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("tradeTime").gte(startTime).lte(endTime));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("amount").gte(0));
        // Cardinality agg
        CardinalityAggregationBuilder cardAgg = AggregationBuilders
                .cardinality("card_accountNumber")
                .field("accountNumber")
                .precisionThreshold(40000);
        SearchResponse card_response = client
                .prepareSearch(tradeIndex)
                .setQuery(boolQueryBuilder)
                .setSize(0)
                .addAggregation(cardAgg)
                .get();
        long dataSize = card_response.getHits().getTotalHits();
        Cardinality card_result = card_response.getAggregations().get("card_accountNumber");
        long acct_count = card_result.getValue();
        // 估算分区数
        int numPartitions = 1 + (int) (acct_count /(terms_size*factor));
        System.out.println("****** account cardinality is "+acct_count+" terms size is "+terms_size+" numPartitions is "+numPartitions);
        // scroll aggregation
        for (int i = 0; i < numPartitions; i++) {
            long startFor = System.currentTimeMillis();
            // partition params
            IncludeExclude includeExclude = new IncludeExclude(i, numPartitions);
            TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("agg_terms").field("accountNumber").size(terms_size).includeExclude(includeExclude);
            MaxAggregationBuilder maxAgg = AggregationBuilders.max("max_xfraudTradeTime").field("xfraudTradeTime");
            SearchResponse searchResponse = client.prepareSearch(tradeIndex)
                    .setQuery(boolQueryBuilder)
                    .setSize(size)
                    .addAggregation(termsAggregationBuilder.subAggregation(maxAgg))
                    .get();
            Terms terms = searchResponse.getAggregations().get("agg_terms");
            for (Terms.Bucket bucket : terms.getBuckets()) {
                String accountNumber = bucket.getKeyAsString();
                long docCount = bucket.getDocCount();
                Max stats = bucket.getAggregations().get("max_xfraudTradeTime");
                String timeValue = format.format(stats.getValue());
                acctSize++;
                System.out.println("accountNumber is  ："+accountNumber +" last time is  :"+timeValue);
            }
        }
    }



    /**
     * 此账号0小时-72小时最大金额
     * @param tradeIndex
     * @param tradeType
     * @param field
     */
    public void getMaxAmountLast72h(String tradeIndex, String tradeType, String field) {
        // 误差范围
        double factor = 0.9;
        long acctSize = 0;
        long min_docs = 1;
        int span_hours = 72;
        int terms_size = 3000;
        int size = 0;
        DecimalFormat format = new DecimalFormat("#0.00");
        String endTime = DateUtil.timeNow();
        String startTime = DateUtil.addHours(endTime,-span_hours);
        //时间查询区间
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("tradeTime").gte(startTime).lte(endTime));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("amount").gte(0));
        // Cardinality agg
        CardinalityAggregationBuilder cardAgg = AggregationBuilders
                .cardinality("card_accountNumber")
                .field("accountNumber")
                .precisionThreshold(40000);
        SearchResponse card_response = client
                .prepareSearch(tradeIndex)
                .setQuery(boolQueryBuilder)
                .setSize(0)
                .addAggregation(cardAgg)
                .get();
        long dataSize = card_response.getHits().getTotalHits();
        Cardinality card_result = card_response.getAggregations().get("card_accountNumber");
        long acct_count = card_result.getValue();
        // 估算分区数
        int numPartitions = 1 + (int) (acct_count /(terms_size*factor));
        System.out.println("****** account cardinality is "+acct_count+" terms size is "+terms_size+" numPartitions is "+numPartitions);
        // scroll aggregation
        for (int i = 0; i < numPartitions; i++) {
            long startFor = System.currentTimeMillis();
            // partition params
            IncludeExclude includeExclude = new IncludeExclude(i, numPartitions);
            TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("agg_terms_acc").field("accountNumber").size(terms_size).includeExclude(includeExclude);
            Script scriptTradeTime = new Script("doc['tradeTime'].value.substring(0,10)");
            TermsAggregationBuilder agg_terms_time = AggregationBuilders.terms("agg_terms_time").script(scriptTradeTime).size(terms_size).order(Terms.Order.term(true));
            MaxAggregationBuilder maxAgg = AggregationBuilders.max("max_amount").field("amount");
            SearchResponse searchResponse = client.prepareSearch(tradeIndex)
                    .setQuery(boolQueryBuilder)
                    .setSize(size)
                    .addAggregation(termsAggregationBuilder.subAggregation(agg_terms_time.subAggregation(maxAgg)))
                    .get();
            Terms terms = searchResponse.getAggregations().get("agg_terms_acc");
            String value = "";
            for (Terms.Bucket bucket : terms.getBuckets()) {
                String accountNumber = bucket.getKeyAsString();
                Terms tradeHourTerms = bucket.getAggregations().get("agg_terms_time");
                value = "";
                for (Terms.Bucket tradeTimeBucket : tradeHourTerms.getBuckets()) {
                    String timeKey = tradeTimeBucket.getKeyAsString();
                    Max stats = tradeTimeBucket.getAggregations().get("max_amount");
                    String maxValue = format.format(stats.getValue());
                    acctSize++;
                    value += maxValue+"_"+timeKey+";";
//                    System.out.println("accountNumber is  ："+"_"+accountNumber +"timeKey :"+timeKey+" max value  :"+maxValue);
                }
                System.out.println("accountNumber is  ："+"_"+accountNumber+"value is :"+value);
            }
        }
    }


    /**
     * 过去24小时内贷记卡交易笔数/金额
     * @param tradeIndex
     * @param tradeType
     * @param field
     */
    public void getdebit24h(String tradeIndex, String tradeType, String field) {
        // 误差范围
        double factor = 0.9;
        long acctSize = 0;
        long min_docs = 1;
        int span_hours = 48;
        int terms_size = 3000;
        int size = 0;
        DecimalFormat format = new DecimalFormat("#0.00");
        String endTime = DateUtil.timeNow();
        String startTime = DateUtil.addHours(endTime,-span_hours);
        //时间查询区间
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("tradeTime").gte(startTime).lte(endTime));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("amount").gte(0));
        // Cardinality agg
        CardinalityAggregationBuilder cardAgg = AggregationBuilders
                .cardinality("card_accountNumber")
                .field("accountNumber")
                .precisionThreshold(40000);
        SearchResponse card_response = client
                .prepareSearch(tradeIndex)
                .setQuery(boolQueryBuilder)
                .setSize(0)
                .addAggregation(cardAgg)
                .get();
        long dataSize = card_response.getHits().getTotalHits();
        Cardinality card_result = card_response.getAggregations().get("card_accountNumber");
        long acct_count = card_result.getValue();
        // 估算分区数
        int numPartitions = 1 + (int) (acct_count /(terms_size*factor));
        System.out.println("****** account cardinality is "+acct_count+" terms size is "+terms_size+" numPartitions is "+numPartitions);
        // scroll aggregation
        for (int i = 0; i < numPartitions; i++) {
            long startFor = System.currentTimeMillis();
            // partition params
            IncludeExclude includeExclude = new IncludeExclude(i, numPartitions);
            TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("agg_terms_acc").field("accountNumber").size(terms_size).includeExclude(includeExclude);
            Script scriptTradeTime = new Script("doc['tradeTime'].value.substring(0,10)");
            TermsAggregationBuilder agg_terms_time = AggregationBuilders.terms("agg_terms_time").script(scriptTradeTime).size(terms_size).order(Terms.Order.term(true));
            SumAggregationBuilder sumAgg = AggregationBuilders.sum("sum_amount").field("amount");
            SearchResponse searchResponse = client.prepareSearch(tradeIndex)
                    .setQuery(boolQueryBuilder)
                    .setSize(size)
                    .addAggregation(termsAggregationBuilder.subAggregation(agg_terms_time.subAggregation(sumAgg)))
                    .get();
            Terms terms = searchResponse.getAggregations().get("agg_terms_acc");
            String value = "";
            for (Terms.Bucket bucket : terms.getBuckets()) {
                String accountNumber = bucket.getKeyAsString();
                Terms tradeHourTerms = bucket.getAggregations().get("agg_terms_time");
                value = "";
                for (Terms.Bucket tradeTimeBucket : tradeHourTerms.getBuckets()) {
                    long count = tradeTimeBucket.getDocCount();
                    String timeKey = tradeTimeBucket.getKeyAsString();
                    Sum stats = tradeTimeBucket.getAggregations().get("sum_amount");
                    String sumValue = format.format(stats.getValue());
                    acctSize++;
                    value += sumValue+"_"+count+"_"+timeKey+";";
//                    System.out.println("accountNumber is  ："+"_"+accountNumber +"timeKey :"+timeKey+" max value  :"+maxValue);
                }
                System.out.println("accountNumber is  ："+"_"+accountNumber+"value is :"+value);
            }
        }
    }


    /**
     * 近6月有线下刷卡交易国家（数据预热结果）
     * @param tradeIndex
     * @param tradeType
     * @param size
     */
    public void getOfflineTread6MounthCountrys(String tradeIndex, String tradeType, int size) {
        // 误差范围
        double factor = 0.9;
        long acctSize = 0;
        long min_docs = 1;
        int span_days = 10;
        int terms_size = 10000;
        long start = System.currentTimeMillis();
        String endTime = DateUtil.timeNow();
        String startTime = DateUtil.addDays(endTime, -span_days);
        DecimalFormat format = new DecimalFormat("#0.00");
        //时间查询区间
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("tradeTime").gte(startTime).lte(endTime));
        boolQueryBuilder.must(QueryBuilders.existsQuery("city")).mustNot(QueryBuilders.termQuery("city","")).mustNot(QueryBuilders.termQuery("city","未知城市"));
        // Cardinality agg
        CardinalityAggregationBuilder cardAgg = AggregationBuilders
                .cardinality("card_accountNumber")
                .field("accountNumber")
                .precisionThreshold(40000);
        SearchResponse card_response = client
                .prepareSearch(tradeIndex)
                .setQuery(boolQueryBuilder)
                .setSize(0)
                .addAggregation(cardAgg)
                .get();
        long dataSize = card_response.getHits().getTotalHits();
        Cardinality card_result = card_response.getAggregations().get("card_accountNumber");
        long acct_count = card_result.getValue();
        // 估算分区数
        int numPartitions = 1 + (int) (acct_count /(terms_size*factor));
        System.out.println("****** account cardinality is "+acct_count+" terms size is "+terms_size+" numPartitions is "+numPartitions);
        // scroll aggregation
        for (int i = 0; i < numPartitions; i++) {
            long startFor = System.currentTimeMillis();
            // partition params
            IncludeExclude includeExclude = new IncludeExclude(i, numPartitions);
            TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("agg_terms").field("accountNumber").size(terms_size).includeExclude(includeExclude);
            TermsAggregationBuilder agg_terms_time = AggregationBuilders.terms("agg_terms_city").field("city").size(terms_size).order(Terms.Order.term(true));
            SearchResponse searchResponse = client.prepareSearch(tradeIndex)
                    .setQuery(boolQueryBuilder)
                    .setSize(size)
                    .addAggregation(termsAggregationBuilder.subAggregation(agg_terms_time))
                    .get();
            Terms terms = searchResponse.getAggregations().get("agg_terms");
            for (Terms.Bucket bucket : terms.getBuckets()) {
                String accountNumber = bucket.getKeyAsString();
                String citys = "&&";
                Terms cityTerms = bucket.getAggregations().get("agg_terms_city");
                for (Terms.Bucket cityBucket : cityTerms.getBuckets()) {
                    long count = cityBucket.getDocCount();
                    String city = cityBucket.getKeyAsString();
                    citys += city +"&&";
                }
                System.out.println("accountNumber is  ："+"_"+accountNumber +"  citys is:"+citys);
            }
        }
    }

    /**
     * 72-2无当前国家交易历史特征
     * @param tradeIndex
     * @param tradeType
     * @param size
     */
    public void getCurrCountryHistoryType72Hours_(String tradeIndex, String tradeType, int size) {
        // 误差范围
        double factor = 0.9;
        long acctSize = 0;
        long min_docs = 1;
        int span_hours_start = 72;
        int span_hours_end = 2;
        int terms_size = 10000;
        long start = System.currentTimeMillis();
        String nowTime = DateUtil.timeNow();
        String startTime = DateUtil.addHours(nowTime, -span_hours_start);
        String endTime = DateUtil.addHours(nowTime, -span_hours_end);

        DecimalFormat format = new DecimalFormat("#0.00");
        //时间查询区间
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("tradeTime").gte(startTime).lte(endTime));
        boolQueryBuilder.must(QueryBuilders.existsQuery("country")).mustNot(QueryBuilders.termQuery("country","")).mustNot(QueryBuilders.termQuery("country","未知国家"));
        // Cardinality agg
        CardinalityAggregationBuilder cardAgg = AggregationBuilders
                .cardinality("card_accountNumber")
                .field("accountNumber")
                .precisionThreshold(40000);
        SearchResponse card_response = client
                .prepareSearch(tradeIndex)
                .setQuery(boolQueryBuilder)
                .setSize(0)
                .addAggregation(cardAgg)
                .get();
        long dataSize = card_response.getHits().getTotalHits();
        Cardinality card_result = card_response.getAggregations().get("card_accountNumber");
        long acct_count = card_result.getValue();
        // 估算分区数
        int numPartitions = 1 + (int) (acct_count /(terms_size*factor));
        System.out.println("****** account cardinality is "+acct_count+" terms size is "+terms_size+" numPartitions is "+numPartitions);
        // scroll aggregation
        for (int i = 0; i < numPartitions; i++) {
            long startFor = System.currentTimeMillis();
            // partition params
            IncludeExclude includeExclude = new IncludeExclude(i, numPartitions);
            TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("agg_terms").field("accountNumber").size(terms_size).includeExclude(includeExclude);
            TermsAggregationBuilder agg_terms_time = AggregationBuilders.terms("agg_terms_country").field("country").size(terms_size).order(Terms.Order.term(true));
            SearchResponse searchResponse = client.prepareSearch(tradeIndex)
                    .setQuery(boolQueryBuilder)
                    .setSize(size)
                    .addAggregation(termsAggregationBuilder.subAggregation(agg_terms_time))
                    .get();
            Terms terms = searchResponse.getAggregations().get("agg_terms");
            for (Terms.Bucket bucket : terms.getBuckets()) {
                String accountNumber = bucket.getKeyAsString();
                String countrys = "&&";
                Terms cityTerms = bucket.getAggregations().get("agg_terms_country");
                for (Terms.Bucket cityBucket : cityTerms.getBuckets()) {
                    long count = cityBucket.getDocCount();
                    String country = cityBucket.getKeyAsString();
                    countrys += country +"&&";
                }
                System.out.println("accountNumber is  ："+"_"+accountNumber +"  countrys is:"+countrys);
            }
        }
    }

    /**
     * 常用设备【习惯设备】（统计全部客户近（12-2天，该设备的交易笔数大于等于5笔且占所有交易的比例大于等于80%
     * @param tradeIndex
     */
    public void getHabitTradeDevice(String tradeIndex) {
        // 误差范围
        double factor = 0.9;
        long acctSize = 0;
        long min_docs = 1;
        int span_days_start = 12;
        int span_days_end = 2;
        int terms_size = 3000;
        int size = 0;
        DecimalFormat format = new DecimalFormat("#0.00");
        DateTime nowTime = DateUtilJoda.getDateTimeInstance();
        DateTime startTime = DateUtilJoda.addDays(nowTime,-span_days_start);
        DateTime endTime = DateUtilJoda.addDays(nowTime,-span_days_end);
        //时间查询区间
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("xfraudTradeTime4RA").gte(startTime.getMillis()).lte(endTime.getMillis()));
        boolQueryBuilder.must(QueryBuilders.existsQuery("userId")).mustNot(QueryBuilders.termQuery("userId","")).mustNot(QueryBuilders.termQuery("userId","undefined"));
        boolQueryBuilder.must(QueryBuilders.existsQuery("tradeDevice")).mustNot(QueryBuilders.termQuery("tradeDevice","")).mustNot(QueryBuilders.termQuery("tradeDevice","undefined"));
        // Cardinality agg
        CardinalityAggregationBuilder cardAgg = AggregationBuilders
                .cardinality("card_userId")
                .field("userId")
                .precisionThreshold(40000);
        SearchResponse card_response = client
                .prepareSearch(tradeIndex)
                .setQuery(boolQueryBuilder)
                .setSize(0)
                .addAggregation(cardAgg)
                .get();
        long dataSize = card_response.getHits().getTotalHits();
        Cardinality card_result = card_response.getAggregations().get("card_userId");
        long acct_count = card_result.getValue();
        // 估算分区数
        int numPartitions = 1 + (int) (acct_count /(terms_size*factor));
        System.out.println("****** userId cardinality is "+acct_count+" terms size is "+terms_size+" numPartitions is "+numPartitions);
        // scroll aggregation
        for (int i = 0; i < numPartitions; i++) {
            long startFor = System.currentTimeMillis();
            // partition params
            IncludeExclude includeExclude = new IncludeExclude(i, numPartitions);
            TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("agg_terms_userId").field("userId").size(terms_size).includeExclude(includeExclude);
            TermsAggregationBuilder agg_terms_tradeDevice = AggregationBuilders.terms("agg_terms_tradeDevice").field("tradeDevice").size(terms_size).order(Terms.Order.term(true));
            SearchResponse searchResponse = client.prepareSearch(tradeIndex)
                    .setQuery(boolQueryBuilder)
                    .setSize(size)
                    .addAggregation(termsAggregationBuilder.subAggregation(agg_terms_tradeDevice))
                    .get();
            Terms terms = searchResponse.getAggregations().get("agg_terms_userId");
            String value = "";
            for (Terms.Bucket bucket : terms.getBuckets()) {
                String userId = bucket.getKeyAsString();
                Terms devicesTerms = bucket.getAggregations().get("agg_terms_tradeDevice");
                long userTradeDeviceCount = bucket.getDocCount();
                value = "userId："+userId + "   tradeDevice:&&";

                for (Terms.Bucket tradeDeviceBucket : devicesTerms.getBuckets()) {
                    long count = tradeDeviceBucket.getDocCount();
                    String tradeDevice = tradeDeviceBucket.getKeyAsString();
                    acctSize++;
                    if(count >= 1 && count/userTradeDeviceCount*1.00 >= 0.001){
                        value += tradeDevice+"&&";
//                        System.out.println("count is "+ count);
                        System.out.println("count is "+ count + " tradeDevice is "+tradeDevice + " userId is "+userId + " userIdCountTradeDevice is "+userTradeDeviceCount);
                    }
//                    System.out.println("accountNumber is  ："+"_"+accountNumber +"timeKey :"+timeKey+" max value  :"+maxValue);
                }
//                System.out.println("userId is  ："+"_"+userId+"value is :"+value);
            }
        }
    }
}
