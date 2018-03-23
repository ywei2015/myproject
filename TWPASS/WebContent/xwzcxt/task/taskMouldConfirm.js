var request = fastDev.Browser.getRequest();
//通过action参数进行信息读取，以确认操作的对象为子项(1)、子项类别(2)、基础项(3)
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
		initSource : "task_queryTaskByPage.action?action=3&taskMouldPojo.c_status=0"
	});	 
};
function operRenderer() { 
		return ['<div><a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="modify" name="modify">确认下发</a>'+
		        '<a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="delete" name="delete">取消任务</a>']
				.join('');
}
function operLinkRenderer(value) { 
	return ['<div style="width:300px;"><a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="taskDetails" name="taskDetails">'+value+'</a>']
	.join('');
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
/**
 * 批量删除信息
 */
function doBatchDeleteCheckModel() {
	var datagrid = fastDev.getInstance('grid1');
	var items = datagrid.getValue();
	if(items.length <=0 ) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm("确认是否取消选择的" + items.length + "条任务下发信息？", "信息提示", function(result){
			if(result){
				var ids = [];
				for(var i = 0; i < items.length; i++) {
					ids.push(items[i].c_task_id);
				}
				deleteCheckModelById(ids.join(","));
			}
		});
	}
}
function doDelete(data){
	fastDev.confirm("确认是否取消任务下发信息？", "信息提示", function(result){
		if(result){
			deleteCheckModelById(data.c_task_id);
		}
	});
}
function deleteCheckModelById(id) {
	var proxy = fastDev.create('Proxy', {
		//action : 'delete_deleteInfo.action?action=4'
		action : 'delete_cancelTaskById.action'
	});
	proxy.save({deleteID:id}, function(result){
		fastDev.alert(result.msg, '信息提示', result.status, function(){
			if(result.status == 'ok') {
				refreshDatagrid();
			}
		});
	});
}
function doTaskDetails(data){
	fastDev.create("Dialog", {
		height:460,
		width:1000,
		showMaxBtn:false,
	    title:"任务详情",
	    src : "taskMouldConfirmDetails.html?edit=details&taskId="+ data.c_task_id,
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
function doModify(data){
	fastDev.create("Dialog", {
		height:460,
		width:1000,
		showMaxBtn:false,
	    title:"任务详情",
	    src : "taskMouldConfirmDetails.html?edit=details&taskId="+ data.c_task_id,
		buttons : [{
			text : "确认下发",
			align : "center",
			iconCls : "icon-save",
			onclick : function(event, that, win, fast) {
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