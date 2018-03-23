var condition = null;
var childwindow = null;

function displayNameRenderer(value) {
	return '<a href="javascript:void(0);" class="btn" name="ShowInfo">' + value	+ '</a>';
}

function onBeforeReady() {
	this.setOptions({
		initSource : "ruletime/getAllRuleTimeAction.action"
	});
}

function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body - 80);
	showResourceModes('M90_2');
}

function operRenderer() {
	return [ '<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="modify" name="modify">修改</a>',
		'<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="delete" name="delete">删除</a>' ]
		.join('');
}

function operLinkRenderer(value) {
	return [ '<a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openTimeRuleDetails" name="openTimeRuleDetails">' + value + '</a>' ].join('');
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

function doSearch() {
	var form = fastDev.getInstance("checkform");
	condition = form.getItems();
	fastDev.getInstance('grid_ruletime').refreshData(condition);
	console.info(condition);
}

function doReset() {
	fastDev.getInstance('checkform').cleanData();
	condition = null;
}

function doAddTimeRule() {
	timeRuleAdd('添加时间规则', null);
}

function doOpenTimeRuleDetails(data) {
	timeRuleAdd('时间规则明细', data.cTimeruleId);
}

function doModify(data) {
	fastDev.confirm("是否需要使用修改功能？<br/>注：（不允许修改：时间类别和所属组织（部门））<br/>如果修改时间规则，则使用该时间规则的标准和模板都会使用修改后的时间规则", "信息提示", function(result) {
		if (result) {
			timeRuleAdd('修改时间规则', data.cTimeruleId);
		}
	});
}

function timeRuleAdd(title, cTimeruleId) {
	var cModifyType = null;
	var button = null;
	var save = {
		id : 'save',
		text : '保存',
		align : 'center',
		iconCls : 'icon-save',
		onclick : function(event, win, childWin, fast) {
			timeRuleCheck(fast);
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

	if ('时间规则明细' == title) {
		cModifyType = 0;
		button = new Array(close);
	} else if ('修改时间规则' == title) {
		cModifyType = 1;
		button = new Array(save, close);
	} else if ('添加时间规则' == title) {
		cModifyType = 2;
		button = new Array(save, close);
	} else {
		cModifyType = 0;
		button = new Array(close);
	}

	childwindow = fastDev.create('Window', {
		width : 700,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : title,
		allowResize : false,
		src : '../xwzcxt/rule/timeRuleAdd.html?cModifyType=' + cModifyType + '&cTimeruleId=' + cTimeruleId,
		buttons : button
	});
}

function timeRuleCheck(fast) {
	var select = fast.getInstance("cDepartment").getTree().getChkedIds("allchkValue");
	var aval = fast.getInstance("cTimeruleType").getValue();
	if (aval == "") {
		window.alert("请设置“时间类型”");
	} else if (select == "") {
		window.alert("请设置“所属组织（部门）”");
	} else {
		fastDev.create("Dialog", {
			width : 450,
			inside : false,
			showMaxBtn : false,
			title : '请确认',
			content : "当前时间规则名称为：" + fast.getInstance("cTimeruleName").getValue() + "<br/>是否需要修改时间规则名称？",
			bodyStyle : 'text-align:center;padding:20px;font-size:15px;',
			allowResize : false,
			buttons : [ {
				text : "修改",
				align : "center",
				onclick : function(_event, _that, _win, _fast) {
					fast.getInstance("cTimeruleName").enable();
					_that.close();
				}
			}, {
				text : "不修改",
				align : "center",
				onclick : function(_event, _that, _win, _fast) {
					fast.getInstance("cDepartments").setValue(select);
					var form = fast.getInstance("addTimeRuleForm");
					form.submit();
					_that.close();
				}
			} ]
		});
	}
}

function doDelete(data) {
	fastDev.confirm("确认是否删除选择的记录？", "信息提示", function(result) {
		if (result) {
			deleteTimeRule(data.cTimeruleId);
		}
	});
}

function batchDeleteObject() {
	var datagrid = fastDev.getInstance('grid_ruletime');
	var items = datagrid.getValue();
	if (items.length <= 0) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm("确认是否删除选择的" + items.length + "条记录？", "信息提示", function(result) {
			if (result) {
				var ids = [];
				for (var i = 0; i < items.length; i++) {
					ids.push(items[i].cTimeruleId);
				}
				deleteTimeRule(ids.join(","));
			}
		});
	}
}

function deleteTimeRule(id) {
	var proxy = fastDev.create('Proxy', {
		action : 'ruletime/deleteRuleTimeByIdsAction.action'
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

function closeDialog() {
	if (childwindow) {
		childwindow.close();
		childwindow = null;
	}
}

function refreshDatagrid() {
	var o = condition || {};
	fastDev.getInstance('grid_ruletime').refreshData(o);
}