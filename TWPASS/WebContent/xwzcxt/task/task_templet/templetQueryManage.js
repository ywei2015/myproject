var condition = null;
var childwindow = null;
var cTsId = null;
var cPositionID = null;

function afterInitRender() {
	//设置所属部门和作业岗位
	fastDev.get("pbo/getPositionIdAction.action", {
		success : function (data) {
			var id = data.positionid + '';
			fastDev.getInstance('orgtree').setValue(id);
			if (fastDev.getInstance('orgtree').getValue()) {
				condition = { cPositionID: id };
				fastDev.getInstance('grid_task_plan').refreshData(condition);
				cPositionID = id;
			}
		}
	});
}

function onBeforeReady() {
	this.setOptions({
		initSource : 'templet/getAllPlanSeriesAction.action'
	});
}

function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body - 60);
	showResourceModes('RWJXCX');
}

function operRenderer() {
	return [ '<a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="delete" name="delete">删除</a>' ].join('');
}

function operLinkRenderer(value) {
	return [ '<a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openTaskDetails" name="openTaskDetails">' + value + '</a>' ].join('');
}

function onNodeClick(event, id, value) {
	var isLeaf = this.isLeaf(id);
	if (isLeaf) {
		if (-1 < id.indexOf("1-")) {
			return;
		}
		condition = { cPositionID: id };
		fastDev.getInstance('grid_task_plan').refreshData(condition);
		cPositionID = id;
	}
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

function doOpenTaskDetails(data) {
	cTsId = data.C_TS_ID;
	planAdd('标准任务解析明细');
}

function doModify(data) {
	cTsId = data.C_TS_ID;
	planAdd('修改任务解析');
}

/**
 * 任务模板查看、修改、评审、新建共用接口
 */
function planAdd(title) {
	var cModifyType = null;
	var button = null;
	var save = {
		id : 'save',
		text : '保存',
		align : 'center',
		iconCls : 'icon-save',
		onclick : function(event, win, childWin, fast) {
			//templetCheck(fast);
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

	if ('标准任务解析明细' == title) {
		cModifyType = 0;
		button = new Array(close);
	} else if ('修改任务解析' == title) {
		cModifyType = 1;
		button = new Array(save, close);
	} else {
		cModifyType = 0;
		button = new Array(close);
	}

	childwindow = fastDev.create('Dialog', {
		width : 800,
		height : 310,
		inside : false,
		showMaxBtn : false,
		title : title,
		allowResize : false,
		src : '../xwzcxt/task/task_templet/templetQueryAdd.html?cModifyType=' + cModifyType + '&cTsId=' + cTsId,
		buttons : button
	});
}

function doDelete(data) {
	fastDev.confirm("确认是否删除选择的记录？", "信息提示", function(result) {
		if (result) {
			var ids = data.C_TS_ID;
			deletePlan(ids);
		}
	});
}

function deletePlan(id) {
	var proxy = fastDev.create('Proxy', {
		action : 'templet/deletePlanSeriesByIdAction.action'
	});
	proxy.save({
		id : id
	}, function(result) {
		fastDev.alert(result.msg, '信息提示', result.status, function() {
			if (result.status == 'ok') {
				refreshDatagrid();
			}
		});
	});
}

function batchDeleteObject() {
	var datagrid = fastDev.getInstance('grid_task_plan');
	var items = datagrid.getValue();
	if (items.length <= 0) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm("确认是否删除选择的" + items.length + "条记录？", "信息提示", function(result) {
			if (result) {
				var ids = [];
				for (var i = 0; i < items.length; i++) {
					ids.push(items[i].C_TS_ID);
				}
				deletePlan(ids.join(","));
			}
		});
	}
}

function closeDialog() {
	if (childwindow) {
		childwindow.close();
		childwindow = null;
	}
}

function refreshDatagrid() {
	var o = condition || {};
	fastDev.getInstance('grid_task_plan').refreshData(o);
}

function doSearch() {
	var form = fastDev.getInstance("checkform");
	condition = form.getItems();
	condition["cPositionID"] = cPositionID;
	fastDev.getInstance('grid_task_plan').refreshData(condition);
}

function doReset() {
	fastDev.getInstance('checkform').cleanData();
	condition = null;
}