var errId="";

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

function showErrDataInPage(d){
	$("#errname").text(d.c_err_name);
	$("#errdes").text(d.c_err_des);
	$("#errareaname").text(d.areaname);
	$("#errwritetime").text(d.c_write_time);
	$("#errobjname").text(d.c_obj_name);
	$("#errdisplayname").text(d.displayname);
	$("#errmanagesectionname").text(d.c_manage_section_name);
	$("#erruploadtime").text(d.c_upload_time);
	$("#errkindname").text(d.c_err_kind_name);
	$("#errtaskname").text(d.c_task_name);
	$("#errtaskid").text(d.c_task_id);
	$("#erractnodename").text(d.c_actnode_name);
	$("#errendtime").text(d.c_end_time);
	$("#errisclosename").text(d.c_isclose_name);
	$("#errpreerrname").text(d.c_pre_err_name);
	$("#errpreerrid").text(d.c_pre_err_id);
}

function showFileDataInPage(d){
	if (d==null) return;
	var inner = '<tr><td class="table_header">捕捉记录</td></tr>';
	for(var i=0; i<d.length; i++){
		inner += '<tr><td><img style="width: 90%;" src="';
		inner += d[i].c_file_path;
		inner += '" /></td></tr>';
	}
	document.getElementById('imagetable').innerHTML = inner;
}

function showFeedbackDataInPage(d){
	if (d==null) return;
	var inner = '<tr class="table_header"><td>接收类别</td><td>接收人</td><td>状态</td></tr>';
	for(var i=0; i<d.length; i++){
		inner += '<tr><td>'+d[i].c_feedback_type_name+'</td><td>'+d[i].displayname+'</td><td>'+d[i].c_isreceived_name+'</td></tr>';
	}
	document.getElementById('feedbacktable').innerHTML = inner;
}

function showDealDataInPage(d){
	if (d==null) return;
	var inner = '';
	inner += '<tr><td class="table_header">任务名称</td><td>'+d.c_sn_task_name+'</td></tr>';
	inner += '<tr><td class="table_header">处理人</td><td>'+d.c_sn_exec_username+'</td></tr>';
	inner += '<tr><td class="table_header">状态</td><td>'+d.c_sn_status_name+'</td></tr>';
	
	document.getElementById('dealtable').innerHTML = inner;
}

$(document).on("pageinit",function(){
	errId = getQueryString("errId");
	//获取异常主要内容
	 $.ajax({
		  url:'taskError_getTaskErrDetailById.action?errId='+errId,
		  dataType:'json',
		  success:function(d){
			  showErrDataInPage(d);
		  }
	  });
	  
	  //获取对应文件
	  $.ajax({
		  url:'taskError_getErrFilesByErrTaskId.action?errId='+errId,
		  dataType:'json',
		  success:function(d){
			  showFileDataInPage(d);
		  }
	  });
	  
	  //获取反馈信息
	  $.ajax({
		  url:'taskError_getErrFeedbacksByErrTaskIdM.action?errId='+errId,
		  dataType:'json',
		  success:function(d){
			  showFeedbackDataInPage(d);
		  }
	  });
	  
	  //获取处理情况
	  $.ajax({
		  url:'taskError_getErrTaskSNByErrTaskId.action?errId='+errId,
		  dataType:'json',
		  success:function(d){
			  showDealDataInPage(d);
		  }
	  });
	}); 

