var userCode = getUrlParam("userCode")||null;
var flag = getUrlParam("flag")||null;
//获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); //匹配目标参数
	if(null != r) {
		return unescape(r[2]);
	} else {
		return null; //返回参数值
	}
}

var up = "../../fastdev/themes/default/images/ico/up.gif";
var down = "../../fastdev/themes/default/images/ico/down.gif";


function str2dt(val){
	if(val==undefined) return "";
	var len = 19;
	if(val.length<19){
		len = val.length;
	    return	val.substring(0, len);
		}
	else{
		len = val.length;
		return val.substring(0, len).replace(".0","");
	}
}

$(document).ready(function() {
	
	$("#selfDiv11").slideToggle("slow");
	if(changeImage("#selfFlip11")) {
		setChart1(0, 0, null);
	}
	$("#selfFlip11").click(function() {
		$("#selfDiv11").slideToggle("slow");
		if(changeImage("#selfFlip11")) {
			setChart1(0, 0, null);
		}
	});
	$("#selfFlip12").click(function() {
		$("#selfDiv12").slideToggle("slow");
		if(changeImage("#selfFlip12")) {
			setChart1(0, 1, null);
		}
	});
	$("#teamFlip11").click(function() {
		$("#teamDiv11").slideToggle("slow");
		if(changeImage("#teamFlip11")) {
			setChart1(1, 0, null);
		}
	});
	$("#teamFlip12").click(function() {
		$("#teamDiv12").slideToggle("slow");
		if(changeImage("#teamFlip12")) {
			setChart1(1, 1, null);
		}
	});
	$("#departmentFlip11").click(function() {
		$("#departmentDiv11").slideToggle("slow");
		if(changeImage("#departmentFlip11")) {
			setChart1(2, 0, null);
		}
	});
	$("#departmentFlip12").click(function() {
		$("#departmentDiv12").slideToggle("slow");
		if(changeImage("#departmentFlip12")) {
			setChart1(2, 1, null);
		}
	});
	$("#selfFlip2").click(function() {
		$("#selfDiv2").slideToggle("slow");
		if(changeImage("#selfFlip2")) {
			setChart2(0, null);
		}
	});
	$("#teamFlip2").click(function() {
		$("#teamDiv2").slideToggle("slow");
		if(changeImage("#teamFlip2")) {
			setChart2(1, null);
		}
	});
	$("#departmentFlip2").click(function() {
		$("#departmentDiv2").slideToggle("slow");
		if(changeImage("#departmentFlip2")) {
			setChart2(2, null);
		}
	});
	$("#selfFlip31").click(function() {
		$("#selfDiv31").slideToggle("slow");
		if(changeImage("#selfFlip31")) {
			setChart3(0, 0, null);
		}
	});
	$("#selfFlip32").click(function() {
		$("#selfDiv32").slideToggle("slow");
		if(changeImage("#selfFlip32")) {
			setChart3(0, 1, null);
		}
	});
	$("#selfFlip33").click(function() {
		$("#selfDiv33").slideToggle("slow");
		if(changeImage("#selfFlip33")) {
			setChart3(0, 2, null);
		}
	});
	$("#teamFlip31").click(function() {
		$("#teamDiv31").slideToggle("slow");
		if(changeImage("#teamFlip31")) {
			setChart3(1, 0, null);
		}
	});
	$("#teamFlip32").click(function() {
		$("#teamDiv32").slideToggle("slow");
		if(changeImage("#teamFlip32")) {
			setChart3(1, 1, null);
		}
	});
	$("#teamFlip33").click(function() {
		$("#teamDiv33").slideToggle("slow");
		if(changeImage("#teamFlip33")) {
			setChart3(1, 2, null);
		}
	});
	$("#departmentFlip31").click(function() {
		$("#departmentDiv31").slideToggle("slow");
		if(changeImage("#departmentFlip31")) {
			setChart3(2, 0, null);
		}
	});
	$("#departmentFlip32").click(function() {
		$("#departmentDiv32").slideToggle("slow");
		if(changeImage("#departmentFlip32")) {
			setChart3(2, 1, null);
		}
	});
	$("#departmentFlip33").click(function() {
		$("#departmentDiv33").slideToggle("slow");
		if(changeImage("#departmentFlip33")) {
			setChart3(2, 2, null);
		}
	});
	
});

function changeImage(name) {
	var img = $(name).find("img")[0];
	var src = img.src;
	src = src.substring(src.indexOf("fastdev"), src.length);
	if(-1 < up.indexOf(src)) {
		img.src = down;
		return true;
	} else {
		img.src = up;
		return false;
	}
}

function switchPage(index) {
	switch(index) {
		case 1:
			$("#navbar").slideToggle("slow");
			$("#page1").show();
			$("#page2").hide();
			$("#page3").hide();
			$("#page4").hide();
			$("#page5").hide();
			$("#page6").hide();
			$("#chart1").show();
			$("#detail1").hide();
			$("#property1").hide();
			initChart1();
			break;
		case 2:
			$("#navbar").slideToggle("slow");
			$("#page1").hide();
			$("#page2").show();
			$("#page3").hide();
			$("#page4").hide();
			$("#page5").hide();
			$("#page6").hide();
			$("#chart2").show();
			$("#detail2").hide();
			$("#property2").hide();
			initChart2();
			break;
		case 3:
			$("#navbar").slideToggle("slow");
			$("#page1").hide();
			$("#page2").hide();
			$("#page3").show();
			$("#page4").hide();
			$("#page5").hide();
			$("#page6").hide();
			$("#chart3").show();
			$("#detail3").hide();
			$("#property3").hide();
			initChart3();
			break;
		case 4:
			$("#navbar").slideToggle("slow");
			$("#page1").hide();
			$("#page2").hide();
			$("#page3").hide();
			$("#page4").show();
			$("#page5").hide();
			$("#page6").hide();
			$("#chart4").show();
			$("#detail4").hide();
			$("#property4").hide();
			setChart4();
			break;
		case 5:
			$("#navbar").slideToggle("slow");
			$("#page1").hide();
			$("#page2").hide();
			$("#page3").hide();
			$("#page4").hide();
			$("#page5").show();
			$("#page6").hide();
			//$("#chart5").show();
			$("#detail5").hide();
			$("#property5").hide();
			$("#map5").attr("src", "../map/map.html");
			$("#map5").css("width", window.innerWidth);
			$("#map5").css("height", (window.innerHeight - $("#hideDiv").css("height").substring(0, $("#hideDiv").css("height").length - 2)) * 0.99);
			//setChart5();
			break;
		case 6:
			$("#navbar").slideToggle("slow");
			$("#page1").hide();
			$("#page2").hide();
			$("#page3").hide();
			$("#page4").hide();
			$("#page5").hide();
			$("#page6").show();
			$("#chart6").show();
			$("#detail6").hide();
			$("#property6").hide();
			setChart6();
			break;
		default:
			break;
	}
}

var chart1 = [];

