var condition = null;
var request = fastDev.Browser.getRequest();
var g_taskId = request['taskId'];

var innerHTml = "";
var stepHtml = "";
var addBtnClickCnt = 0;
fastDev.post("taskEntry_queryEntryList.action?action=1&taskMouldPojo.c_task_id="+g_taskId,{
	success : function(data){
		//innerHTml += '<form id="detailactform" method="post" action="" enctype="multipart/form-data">';
		innerHTml += '<table class="ui-form-table">';
		if (data.length>0){
				fastDev.getInstance("c_isstd").setValue(data[0].c_isstd);
				fastDev.getInstance("c_step_count").setValue(data.length);
				var standard="";
				var sFlag = 0;
				if (data[0].c_isstd == "1"){//标准任务
					for(var i=0;i<data.length;i++){
						if (standard != data[i].c_actitem_id){//拆分出标准
							//非出示状态下，需要完结上一个标准下步骤的table
							if (standard != "")   innerHTml += '</table>';
							standard = data[i].c_actitem_id;
							sFlag ++;
							//生成该标准的内容
							innerHTml += '<tr><td  class="ui-form-table-dt" style="width:100px;"><a href="" style="color:#ff7f61;">工作标准'+sFlag+'>></a></td><td class="ui-form-table-dd" colspan=5>'+data[i].c_actitem_what+'</td></tr>';
							innerHTml += '<table class="ui-form-table">';
						}
						//生成步骤内容
						processStandTaskStepHtml(data[i]);
					}
					innerHTml += '<tr><td class="ui-form-table-dd" colspan=6>自处理异常</td></tr>';
					innerHTml += '<tr><td class="ui-form-table-dt" style="width:100px;">异常原因：</td>';
					innerHTml += '<td class="ui-form-table-dd" colspan=5><input type="text" id="c_handle_des" /></td></tr>';
					innerHTml += '</table>';//最后一个标准下的步骤的table完结
				}else {//非标准任务
					innerHTml +='<tr><td class="ui-form-table-dt" style="width:100px;">新增步骤:</td>';
					innerHTml +='<td class="ui-form-table-dd" colspan=5 width="100%"><select id="unstandardTaskType" style="width:150px;">';
					innerHTml +='<option value="1">拍照</option>';
					innerHTml +='<option value="2">录音</option>';
					innerHTml +='<option value="3">录视频</option>';
					innerHTml +='<option value="10">文本录入</option>';
					innerHTml +='<option value="20">扫码</option>';
					innerHTml +='<option value="30">GPS定位</option>';
					innerHTml +='</select>';
					innerHTml +='<button type="button" onClick="addUnstandardTaskStep()">新增步骤</button></tr>';
				}
			}
		innerHTml += '</table>';//</form>';//标准的显示区域完结
		document.getElementById('divDetails').innerHTML = innerHTml;
	}
});

function processStandTaskStepHtml(value){
	var step = value.c_step_index;
	innerHTml +='<tr hidden="true"><td class="ui-form-table-dd"><input type="text" id="c_step_index'+step+'" value="'+step+'" /></td>';
	innerHTml +='<td class="ui-form-table-dd"><input type="text" id="c_step_isfile'+step+'" value="'+value.c_isfile+'" /></td></tr>';
	innerHTml +='<tr><td class="ui-form-table-dd" colspan=6>步骤【'+step+'】</td></tr>';
	innerHTml +='<tr><td class="ui-form-table-dt" style="width:100px;">步骤提示名称：</td><td class="ui-form-table-dd">'+value.c_step_prompt+'</td>';
	innerHTml +='<td class="ui-form-table-dt" style="width:100px;">作业区域名称：</td><td class="ui-form-table-dd">'+(value.areaname==undefined?"":value.areaname)+'</td>';
	innerHTml +='<td class="ui-form-table-dt" style="width:100px;">作业对象名称：</td><td class="ui-form-table-dd">'+(value.c_obj_name==undefined?"":value.c_obj_name)+'</td></tr>';
	innerHTml += '<tr><td class="ui-form-table-dt" style="width:100px;">执行结果：</td>';
	if (value.c_isfile == "1"){//结果是文件，提供文件上传接口
		innerHTml +='<td class="ui-form-table-dd" colspan=5>';
		innerHTml +='<form action="taskEntry_uploadStepDetailsFile.action?index='+step+'" method="post" enctype="multipart/form-data" target="myFrame">';
		innerHTml +='<input type="file" name="uploadfile" /><input type="submit" value="上传图片" /></form></td></tr>';
	}else{//结果是非文件，提供文本输入接口
		innerHTml +='<td class="ui-form-table-dd" colspan=5><input type="text" id="c_result'+step+'" /></td></tr>';
	}
}

