var condition = null;

function onBeforeReady(){
	this.setOptions({
		initSource : "task_queryTaskByPage.action?action=22",
		dataSource : "task_queryTaskByPage.action?action=22"
	});	
}

function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body-60);
}

function operLinkRenderer(value){
	return ['<div style="width:300px;"><a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openTaskDetails" name="openTaskDetails">'+value+'</a>']
	.join('');
}

function doOpenTaskDetails(data){
	fastDev.create("Dialog", {
		height:500,
		width:900,
		showMaxBtn:false,
	    title:"任务详情",
	    src : "../task/taskDetails.html?edit=details&taskId="+ data.c_task_id,
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