function initChart1() {
	if(0 == chart1.length) {
		var self = [];
		// self11
		document.getElementById('self11').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('self11').style.width = window.innerWidth + 'px';
		self[0] = echarts.init(document.getElementById('self11'));
		self[0].on("click", function(param) {
			var statusStr=param.name;
			console.info(statusStr);
			if(statusStr=='未生成'){
				switchToDetail1(0,0,null,0);
			}else if(statusStr=='未下发'){
				switchToDetail1(0,0,null,1);
			}
			else if(statusStr=='未接收'){
				switchToDetail1(0,0,null,2);
			}
			else if(statusStr=='未执行'){
				switchToDetail1(0,0,null,3);
			}
			else if(statusStr=='未验证'){
				switchToDetail1(0,0,null,4);
			}
			else if(statusStr=='未评价'){
				switchToDetail1(0,0,null,5);
			}
		});

		// self12
		document.getElementById('self12').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('self12').style.width = window.innerWidth + 'px';
		self[1] = echarts.init(document.getElementById('self12'));
		self[1].on("click", function(param) {
			var statusStr=param.name;
			console.info(statusStr);
			if(statusStr=='已生成'){
				switchToDetail1(0,1,null,0);
			}else if(statusStr=='已下发'){
				switchToDetail1(0,1,null,1);
			}
			else if(statusStr=='已接收'){
				switchToDetail1(0,1,null,2);
			}
			else if(statusStr=='已执行'){
				switchToDetail1(0,1,null,3);
			}
			else if(statusStr=='已验证'){
				switchToDetail1(0,1,null,4);
			}
			else if(statusStr=='已评价'){
				switchToDetail1(0,1,null,5);
			}
		});

		chart1[0] = self;

		var team = [];
		// team11
		document.getElementById('team11').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('team11').style.width = window.innerWidth + 'px';
		team[0] = echarts.init(document.getElementById('team11'));
		team[0].on("click", function(param) {
			var statusStr=param.name;
			console.info(statusStr);
			if(statusStr=='未生成'){
				switchToDetail1(1,0,null,0);
			}else if(statusStr=='未下发'){
				switchToDetail1(1,0,null,1);
			}
			else if(statusStr=='未接收'){
				switchToDetail1(1,0,null,2);
			}
			else if(statusStr=='未执行'){
				switchToDetail1(1,0,null,3);
			}
			else if(statusStr=='未验证'){
				switchToDetail1(1,0,null,4);
			}
			else if(statusStr=='未评价'){
				switchToDetail1(1,0,null,5);
			}
		});

		// team12
		document.getElementById('team12').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('team12').style.width = window.innerWidth + 'px';
		team[1] = echarts.init(document.getElementById('team12'));
		team[1].on("click", function(param) {
			var statusStr=param.name;
			console.info(statusStr);
			if(statusStr=='已生成'){
				switchToDetail1(1,1,1,null,0);
			}else if(statusStr=='已下发'){
				switchToDetail1(1,1,1,null,1);
			}
			else if(statusStr=='已接收'){
				switchToDetail1(1,1,1,null,2);
			}
			else if(statusStr=='已执行'){
				switchToDetail1(1,1,1,null,3);
			}
			else if(statusStr=='已验证'){
				switchToDetail1(1,1,1,null,4);
			}
			else if(statusStr=='已评价'){
				switchToDetail1(1,1,1,null,5);
			}
		});

		chart1[1] = team;

		var department = [];
		// department11
		document.getElementById('department11').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('department11').style.width = window.innerWidth + 'px';
		department[0] = echarts.init(document.getElementById('department11'));
		department[0].on("click", function(param) {
			var statusStr=param.name;
			console.info(statusStr);
			if(statusStr=='未生成'){
				switchToDetail1(2,0,null,0);
			}else if(statusStr=='未下发'){
				switchToDetail1(2,0,null,1);
			}
			else if(statusStr=='未接收'){
				switchToDetail1(2,0,null,2);
			}
			else if(statusStr=='未执行'){
				switchToDetail1(2,0,null,3);
			}
			else if(statusStr=='未验证'){
				switchToDetail1(2,0,null,4);
			}
			else if(statusStr=='未评价'){
				switchToDetail1(2,0,null,5);
			}
		});

		// department12
		document.getElementById('department12').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('department12').style.width = window.innerWidth + 'px';
		department[1] = echarts.init(document.getElementById('department12'));
		department[1].on("click", function(param) {
			var statusStr=param.name;
			console.info(statusStr);
			if(statusStr=='已生成'){
				switchToDetail1(2,1,null,0);
			}else if(statusStr=='已下发'){
				switchToDetail1(2,1,null,1);
			}
			else if(statusStr=='已接收'){
				switchToDetail1(2,1,null,2);
			}
			else if(statusStr=='已执行'){
				switchToDetail1(2,1,null,3);
			}
			else if(statusStr=='已验证'){
				switchToDetail1(2,1,null,4);
			}
			else if(statusStr=='已评价'){
				switchToDetail1(2,1,null,5);
			}
			
		});

		chart1[2] = department;
	} else {
//		$("#selfDiv11").hide();
//		$("#selfDiv12").hide();
//		$("#teamDiv11").hide();
//		$("#teamDiv12").hide();
//		$("#departmentDiv11").hide();
//		$("#departmentDiv12").hide();
//		$("#selfFlip11").find("img")[0].src = up;
//		$("#selfFlip12").find("img")[0].src = up;
//		$("#teamFlip11").find("img")[0].src = up;
//		$("#teamFlip12").find("img")[0].src = up;
//		$("#departmentFlip11").find("img")[0].src = up;
//		$("#departmentFlip12").find("img")[0].src = up;
	}
}

function setChart1(x, y, time) {
	if(null == time) {
		time = $('input:radio[name="timeRadio1' + (x + 1) + (y + 1) + '"]:checked').val();
	}
	console.info(time);
	var data1, data2;
	var data10 = ['未生成', '未下发', '未接收', '未执行', '未验证', '未评价'];
	var data11 = ['已生成', '已下发', '已接收', '已执行', '已验证', '已评价'];
	
	$.ajax({
		  url:'statistical_statisticalTask.action?userCode='+userCode+'&x='+x+'&y='+y+'&time='+time+'&flag='+flag,
		  dataType:'json',
		  success:function(data){ 
			  console.info(data); 
			  if(data==undefined||data.length==0){ return; } 
			  var sum = data["sum"];
			  var dataList=data["data"];
			  var len=dataList.length;
			  for(var i=0;i<len;i++){
				  value=dataList[i]["value"];
				  name=dataList[i]["name"];
				  var data20 = dataList;
				  var data21 =dataList;
				  
				  if(0 == y) {
						data1 = data10;
						data2 = data20;
					} else {
						data1 = data11;
						data2 = data21;
					}
				    var tbs=new Array(3,2);
					  tbs=[
					       ['selfUnfinishCount','selfFinishCount'],
					       ['teamUnfinishCount','teamFinishCount'],
					       ['departUnfinishCount','departFinishCount']
					      ];
					  var sums={'key':'任务总数:',value:sum};
					   $("#"+tbs[x][y]).children().remove();
					    var td,tr=document.createElement('tr');
					    $("#"+tbs[x][y]).append(tr);
					  	td=document.createElement('td');
					  	var text=document.createTextNode(sums.key);
					  	td.appendChild(text);
					  	td.setAttribute("nowrap", "nowrap");
					  	tr.appendChild(td);
					  	td=null;
					  	td=document.createElement('td');
					  	text=document.createTextNode(sums.value);
					  	td.appendChild(text);
					  	td.setAttribute("nowrap", "nowrap");
					  	tr.appendChild(td);
					  	td=null;
				    chart1[x][y].clear();
					chart1[x][y].setOption({
					/*	title : {
							text : '任务总数:'+sum
						},*/
						legend : {
							show : false,
							data : data1
						},
						calculable : true,
						series : [{
							type : 'pie',
							center : ['45%', '50%'],
							radius : [0, '50%'],
							minAngle : 10,
							itemStyle : {
								normal : {
									label : {
										show : true,
										formatter: '{b}：\n{c} ({d}%)'
									},
									labelLine : {
										show : true,
										length : 0
									}
								}
							},
							data : data2
						}]
					});
			  };
		  }
		}); 

}

