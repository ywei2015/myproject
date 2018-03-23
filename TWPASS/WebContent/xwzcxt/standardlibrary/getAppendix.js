var request = fastDev.Browser.getRequest();
//通过action参数进行信息读取，以确认操作的对象为子项(1)、子项类别(2)、基础项(3)
var c_sfile_id = request['c_sfile_id'];
//alert(c_sfile_id);

function initree(){
var tree1=fastDev.getInstance("activityTree");
tree1.reLoad({url:"standardlibrary_getAppendix.action?standardfile.c_sfile_id="+c_sfile_id});
}


//刷新条件
var condition=null;
var appDialog;
var delectAction = "task_delete.action?action=1"; 
var currentActitemId = "";
var operTpl = '&nbsp;<a name="del">删除</a>&nbsp;&nbsp;<a name="edit">编辑</a>';

/**
 * 关闭对话框
 */
function closeDialog() {
	if(appDialog) {
		appDialog.close();
		appDialog = null;
	}
}	
function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body-60);
}
function onBeforeReady(){	 
	this.setOptions({
		initSource : "basicXwgl_queryTaskByPage.action?action=11"
	});	 
};
function operRenderer() { 
		return ['<div style="width:200px;"><a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="add" name="add">增加步骤</a>']
				.join('');
}
function operLinkRenderer(value) { 
	return	"<a  onclick='openTaskDetails("+value+")'>"+value+"</a>";			
}
function openTaskDetails(value){
	fastDev.create("Dialog", {
		height:500,
		width:900,
		showMaxBtn:false,
	    title:"任务详情",
	    src : "taskDetails.html?edit=details&taskId="+ value,
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
function onRowClick(event,rowindex,rowData) {
	currentActitemId = rowData.c_actitem_id;
	var oper = event.target.name;
	switch (oper) {
	case "add":
		addStep(currentActitemId);
		return;
	default:
		return;
	}
}

function addStandard(){
	if(!currentActivityOP){
	    var activityTree = fastDev.getInstance("activityTree");
	    var currentId = activityTree.getCurrentId();
	    if(getActNodeId(currentId)=='') {
	    	fastDev.alert('请选择活动末节点！','信息提示','warning');
	    	return false;
	    }
	    else {
	    	doAddStandard("add",getActNodeId(currentId),"");
//	    	var grid1 = fastDev.getInstance("grid1");
//	    	grid1.addInitReqParam({"basicMouldPojo.c_actnode_id": getActNodeId(currentId)});
//	    	grid1.refreshData(true);
	    }
	    
	}
}

function onBeforeDetailGridReady(){
	this.setOptions({
		initSource : "basicAdd_getStandardStepById.action?basicMouldPojo.c_actitem_id="+currentActitemId
	});
}

function editStandard(){
	var data = fastDev.getInstance("grid1").getValue();
	
	if(data.length==0) {
		fastDev.alert('请选择需要修改的标准项！','信息提示','warning');
    	return false;
	}
	
	if (data != null && data.length > 0)
		doAddStandard("update","",data[0].c_actitem_id);
}

function doAddStandard(edit,c_actnode_id,c_actitem_id){
	var src = "addActiveStandard.html?edit="+edit+"&c_actnode_id="+c_actnode_id+"&c_actitem_id="+c_actitem_id;
	createWindow({
		src : src,
		title : "标准项信息",
		width : "640",
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin, fd) {
				var form=fd.getInstance("addActiveStandardForm");
				form.submit();		
			}
		}]		
	});
}

function createWindow(o) {
	var config = fastDev.apply({
		width : "640",
		height : "365",
		allowResize : false,
		showMaxBtn : false
	}, o || {});
	(config.buttons = config.buttons || []).push({
		text : "关闭",
		iconCls : "icon-close",
		onclick : function(e,win) {
			win.close();
		}
	});		
	appDialog = fastDev.create('Window', config);
}


