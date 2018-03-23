var condition=null;
var dialog =null;
var request=fastDev.Browser.getRequest();
var usercode = getUrlParam('userCode');
var extype = getUrlParam('extype');
window.onload = function(){ 
//	  alert(cFinalStatus);
	  if(extype!=null&&extype==1){
	  fastDev.getInstance("taskMouldPojo.extype").setValue(1);
	  fastDev.getInstance("taskMouldPojo.userCode").setValue(usercode);
	  searchForm();
	  }else{
		  fastDev.Core.ControlBus.checkSession('portal_validateSessionExpire.action', function(msg) {
				fastDev.alert('登录超时，请重新登录', '信息提示', 'warning', function() {
					top.location = '../component/login.html';
				});
			});
	  }
	}

function getUrlParam(name) {
	var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)'); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); //匹配目标参数
	if (null != r) {
		return unescape(r[2]);
	} else {
		return null; //返回参数值
	}
}

function onBeforeReady(){
	setPath();
	var url='';
	var url1="waithandTask_getWorkTask.action";
	var url2="waithandTask_getArrangeTask.action";
	var type=request['c_task_kind'];
	if(type==1){
		url=url1;
	}else if(type==3){
		url=url2;
	}

	this.setOptions({
		initSource : url
	});	
}



function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body-80);
	showResourceModes("WDRW001");
}

var operTpl = '&nbsp;<a name="do">执行</a>';
function onRowClick(event,rowindex,data) {
	if (event.target.name == "do"){
		//转到执行界面
		createWindow({
			src : "doMytask.html?taskId="+ data.c_task_id,
			title : "任务执行",
			width : "900",
			height: "500",
			buttons : [{
				text : '提交',
				align : "center",
				iconCls : 'icon-save', 
				onclick : function(e,win,cwin, fd) {
					var form=fd.getInstance("dotaskform");
					var jsonData = cwin.formatJson();
					form.setOptions({"otherSubmitData" : fastDev.Util.JsonUtil.parseJson(jsonData)});
					form.submit();
				}
			}]		
		});
	}
}

function createWindow(o) {
	var config = fastDev.apply({
		width : "900",
		height : "500",
		allowResize : false,
		inside : false,
		showMaxBtn : false
	}, o || {});
	
	(config.buttons = config.buttons || []).push({
		text : "关闭",
		iconCls : "icon-close",
		onclick : function(e,win) {
			win.close();
		}
	});		
	dialog = fastDev.create('Window', config);
}

/**
 * 过滤信息
 */
function searchForm() {
	var fm = fastDev.getInstance('checkform');
	condition = fm.getItems();
	
	refreshDatagrid();
}
/**
 * 重置表单数据
 */
function resetForm() {
	fastDev.getInstance('checkform').cleanData();
}
/**
 * 刷新datagrid
 * @param o
 */
function refreshDatagrid() {
	fastDev.getInstance('grid1').refreshData(condition);
}

var dialog;

var url = '';
var path = '';

function  setPath(){
	url = window.location.href;
	path = url.substring(0, url.lastIndexOf("/")+1);
}

//为每个datagrid的单元格绑定样式 
function renderer(value) {
	var data = fastDev.getInstance("grid1").getValue(this)[0];
	fastDev(this).parents("tr").children("td").css("text-align", "center");
}


//为隐藏表单设置initSource
function initHiddenInput(){
	setPath();
	this.setOptions({
		initSource : "waithandTask_getTaskStepAndResult.action?c_task_id="+ taskId
	});	
}
//value:任务ID
function submitHiddenForm(value){
	var taskId=fastDev.getInstance("taskId").getValue();
	if(taskId!=value){
		fastDev.getInstance("taskId").setValue(value);
		var fm = fastDev.getInstance("hiddenform");
		var items = fm.getItems();		
		fastDev.getInstance("grid2").refreshData(items);
	}
}

function refreashData2(){
	var fm = fastDev.getInstance("hiddenform");
	var items = fm.getItems();
	fastDev.getInstance("grid2").refreshData(items);
}

function showImgDialog(img){
	fastDev.create("Dialog", {
		src : path+"imgView.html?path="+img,
		title : "多媒体信息查看",
		width : "80%",
		height : "80%",
		inside : false,
		modal : true
	});
	
}