var chart2 = [];

function initChart2() {
	if(0 == chart2.length) {
		// self2
		document.getElementById('self2').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('self2').style.width = window.innerWidth + 'px';
		chart2[0] = echarts.init(document.getElementById('self2'));
		chart2[0].on("click", function(param) {//seriesIndex=0 ,1  //name日期
			var time=param.name;
			var y=param.seriesIndex;
			if(y==0){
				switchToDetail2(0,0,time);
			}else if(y==1){
				switchToDetail2(0,1,time);
			}
				
		});

		// team2
		document.getElementById('team2').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('team2').style.width = window.innerWidth + 'px';
		chart2[1] = echarts.init(document.getElementById('team2'));
		chart2[1].on("click", function(param) {
			var time=param.name;
			var y=param.seriesIndex;
			if(y==0){
				switchToDetail2(1,0,time);
			}else if(y==1){
				switchToDetail2(1,1,time);
			}
				
		});

		// department2
		document.getElementById('department2').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('department2').style.width = window.innerWidth + 'px';
		chart2[2] = echarts.init(document.getElementById('department2'));
		chart2[2].on("click", function(param) {
			var time=param.name;
			var y=param.seriesIndex;
			if(y==0){
				switchToDetail2(2,0,time);
			}else if(y==1){
				switchToDetail2(2,1,time);
			}
			
		});
	} else {
//		$("#selfDiv2").hide();
//		$("#teamDiv2").hide();
//		$("#departmentDiv2").hide();
//		$("#selfFlip2").find("img")[0].src = up;
//		$("#teamFlip2").find("img")[0].src = up;
//		$("#departmentFlip2").find("img")[0].src = up;
	}
}

function setChart2(x, time) {
    chart2[x].clear();
	if(null == time) {
		time = $('input:radio[name="timeRadio2' + (x + 1) + '"]:checked').val();
	}
	console.info(time);
	$.ajax({
		  url:'statistical_queryErrCount.action?userCode='+userCode+'&x='+x+'&time='+time+'&flag='+flag,
		  dataType:'json',
		  success:function(data){ 
			  console.info(data); 
			  if(data==undefined||data.length==0){ return; } 
			  var mydate=data["mydate"];
			  var result=new Array();
			  var sum=data["sum"];
			  var sum2=data["sum2"];
			  var sum3=data["sum3"];
			  var a;
			  for(var i=0;i<mydate.length;i++){
				   sumX=sum[i];
				   sumX2=sum2[i];
				   sumX3=sum3[i];
				   if(sumX=="0"){
						  result[i]='-';
					}else{
						 var sumN=Number(sumX);
						 var sumN2=Number(sumX2);
						 var sumN3=Number(sumX3);
						  a=(sumN2+sumN3)/sumN;
						  result[i]=Math.round(a * 10000) / 100.00; 
					}		
				  console.info(sumX+','+sumX2+','+sumX3+','+result[i]);
			  }
			  console.info(sum2);
			  var s1=0,s2=0,s3=0;
			  for(var i=0;i<sum2.length;i++){
				  s1+=(sum2[i]-0);
			  }
			  console.info(sum3);
			  for(var i=0;i<sum3.length;i++){
				  s2+=(sum3[i]-0);
			  }
			  console.info(result);
			  for(var i=0;i<result.length;i++){
				  if(result[i]=='-'){
					  continue;
				  }
				  s3+=(result[i]-0);
			  }
			  console.info(time);
			 $.ajax({
				  url:'statistical_getErrTotalCount.action?userCode='+userCode+'&x='+x+'&time='+time+'&flag='+flag, 
				  dataType:'json',
				  success:function(data){ 
					  console.info(data); 
					  if(data==undefined||data.length==0){ return; } 
					  var sum=data;
					  console.info(sum);
					  
					  var tbs=new Array(2,3);
					  tbs=[
					       ['selfErrCount','teamErrCount','departErrCount'],
					       ['个人异常总数','班组异常总数','部门异常总数']
					      ];
					  var sums=[{'key':'工作执行异常总数:',value:s1},{'key':'异常反馈总数:',value:s2},{'key':'总异常反馈率:',value:s3}];
					  $("#"+tbs[0][x]).children().remove();
					  for(var i=0;i<1;i++){
						for(var k=0;k<3;k++){
							
					    var td,tr=document.createElement('tr');
					    $("#"+tbs[0][x]).append(tr);
					  	td=document.createElement('td');
					  	var text=document.createTextNode(sums[k].key);
					  	td.appendChild(text);
					  	td.setAttribute("nowrap", "nowrap");
					  	tr.appendChild(td);
					  	td=null;
					  	td=document.createElement('td');
					  	
					  	text=document.createTextNode(sums[k].value);
					  	td.appendChild(text);
					  	td.setAttribute("nowrap", "nowrap");
					  	tr.appendChild(td);
					  	td=null;
					  }
					}
				chart2[x].setOption({
				/*	title : {
						text : '异常总数:'+sum
					},*/
					legend : {
						data : ['工作执行异常数', '异常反馈数', '异常反馈率']
					},
					grid : {
						x : '10%',
						y : '20%',
						x2 : '20%',
						y2 : '20%'
					},
					xAxis : [{
						type : 'category',
						data : mydate,
						axisLabel : {
							rotate : 30
						}
					}],
					yAxis : [{
						type : 'value',
						name : '数量',
						scale : true,
						splitLine : {
							show : false
						}
					},
					{
						type : 'value',
						name : '比率',
						scale : true,
						axisLabel : {
							formatter : function(value) {
								return value + '%';
							}
						},
						splitLine : {
							show : false
						}
					}],
					series : [{
						name : '工作执行异常数',
						type : 'bar',
						stack : 'group1',
						barMinHeight : 20,
						yAxisIndex : 0,
						itemStyle : {
							normal : {
								label : {
									show : true,
									position : 'insideTop'
								},
								labelLine : {
									show : false
								},
								color : function(param) {
									return 'red';
								}
							}
						},
						data : sum2
					}, {
						name : '异常反馈数',
						type : 'bar',
						stack : 'group1',
						barMinHeight : 20,
						yAxisIndex : 0,
						itemStyle : {
							normal : {
								label : {
									show : true,
									position : 'insideTop'
								},
								labelLine : {
									show : false
								},
								color : function(param) {
									return 'brown';
								}
							}
						},
						data : sum3
					}, {
						name : '异常反馈率',
						type : 'line',
						clickable : false,
						yAxisIndex : 1,
						itemStyle : {
							normal : {
								label : {
									show : true,
									formatter: function(param) {
										return param.data + '%';
									}
								},
								labelLine : {
									show : false
								},
								color : function(param) {
									return 'orange';
								}
							}
						},
						data : result
					}]
				});
				
				  }
			  });
		  }
	});
	
}

var chart3 = [];

