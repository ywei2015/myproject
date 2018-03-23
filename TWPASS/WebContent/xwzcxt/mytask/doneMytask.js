var url = window.location.pathname;
var path = url.substring(0, url.lastIndexOf("/")+1);
var p_url = url.substring(0, url.lastIndexOf("/"));
var p_path = p_url.substring(0, p_url.lastIndexOf("/")+1);

function onBeforeReady(){
	this.setOptions({
		initSource : "alreadyhandTask_getDefaultAlreadyhandTasks.action"
	});	
}

function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body-110);
	
}

function operLinkRenderer(value){
	return ['<div style="width:300px;"><a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openTaskDetails" name="openTaskDetails">'+value+'</a>']
	.join('');
}

function doOpenTaskDetails(c_task_id){
	fastDev.create("Dialog", {
		height:500,
		width:900,
		inside:false,
		showMaxBtn:false,
	    title:"任务详情",
	    src : p_path+"task/taskDetails.html?edit=details&taskId="+ c_task_id,
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

function closeDialog() {
	if(checkDialog) {
		checkDialog.close();
		checkDialog = null;
	}
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

function toTaskKind(value){
	renderer(value);
	switch(value){
	case '1': return '工作任务';
	case '2': return '异常信息';
	case '3': return '工作安排';
	}
}