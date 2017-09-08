package com.otsi.action;

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
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import org.elasticsearch.search.aggregations.metrics.stats.StatsAggregationBuilder;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetBankDetailsState
 */
//@WebServlet("/GetBankDetailsState")
public class GetBankDetailsState extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBankDetailsState() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json");
		   TransportClient client=GetClientObjectUtil.getObject();
	/*	
		SearchResponse  response1 = client.prepareSearch("bank").addAggregation( AggregationBuilders.terms("keys").field("state.keyword").order(Terms.Order.count(false)))
				.execute().actionGet();
		Terms  terms1 = response1.getAggregations().get("keys");
		Collection<Terms.Bucket> buckets1 = terms1.getBuckets();
		System.out.println("aggregation details   "+buckets1);
		
		ArrayList data=new ArrayList();
		
		for (Bucket bucket : buckets1) {
			DataBean bean=new DataBean();
			bean.setMakerName((String) bucket.getKey());
			bean.setNoOfVechicles((int) bucket.getDocCount());
			data.add(bean); 
		}
		
		
		*/
		
		
		   ArrayList<BankBean> data=new ArrayList();
		StatsAggregationBuilder aggregation =
		        AggregationBuilders
		                .stats("agg")
		                .field("balance");
		

		/*
		 * specific state sum ,min,max
		 *
		*/
		SearchResponse  sr = client.prepareSearch("bank").addAggregation( AggregationBuilders.terms("state").field("state.keyword").subAggregation(aggregation)).execute().actionGet();
		
		Terms  terms= sr.getAggregations().get("state");
		
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
		String state=null;
	
Collection<Terms.Bucket> buckets = terms.getBuckets();
		
		for (Bucket bucket : buckets) {
			Stats	 agg2 =bucket.getAggregations().get("agg");
			BankBean bean=new BankBean();
			
			 min = agg2.getMin();
			 max = agg2.getMax();
			 avg = agg2.getAvg();
			sum = agg2.getSum();
			state= bucket.getKeyAsString();
			count=bucket.getDocCount();
			
			
			bean.setState(state);
			bean.setAvg(avg);
			bean.setMax(max);
			bean.setMin(min);
			bean.setSum(sum);
			bean.setCount(count);
			data.add(bean);
			System.out.println( "in  each  state metrics are  state name::"+state+" buket count :: " +count+ "   min:: "+min +" max:: "+max+"  avg::  "+avg+"  sum:: "+sum+" count:: "+count);
			
		}
         
		Gson gson=new Gson();
		String json = gson.toJson(data);
		response.getWriter().println(json);
		System.out.println(json);
		request.setAttribute("data1", data);
		request.getSession().setAttribute("data", data);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