function initChart3() {
	if(0 == chart3.length) {
		var self = [];
		// self31
		document.getElementById('self31').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('self31').style.width = window.innerWidth + 'px';
		self[0] = echarts.init(document.getElementById('self31'));
		self[0].on("click", function(param) {
			switchToDetail3(param,0,0);
		});

		// self32
		document.getElementById('self32').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('self32').style.width = window.innerWidth + 'px';
		self[1] = echarts.init(document.getElementById('self32'));
		self[1].on("click", function(param) {
			switchToDetail3(param,0,1);
		});

		// self33
		document.getElementById('self33').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('self33').style.width = window.innerWidth + 'px';
		self[2] = echarts.init(document.getElementById('self33'));
		self[2].on("click", function(param) {
			switchToDetail3(param,0,2);
		});

		chart3[0] = self;

		var team = [];
		// team31
		document.getElementById('team31').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('team31').style.width = window.innerWidth + 'px';
		team[0] = echarts.init(document.getElementById('team31'));
		team[0].on("click", function(param) {
			switchToDetail3(param,1,0);
		});

		// team32
		document.getElementById('team32').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('team32').style.width = window.innerWidth + 'px';
		team[1] = echarts.init(document.getElementById('team32'));
		team[1].on("click", function(param) {
			switchToDetail3(param,1,1);
		});

		// team33
		document.getElementById('team33').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('team33').style.width = window.innerWidth + 'px';
		team[2] = echarts.init(document.getElementById('team33'));
		team[2].on("click", function(param) {
			switchToDetail3(param,1,2);
		});

		chart3[1] = team;

		var department = [];
		// department31
		document.getElementById('department31').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('department31').style.width = window.innerWidth + 'px';
		department[0] = echarts.init(document.getElementById('department31'));
		department[0].on("click", function(param) {
			switchToDetail3(param,2,0);
		});

		// department32
		document.getElementById('department32').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('department32').style.width = window.innerWidth + 'px';
		department[1] = echarts.init(document.getElementById('department32'));
		department[1].on("click", function(param) {
			switchToDetail3(param,2,1);
		});

		// department33
		document.getElementById('department33').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('department33').style.width = window.innerWidth + 'px';
		department[2] = echarts.init(document.getElementById('department33'));
		department[2].on("click", function(param) {
			switchToDetail3(param,2,2);
		});

		chart3[2] = department;
	} else {
//		$("#selfDiv31").hide();
//		$("#selfDiv32").hide();
//		$("#selfDiv33").hide();
//		$("#teamDiv31").hide();
//		$("#teamDiv33").hide();
//		$("#teamDiv33").hide();
//		$("#departmentDiv31").hide();
//		$("#departmentDiv32").hide();
//		$("#departmentDiv33").hide();
//		$("#selfFlip31").find("img")[0].src = up;
//		$("#selfFlip32").find("img")[0].src = up;
//		$("#selfFlip33").find("img")[0].src = up;
//		$("#teamFlip31").find("img")[0].src = up;
//		$("#teamFlip32").find("img")[0].src = up;
//		$("#teamFlip33").find("img")[0].src = up;
//		$("#departmentFlip31").find("img")[0].src = up;
//		$("#departmentFlip32").find("img")[0].src = up;
//		$("#departmentFlip33").find("img")[0].src = up;
	}
}

function setChart3(x, y, time) {
	if(null == time) {
		time = $('input:radio[name="timeRadio3' + (x + 1) + (y + 1) + '"]:checked').val();
	}
	var url='';
	if(x==0){
		url="taskExecuteCount_getSelfExecutability.action";
	}else if(x==1){
		//url="taskExecuteCount_getSelfExecutability.action";
		url="taskExecuteCount_getGroupExecutability.action";
	}else if(x==2){
		//url="taskExecuteCount_getSelfExecutability.action";
		url="taskExecuteCount_getDepartExecutability.action";
	}
	if(!flag){
		flag=null;
	}
   $.post(url,
	      {"userCode":userCode,"time_type":time,"task_type":y,"flag":flag},
          function(data,status){
	if(status!='success'){
		return;
	}			
	
	var data1;
	var data10 = ['提前数', '逾期数', '提前率', '逾期率', '提前量', '逾期量', '合格数', '不合格数'];
	var data11 = ['提前数', '逾期数', '提前率', '逾期率', '提前量', '逾期量'];
	if(0 == y) {
		data1 = data10;
		//data2 = [22, 18, 19, 23, 23, 25, 21];
		//data3 = [-12, -13, -10, -13, -9, -13, -10];
	} else {
		data1 = data11;
		//data2 = ['-'];
		//data3 = ['-'];
	}
	chart3[x][y].clear();
	var  zoom={};
	var  timeAxis=new Array();
	timeAxis=[{
		type : 'category',
		data : data.days,
		axisLabel : {
			rotate : 30
		}
	}];
	if(time==3){
		zoom={
				 zoomLock:true,
			        show : true,
			        realtime: true,
			        start : 0,
			        end : 25
			    };
		timeAxis=[{
			type : 'category',
			boundaryGap : true,
	        axisTick: {onGap:false},
	        splitLine: {show:false},
			data : data.days,
			axisLabel : {
				rotate : 30
			}
		}];
	}
	
	
	
	
	chart3[x][y].setOption({
		legend : {
			selected : {
				'合格数' : false,
				'不合格数' : false
			},
			data : data1
		},
		grid : {
			x : '10%',
			y : '20%',
			x2 : '20%',
			y2 : '20%'
		},
		 dataZoom : zoom,
		xAxis : timeAxis,
		yAxis : [{
			type : 'value',
			name : '数量',
			scale : true,
			splitLine : {
				show : false
			}
		},
		{
			type : 'value',
			name : '比率',
			scale : true,
			axisLabel : {
				formatter : function(value) {
					return value + '%';
				}
			},
			splitLine : {
				show : false
			}
		}],
		series : [{
			name : '提前数',
			type : 'bar',
			stack : 'group1',
			barMinHeight : 20,
			yAxisIndex : 0,
			itemStyle : {
				normal : {
					label : {
						show : true,
						position : 'insideTop'
					},
					labelLine : {
						show : false
					},
					color : function(param) {
						return 'green';
					}
				}
			},
			data : data.advanceCountList
		}, {
			name : '逾期数',
			type : 'bar',
			stack : 'group1',
			barMinHeight : 20,
			yAxisIndex : 0,
			itemStyle : {
				normal : {
					label : {
						show : true,
						position : 'insideBottom',
						formatter : function(param) {
							return (0 - param.data);
						}
					},
					labelLine : {
						show : false
					},
					color : function(param) {
						return 'red';
					}
				}
			},
			data : data.lateCountList
		}, {
			name : '提前率',
			type : 'line',
			clickable : false,
			yAxisIndex : 1,
			itemStyle : {
				normal : {
					label : {
						show : true,
						formatter: function(param) {
							return param.data + '%';
						}
					},
					labelLine : {
						show : false
					}
				}
			},
			data : data.advanceCountRateList
		}, {
			name : '逾期率',
			type : 'line',
			clickable : false,
			yAxisIndex : 1,
			itemStyle : {
				normal : {
					label : {
						show : true,
						formatter: function(param) {
							return param.data + '%';
						}
					},
					labelLine : {
						show : false
					}
				}
			},
			data : data.lateCountRateList
		}, {
			name : '提前量',
			type : 'line',
			clickable : false,
			yAxisIndex : 1,
			itemStyle : {
				normal : {
					label : {
						show : true,
						formatter: function(param) {
							return param.data + '%';
						}
					},
					labelLine : {
						show : false
					}
				}
			},
			data : data.advanceTimeTotalRateList
		}, {
			name : '逾期量',
			type : 'line',
			clickable : false,
			yAxisIndex : 1,
			itemStyle : {
				normal : {
					label : {
						show : true,
						formatter: function(param) {
							return param.data + '%';
						}
					},
					labelLine : {
						show : false
					}
				}
			},
			data : data.lateTimeTotalRateList
		}, {
			name : '合格数',
			type : 'bar',
			stack : 'group2',
			barMinHeight : 20,
			yAxisIndex : 0,
			itemStyle : {
				normal : {
					label : {
						show : true,
						position : 'insideTop'
					},
					labelLine : {
						show : false
					},
					color : function(param) {
						return 'orange';
					}
				}
			},
			data : data.dayHeGeCountList||[]
		}, {
			name : '不合格数',
			type : 'bar',
			stack : 'group2',
			barMinHeight : 20,
			yAxisIndex : 0,
			itemStyle : {
				normal : {
					label : {
						show : true,
						position : 'insideBottom',
						formatter : function(param) {
							return (0 - param.data);
						}
					},
					labelLine : {
						show : false
					},
					color : function(param) {
						return 'brown';
					}
				}
			},
			data : data.dayBuHeGeCountList||[]
		}]
	});
	
if(data.sumLateCountRate!='-'){
	data.sumLateCountRate+='%';
}

if(data.sumAdvanceCountRate!='-'){
	data.sumAdvanceCountRate+='%';
}
if(data.sumAdvancTimeRate!='-'){
	data.sumAdvancTimeRate+='%';
}
if(data.sumLateTimeRate!='-'){
	data.sumLateTimeRate+='%';
}
/*var str="<tr ><td>总提前数：</td><td>"+data.sumAdvanceCount+
"</td><td>总逾期数：</td><td>"+data.sumLateCount+
"</td><td>总提前率：</td><td>"+data.sumAdvanceCountRate+
"</td><td>总逾期率：</td><td>"+data.sumLateCountRate+"</td></tr>"+
"<tr><td>总提前量：</td><td>"+data.sumAdvancTimeRate+
"</td><td>总逾期量：</td><td>"+data.sumLateTimeRate+
"</td><td>总合格数：</td><td>"+data.sumHeGe+
"</td><td>总不合格数：</td><td>"+data.sumBuHeGe+"</td></tr>";
$("#sumInfo").append(str);*/

var name=new Array(8,2);
name=[    ['总提前数:',data.sumAdvanceCount],
          ['总逾期数:',data.sumLateCount],
          ['总提前量:',data.sumAdvancTimeRate],
          ['总逾期量:',data.sumLateTimeRate],
          ['总提前率:',data.sumAdvanceCountRate],
          ['总逾期率:',data.sumLateCountRate],
          ['总合格数:',data.sumHeGe||'0'],
          ['总不合格数:',data.sumBuHeGe||'0']];
var tr=document.createElement('tr'),td;
var tables=new Array(3,3);
tables=[
        ['sumSelfExecuteInfo','sumSelfChkInfo','sumSelfEvaluateInfo'],
        ['sumGroupExecuteInfo','sumGroupChkInfo','sumGroupEvaluateInfo'],
        ['sumDepartExecuteInfo','sumDepartChkInfo','sumDepartEvaluateInfo']
       ];
var tb=tables[x][y];
$("#"+tb).children().remove();
$("#"+tb).append(tr);
var rowcount=6;
if(y==0){
	rowcount=8;
}
for(var i=0;i<8;i++){
	if(i%2==0&&i!=0){
		tr=document.createElement('tr');
		$("#"+tb).append(tr);
	}
	if(rowcount==6&&(i==6||i==7)){
		continue;
	}
	td=document.createElement('td');
	var text=document.createTextNode(name[i][0]);
	td.appendChild(text);
	td.setAttribute("nowrap", "nowrap");
	tr.appendChild(td);
	td=null;
	td=document.createElement('td');
	
	text=document.createTextNode(name[i][1]);
	td.appendChild(text);
	td.setAttribute("nowrap", "nowrap");
	tr.appendChild(td);
	td=null;
}

	      });
   
}

