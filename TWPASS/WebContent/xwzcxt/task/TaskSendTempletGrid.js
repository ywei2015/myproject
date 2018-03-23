var request = fastDev.Browser.getRequest();

var item = request['item'];
//刷新条件
var condition=null;
var checkDialog;

/**
 * 关闭对话框
 */
function closeDialog() {
	if(checkDialog) {
		checkDialog.close();
		checkDialog = null;
	}
}	
function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body-60);
}
function onBeforeReady(){	 
	this.setOptions({
		initSource : "task_queryTaskByPage.action?action=1"
	});	 
};
function operRenderer() { 
		return ['<div style="width:200px;"><a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="taskMould" name="taskMould">任务下发</a>']
				.join('');
}
function operLinkRenderer(value) {
	return ['<div style="width:300px;"><a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openTaskDetails" name="openTaskDetails">'+value+'</a>']
	.join('');
}
function doOpenTaskDetails(data){
	fastDev.create("Dialog", {
		height:500,
		width:900,
		showMaxBtn:false,
	    title:"模板详情",
	    src : "../rule/addTaskMould.html?edit=update&taskId="+  data.c_tasktemplet_id,
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
function onRowClick(event,rowindex,data) {		
	var target = event.target;
	var name = target['name'];
	if(name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		if(window['do' + name]) {
			window['do' + name].call(window, data);
		}
	}
}

function doTaskMould(data){
	checkDialog = fastDev.create("Dialog", {
		showMaxBtn:false,
		width :600,
		height:240,
	    title:"任务下发",
	    src : "TaskMultiSend.html?taskId="+data.c_tasktemplet_id+"&postId="+data.c_positionid+"&areaId="+data.c_arer+"&taskName="+data.c_tasktemplet_name,
		buttons : [{
			text : "开始下发",
			align : "center",
			iconCls : "icon-save",
			onclick : function(event, that, win, fast) {
				//fastDev.tips("你点击了保存按钮");
				// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
				// 参数that为当前对话框控件实例
				win.doSubmit();
			}
		}, {
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
	fastDev.getInstance('grid1').refreshData(condition);
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
	    	grid1.addInitReqParam({"taskMouldPojo.c_actnode_id": getActNodeId(currentId)});
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


var onNodeClick = function(){
	//只在没有做资源的增加，修改操作才做datagrid的刷新操作
	if(!currentActivityOP){
	    var positiontree = fastDev.getInstance("positiontree");
	    var currentId = positiontree.getCurrentId();
    	var grid1 = fastDev.getInstance("grid1");
    	grid1.addInitReqParam({"taskMouldPojo.c_positionid": currentId});
    	grid1.refreshData(true);
	    
	}	
};

function onAfterDataRender(){
	var obj = fastDev(this.elems[0]);
	obj.height(obj.parent().height()-32);
}
