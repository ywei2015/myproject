//
//function onAfterLoadData() {
//	fastDev.getInstance('modifycheckForm').setValue({
//		'org.basicID':1001619
//	});
//}
var request = fastDev.Browser.getRequest();
var taskId = request['taskId'];
var edit = request['edit'];

$(document).ready(function(){
	  $("#diverrdealflip").click(function(){
		  $("#diverrdeal").slideToggle("slow");
		  $("#diverrdealicon").attr("iconCls","icon-down");
	  });
	  $("#diverrbaseinfoflip").click(function(){
		  $("#diverrbaseinfo").slideToggle("slow");
		  $("#diverrbaseinfoicon").attr("iconCls","icon-down");
	  });
	  $("#diverrhandleinfoflip").click(function(){
		  $("#diverrhandleinfo").slideToggle("slow");
		  $("#diverrhandleinfoicon").attr("iconCls","icon-down");
	  });
//调试时用
 // $("#basepanel").hide();
});


fastDev.post("taskEntry_queryEntryList.action?action=1&taskMouldPojo.c_task_id="+taskId,{
	success : function(data){
		var innerHTml = "";
		for(var i=0;i<data.length;i++){
			innerHTml +='<div width="100%" height="100%">';
			innerHTml +='<table class="ui-form-table"><tr valign="middle"><td class="ui-form-table-dd" colspan=4>步骤【'+(i+1)+'】</td></tr>';
			innerHTml +='<tr valign="middle">';
			innerHTml +='<td class="ui-form-table-dt" style="width:60px;">步骤名称：</td><td class="ui-form-table-dd">'+(data[i].c_step_prompt==undefined?"收集结果---"+(i+1):data[i].c_step_prompt)+'</td>';
			innerHTml +='<td class="ui-form-table-dt" style="width:60px;">证据收集方式：</td><td class="ui-form-table-dd">'+(data[i].c_tracefun_name==undefined?"无":data[i].c_tracefun_name)+'</td>';
			innerHTml +='</tr>';
			//innerHTml +='<tr valign="middle">';
			//innerHTml +='<td class="ui-form-table-dt" style="width:60px;">记录时间：</td><td class="ui-form-table-dd">'+(data[i].c_exec_time==undefined?"":data[i].c_exec_time)+'</td>';
			//innerHTml +='<td class="ui-form-table-dt" style="width:60px;">状态：</td><td class="ui-form-table-dd">'+(data[i].c_status_name==undefined?"":data[i].c_status_name)+'</td>';
			//innerHTml +='</tr>';
			if(data[i].c_tracefunid == "1"){ 
				innerHTml +='<tr valign="middle"><td class="ui-form-table-dt" style="width:60px;">步骤执行结果：</td><td class="ui-form-table-dd" colspan=3>';
				//innerHTml +='<a href="'+data[i].c_file_path+'" target="_blank" class="imglinkpreview"> <img height=260px width=400px src="'+data[i].c_file_path+'"/></a></td></tr>';					
				innerHTml +='<a href="#" rel="'+data[i].c_file_path+'" target="_self" class="imglinkpreview"><img height=260px width=400px src="'+data[i].c_file_path+'" title="点我放大" alt="点我放大"/></a></td></tr>';					
			}else{
				innerHTml +='<tr valign="middle"><td class="ui-form-table-dt" style="width:60px;">步骤执行结果：</td><td class="ui-form-table-dd" colspan=3>'+(data[i].c_result==undefined?"*（未收集）":data[i].c_result)+'</td></tr>';					
			}
			innerHTml +='</table></div>';
		}
		document.getElementById('divtaskstepresult').innerHTML = innerHTml;

		imagePreview();
	}
});