var chart4 = null;

function setChart4() {
	if(null == chart4) {
		// chart4
		document.getElementById('chart4').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('chart4').style.width = window.innerWidth + 'px';
		chart4 = echarts.init(document.getElementById('chart4'));
		chart4.on("click", function(param) {
			var areaName=param.name;
			var area='';
			
			if("高层仓库"==areaName){
				area="1000100000009";
			}else if("锅炉房"==areaName){
				area="1000100000005";
			}else if("动力中心"==areaName){
				area="1000100000015";
			}else if("卷包车间"==areaName){
				area="1000100000009";
			}else if("制丝车间"==areaName){
				area="1000100000009";
			}else if("办公楼"==areaName){
				area="1000100000009";
			}
			switchToDetail4(area);
		});
	} else {
		chart4.clear();
	}
	$.ajax({
		  url:'statistical_checkTempStatus.action', 
		  dataType:'json',
		  success:function(data){ 
			  console.info(data); 
			  if(data==undefined||data.length==0){ return; }
			  chart4.setOption({
					grid : {
						x : '20%',
						y : '10%',
						x2 : '20%',
						y2 : '10%'
					},
					xAxis : [{
						show : false,
						type : 'value'
					}],
					yAxis : [{
						type : 'category',
						data : ['办公楼', '制丝车间', '卷包车间', '动力中心','锅炉房', '高层仓库']
					}],
					series : [{
						type : 'bar',
						itemStyle : {
							normal : {
								label : {
									show : true,
									formatter: '{c}'
								},
								labelLine : {
									show : false
								},
								color : function(param) {
									return param.data.color;
								}
							}
						},
						data : [{
							value : 120,
							color : 'green'
						},
						{
							value : 132,
							color : 'green'
						},
						{
							value : 90,
							color : 'blue'
						},
						{
							value : data[2],
							color : 'orange'
						},
						{
							value : data[1],
							color : 'orange'
						},
						{
							value : data[0],
							color : 'red'
						}]
					}]
				});
		  }
	});
	
}

var chart5 = null;

function setChart5() {
	if(null == chart5) {
		// chart5
		document.getElementById('chart5').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('chart5').style.width = window.innerWidth + 'px';
		chart5 = echarts.init(document.getElementById('chart5'));
		chart5.on("click", function(param) {
			switchToDetail5();
		});
	} else {
		chart5.clear();
	}

	chart5.setOption({
		grid : {
			x : '20%',
			y : '10%',
			x2 : '20%',
			y2 : '10%'
		},
		xAxis : [{
			show : false,
			type : 'value'
		}],
		yAxis : [{
			type : 'category',
			data : ['办公楼', '动力中心', '制丝车间', '卷包车间', '锅炉房', '高层仓库']
		}],
		series : [{
			type : 'bar',
			itemStyle : {
				normal : {
					label : {
						show : true,
						formatter: '{c}'
					},
					labelLine : {
						show : false
					},
					color : function(param) {
						return param.data.color;
					}
				}
			},
			data : [{
				value : 120,
				color : 'green'
			},
			{
				value : 132,
				color : 'green'
			},
			{
				value : 90,
				color : 'blue'
			},
			{
				value : 230,
				color : 'orange'
			},
			{
				value : 210,
				color : 'orange'
			},
			{
				value : 300,
				color : 'red'
			}]
		}]
	});
}

var chart6 = null;

