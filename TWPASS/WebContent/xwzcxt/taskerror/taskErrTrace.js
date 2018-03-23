//刷新条件
var condition = null;
var loadingWindow = null;

function createNewErr() {
	var path = window.location.pathname;
	var url = path.substring(0, path.lastIndexOf("/"));
	url = url + "/" + "newErr.html?c_err_kind=2";
	var grid=fastDev.getInstance('grid_task');
	fastDev.create("Window", {
		title : "发起异常",
		src : url,
		width : 800,
		height : 400,
		buttons : [{
			text : "保存",
			align : "center",
			iconCls : "icon-save",
			onclick : function(event, that, win, fast) {
				win.doSave(that,grid);
			}
		},{
			text : "关闭",
			align : "center",
			iconCls : "icon-close",
			onclick : function(event, that, win, fast) {
				that.close();
			}
		}]
	});
}

function exportXLS() {
	var formData = fastDev.getInstance("checkform").getItems();
	fastDev.create("Proxy", {
		action : "taskErrorInfo/exportTaskErrorInfoExcelAction.action"
	}).save(formData, function(data) {
		fastDev("#dc").prop("src", data.url);
	});	
}

function onBeforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkform",
		text : "加载中..."
	});

	this.setOptions({
		initSource : 'errTrace_getErrorFeedbackList.action'
	});
}

function onAfterDataRender() {
	var body = fastDev(window).height();
	this.setHeight(body - 125);
	showResourceModes('ycczcx');
	loadingWindow.close();
}

/**
 * 过滤信息
 */
function doSearch() {
	var form = fastDev.getInstance("checkform");
	condition = form.getItems();
	fastDev.getInstance('grid_task').refreshData(condition);
}
/**
 * 重置表单数据
 */
function doReset() {
	var type=fastDev.getInstance('taskErrTraceVo.infotype').getValue();
	fastDev.getInstance('checkform').cleanData();
	fastDev.getInstance('taskErrTraceVo.infotype').setValue(type);
	condition = null;
}

function operLinkRenderer(value) {
	return [ '<div style="width:300px;"><a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openErrTaskDetails" name="openErrTaskDetails">'
 		+ value + '</a>' ].join('');
}

function doOpenErrTaskDetails(data) {
	var c_err_id=data.c_err_idStr;
	var c_task_id= data.c_task_idStr+"";
	fastDev.create("Dialog", {
		height : 500,
		width : 900,
		inside : false,
		showMaxBtn : false,
		allowResize : false,
		title : "异常详情",
		src : "../xwzcxt/taskerror/taskErrorDetail.html?cErrId=" + c_err_id 
				+ "&cTaskId=" + c_task_id+"&dg_seq="+ data.dg_seq,
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

function onSelectPosition(e) {
	var element = e.srcElement || e.target;
	fastDev.confirm("是否需要修改当前反馈人？", "信息提示", function(result) {
		if (result) {
			doSelectPosition(element.id);
		}
	});
}

function doSelectPosition(name) {
	var id = name;
	var cPid = fastDev.getInstance(id).getValue();

	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "反馈人",
		allowResize : false,
		
		src : '../xwzcxt/taskerror/PositionTree/DynamicPositionTree.html',
		buttons : [ {
			id : 'ok',
			text : '确定',
			onclick : function(event, win, cwin, fast) {
				//var cPosition = fast.getInstance("cPosition");
				var user = cwin.getLeafValue();
				if (!!user) {
					setPosition(name, user.id, user.name);
					win.close();
				} else {
					window.alert("请选择反馈人！");
				}
			}
		}, {
			id : 'clear',
			text : '清空',
			onclick : function(event, win) {
				setPosition(name, "", "");
				win.close();
			}
		} ]
	});
}

function setPosition(name, cPid, cPidName) {
	//var id = name.substring(0, name.indexOf("Name"));
	fastDev.getInstance("taskErrTraceVo.c_writer").setValue(cPid.replace("U-",""));
	fastDev.getInstance("c_feedbacker_name").setValue(cPidName);
}

var oldName='我负责的';
function onTabClick(Object, name) {
	if(oldName==name){
		return;
	}
	var feedbacker=fastDev.getInstance('c_feedbacker_name');
	oldName=name;
	var type;
	if("我负责的" == name) {
		feedbacker.enable();
		type = 1;
	} else if("抄送给我的" == name){
		feedbacker.enable();
		type = 2;
	}/*else if("全部" == name){
	    feedbacker.enable();
		type =null;
	}*/
	else {   //我反馈的
		feedbacker.disable();
		type = 3;
	}
	fastDev.getInstance('taskErrTraceVo.infotype').setValue(type);
	doSearch();
}

function toResult(value){
	var a=['未完成','已完成'];
	return a[value];
}

function toErrKindName(value){
	var a=['工作执行异常','人工发起异常'];
	return a[value-1];
}

function timeRender(value){
	if(value){
		return value.replace("T"," ");
	}
}