function doBatchDelete(){
    var list = fastDev.getInstance("grid1").getValue();
    if (list.length <= 0) {
        fastDev.alert("请至少选择一条记录进行操作","信息提示","warning");
        return;
    }                
    var ids = new Array();                
    for (var i = 0; i < list.length; i++) {
        var obj = list[i];
        ids.push(obj.c_actitem_id);
    }
    deleteStandard(ids.join(","));	
}

function deleteStandard(ids){	
	fastDev.confirm("确定删除记录？", "确认删除", function(response) {
		if (response) {
			fastDev.create("Proxy", {
				action : "basicAdd_deleteStandardStepByIds.action"
			}).save({
				ids : ids
			},function(data) {
				fastDev.alert(data.msg, "信息提示", data.status, function() {
					if(data.status=="ok")
						refreshDatagrid();
				});
			});
		}
	});		
}

/**
 * 过滤信息
 */
function doSearch() {
	var fm = fastDev.getInstance('checkform');
	condition = fm.getItems();
	
	refreshDatagrid();
}
/**
 * 重置表单数据
 */
function doReset() {
	fastDev.getInstance('checkform').cleanData();
}
/**
 * 刷新datagrid
 * @param o
 */
function refreshDatagrid() {
	var activityTree = fastDev.getInstance("activityTree");
    var currentId = activityTree.getCurrentId();
	var o = fastDev.apply(o || {}, condition || {});
	o['basicMouldPojo.c_actnode_id'] = getActNodeId(currentId);
	fastDev.getInstance('grid1').refreshData(o);
}

function refreshDetailgrid(){
	/*var fm = fastDev.getInstance('checkform');
	condition = fm.getItems();*/
//	console.info(fastDev.getInstance('detailGrid'));
	fastDev.getInstance('detailGrid').refreshData(true);
}

function detailRowClick(event,rowindex,data) {
	switch(event.target.name){
		case "del":
			deleteStepDataById(data);
			break;
		case "edit":
			editStepDataByCid(data);
			break;
	}
}

function deleteStepDataById(data){
	fastDev.confirm("确定删除该条记录？", "确认删除", function(response) {
		if (response) {
			var proxy = fastDev.create('Proxy', {
				action : "basicAdd_deleteStepDataById.action?cid="+data.c_id
			});
			proxy.save({deleteID:data.c_id}, function(result){
				fastDev.alert(result.msg, "信息提示", result.status, function() {
					if(result.status=="ok")
						refreshDatagrid();
				});
			});
		}
	});
}

function editStepDataByCid(data){
	doAddStep("update",0,data.c_id);
}

function addStep(stepid){
	/*var data = fastDev.getInstance("grid1").getValue();
	console.log(data[0].c_actitem_id);
	console.log(data[0].c_actstep_index);
	if (data != null && data.length > 0)
		doAddStep("",data[0].c_actitem_id,data[0].c_actstep_index);*/
	doAddStep("",stepid,"");
}

function doAddStep(event,item,stepid){
	var src = "addStep.html?item="+item;
	var title = "新增步骤";
	if (event=="update"){
		src = src + "&stepid="+stepid;
		title = "修改步骤";
	}
	createWindow({
		src : src,
		title : title,
		width : "320",
		height: "160",
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin, fd) {
				var form=fd.getInstance("addStepForm");
				form.submit();		
			}
		}]		
	});
}

var currentActivityOP=null;
var reloadActivity = function(){
	//只在没有做资源的增加，修改操作才做datagrid的刷新操作
	if(!currentActivityOP){
	    var activityTree = fastDev.getInstance("activityTree");
	    var currentId = activityTree.getCurrentId();
	    if(getActNodeId(currentId)=='') 
	    	return;
	    else {
	    	var grid1 = fastDev.getInstance("grid1");
	    	grid1.addInitReqParam({"basicMouldPojo.c_actnode_id": getActNodeId(currentId)});
	    	grid1.refreshData(true);
	    }
	    
	}	
};

function getActNodeId(value) {
	if (value.indexOf('_')<0)
	    return '';
	else 
		return value.split('_')[0];
	    
}