function setChart6() {
	if(null == chart6) {
		// chart6
		document.getElementById('chart6').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('chart6').style.width = window.innerWidth + 'px';
		chart6 = echarts.init(document.getElementById('chart6'));
		chart6.on("click", function(param) {
			var areaName=param.name;
			var area='';
			
			if("高层仓库"==areaName){
				area="1000100000009";
			}else if("锅炉房"==areaName){
				area="1000100000005";
			}else if("动力中心"==areaName){
				area="1000100000015";
			}else if("卷包车间"==areaName){
				area="1000100000009";
			}else if("制丝车间"==areaName){
				area="1000100000009";
			}else if("办公楼"==areaName){
				area="1000100000009";
			}
			switchToDetail6(area);
		});
	} else {
		chart6.clear();
	}
	$.ajax({
		  url:'statistical_checkEnergyStatus.action', 
		  dataType:'json',
		  success:function(data){ 
			  console.info(data); 
			  if(data==undefined||data.length==0){ return; }
				chart6.setOption({
					grid : {
						x : '20%',
						y : '10%',
						x2 : '20%',
						y2 : '10%'
					},
					xAxis : [{
						show : false,
						type : 'value'
					}],
					yAxis : [{
						type : 'category',
						data : ['办公楼', '制丝车间', '卷包车间','动力中心', '锅炉房', '高层仓库']
					}],
					series : [{
						type : 'bar',
						itemStyle : {
							normal : {
								label : {
									show : true,
									formatter: '{c}'
								},
								labelLine : {
									show : false
								},
								color : function(param) {
									return param.data.color;
								}
							}
						},
						data : [{
							value : 120,
							color : 'green'
						},
						{
							value : 132,
							color : 'green'
						},
						{
							value : 90,
							color : 'blue'
						},
						{
							value : data[2],
							color : 'orange'
						},
						{
							value : data[1],
							color : 'orange'
						},
						{
							value : data[0],
							color : 'red'
						}]
					}]
				});
		  }
	});
}


function switchToDetail1(x,y,time,status){
	if(null == time) {
		time = $('input:radio[name="timeRadio1' + (x + 1) + (y + 1) + '"]:checked').val();
	}
	
	$("#chart1").hide();
	$("#detail1").show();
	$("#property1").hide();
	//$("#navbar").slideToggle("slow");
	setDetail1(x,y,time,status);
}

function switchToDetail2(x,y,time){
	$("#chart2").hide();
	$("#detail2").show();
	$("#property2").hide();
	setDetail2(x,y,time);
}

function switchToDetail3(param,x,y){
	$("#chart3").hide();
	$("#detail3").show();
	$("#property3").hide();
	var params={};
	params['orgtype']=x;
	params['start_time']=param.name;
	params['task_type']=y;
	params['userCode']=userCode;
	var sname=param.seriesName||'';
	sname=$.trim(sname);
	if(sname=='提前数'){
		params['details_type']=1;
	}else if(sname=='逾期数'){
		params['details_type']=2;
	}else if(sname='合格数'){
		params['details_type']=3;
	}else if(sname='不合格数'){
		params['details_type']=4;
	}
	setDetail3(params);
}
function switchToDetail4(area){
	$("#chart4").hide();
	$("#detail4").show();
	$("#property4").hide();
	setDetail4(area);
}
function switchToDetail5(){
	$("#chart5").hide();
	$("#detail5").show();
	$("#property5").hide();
	setDetail5();
}
function switchToDetail6(area){
	$("#chart6").hide();
	$("#detail6").show();
	$("#property6").hide();
	setDetail6(area);
}


function setDetail1(x,y,time,status) {
	//var detail = document.getElementById('detail1');

	$.ajax({
		 url:'statistical_queryTaskList.action?userCode='+userCode+'&x='+x+'&y='+y+'&time='+time+'&status='+status+'&flag='+flag,
		  dataType:'json',
		  success:function(data){ 
			  console.info(data); 
			  if(data==undefined||data.length==0){ return; } 
			  row = data["row"];
			  dataList=data["data"];
			  var statusStr="";
			  var innerHTml = ""; 
			      innerHTml +='<div width="100%" height="100%" >';
			      innerHTml +='<table id="tb12345" cellspacing="0" cellpadding="0" width="100%" height="100%" style="margin-top:40px;">';
			      innerHTml +='<tr><th  class="th123456" style="border-left:1px solid black;">任务名</th><th   class="th123456">任务类型</th><th class="th123456">任务类别</th><th class="th123456">任务状态</th><th class="th123456">执行人</th><th class="th123456">部门</th><th class="th123456">执行区域</th><th class="th123456">岗位</th></tr>';
			  
			  for(var i=0;i<dataList.length;i++){
					innerHTml +='<tr>';
					innerHTml +='<td  class="td123456" style="border-left:1px solid black;">'+(dataList[i].c_task_name==undefined?"":dataList[i].c_task_name)+'</td>';
					innerHTml +='<td  class="td123456">'+(dataList[i].c_task_kindname==undefined?"":dataList[i].c_task_kindname)+'</td>';
					innerHTml +='<td  class="td123456">'+(dataList[i].typename==undefined?"":dataList[i].typename)+'</td>';
				    switch(y){
					case 0:
						switch(status){
						case 0:
							statusStr='未生成';
							break;
					    case 1:
						    statusStr='未下发';
						    break;
			            case 2:
					        statusStr='未接收';
					        break;
		                case 3:
				            statusStr='未执行';
				            break;
	                    case 4:
		                    statusStr='未验证';
		                    break;
	                    case 5:
		                    statusStr='未评价';
		                    break;
						}
		            break;
					case 1:
						switch(status){
							case 0:
								statusStr='已生成';
								break;
						    case 1:
							    statusStr='已下发';
							    break;
				            case 2:
						        statusStr='已接收';
						        break;
			                case 3:
					            statusStr='已执行';
					            break;
		                    case 4:
			                    statusStr='已验证';
			                    break;
		                    case 5:
			                    statusStr='已评价';
			                    break;
						}
					}
					innerHTml +='<td class="td123456">'+statusStr+'</td>';
					innerHTml +='<td class="td123456">'+(dataList[i].c_exec_username==undefined?"":dataList[i].c_exec_username)+'</td>';
					innerHTml +='<td class="td123456">'+(dataList[i].posiname==undefined?"":dataList[i].posiname)+'</td>';
					innerHTml +='<td class="td123456">'+(dataList[i].orgname==undefined?"":dataList[i].orgname)+'</td>';
					innerHTml +='<td class="td123456">'+(dataList[i].c_areaname==undefined?"":dataList[i].c_areaname)+'</td>';
					innerHTml +='</tr>';
					
				}
			   innerHTml +='</table></div>';
			   document.getElementById('detail1').innerHTML = innerHTml;
		  }
	   });
}


function setDetail2(x,y,time) {
	$.ajax({
		 url:'statistical_queryErrList.action?userCode='+userCode+'&x='+x+'&y='+y+'&time='+time+'&flag='+flag,
		  dataType:'json',
		  success:function(data){ 
			  console.info(data); 
			  if(data==undefined||data.length==0){ return; } 
			  var innerHTml = ""; 
			      innerHTml +='<div width="100%" height="100%" >';
			      innerHTml +='<table id="tb123456"  cellspacing="0" cellpadding="0" width="100%" height="100%">';
			      innerHTml +='<tr><th class="th123456" style="border-left:1px solid black;">异常主题</th><th class="th123456">所属板块</th><th class="th123456">执行人</th><th class="th123456">反馈人</th><<th class="th123456">反馈时间</th><th class="th123456">接收时间</th></tr>';
			  
			  for(var i=0;i<data.length;i++){
					innerHTml +='<tr >';
					innerHTml +='<td class="td123456" style="border-left:1px solid black;">'+(data[i].c_feedback_title==undefined?"":data[i].c_feedback_title)+'</td>';
					innerHTml +='<td class="td123456" >'+(data[i].c_manage_sectionname==undefined?"":data[i].c_manage_sectionname)+'</td>';
					innerHTml +='<td class="td123456" >'+(data[i].c_receiver_username==undefined?"":data[i].c_receiver_username)+'</td>';
					innerHTml +='<td class="td123456" >'+(data[i].c_feedbacker_name==undefined?"":data[i].c_feedbacker_name)+'</td>';
					innerHTml +='<td class="td123456" >'+(data[i].c_feedback_time==undefined?"":str2dt(data[i].c_feedback_time))+'</td>';
					innerHTml +='<td class="td123456" >'+(data[i].c_receiver_time==undefined?"":str2dt(data[i].c_receiver_time))+'</td>';
					innerHTml +='</tr>';
					
				}
			   innerHTml +='</table></div>';
			   document.getElementById('detail2').innerHTML = innerHTml;
		  }
	   });
	
}

