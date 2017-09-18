<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.ArrayList,com.otsi.action.DataBean"
	isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.bundle.js" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.bundle.min.js" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.js" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.min.js" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js" />
<script src="path/to/chartjs/dist/Chart.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>




<style>
.dropbtn {
	background-color: #4CAF50;
	color: white;
	/*     padding: 16px; */
	font-size: 16px;
	border: none;
	cursor: pointer;
}

.dropdown {
	position: relative;
	display: inline-block;
}

.dropdown-content {
	display: none;
	position: absolute;
	background-color: #f9f9f9;
	min-width: 160px;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
}

.dropdown-content a {
	color: black;
	/*     padding: 12px 16px; */
	text-decoration: none;
	display: block;
}

.dropdown-content p:hover {
	background-color: #E9967A
}

.dropdown:hover .dropdown-content {
	display: block;
}

.dropdown:hover .dropbtn {
	background-color: #3e8e41;
}
</style>
<div class="container">
	<h2>Table</h2>
	<div class="table-responsive" id="chartjs-tooltip">

		<table></table>
	</div>

</div>
</head>
<body>
	<div id="Data">
		<table border="1px" style="width: 100%; height: 100%" id="tables">
			<tr >
			
			<td ><div class="dropdown"
						style="float: right; padding-right: 80px;">
						<button class="dropbtn">Dropdown</button>
						<div id="dropDown" class="dropdown-content"></div>
					</div>
					<canvas id="pie-chartcanvas-1" width="250" height="200"></canvas></td>
				<td ><canvas id="bar-chartcanvas"
						width="450" height="400"></canvas></td>
<td><canvas id="highChart" width="500" height="400"></canvas></td>
					
			</tr>
			<tr >
				<td ><canvas id="pie-chartcanvas-2"
						width="250" height="200"></canvas></td>
				<td><canvas id="mycanvas" width="400"
						height="200"></canvas></td>

			</tr>


		</table>
	</div>
	<div id="ClickData"></div>
	<canvas id="chartjs-4" class="chartjs" width="506" height="253"
		style="display: block; width: 506px; height: 253px;"></canvas>


	<script type="text/javascript">
var ctxPie = $("#pie-chartcanvas-1");
var ctxDht = $("#pie-chartcanvas-2");
var ctxBar = $("#bar-chartcanvas");
var ctxLine = $("#mycanvas");
var ctxHigh=$("#highChart");
var chart1;
var makerName = [];
var noOfVechicles = [];
var noOfVechiclesBodyType = [];
var bodyType = [];
var googleplus_follower = [];
var makerNameClick="" ;
var LineGraph ;
var BarChart;
var HighChart;	
var chart2;
var stateName=[];
var cityName=[];
var docCount=[];
var docCount1=[];
var min=[];
var max=[];
var sum=[];
var avg=[];

function f1(a,b) {
	   myFunc(a,b);
}