function addUnstandardTaskStep(){
	addBtnClickCnt++;
	if (addBtnClickCnt == 1){//添加第一个步骤，填写表头
		stepHtml = '<table class="ui-form-table">';
	}
	stepHtml +='<tr><td class="ui-form-table-dd" colspan=6>步骤【'+addBtnClickCnt+'】</td></tr>';
	var stepType = document.getElementById('unstandardTaskType').value;
	if(stepType == "3"||stepType == "1"||stepType == "2"){//提供文件上传输入
		stepHtml +='<tr><td class="ui-form-table-dt" style="width:100px;">执行结果：</td>';
		stepHtml +='<td class="ui-form-table-dd" colspan=6><input id="c_result'+addBtnClickCnt+'" type="file" /></td></tr>';
		stepHtml +='<tr hidden="true"><td class="ui-form-table-dd"><input id="c_step_isfile'+addBtnClickCnt+'" type="text" value="1" /></td></tr>';
	}else{//提供文本输入
		stepHtml +='<tr><td class="ui-form-table-dt" style="width:100px;">执行结果：</td>';
		stepHtml +='<td class="ui-form-table-dd" colspan=6><input id="c_result'+addBtnClickCnt+'" type="text" /></td></tr>';
		stepHtml +='<tr hidden="true"><td class="ui-form-table-dd"><input id="c_step_isfile'+addBtnClickCnt+'" type="text" value="0" /></td></tr>';
	}
	stepHtml +='<tr hidden="true"><td class="ui-form-table-dd"><input id="c_tracefun_id'+addBtnClickCnt+'" type="text" value="'+stepType+'" /></td></tr>';
	stepHtml +='<tr hidden="true"><td class="ui-form-table-dd"><input id="c_step_index'+addBtnClickCnt+'" type="text" value="'+addBtnClickCnt+'" /></td></tr>';
	document.getElementById('unstandardDetails').innerHTML = (stepHtml+'<tr><td class="ui-form-table-dd" colspan=6>自处理异常</td></tr><tr><td class="ui-form-table-dt" style="width:100px;">异常原因：</td><td class="ui-form-table-dd" colspan=5><input type="text" id="c_handle_des" /></td></tr></table>');
	console.info(document.getElementById('unstandardDetails').innerHTML);
}

function onFormBeforeReady(){
	this.setOptions({
		dataSource : 'taskEntry_queryTaskEntry.action?action=2&taskId='+g_taskId,
		action : 'taskEntry_submitStepDetails.action'
	});
	
}

function onSubmitSuccess(data){
	fastDev.alert(data.msg, "信息提示", data.result,function(){
		if (data.result >= 0){//成功
			parent.appDialog.close();
			parent.refreshDatagrid();
		}
	});
}

function closeDialog() {
	if(checkDialog) {
		checkDialog.close();
		checkDialog = null;
	}
}

function formatJson(){
	var isstd = $("#c_isstd").val();
	var jsonData = "{'TaskId':'"+g_taskId+"','IsStd':'"+isstd+"','HandleDes':'"+$("#c_handle_des").val()+"'";
	if (isstd == "1"){//标准任务
		var StepCount = $("#c_step_count").val();
		jsonData += ",'StepCount':'"+StepCount+"'";
		for (var i=1; i<=StepCount; i++){
			jsonData += ",'StepIndex" + i + "':'" + $("#c_step_index"+i).val() + "'";
			jsonData += ",'IsFile" + i + "':'" + $("#c_step_isfile"+i).val() + "'";
			jsonData += ",'Result" + i + "':'" + $("#c_result"+i).val() + "'";
		}
	}else{//非标准任务
		jsonData += ",'StepCount':'"+addBtnClickCnt+"'";
		for (var j=1; j<=addBtnClickCnt; j++){
			jsonData += ",'StepIndex" + j + "':'" + $("#c_step_index"+j).val() + "'";
			jsonData += ",'TracefunId" + j + "':'" + $("#c_tracefun_id"+j).val() + "'";
			jsonData += ",'IsFile" + j + "':'" + $("#c_step_isfile"+j).val() + "'";
			jsonData += ",'Result" + j + "':'" + $("#c_result"+j).val() + "'";
		}
	}
	jsonData += "}";
	return jsonData;
}

/**
 * 过滤信息
 */
function doSearch() {
	var fm = fastDev.getInstance('dotaskform');
	condition = fm.getItems();
	refreshDatagrid();
}
/**
 * 重置表单数据
 */
function doReset() {
	fastDev.getInstance('dotaskform').cleanData();
}