function setDetail3(params) {
	params['flag']=flag;
	var detail = document.getElementById('detail3');
	detail.innerHTML = "";
	var t = document.createElement('table');
	t.setAttribute("width", "100%");
	var b = document.createElement('tbody');
	var org=['本人执行力(','班组执行力(','部门执行力('];
	var step=['任务执行','任务验证','任务评价'];
	var dataType=['','提前完成数详情)','逾期完成数详情)','合格完成数详情)','不合格完成数详情)'];
	
	var columnsTitles2=['c_task_name','start_time','end_time','fact_end_time'];
	var columnsTitles=new Array(1,columnsTitles2.length);
		columnsTitles=[
		               ['序号','任务名称','任务开始时间','任务结束时间','任务实际完成时间'],
		               ['序号','任务名称','验证开始时间','验证期限','验证实际完成时间'],
		               ['序号','任务名称','评价开始时间','评价期限','评价实际完成时间']
		              ];
    
	var caption = document.createElement('th');
	caption.setAttribute("class","th123456");
	var captionText=document.createTextNode(org[params['orgtype']]+step[params['task_type']]+dataType[params['details_type']]);
	caption.appendChild(captionText);
	caption.setAttribute("nowrap", 'nowrap');
	caption.setAttribute('colspan', (columnsTitles2.length+1)+"");
	//caption.setAttribute('border', '0px');
	b.appendChild(caption);
	
	var row = document.createElement('tr');
	for(var i=0;i<columnsTitles2.length+1;i++){
		var c = document.createElement('td');
		c.setAttribute("class", "td123456");
		var m = document.createTextNode(columnsTitles[params['task_type']][i]);
		c.appendChild(m);
		row.appendChild(c);
	}
	b.appendChild(row);
	$.post("taskExecuteCount_getDataDetails.action",params,function(data,status){
		if(status=='success'&&data){
		for (var i = 0; i <data.length; i++) {
			var r = document.createElement('tr');
			var c = document.createElement('td');
			c.setAttribute("class","td123456");
			var m = document.createTextNode(i+1);
			c.appendChild(m);
			r.appendChild(c);
			for (var j = 0; j < columnsTitles2.length; j++) {
			    c = document.createElement('td');
			    c.setAttribute("class","td123456");
			   // c.setAttribute('border', '1px');
			   // c.setAttribute("nowrap", "nowrap");
			    var str=data[i][columnsTitles2[j]];
			    if(j==2||j==3){
			    	
			    	str=str.replace("T"," ");
			    }
			    m = document.createTextNode(str);
				c.appendChild(m);
				r.appendChild(c);
			}
			b.appendChild(r);
		}
		t.appendChild(b);
	//	t.setAttribute('border', '1');
		t.setAttribute('cellspacing', '0');
		detail.appendChild(t);
		}
	});
	
}

var detail4 = null;

function setDetail4(area) {
	if(null == detail4) {
		// detail4
		document.getElementById('detail4').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('detail4').style.width = window.innerWidth + 'px';
		detail4 = echarts.init(document.getElementById('detail4'));
		detail4.on("click", function(param) {
			switchToProperty(4);
		});
	} else {
		detail4.clear();
	}	
	$.ajax({
		 url:'statistical_getSecondAreaCount.action?area='+area,
		  dataType:'json',
		  success:function(data){ 
			  console.info(data); 
			  if(data==undefined||data.length==0){ return; } 
			  var arr=new Array();
			  var item=[];
			  for(var i=0;i<data.length;i++){
				  arr[i]=data[i].name;
				  var  json={};
				  json['value']=data[i].count;
				  json['color']='gray';
				  item[i]=json;

			  }
			  console.info(item);
			  detail4.setOption({
					grid : {
						x : '15%',
						y : '10%',
						x2 : '20%',
						y2 : '10%'
					},
					xAxis : [{
						show : false,
						type : 'value'
					}],
					yAxis : [{
						type : 'category',
						data : arr
					}],
					series : [{
						type : 'bar',
						itemStyle : {
							normal : {
								label : {
									show : true,
									formatter: '{c}'
								},
								labelLine : {
									show : false
								},
								color : function(param) {
									return param.data.color;
								}
							}
						},
						data : item
					}]
				});
		  }
	});	 
}

var detail5 = null;

function setDetail5() {
	if(null == detail5) {
		// detail5
		document.getElementById('detail5').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('detail5').style.width = window.innerWidth + 'px';
		detail5 = echarts.init(document.getElementById('detail5'));
		detail5.on("click", function(param) {
			switchToProperty(5);
		});
	} else {
		detail5.clear();
	}

	detail5.setOption({
		grid : {
			x : '15%',
			y : '10%',
			x2 : '20%',
			y2 : '10%'
		},
		xAxis : [{
			show : false,
			type : 'value'
		}],
		yAxis : [{
			type : 'category',
			data : ['一楼', '二楼', '三楼', '四楼', '五楼', '六楼']
		}],
		series : [{
			type : 'bar',
			itemStyle : {
				normal : {
					label : {
						show : true,
						formatter: '{c}'
					},
					labelLine : {
						show : false
					},
					color : function(param) {
						return param.data.color;
					}
				}
			},
			data : [{
				value : 18,
				color : 'gray'
			},
			{
				value : 23,
				color : 'gray'
			},
			{
				value : 29,
				color : 'gray'
			},
			{
				value : 10,
				color : 'gray'
			},
			{
				value : 13,
				color : 'gray'
			},
			{
				value : 15,
				color : 'gray'
			}]
		}]
	});
	
}

var detail6 = null;

function setDetail6(area) {
	if(null == detail6) {
		// detail6
		document.getElementById('detail6').style.height = window.innerHeight * 0.6 + 'px';
		document.getElementById('detail6').style.width = window.innerWidth + 'px';
		detail6 = echarts.init(document.getElementById('detail6'));
		detail6.on("click", function(param) {
			switchToProperty(6);
		});
	} else {
		detail6.clear();
	}

	$.ajax({
		 url:'statistical_getSecondAreaCount2.action?area='+area,
		  dataType:'json',
		  success:function(data){ 
			  console.info(data); 
			  if(data==undefined||data.length==0){ return; } 
			  var arr=new Array();
			  var item=[];
			  for(var i=0;i<data.length;i++){
				  arr[i]=data[i].name;
				  var  json={};
				  json['value']=data[i].count;
				  json['color']='gray';
				  item[i]=json;

			  }
			  console.info(item);
			  detail6.setOption({
					grid : {
						x : '15%',
						y : '10%',
						x2 : '20%',
						y2 : '10%'
					},
					xAxis : [{
						show : false,
						type : 'value'
					}],
					yAxis : [{
						type : 'category',
						data : arr
					}],
					series : [{
						type : 'bar',
						itemStyle : {
							normal : {
								label : {
									show : true,
									formatter: '{c}'
								},
								labelLine : {
									show : false
								},
								color : function(param) {
									return param.data.color;
								}
							}
						},
						data : item
					}]
				});
		  }
	});
}

