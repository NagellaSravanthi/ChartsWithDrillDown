package com.otsi.action;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.AggregatorFactories.Builder;
import org.elasticsearch.search.aggregations.InternalAggregation;
import org.elasticsearch.search.aggregations.InternalAggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.max.Max;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.stats.StatsAggregationBuilder;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetBankDetailsCity
 */
//@WebServlet("/GetBankDetailsCity")
public class GetBankDetailsCity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBankDetailsCity() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  TransportClient client=GetClientObjectUtil.getObject();
			
			
			response.setContentType("text/json");
			String name=  request.getParameter("list");
			
			QueryBuilder qb = matchPhraseQuery(
				    "state",                  
				  name  
				);
			
			
			
			StatsAggregationBuilder aggregation =
			        AggregationBuilders
			                .stats("agg")
			                .field("balance");
			

			/*
			 * specific state sum ,min,max
			 * 
			 * 
			 * SearchResponse sr = client
	                .prepareSearch("bank")
	                .setQuery(qb).addAggregation(aggregation).execute().actionGet();
			*/
			
			
		/*	Stats agg = sr.getAggregations().get("agg"); // get single state response
			long count = agg.getCount();
			double min1 = agg.getMin();
			double max1 = agg.getMax();
			double avg1 = agg.getAvg();
			double sum1 = agg.getSum();
			long count1 = agg.getCount();*/
			
			/*
			 * state wise metrics
			 * 
			 * SearchResponse  sr = client.prepareSearch("bank").addAggregation( AggregationBuilders.terms("state").field("state.keyword").subAggregation(aggregation)).execute().actionGet();
				Terms  terms= sr.getAggregations().get("state");
				
				Collection<Terms.Bucket> buckets = terms.getBuckets();
			
			for (Bucket bucket : buckets) {
				Stats	 agg2 =bucket.getAggregations().get("agg");
				 min = agg2.getMin();
				 max = agg2.getMax();
				 avg = agg2.getAvg();
				sum = agg2.getSum();
				System.out.println( "in  each  state metrics are  state name::"+bucket.getKey()+" buket count :: " +bucket.getDocCount()+ "   min:: "+min +" max:: "+max+"  avg::  "+avg+"  sum:: "+sum+" count:: "+count);
				
			}
	              
			*/
			double min = 0;
			double max = 0;
			double avg = 0;
			double sum = 0;
			long count = 0;
			double min1 = 0;
			double max1= 0;
			double avg1 = 0;
			double sum1 = 0;
			long count1 = 0;
			String city=null;
			  ArrayList<BankBean> data=new ArrayList();
			
			
			
			//city wise metrics
			
			SearchResponse  response1 = client.prepareSearch("bank").setQuery(qb).addAggregation( AggregationBuilders.terms("state").field("state.keyword").subAggregation( AggregationBuilders.terms("city").field("city.keyword").subAggregation(aggregation))).execute().actionGet();	
			
			Terms  terms2  = response1.getAggregations().get("state");
			Collection<Terms.Bucket> buckets2 = terms2.getBuckets();
			
			for (Bucket bucket : buckets2) {
				Terms types =bucket.getAggregations().get("city");
	               
	                System.out.println(" city  "+types);
	                Collection<Terms.Bucket> bkts = types.getBuckets();
	                for (Bucket b : bkts) {
	                	Stats	 agg2 = b.getAggregations().get("agg");
	                	BankBean bean= new BankBean();
	        			 min1 = agg2.getMin();
	        			 max1 = agg2.getMax();
	        			 avg1 = agg2.getAvg();
	        			 sum1 = agg2.getSum();
	        			 count1 = agg2.getCount();
	        			 city= b.getKeyAsString();
	        			 count=b.getDocCount();
	        			 
	        			 bean.setCity(city);
	        				bean.setAvg(avg1);
	        				bean.setMax(max1);
	        				bean.setMin(min1);
	        				bean.setSum(sum1);
	        				bean.setCount(count);
	        				data.add(bean);
	        			 
	        			 
	        			 System.out.println(" in each city metrics city name::  "+city+"  count::"+count+ " min1:: "+min1 +" max1:: "+max1+"  avg1::  "+avg1+"  sum1:: "+sum1+" count1:: "+count1);
	        		 }
	                
			 }
			
		
			Gson gson=new Gson();
			String json = gson.toJson(data);
			response.getWriter().println(json);
			System.out.println(json);
			
			request.getSession().setAttribute("bodyTypes", data);
			
			
			//String name=  request.getParameter("list");
			
			/*String name= "ID" ;
			System.out.println("   "+name);
			
			
			
			
				//SearchResponse  response1 = client.prepareSearch("bank").setQuery(qb).addAggregation( AggregationBuilders.terms("state").field("state.keyword").subAggregation( AggregationBuilders.terms("city").field("city.keyword").subAggregation(AggregationBuilders.max("balance")).subAggregation(AggregationBuilders.sum("balance").subAggregation(AggregationBuilders.avg("balance"))))).execute().actionGet();
				Builder subFactories = new Builder();
				subFactories.addAggregator(AggregationBuilders.max("max"));
				subFactories.addAggregator(AggregationBuilders.min("min"));
				subFactories.addAggregator(AggregationBuilders.sum("sum"));

				
				
				
				SearchResponse sr = client
		                .prepareSearch("bank")
		                .setQuery(qb)
		                .addAggregation(AggregationBuilders.terms("state").field("state.keyword").subAggregation( AggregationBuilders.terms("city").field("city.keyword"))
		                .subAggregation(
		                        AggregationBuilders.max("id").field(
		                                "balance.keyword"))).execute().actionGet();
				
				Terms  terms =sr.getAggregations().get("state");
				Collection<Terms.Bucket> buckets = terms.getBuckets();
				
				ArrayList data=new ArrayList();
				
				 for (Bucket bucket : buckets) {

			                Terms types =bucket.getAggregations().get("city");
			               
			                System.out.println(" city  "+types);
			                Collection<Terms.Bucket> bkts = types.getBuckets();
			                for (Bucket b : bkts) {
			                	  System.out.println(b.getKey()+"  "+b.getDocCount()+"  ");
			                	b.
			                
			                }
				 }
				*/
				
				
				/*for (Aggregation maxAggs : sr.getAggregations()) {
		              Max max = (Max) maxAggs;
		              double maxValue = max.getValue();
		              System.out.println("maxValue => " + maxValue);
		}
				*/
				
			
				/*Terms  terms = response1.getAggregations().get("state");
				Collection<Terms.Bucket> buckets = terms.getBuckets();
				
				ArrayList data=new ArrayList();
				
				 for (Bucket bucket : buckets) {

			            if (bucket.getDocCount() != 0) {

			             //   System.out.println((int) bucket.getDocCount());
			               System.out.println(bucket.getKeyAsString());
			             
			                Terms types =bucket.getAggregations().get("city");
			                Collection<Terms.Bucket> bkts = types.getBuckets();
			                for (Bucket b : bkts) {
			                	System.out.println(bkts);
			                	DataBean bean=new DataBean();
			                   
			                        //ESClassification classificaiton = new ESClassification();
			                    	bean.setBodyType((String) b.getKey());
			    					bean.setNoOfVechicles((int) b.getDocCount());
			    				
			    					data.add(bean); 

			                   

			                }

			             
			            } else {
			                //list = Collections.<ESClassification> emptyList();
			            }

			        }*/
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
