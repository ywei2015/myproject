var request = fastDev.Browser.getRequest();
var taskId = request['taskId'];
var taskstatus = request['c_status'];
var edit = request['edit'];

function onFormBeforeReady(){
	if (edit == "detail") {
		this.setOptions({
			dataSource : 'iitask_getTaskInfoByTaskId.action?c_task_id='+taskId
		});
	}
	if (edit == "confirm") {
		this.setOptions({
			dataSource : 'iitask_getTaskInfoByTaskId.action?c_task_id='+taskId
		});
	}
}

function setConfirmModel(){
	console.info("--1--");
	$("#taskinfo.c_exec_username").attr("readonly","false");
	$("#taskinfo.c_start_time").attr("readonly","false");
	console.info("--2--");
}

function onFormAfterDataRender(){
	var data = this.getItems();
	//console.info(data);
	/*var select_status=fastDev.getInstance("taskinfo.c_status1");
	select_status.setValue(data["taskinfo.c_status"]); //fastDev.getInstance("taskinfo.c_status").getValue());
	var select_execuserid=fastDev.getInstance("taskinfo.c_exec_userid1");
	select_execuserid.setValue(data["taskinfo.c_exec_userid"]); //fastDev.getInstance("taskinfo.c_exec_userid").getValue());
	var select_areaid=fastDev.getInstance("taskinfo.c_area_id1");
	select_areaid.setValue(data["taskinfo.c_area_id"]); //fastDev.getInstance("taskinfo.c_area_id").getValue());
*/	/*if (edit == "confirm") {
		setConfirmModel(); //设置一些控件为可修改状态
	}*/
}

function onSelectChange(flag){
	if(flag=='areaid'){
		var select_areaid=fastDev.getInstance("taskinfo.c_area_id");
		select_areaid.setValue(fastDev.getInstance("taskinfo.c_area_id1").getValue());
	}
	if(flag=='execuserid'){
		var select_execuserid=fastDev.getInstance("taskinfo.c_exec_userid");
		select_execuserid.setValue(fastDev.getInstance("taskinfo.c_exec_userid1").getValue());
	}
	if(flag=='status'){
		var select_status=fastDev.getInstance("taskinfo.c_status1");
		select_status.setValue(fastDev.getInstance("taskinfo.c_status").getValue());
	}
	var data = this.getItems();
	console.info(data);
}

function slideToggle_StepTable(tableid){
	var tableid = "#StepTable"+tableid;
	//console.info(tableid);
	var ishidden = $(tableid).css("display"); //$(tableid).is(":hidden");
	//console.info(ishidden);
	if(ishidden!="none"){
		$(tableid).hide();
	}else{
		$(tableid).show();
	}
	//$("#StepTable"+tableid).slideToggle("slow");
}

var taskitemids = new Array();
fastDev.post("iitask_getTaskInfoItemListByMap.action?c_task_id="+taskId,{
	success : function(data){
		var StepDetailHtml = "";
		if(data!=undefined){
			for(var i=0;i<data.length;i++){
				taskitemids.push(data[i].c_actitem_index);
				StepDetailHtml +='<div class="toggler"><div id="effect" class="ui-widget-content ui-corner-all">';
				StepDetailHtml +='<h3 class="ui-widget-header ui-corner-all" onclick="slideToggle_StepTable('+i+');">第'+data[i].c_actitem_index+'步，----'+data[i].c_actitem_name+'" </h3>';
				StepDetailHtml +='<table id="StepTable'+i+'" class="ui-form-table">';
				//StepDetailHtml +='<tr valign="middle"><td class="ui-form-table-dt" style="width:100px;">结果收集要求：</td><td class="ui-form-table-dd">'+data[i].c_exec_getdatatype+'</td>';
				//StepDetailHtml +='<tr valign="middle"><td class="ui-form-table-dt" style="width:100px;">验证收集要求：</td><td class="ui-form-table-dd">'+data[i].c_check_getdatatype+'</td>';
				if(taskstatus==33){
					StepDetailHtml +='<tr valign="middle"><td class="ui-form-table-dd" style="width:100px;">收集结果：</td><td id="TaskStepResult'+data[i].c_actitem_index+'" class="ui-form-table-dd">'; 
				}
				//StepDetailHtml +='<tr valign="middle"><td class="ui-form-table-dt" style="width:100px;">收集结果：</td><td id="TaskStepResult" class="ui-form-table-dd">';
				StepDetailHtml +='</td></tr>';
				StepDetailHtml +='</table>';
				StepDetailHtml +='</div></div>';
			}
		}
		document.getElementById('divDetails').innerHTML = StepDetailHtml;  //$("divDetails").append(StepDetailHtml);
		if(taskstatus==33){
			showstepresultdetail();
		}
	}
});