function switchToProperty(index) {
	switch(index) {
		case 1:
			$("#chart1").hide();
			$("#detail1").hide();
			$("#property1").show();
			setProperty1();
			break;
		case 2:
			$("#chart2").hide();
			$("#detail2").hide();
			$("#property2").show();
			setProperty2();
			break;
		case 3:
			$("#chart3").hide();
			$("#detail3").hide();
			$("#property3").show();
			setProperty3();
			break;
		case 4:
			$("#chart4").hide();
			$("#detail4").hide();
			$("#property4").show();
			setProperty4(null);
			break;
		case 5:
			$("#chart5").hide();
			$("#detail5").hide();
			$("#property5").show();
			setProperty5();
			break;
		case 6:
			$("#chart6").hide();
			$("#detail6").hide();
			$("#property6").show();
			setProperty6();
			break;
		default:
			break;
	}
}

var property4 = null;

function setProperty4(time) {
	if(null == property4) {
		// property4
		document.getElementById('propertyChart4').style.height = window.innerHeight * 0.7 + 'px';
		document.getElementById('propertyChart4').style.width = window.innerWidth + 'px';
		property4 = echarts.init(document.getElementById('propertyChart4'));
	} else {
		property4.clear();
	}

	if(null == time) {
		time = $('input:radio[name="timeRadio4"]:checked').val();
	}

	property4.setOption({
		legend : {
			data : ['温度', '湿度']
		},
		grid : {
			x : '15%',
			y : '20%',
			x2 : '20%',
			y2 : '40%'
		},
		xAxis : [{
			type : 'category',
			data : ['2015-03-23 12:28:00', '2015-03-23 17:28:00', '2015-03-24 12:28:00', '2015-03-24 17:28:00', '2015-03-25 12:28:00', '2015-03-25 17:28:00', '2015-03-26 12:28:00','2015-03-26 17:28:00', '2015-03-27 12:28:00', '2015-03-27 17:28:00'],
			axisLabel : {
				rotate : 60
			}
		}],
		yAxis : [{
			type : 'value',
			name : '温度/°C',
			scale : true,
			axisLine : {
				lineStyle : {
					color : 'orange'
				}
			}
		},
		{
			type : 'value',
			name : '湿度/%',
			scale : true,
			axisLine : {
				lineStyle : {
					color : 'green'
				}
			}
		}],
		series : [{
			name : '温度',
			type : 'line',
			clickable : false,
			yAxisIndex : 0,
			itemStyle : {
				normal : {
					label : {
						show : true,
						formatter: function(param) {
							return param.data.value + '°C';
						}
					},
					labelLine : {
						show : false
					},
					color : function(param) {
						if(param.data) {
							return param.data.color;
						} else {
							return 'orange';
						}
					}
				}
			},
			data : [{
				value : 28,
				color : 'orange'
			},
			{
				value : 29,
				color : 'orange'
			},
			{
				value : 30,
				color : 'orange'
			},
			{
				value : 31,
				color : 'red'
			},
			{
				value : 30,
				color : 'orange'
			},
			{
				value : 29,
				color : 'orange'
			},
			{
				value : 28,
				color : 'orange'
			},
			{
				value : 31,
				color : 'red'
			},
			{
				value : 29,
				color : 'orange'
			},
			{
				value : 30,
				color : 'orange'
			}],
			markLine : {
				clickable : false,
				symbolSize : [0, 0],
				itemStyle : {
					normal : {
						color : 'black',
						lineStyle : {
							type : 'solid'
						},
						label : {
							show : true,
							position : 'right',
							formatter: function(param) {
								return param.data.value;
							}
						}
					}
				},
				data : [
					[{
						value : '30°C',
						xAxis : -1,
						yAxis : 30
					}, {
						xAxis : 99999,
						yAxis : 30
					}],
					[{
						value : '25°C',
						xAxis : -1,
						yAxis : 25
					}, {
						xAxis : 99999,
						yAxis : 25
					}]
				]
			}
		},
		{
			name : '湿度',
			type : 'line',
			clickable : false,
			yAxisIndex : 1,
			itemStyle : {
				normal : {
					label : {
						show : true,
						formatter: function(param) {
							return param.data.value + '%';
						}
					},
					labelLine : {
						show : false
					},
					color : function(param) {
						if(param.data) {
							return param.data.color;
						} else {
							return 'green';
						}
					}
				}
			},
			data : [{
				value : 58,
				color : 'red'
			},
			{
				value : 60,
				color : 'green'
			},
			{
				value : 59,
				color : 'red'
			},
			{
				value : 61,
				color : 'green'
			},
			{
				value : 60,
				color : 'green'
			},
			{
				value : 62,
				color : 'green'
			},
			{
				value : 61,
				color : 'green'
			},
			{
				value : 60,
				color : 'green'
			},
			{
				value : 59,
				color : 'red'
			},
			{
				value : 61,
				color : 'green'
			}],
			markLine : {
				clickable : false,
				symbolSize : [0, 0],
				itemStyle : {
					normal : {
						color : 'black',
						lineStyle : {
							type : 'solid'
						},
						label : {
							show : true,
							position : 'left',
							formatter: function(param) {
								return param.data.value;
							}
						}
					}
				},
				data : [
					[{
						value : '65%',
						xAxis : -1,
						yAxis : 65
					}, {
						xAxis : 99999,
						yAxis : 65
					}],
					[{
						value : '60%',
						xAxis : -1,
						yAxis : 60
					}, {
						xAxis : 99999,
						yAxis : 60
					}]
				]
			}
		}]
	});
}

function setProperty5() {
	var detail = document.getElementById('property5');
	detail.innerHTML = "";
	var t = document.createElement('table');
	var b = document.createElement('tbody');
	for (var i = 0; i < 10; i++) {
		var r = document.createElement('tr');
		for (var j = 0; j < 6; j++) {
			var c = document.createElement('td');
			var m = document.createTextNode(i + ',' + j);
			c.appendChild(m);
			r.appendChild(c);
		}
		b.appendChild(r);
	}
	t.appendChild(b);
	t.setAttribute('border', '1');
	detail.appendChild(t);
}

function setProperty6() {
	var detail = document.getElementById('property6');
	detail.innerHTML = "";
	var t = document.createElement('table');
	var b = document.createElement('tbody');
	for (var i = 0; i < 10; i++) {
		var r = document.createElement('tr');
		for (var j = 0; j < 6; j++) {
			var c = document.createElement('td');
			var m = document.createTextNode(i + ',' + j);
			c.appendChild(m);
			r.appendChild(c);
		}
		b.appendChild(r);
	}
	t.appendChild(b);
	t.setAttribute('border', '1');
	detail.appendChild(t);
}

var flags=0;

function oper(){
	if(flags==0){
		flags=1;	
		$("#showHide").html('<img id="imgs" src="../images/g2.png"/>');
		//$("#showHide").text('展开');
		//$('#navbar').show();
		$("#navbar").slideToggle("slow");
		//$('#showHide').hide();
	}else{
		flags=0;
		$("#showHide").html('<img id="imgs" src="../images/g1.png"/>');
		//$("#showHide").text('收起');
		//$('#navbar').hide();
		$("#navbar").slideToggle("slow");
	}
	
}


/*$("#navbar ul li").mouseout(function(){
    $("#navbar").css("background-color","yellow");
  });*/

function mouse(){
	//alert("1234");
	$("#navbar").slideToggle("slow");
}

function mouse2(){
	
	$("#navbar").hide();
	
}