//FORM加载完后回调
function onFormAfterDataRender(){
	var form = fastDev.getInstance('checkForm');
	var items = form.getItems();
	//console.info(items);
	var select_status=fastDev.getInstance("taskMouldPojo.c_status1");
	select_status.setValue(items["taskMouldPojo.c_status"]);
	var taskremark=items["taskMouldPojo.c_remark"];
	if(taskremark==""){
		fastDev("#trtaskremark").hide(); //fastDev.getInstance("trtaskremark").hide();
	}
	
	//fastDev.getInstance("result_c_task_name").setValue(items["taskMouldPojo.c_task_name"]);
	document.getElementById('result_c_task_name').innerHTML = items["taskMouldPojo.c_task_name"];
	var c_handle_des = items["taskMouldPojo.c_handle_des"];
	if(c_handle_des==""||c_handle_des==undefined){
		//fastDev("#tr_handledes").hide();
		document.getElementById('result_handledes').innerHTML = "无";
	}else{
		document.getElementById('result_handledes').innerHTML = c_handle_des;
	}
	
	var taskiserr=items["taskMouldPojo.c_iserror"];
	if(taskiserr=="0"){//正常
		fastDev("#diverrinfo").hide();
		fastDev("#diverrdeal").hide();
		document.getElementById('task_result_type').innerHTML = "正常";		
	}else{
		fastDev("#diverrinfo").show();
		fastDev("#diverrdeal").show();
		document.getElementById('task_result_type').innerHTML = "发现异常";			
	}
	
	//fastDev.getInstance("taskMouldPojo.c_remark").setValue(items["taskMouldPojo.c_remark"]==""?"无":items["taskMouldPojo.c_remark"]);
	var isstdtask=items["taskMouldPojo.c_isstd"];
	if(isstdtask=="0"){//非标准任务
		fastDev("#divstdinfo").hide();
		document.getElementById('task.dowhat').innerHTML = items["taskMouldPojo.c_remark"];
		var tab1=fastDev.getInstance("tasktabs"); //divstdinfo stdtab tasktabs
		if(tab1==undefined) { 
			return;
		}
	}else{ //标准任务
		fastDev("#divtaskrequireinfo").hide();	
		getStdInfo(items["taskMouldPojo.c_actnode_id"],items["taskMouldPojo.c_std_verflag"]);
	}
}

function getStdInfo(stdid, version)
{
	fastDev.post("actNode_getCommStdInfoById.action?actnodeid="+stdid+"&version="+version,{
		success : function(data){
			var innerHTml = "";
			//console.info("--------------------------------------------------");
			//console.info(data);
			if(data!=undefined){
				var tmpstr = data["StdInfo.c_dowhat"]+"";
				//tmpstr = tmpstr.replace("\\n","<br/>") +"<br/><br/>";
				//tmpstr = tmpstr.replace(/\\n/g, "<br/>") +"<br/><br/>";
				tmpstr = tmpstr.split("\\n").join("<br/>") +"<br/><br/>";
				//console.info(tmpstr);
				document.getElementById('std.what').innerHTML = tmpstr; //data["StdInfo.c_dowhat"].replace("\n", "<br/><br/>") +"<br/><br/>";
				document.getElementById('std.dorequire').innerHTML = (data["StdInfo.c_exec_std"]==null?"":data["StdInfo.c_exec_std"]).replace(/\\n/g, "<br/>")+"<br/><br/>";
				document.getElementById('std.errdeal').innerHTML = (data["StdInfo.c_err_std"]==null?"":data["StdInfo.c_err_std"]).replace(/\\n/g, "<br/>")+"<br/><br/>";
				document.getElementById('std.check').innerHTML = (data["StdInfo.c_check_std"]==null?"":data["StdInfo.c_check_std"]).replace(/\\n/g, "<br/>")+"<br/><br/>";
				document.getElementById('std.review').innerHTML = (data["StdInfo.c_review_std"]==null?"":data["StdInfo.c_review_std"]).replace(/\\n/g, "<br/>")+"<br/><br/>";
			}
		}
	});
}

function onFormBeforeReady(){
	if (edit == "details") {
		this.setOptions({
			dataSource : 'taskEntry_queryTaskEntry.action?action=2&taskId='+taskId
		});
	}
}

function doSubmit() {
	var form = fastDev.getInstance('checkForm');
	form.setOptions({
		action : "taskEdit_editData.action?action=1&edit=" + edit
	});	 
	form.submit();
}
function doReset() {
	fastDev.getInstance('checkForm').resetData();
}
//表单提交后回调
function onSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function() {
		if (result.status == 'ok') {
			parent.refreshDatagrid();
			parent.closeDialog();
		}
	});
}

/*
//添加成功后事件
var add=function(title){
	fastDev.tips("添加"+title+"成功");
};
//修改完成后事件
var update=function(title){
	fastDev.tips("修改"+title+"成功");
};
//修改完成后事件
var onAfterInit=function(){
	fastDev.tips("Tabs init成功");
};
*/