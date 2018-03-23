var request = fastDev.Browser.getRequest();
// 通过action参数进行信息读取，以确认操作的对象为子项(1)、子项类别(2)、基础项(3)
var item = request['item'];
// 刷新条件
var condition = null;
var checkDialog;
var status = "first";
var orgid;
var position;

function exportXLS() {
	var formData = fastDev.getInstance("checkform").getItems();
	fastDev.create("Proxy", {
		action : "task_new/exportTaskExcelAction.action"
	}).save(formData, function(data) {
		fastDev("#dc").prop("src",data.url);
	});	
	
}

/** ************************************************************** */
/** SelectTree * */
function showManaPosition() {
	var dpt = fastDev.getInstance('department');
	orgid = dpt.getValue();
	// var position = fastDev.getInstance('position');
	// position.clean(true);
	// position.initRefresh({
	// url : 'dpPosition/getPositionByOrgIdAction.action?orgid=' + orgid
	// });
}

function getPosition(value) {
//	var stringlist = value.split('-');
//	if (stringlist.length > 1) {
//		window.alert(stringlist[0]);
//		window.alert(stringlist[1]);
//	} else {
//		window.alert(stringlist[0]);
//	}

	var pos = fastDev.getInstance('position');
	position = pos.getValue();
}

/** ************************************************************** */
function onBeforeReady() {
	this.setOptions({
		initSource : 'task_new/getAllTaskAction.action'
	});
};
function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body - 120);
}
/**
 * 过滤信息
 */
function doSearch() {
	var form = fastDev.getInstance("checkform");
	condition = form.getItems();
	fastDev.getInstance('grid_task').refreshData(condition);
	console.info(condition);
}

/**
 * 重置表单数据
 */
function doReset() {
	fastDev.getInstance('checkform').cleanData();
	condition = null;
}

function operLinkRenderer(value) {
	return [ '<div style="width:300px;"><a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openTaskDetails" name="openTaskDetails">'
			+ value + '</a>' ].join('');
}

function operLinkStatusRenderer(value) {
	return [ '<div style="width:300px;"><a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openTaskStatusDetails" name="openTaskStatusDetails">'
			+ value + '</a>' ].join('');
}

function doOpenTaskStatusDetails(data) {
	fastDev.create("Dialog", {
		height : 170,
		width : 1100,
		showMaxBtn : false,
		title : data.cTaskName + " 任务的状态进度 ： 执行人（" + data.cExecUsername
				+ "）， 执行岗位（" + data.execpositionname + "），部门（"
				+ data.orgdepartname + "）， 状态（" + data.cStatusName + "）",
		src : "taskStatus.html?isctrl=" + data.cIskeyctrl + "&cStatusName="
				+ data.cStatusName + "&cExecUsername=" + data.cExecUsername
				+ "&orgdepartname=" + data.orgdepartname + "&execpositionname="
				+ data.execpositionname,
		buttons : [ {
			text : "关闭",
			align : "center",
			iconCls : "icon-close",
			onclick : function(event, that, win, fast) {
				// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
				// 参数that为当前对话框控件实例
				that.close();
			}
		} ]
	});
}

function doOpenTaskDetails(data) {
	fastDev.create("Dialog", {
		height : 500,
		width : 900,
		showMaxBtn : false,
		title : "任务详情",
		src : "taskDetails.html?edit=details&taskId=" + data.cTaskId,
		buttons : [ {
			text : "关闭",
			align : "center",
			iconCls : "icon-close",
			onclick : function(event, that, win, fast) {
				// 按钮点击事件回调的作用域(this)指向当前按钮控件实例
				// 参数that为当前对话框控件实例
				that.close();
			}
		} ]
	});
}

function onRowClick(event, rowindex, data) {
	var target = event.target;
	var name = target['name'];
	if (name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		if (window['do' + name]) {
			window['do' + name].call(window, data);
		}
	}
}
