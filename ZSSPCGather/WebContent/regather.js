var gatherflag=1;         //采集方式 1工序，2时间
var processFlag=0;
$(document).ready(function(){
	$("#my_process").hide();
	$("#submit").on('click',function(){
		regatherCheck()
	});
	initTimePicker();
	initDisplayOne();
});

//初始化时间控件
function initTimePicker(){
	$("#startDate").datetimepicker({
	    format: 'yyyy-mm-dd hh',//格式
	    minView:1,//最小显示
	    initialDate: new Date(),//初始化当前日期
		todayBtn: true,//显示今天按钮
	    language: 'zh-CN',//设置文字
	    autoclose:true//点击关闭
	}).on("click",function(){
	    $("#startDate").datetimepicker("setEndDate",$('#endDate').val())//甚至可选的最后时间
	});
	$("#endDate").datetimepicker({
	    format: 'yyyy-mm-dd hh',
	    minView:1,
		initialDate: new Date(),//初始化当前日期
		todayBtn: true,
	    language: 'zh-CN',
	    autoclose:true
//		        startDate:new Date()
	}).on("click",function(){
	    $("#endDate").datetimepicker("setStartDate",$("#startDate").val())
	});
}
//初始化复选panel
function initDisplayOne(){
	var panlHtml="";
	$.ajax({
		type : "post",
		url: 'dynamic/regather/getProcessMap',
		success : function(data) {
			var processArray=data
			var displayOne=$('#process');
			for(processId in processArray){
				var process=processArray[processId];
				panlHtml=panlHtml+'<option value='+process.ftag+'>'+process.fname+'</option>';
			}
			displayOne.html(panlHtml);
		}
	});
}
function regatherCheck(){
	var data=checkParam();
	if(processFlag==0){ //1表示有处理未完成
		if(data.flag==1){
			processFlag=1;
			showProcessInfo();
			$.ajax({
				type : "post",
				url: 'dynamic/regather/regatherByProcessBatch',
				data:data,
				timeout:45000,
				success:function(dataj) {
					showSucessInfo();
					var message=dataj;
					if(message.trim()!=""){
						$("#my_info h5").html(message);
						$("[data-toggle='popover']").attr("data-content",message);
						$("[data-toggle='popover']").popover('show');
						
						setTimeout(function(){
							$("[data-toggle='popover']").popover('destroy');
						},1800);
						
						showSucessInfo();
					}
					processFlag=0;
				},
				complete:function(XMLHttpRequest,status){
					if(status=='timeout'){//超时,status还有success,error等值的情况
						$("#my_info h5").html("请求超时");
						$("#my_processbar").removeClass("active");
						$("#my_processinfo").removeClass("progress-bar-info");
						$("#my_processinfo").addClass("progress-bar-warning");
						processFlag=0;
					}
					if(status=='error'){//超时,status还有success,error等值的情况
						$("#my_info h5").html("采集分析出错!");
						$("#my_processbar").removeClass("active");
						$("#my_processinfo").removeClass("progress-bar-info");
						$("#my_processinfo").addClass("progress-bar-danger");
						processFlag=0;
					}
					if(status=='failed'){//超时,status还有success,error等值的情况
						$("#my_info h5").html("网络连接失败!");
						$("#my_processbar").removeClass("active");
						$("#my_processinfo").removeClass("progress-bar-info");
						$("#my_processinfo").addClass("progress-bar-danger");
						processFlag=0;
					}
				}
			});
		}
	}else{
		$("[data-toggle='popover']").attr("data-content","处理正在进行中!");
		$("[data-toggle='popover']").popover('show');
		setTimeout(function(){
			$("[data-toggle='popover']").popover('destroy');
		},1800);
	}
	
	
}

function showSucessInfo(){
	$("#my_processbar").removeClass("active");
	$("#my_processinfo").removeClass("progress-bar-info");
	$("#my_processinfo").addClass("progress-bar-success");
}

function showProcessInfo(){
	$("#my_process").show();
	$("#my_processinfo").removeClass("progress-bar-success");
	$("#my_processinfo").removeClass("progress-bar-warning");
	$("#my_processinfo").removeClass("progress-bar-danger");
	$("#my_processinfo").addClass("progress-bar-info");
	$("#my_processbar").addClass("active");
	$("#my_info h5").html("处理中...");
}
function checkParam(){
	var process=$("#process").val();
	var batch=$("#batch").val();
	var startTime=$("#startDate").val();
	var endDate=$("#endDate").val();
	var flag=1;
	if(batch.trim()==""){
		$("[data-toggle='popover']").attr("data-content","批次信息不可为空");
		$("[data-toggle='popover']").popover('show');
		flag=0;
	}
	if(startTime.trim()==""){
		$("[data-toggle='popover']").attr("data-content","开始时间不可为空");
		$("[data-toggle='popover']").popover('show');
		flag=0;
	}
	if(endDate.trim()==""){
		$("[data-toggle='popover']").attr("data-content","结束时间不可为空");
		$("[data-toggle='popover']").popover('show');
		flag=0;
	}
	setTimeout(function(){
		$("[data-toggle='popover']").popover('destroy');
	},1000);
	startTime=startTime.replace(" ","").replace("-","").replace("-","")+"0000";
	endDate=endDate.replace(" ","").replace("-","").replace("-","")+"0000";
	var data={
			'process':process,
			'batch':batch,
			'startTime':startTime,
			'endDate':endDate,
			'flag':flag
	}
	return data;
}
