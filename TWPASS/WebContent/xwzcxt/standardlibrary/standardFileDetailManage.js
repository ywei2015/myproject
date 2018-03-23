var dialog = null;
var condition = null;

function onBeforeReady() {
	this.setOptions({
		initSource : 'standardfile/getAllTSdStandardfileByConditionsAction.action'
	});
}

function onAfterInitRender() {
	showResourceModes('BZWJMXB');
	var body = fastDev(window).height();
	this.setHeight(body - 105);
}

function operLinkRenderer(value) {
	return [ '<a href="javascript:void(0);" class="btn" id="openDetails" name="openDetails">' + value + '</a>' ].join('');
}

function operHrefRenderer(value) {
	return [ '<a href="' + value + '?aram=xwxt" target="_blank" class="btn" style="display:none;" id="link" name="link">链接</a>' ].join('');
}

function operRenderer() {
	return [ '<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="modify" name="modify">修改</a>',
		'<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="delete" name="delete">删除</a>' ]
		.join('');
}

function onRowClick(event, rowindex, rowData) {
	var target = event.target;
	var name = target['name'];
	if (name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		if (window['do' + name]) {
			window['do' + name].call(window, rowData);
		}
	}
}

function doOpenDetails(data) {
	standardFileAdd('查看标准文件明细', data.cFiledetailId);
}

function doAdd() {
	standardFileAdd('添加标准文件明细');
}

function doModify(data) {
	standardFileAdd('修改标准文件明细', data.cFiledetailId);
}

function standardFileAdd(title, cFiledetailId) {
	var cModifyType = null;
	var button = null;
	var save = {
		id : 'save',
		text : '保存',
		align : 'center',
		iconCls : 'icon-save',
		onclick : function(event, win, childWin, fast) {
			var sortId = fast.getInstance("cSortId").getValue();
			var sfileVersion = fast.getInstance("cSfileVersion").getValue();
			var sfileName = fast.getInstance("cSfileName").getValue();
			var fwQcbm = fast.getInstance("cFwQcbm").getValue();
			var systag = fast.getInstance("cSystag").getValue();
			var status = fast.getInstance("cStatus").getValue();
			var statusTime = fast.getInstance("cStatusTime").getValue();
			var fileUrl = fast.getInstance("cFileUrl").getValue();
			if (sortId == "") {
				window.alert("请设置“门类”");
			} else if (sfileVersion == "") {
				window.alert("请设置“编号”");
			} else if (sfileName == "") {
				window.alert("请设置“标准名称”");
			} else if (fwQcbm == "") {
				window.alert("请设置“起草部门”");
			} else if (systag == "") {
				window.alert("请设置“所属体系”");
			} else if (status == "") {
				window.alert("请设置“状态”");
			} else if (statusTime == "") {
				window.alert("请设置“时间”");
			} else if (fileUrl == "") {
				window.alert("请设置“文件地址”");
			} else {
				this.disable();
				childWin.showWaitingDialog(this);
				var form = fast.getInstance("checkForm");
				form.submit();
			}
		}
	};
	var reset = {
		id : 'reset',
		text : '重置',
		align : 'center',
		iconCls : 'icon-reset',
		onclick : function(e, win, cwin) {
			cwin.doReset();
		}
	};
	var close = {
		id : 'close',
		text : '关闭',
		align : 'center',
		iconCls : 'icon-close',
		onclick : function(event, win) {
			win.close();
		}
	};

	if ('查看标准文件明细' == title) {
		cModifyType = 0;
		button = new Array(close);
	} else if ('修改标准文件明细' == title) {
		cModifyType = 1;
		button = new Array(save, close);
	} else if ('添加标准文件明细' == title) {
		cModifyType = 2;
		button = new Array(save, reset, close);
	} else {
		cModifyType = 0;
		button = new Array(close);
	}

	dialog = fastDev.create('Dialog', {
		width : '800px',
		height : '360px',
		inside : false,
		showMaxBtn : false,
		title : title,
		allowResize : false,
		src : '../xwzcxt/standardlibrary/standardFileDetailAdd.html?cModifyType='
			+ cModifyType + '&cFiledetailId=' + cFiledetailId,
		buttons : button
	});
}

function doDelete(data) {
	fastDev.confirm("确认是否删除选择的记录？", "信息提示", function(result) {
		if (result) {
			var ids = data.cFiledetailId;
			deleteObject(ids);
		}
	});
}

function batchDeleteObject() {
	var datagrid = fastDev.getInstance("dataGrid");
	var objects = datagrid.getValue();
	var ids = new Array();
	if (!!objects) {
		for (var i = 0; i < objects.length; i++)
			ids.push(objects[i].cFiledetailId);
	}
	if (ids.length == 0) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm('是否删除所选择的' + ids.length + '条记录？', '信息提示', function(result) {
			if (result) {
				deleteObject(ids.join(","));
			}
		});
	}
}

function deleteObject(idStr) {
	fastDev.create("Proxy", {
		action : "standardfile/deleteTSdStandardfileByIdAction.action"
	}).save({
		id : idStr
	},function(data) {
		fastDev.alert(data.msg, "信息", data.status, function() {
			if (data.status == 'ok') {
				refreshDatagrid();
			}
		});
	});
}

function refreshDatagrid() {
	var o = condition || {};
	fastDev.getInstance('dataGrid').refreshData(o);
}

function closeDialog() {
	if (dialog) {
		dialog.close();
		dialog = null;
	}
}

function doSearch() {
	condition = fastDev.getInstance('standardFileSearchForm').getItems();
	refreshDatagrid();
}

function doReset() {
	fastDev.getInstance('standardFileSearchForm').cleanData();
}

function onSelectDepartment(e) {
	var element = e.srcElement || e.target;
	fastDev.confirm("是否需要修改当前起草部门？", "信息提示", function(result) {
		if (result) {
			doSelectDepartment(element.id);
		}
	});
}

function doSelectDepartment(name) {
	var id = name.substring(0, name.indexOf("Name"));
	var cPid = fastDev.getInstance(id).getValue();

	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "选择部门组织",
		allowResize : false,
		src : '../xwzcxt/task/task_templet/templetSelectPositionForSearch.html?orgtype=4&module=BZWJMXB&cPid=' + cPid,
		buttons : [ {
			id : 'ok',
			text : '确定',
			align : 'center',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				var cPosition = fast.getInstance("cPosition");
				var pids = cPosition.getChkedIds("onlyLeafValue");
				if (pids) {
					setDepartment(name, pids, cPosition.getValsByids(cPosition.getChkedIds("allchkValue")));
					win.close();
				} else {
					window.alert("请选择部门！");
				}
			}
		}, {
			id : 'clear',
			text : '清空',
			align : 'center',
			iconCls : 'icon-reset',
			onclick : function(event, win) {
				setDepartment(name, "", "");
				win.close();
			}
		} ]
	});
}

function setDepartment(name, cPid, cPidName) {
	var id = name.substring(0, name.indexOf("Name"));
	fastDev.getInstance(id).setValue(cPid);
	fastDev.getInstance(name).setValue(cPidName);
}