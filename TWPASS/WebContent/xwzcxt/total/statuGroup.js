var request = fastDev.Browser.getRequest();
//通过action参数进行信息读取，以确认操作的对象为子项(1)、子项类别(2)、基础项(3)
var item = request['item'];
//刷新条件
var condition=null;
var checkDialog;
var delectAction = "task_delete.action?action=1"; 

/**
 * 关闭对话框
 */
function closeDialog() {
	if(checkDialog) {
		checkDialog.close();
		checkDialog = null;
	}
}	
function onBeforeReady(){	 
	this.setOptions({
		initSource : "task_queryTaskByPage.action?action=14"
	});	 
};
function operRenderer() { 
		return ['<div style="width:200px;"><a href="javascript:void(0);" style="margin-left:5px;display:none;" class="btn" id="modify" name="modify">修改</a>'+
		        '<a href="javascript:void(0);" style="margin-left:5px;display:none;" class="btn" id="delete" name="delete">删除</a>']
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

/**
 * 批量删除信息
 */
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
					ids.push(items[i].c_basic_id);
				}
				deleteCheckModelById(ids.join(","));
			}
		});
	}
}

function deleteCheckModelById(id) {
	var proxy = fastDev.create('Proxy', {
		action : delectAction
	});
	proxy.save({deleteID:id}, function(result){
		fastDev.alert(result.msg, '信息提示', result.status, function(){
			if(result.status == 'ok') {
				refreshDatagrid();
			}
		});
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