function myFunc(evt, item) {
	
	
   
$(document).ready(function(){
	
	
	  
	$.ajax({
		
	
		url : '<%=application.getContextPath()%>/GetDetails',
										type : "POST",
										success : function(data) {
											console.log(data);

											for ( var i in data) {
												stateName.push(data[i].state);
												docCount.push(data[i].count);
												min.push(data[i].min);
												max.push(data[i].max);
												avg.push(data[i].avg);
												sum.push(data[i].sum);
												// 				googleplus_follower.push(data[i].googleplus);
											}
											
											for ( var k in stateName ){
										    	$("#dropDown").append('<p  id="' + k + '" onClick=f1(0,[{_index:'+k+'}])>' +stateName[k]+ '<p>');
										    	}
											

											var options = {
												'onClick' : function(evt, item) {
													myFunc(evt, item)
												},//fuction
												// 		    		events: ['click'],
												tooltips : {
													callbacks : {
														label : function(
																tooltipItem) {

															/*  return  "    clicked maker  "+ makerNameClick+"  "+  " xLablel:    "+tooltipItem.xLabel+"  makerName::   "+makerName[tooltipItem.index] +"  noOfVechicles::   "+
															 noOfVechicles[tooltipItem.index]+"  noOfVechiclesOfEachBodyType::  "+noOfVechiclesBodyType[tooltipItem.index]  ;
															 */

															return "  stateName::"
																	+ stateName[tooltipItem.index]
																	+ "  no.of Customers::"
																	+ docCount[tooltipItem.index];

														}
													}
												}

											};

											var chartdata = {
												labels : stateName,
												datasets : [
														
														{
															label : "Min  Balance State wise",
															fill : false,
															lineTension : 0.1,
															backgroundColor : "rgba(29, 202, 255, 0.75)",
															borderColor : "rgba(29, 202, 255, 1)",
															pointHoverBackgroundColor : "rgba(29, 202, 255, 1)",
															pointHoverBorderColor : "rgba(29, 202, 255, 1)",
															data : min
														} ,
														{
															label : " Max Balance in  Each State",
															fill : false,
															lineTension : 0.1,
															backgroundColor : "rgba(59, 89, 152, 0.75)",
															borderColor : "rgba(59, 89, 152, 1)",
															pointHoverBackgroundColor : "rgba(59, 89, 152, 1)",
															pointHoverBorderColor : "rgba(59, 89, 152, 1)",
															data : max
														}
														]
											};

											var options2 = {

												tooltips : {
													enabled : false,
												},
												responsive : true,
												title : {
													display : true,
													position : "top",
													text : "Line Chart",
													fontSize : 18,
													fontColor : "#111"
												},
												legend : {
													display : true,
													position : "bottom",
													labels : {
														fontColor : "#333",
														fontSize : 16
													}
												}
											};
											LineGraph = new Chart(ctxLine, {
												type : 'line',
												data : chartdata,
												options : {}

											});

											//get the pie chart canvas

											//pie chart data

											var data1 = {

												//labels:makerName,
												datasets : [ {
													label:"Customers Count State Wise",
													data : docCount,
													backgroundColor : [
															"#DEB887",
															"#A9A9A9",
															"#DC143C",
															"#F4A460",
															"#2E8B57",
															"#9ACD32",
															'moccasin',
										                     'saddlebrown',
										                     'lightpink'],
													labels : stateName,
													borderColor : [ "#CDA776",
															"#989898",
															"#CB252B",
															"#E39371",
															"#1D7A46" ],
													borderWidth : [ 1, 1, 1, 1,
															1 ]
												} ]
											};

											//pie chart data
											var data2 = {
												labels : stateName,
												datasets : [ {
													label : "Avg Balance in each state ",
													data : avg,
													backgroundColor : [
														
															
															"#DCDCDC",
															"#E9967A",
															"#9ACD32",'indigo',
										                     'saddlebrown',
										                     'lightpink',"aqua","red","blue","green" ],
													
													borderWidth : [ 1, 1, 1, 1,
															1 ]
												} ]
											};

											//create Chart class object
											chart1 = new Chart(ctxPie, {
												type : "pie",
												data : data1,
												options : options
											});

											//create Chart class object
											chart2 = new Chart(ctxDht, {
												type : "doughnut",
												data : data2,
												options : {}
											});

											//get the bar chart canvas

											//bar chart data
											var data3 = {

												labels : stateName,
												datasets : [ {
													  label: " Sum of Banlance State Wise",
													data : sum,
													backgroundColor : [

													"#FAEBD7", "#DCDCDC",
															"#E9967A",
															"#F5DEB3",
															"#9ACD32",
															'moccasin',
															'saddlebrown',
															'lightpink' ],
													borderColor : [

													"#CDA776", "#989898",
															"#CB252B",
															"#E39371",
															"#1D7A46"

													],
													borderWidth : 1
												} ]
											};

											//optional 
											var options2 = {
												tooltips : {
													callbacks : {
														label : function(
																tooltipItem) {

																return "$"
																		+ stateName[tooltipItem.index]
																		+ " and so worth it !"
																		+ docCount[tooltipItem.index];
															

														}
													}
												},
												responsive : true,
												title : {
													display : true,
													position : "top",
													text : "Bar Graph",
													fontSize : 18,
													fontColor : "#111"
												},
												legend : {
													display : true,
													position : "bottom",
													labels : {
														fontColor : "#333",
														fontSize : 16
													}
												},
												scales : {
													yAxes : [ {
														ticks : {
															min : 0
														}
													} ]
												}
											};

											//create Chart class object
											BarChart = new Chart(ctxBar, {
												type : "bar",
												data : data3,
												options : {}
											});
											
											
											
											//highchart
											
											
											var stackedBar = {

												labels : stateName,
												datasets : [ {
												     
												      label: 'Sum  of balance State wise',
												      data: sum,
												      backgroundColor:  'green',
												      fill: false
												    }, {
												    
												      label: 'Avgerage Balance State Wise',
												      backgroundColor: "blue",
												      data: avg,
												    }, {
												    
												      label: 'Min Balance State wise',
												      backgroundColor:  "red",
												      data: min
												    }, {
												       
												        label: 'Max Balance State wise',
												        backgroundColor: "orange", 
									                    data: max
												      }
													      
													     ]
											};
										
											HighChart = new Chart(ctxHigh,{
												type : 'horizontalBar',
												data : stackedBar,
												options : {scales: {
												      xAxes: [{
												          stacked: true
												        }],
												        yAxes: [{
												          stacked: true
												        }]
												      }}
											});
	

										},//sucess
										error : function(data) {

										}
									});
						});
	</script>



	<script type="text/javascript">



</script>
</body>
</html>