function showStepResult(actitemindex)
{
	fastDev.post("iitask_getTaskStepResultListByMap.action?c_task_id="+taskId+"&c_actitem_index="+actitemindex, {
		success:function(resdata){
			if(resdata!=undefined){
				var ResultDetailHtml = "";
				ResultDetailHtml +='<table class="ui-form-table">';
				//console.info(resdata.length);
				for(var j=0;j<resdata.length;j++){
					ResultDetailHtml += '<tr valign="middle">';
					ResultDetailHtml += '<td class="ui-form-table-dd" style="width:5%;">'+resdata[j].c_step_index+'</td>';
					ResultDetailHtml += '<td class="ui-form-table-dd" style="width:15%;">'+resdata[j].c_tracefun_name+'</td>';
					ResultDetailHtml += '<td class="ui-form-table-dd" style="width:80%;">';
					if(resdata[j].c_isfile=="1"){
						if(resdata[j].c_tracefun_id=="1"){
							ResultDetailHtml += '<img height=200px width=300px src="'+(resdata[j].c_file_path==undefined?"":resdata[j].c_file_path)+'"/>';
						}else{
							ResultDetailHtml += '<a href="'+(resdata[j].c_file_path==undefined?"":resdata[j].c_file_path)+'">'+(resdata[j].c_file_title==undefined?"":resdata[j].c_file_title)+'</a>';
						}
					}else{
						ResultDetailHtml += (resdata[j].c_result==undefined?"":resdata[j].c_result);
					}
					ResultDetailHtml += '</td>';
					ResultDetailHtml += '</tr>';
				}
				ResultDetailHtml +='</table>';
				document.getElementById('TaskStepResult'+actitemindex).innerHTML = ResultDetailHtml; 
			}
		}
	});
}

function showstepresultdetail(){
	//console.info(taskitemids);
	var rs_count = taskitemids.length;
	for(var k=0;k<rs_count;k++){
		var tmpid = taskitemids.pop();
		showStepResult(tmpid);
	};
}

//确认下发提交 
function doSubmit(win) {
	fastDev.confirm("是否确认下发？", "信息提示", function(result){
		if(result){
			var form = fastDev.getInstance('TaskinfoForm');
			var f_taskinfo = form.getItems();
			//console.info(f_taskinfo);
			var params = {
					"c_task_id": taskId,
					"c_exec_userid": f_taskinfo["taskinfo.c_exec_userid"],
					"c_start_time": f_taskinfo["taskinfo.c_start_time"],
					"c_end_time": f_taskinfo["taskinfo.c_end_time"],
					"c_plandown_time": f_taskinfo["taskinfo.c_plandown_time"],
					"c_area_id": f_taskinfo["taskinfo.c_area_id"]
				};
			console.info(params);
			
			fastDev.post("iitask_execTaskinfoConfirm.action",{
				success : function(result){
					fastDev.tips("确认成功");
					//console.info(result);
					fastDev.alert(result.msg, '信息提示', result.status, function() {
						if (result.status == 'ok') {
							parent.refreshDatagrid();
							parent.closeDialog();
							win.close();
						}
					});
				},
				data : params 
			});
		}
	});

	/*var proxy = fastDev.create('Proxy', {
		action : 'delete_deleteStepByCid.action'
	});
	proxy.save({deleteID:id}, function(result){
		fastDev.alert(result.msg, '信息提示', result.status, function(){
			if(result.status == 'ok') {
				refreshDatagrid();
			}
		});
	});*/
}

