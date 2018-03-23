//刷新条件
var condition = null;
var loadingWindow = null;
var extype = getUrlParam('extype');
var userCode = getUrlParam('userCode');
window.onload = function(){ 
	  if(extype!=null&&extype==1){
	  fastDev.getInstance("extype").setValue(1);
	  fastDev.getInstance("userCode").setValue(userCode);
	  doSearch();
	  }
}
function exportXLS() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkform",
		text : "导出中..."
	});
	var formData = fastDev.getInstance("checkform").getItems();
	fastDev.create("Proxy", {
		action : "taskErrorInfo/exportTaskErrorInfoExcelAction.action"
	}).save(formData, function(data) {
		fastDev("#dc").prop("src", data.url);
		loadingWindow.close();
	});	
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

function onBeforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkform",
		text : "加载中..."
	});

	this.setOptions({
		initSource : 'taskErrorInfo/getAllTaskErrorInfoAction.action'
	});
}

function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body - 100);
	showResourceModes('ycczcx');
	loadingWindow.close();
}

function doSearch() {
	var form = fastDev.getInstance("checkform");
	condition = form.getItems();
	fastDev.getInstance('grid_taskErrror').refreshData(condition);
}

function doReset() {
	fastDev.getInstance('checkform').cleanData();
	condition = null;
}

function operLinkRenderer(value) {
	return [ '<a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openErrTaskDetails" name="openErrTaskDetails">' + value + '</a>' ].join('');
}

function doOpenErrTaskDetails(data) {
	fastDev.create("Dialog", {
		height : 536,
		width : 1000,
		inside : false,
		showMaxBtn : false,
		allowResize : false,
		title : "异常详情",
		src : "../xwzcxt/taskerror/taskErrorDetail.html?cErrId=" + data.C_ERR_ID + "&cTaskId=" + data.C_TASK_ID + "&dg_seq=" + data.dg_seq,
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
	var id = name.substring(0, name.indexOf("Name"));
	var cPid = fastDev.getInstance(id).getValue();

	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "选择异常反馈人",
		allowResize : false,
		src : '../xwzcxt/task/task_templet/templetSelectPositionForSearch.html?orgtype=7&module=ycczcx&cPid=' + cPid,
		buttons : [ {
			id : 'ok',
			text : '确定',
			align : 'center',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				var cPosition = fast.getInstance("cPosition");
				var pids = cPosition.getChkedIds("onlyLeafValue");
				if (pids) {
					setPosition(name, pids, cPosition.getValsByids(cPosition.getChkedIds("allchkValue")));
					win.close();
				} else {
					window.alert("请选择反馈人！");
				}
			}
		}, {
			id : 'clear',
			text : '清空',
			align : 'center',
			iconCls : 'icon-reset',
			onclick : function(event, win) {
				setPosition(name, "", "");
				win.close();
			}
		} ]
	});
}

function setPosition(name, cPid, cPidName) {
	var id = name.substring(0, name.indexOf("Name"));
	fastDev.getInstance(id).setValue(cPid);
	fastDev.getInstance(name).setValue(cPidName);
}

function batchDeleteObject() {
	var datagrid = fastDev.getInstance('grid_taskErrror');
	var items = datagrid.getValue();
	if (items.length <= 0) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm("确认是否删除选择的" + items.length + "条记录？", "信息提示", function(result) {
			if (result) {
				var ids = [];
				for (var i = 0; i < items.length; i++) {
					ids.push(items[i].C_ERR_ID);
				}
				fastDev.create("Proxy", {
					action : "taskErrorInfo/deleteTaskErrorByIdAction.action"
				}).save({
					id : ids.join(",")
				},function(data) {
					fastDev.alert(data.msg, "信息", data.status, function() {
						if (data.status == 'ok') {
							datagrid.refreshData(condition);
						}
					});
				});
			}
		});
	}
}

function suggestendTimeBlur() {
	if (fastDev.getInstance("cSuggestendTime").getValue()) {
		fastDev.getInstance("cCloseTime").reset();
	}
}

function closeTimeBlur() {
	if (fastDev.getInstance("cCloseTime").getValue()) {
		fastDev.getInstance("cSuggestendTime").reset();
	}
}