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
		initSource : "iitask_getTaskInfoListByMapAndOrgid.action"
	});	 
};
function operLinkRenderer(value) {
	return ['<div style="width:300px;"><a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openTaskDetails" name="openTaskDetails">'+value+'</a></div>']
	.join('');
}

function TaskStatusRenderer(value) {
	var innerelement = "";
	if(value==0) {
		innerelement = "<div>已生成</div>";
	}else if(value==11) {
		innerelement = "<div>已确认</div>";
	}else if(value==22) {
		innerelement = "<div>已推送</div>";
	}else if(value==33) {
		innerelement = "<div>已执行</div>";
	}else if(value==44) {
		innerelement = "<div>被取消</div>";
	}else{
		innerelement = "<div>未定义</div>";
	}
	return [innerelement].join('');
}

function doOpenTaskDetails(data){
	fastDev.create("Dialog", {
		height:500,
		width:900,
		showMaxBtn:false,
	    title:"任务详情",
	    src : "IITaskDetail.html?edit=detail&taskId="+ data.c_task_id+"&c_status="+data.c_status,
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

/* 岗位选择 */
function doChange(){
	fastDev.getInstance("c_positionid").setValue(arguments[0]);
}

/**
 * 过滤信息
 */
function doSearch() {
	var fm = fastDev.getInstance('checkform');
	condition = fm.getItems();
	console.info(condition);
	refreshDatagrid();
}
/**
 * 重置表单数据
 */
function doReset() {
	fastDev.getInstance('checkform').cleanData();
	condition = null;
}
/**
 * 刷新datagrid
 * @param o
 */
function refreshDatagrid() {
	fastDev.getInstance('grid1').refreshData(condition);
}

/**
 * 批量删除信息
 
function doBatchDeleteCheckModel() {
	var datagrid = fastDev.getInstance('grid1');
	var items = datagrid.getValue();
	if(items.length <=0 ) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm("确认是否删除选择的" + items.length + "条记录？", "信息提示", function(result){
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
		action : 'delete_deleteInfo.action?action=4'
	});
	proxy.save({deleteID:id}, function(result){
		fastDev.alert(result.msg, '信息提示', result.status, function(){
			if(result.status == 'ok') {
				refreshDatagrid();
			}
		});
	});
}


function doAddBasic(){
	fastDev.create("Dialog", {
		showMaxBtn:false,
		width : '400',
	    title:"新增数据项信息",
	    src : "addBasic.html?edit=add&item="+ item,
		buttons : [{
			text : "保存",
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

*/