//打开录入文本的对话框
function showSrcDialog(obj) {
	var url=window.location.href;
	var index=url.lastIndexOf("/");
	//alert(url.substring(0, index));
	var val=fastDev.getInstance('grid2').getValue(obj)[0];
	//alert(fastDev.Util.JsonUtil.parseString(val));
	var type=obj.innerHTML;
	var data={
		"result":"",
		"resultId":"",
		"traceFun":"",
	};
	//alert(fastDev.Util.JsonUtil.parseString(data));
	//录入文本 
	if(type=="文本"){
		data.result=val.c_result;
		data.resultId=val.c_id;
		//alert(fastDev.Util.JsonUtil.parseString(data));
		fastDev.create("Dialog", {
			src : path+"addText.html",
			title : type,
			width : 389,
			height : 247,
			inside : false,
			modal : true,
			buttons : [{
				text : "保存",
				iconCls : "icon-save",
				onclick : function(event, that, win, fast) {
					if(win.document.getElementById("taskStepResultPojo.c_result").value==""){
						window.fastDev.tips("请输入文本内容！");
						return false;
					}
					 var grid2=fastDev.getInstance('grid2');
					win.submit(that,grid2);
					//window.fastDev.tips("文本录入成功");
					// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
					// 参数that为当前对话框控件实例
					//that.close();
					
					
					//setInterval("refreashData2()", 500);
					
				}
			}, {
				text : "关闭",
				iconCls : "icon-close",
				onclick : function(event, that, win, fast) {
					// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
					// 参数that为当前对话框控件实例
					
					that.close();
				}
			}]
		}).setData("data",data);
	}
	else{
		var traceFun=0;
		if(type=="拍照"){
			traceFun=1;
		}
		else if(type=="录音"){
			traceFun=2;
		}
		else if(type=="录视频"){
			traceFun=3;
		}
		data.resultId=val.c_id;
		data.traceFun=traceFun;
		dialog = fastDev.create("Dialog", {
			src : path+"uploadFile.html",
			title : type,
			width : 294,
			height : 140,
			inside : false,
			modal : true,
			buttons : [{
				text : "关闭",
				iconCls : "icon-close",
				onclick : function(event, that, win, fast) {
					// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
					// 参数that为当前对话框控件实例
					fastDev.getInstance('grid2').refreshData(false);
					that.close();
				}
			}]
		}).setData("data",data);
	}
}

function requestModifyStatus(taskId){
	fastDev.post("waithandTask_executeTask.action",{
		data:{
			"taskId":taskId
		},
		success:function(dat){
			fastDev.tips(dat);
			fastDev.getInstance('grid1').refreshData(false);
		}
	});
}

function executeTask(taskId){
	fastDev.post("waithandTask_isAllTaskStepsComplete.action",{
		data:{
			"taskId":taskId
			
		},
		success:function(dat){
			if(dat.result=="no"){
				fastDev.confirm('还有动作未执行，是否提交？', '信息提示', function(result){
					if (result) {
						requestModifyStatus(taskId);
					}
				});
			}
			else{
				requestModifyStatus(taskId);
				
			}
		}
	});
}

function closeDialog(){
	if(dialog) {
		dialog.close();
		dialog = null;
	}
}

function kindRenderer(value){
	renderer(value);
	
	switch(value){
		case '1': return '工作任务';
		case '2':return "异常信息";
		case '3':return "工作安排";
	}
}

function toOperation(){
    var url= ['<a href="javaScript:void(0);" id="execute"  onclick=showSteps(this)>执行任务</a>'];
   // alert(url);style="display:none;"
	return url.join('');
}

function showSteps(obj){
	var data=fastDev.getInstance('grid1').getValue(obj)[0];
	var c_task_id=data.c_task_id;
	var c_task_name=data.c_task_name;
	var url=window.location.href;
	var index=-1,url2=url;
	index=url2.indexOf("/", 0);
	for(var i=0;i<3;i++){
		index=url2.indexOf("/", index+1);
	}
	url=url.substring(0, index);
	fastDev.create("Dialog", {
		height:500,
		width:900,
		inside:false,
		showMaxBtn:false,
	    title:"任务步骤",
	    src : url+"/xwzcxt/mytask/taskStep.html?c_task_id="+ c_task_id+"&c_task_name="+c_task_name,
		buttons : [{
			text : "保存",
			align : "center",
			iconCls : "icon-save",
			onclick : function(event, that, win, fast) {
				// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
				// 参数that为当前对话框控件实例
				win.save();
			}
		},{
			text : "关闭",
			align : "center",
			iconCls : "icon-close",
			onclick : function(event, that, win, fast) {
				// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
				// 参数that为当前对话框控件实例
				that.close();
			}
		}]
	});
}

function toDetailsLink(value){
    renderer(value);
	var cTaskId=fastDev.getInstance('grid1').getValue(this)[0].c_task_id;
	var link=['<a href="javaScript:void(0);"  onclick = doOpenTaskDetails("'+
	          cTaskId+'")>'+value+'<a/>'];
	return link.join('');
}	

function doOpenTaskDetails(c_task_id){
	var url=window.location.href;
	var index=-1,url2=url;
	index=url2.indexOf("/", 0);
	for(var i=0;i<3;i++){
		index=url2.indexOf("/", index+1);
	}
	url=url.substring(0, index);
	fastDev.create("Dialog", {
		height:500,
		width:900,
		inside:false,
		showMaxBtn:false,
	    title:"任务详情",
	    src : url+"/xwzcxt/task/taskDetails.html?edit=details&taskId="+ c_task_id,
		buttons : [{
			text : "关闭",
			align : "center",
			iconCls : "icon-close",
			onclick : function(event, that, win, fast) {
				// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
				// 参数that为当前对话框控件实例
				that.close();
			}
		}]
	});
}

