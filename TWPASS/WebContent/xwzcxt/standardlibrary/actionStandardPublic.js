var request = fastDev.Browser.getRequest();
var type = request['type'];
var cPublicId = '';
var cPublicName = '';
var cActnodetype = 1;
var cActnodeId = null;

var condition = null;
var addNodeWindow = null;
var dialog = null;

function onAfterInitRender() {
	if ('' != cPublicId) {
		this.setValue(cPublicId);
	}
	if ('search' != type) {
		showResourceModes('GG5W2HBZZD');
	}
}

function onNodeClick(event, id, value) {
	cPublicId = id;
	cPublicName = value;

	refreshDatagrid();
}

function doAddActionLeft() {
	addNode('添加节点');
}

function doModifyActionLeft() {
	addNode('修改节点');
}

function addNode(title) {
	if ('' == cPublicId) {
		fastDev.alert('请先选择通用标准节点！', '信息提示', 'warning');
	} else if (('-1' == cPublicId) && ('修改节点' == title)) {
		fastDev.alert('“所有节点”节点禁止修改操作，请选择其他节点！', '信息提示', 'warning');
	} else {
		var cModifyType = null;
		var button = null;
		var save = {
			id : 'save',
			text : '保存',
			align : 'center',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				var name = fast.getInstance('cPublicSname').getValue();
				var section = fast.getInstance('cSectionId').getValue();
				if (name == '') {
					window.alert('请设置“节点名称”');
				} else if (section == '') {
					window.alert('请设置“板块”');
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

		if ('修改节点' == title) {
			cModifyType = 1;
			button = new Array(save, close);
		} else if ('添加节点' == title) {
			cModifyType = 2;
			button = new Array(save, reset, close);
		} else {
			cModifyType = 0;
			button = new Array(close);
		}

		addNodeWindow = fastDev.create('Window', {
			width : 600,
			height : 210,
			inside : false,
			showMaxBtn : false,
			title : title,
			allowResize : false,
			src : '../xwzcxt/standardlibrary/publicNodeAdd.html?cModifyType=' + cModifyType + '&cPublicId=' + cPublicId + '&cPublicName=' + cPublicName,
			buttons : button
		});
	}
}

function doDeleteActionLeft() {
	if ('' == cPublicId) {
		fastDev.alert('请先选择通用标准节点！', '信息提示', 'warning');
	} else if ('-1' == cPublicId) {
		fastDev.alert('“所有节点”节点禁止删除操作，请选择其他节点！', '信息提示', 'warning');
	} else {
		fastDev.confirm("确认是否删除选择的记录？<br/>注：<br/>如果删除该节点，则该节点和其子节点均会被删除<br/>同时该节点和其子节点关联的通用标准也会被删除", "信息提示", function(result) {
			if (result) {
				var proxy = fastDev.create('Proxy', {
					action : 'tSdPublic/deletePublicNodesByIdAction.action'
				});
				proxy.save({
					cPublicId : cPublicId
				}, function(result) {
					fastDev.alert(result.msg, '信息提示', result.status, function() {
						if (result.status == 'ok') {
							cPublicId = fastDev.getInstance('publicTree').getParentid(cPublicId);
							refreshPublicTree();
							refreshDatagrid();
						}
					});
				});
			}
		});
	}
}

function refreshPublicTree() {
	var publicTree = fastDev.getInstance('publicTree');
	publicTree.initRefresh();
}

function closeDialogPublicTree() {
	if (addNodeWindow) {
		addNodeWindow.close();
		addNodeWindow = null;
	}
}

function doSearch() {
	condition = fastDev.getInstance('actnodeSearchForm').getItems();
	refreshDatagrid();
}

function doReset() {
	fastDev.getInstance('actnodeSearchForm').cleanData();
}

function refreshDatagrid() {
	var o = condition || {};

	o['actnode.c_public_id'] = cPublicId;
	o['actnode.c_actnodetype'] = cActnodetype;

	fastDev.getInstance('grid_actnode' + cActnodetype).addInitReqParam(o);
	fastDev.getInstance('grid_actnode' + cActnodetype).refreshData();
}

function closeDialog() {
	if (dialog) {
		dialog.close();
		dialog = null;
	}
}

function onTabClick(Object, name) {
	if ("作业类" == name) {
		cActnodetype = 1;
		document.getElementById("c_manageattr_title").style.display = "none";
		document.getElementById("c_manageattr").style.display = "none";
		document.getElementById("actnodeSearchFormButton").colSpan = "4";
	} else {
		cActnodetype = 2;
		document.getElementById("c_manageattr_title").style.display = "table-cell";
		document.getElementById("c_manageattr").style.display = "table-cell";
		document.getElementById("actnodeSearchFormButton").colSpan = "2";
	}
	if (cPublicId) {
		refreshDatagrid();
	}
}

function onRowClick(event, rowindex, rowData) {
	cActnodeId = rowData.cActnodeId;
	var target = event.target;
	var name = target['name'];
	if (name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		if (window['do' + name]) {
			window['do' + name].call(window, rowData);
		}
	}
}

function beforeReady() {
	this.setOptions({
		initSource : 'tsdactnode/getAllActNodesByConditionsByPublicAction.action'
	});
}

function afterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body - 135);
	if ('search' != type) {
		showResourceModes('GG5W2HBZZD');
	}
}

function beforeDetailGridReady() {
	this.setOptions({
		initSource : "tsdactnode/getActNodeItemByActNodeIDAction.action?cActnodeId=" + cActnodeId
	});
}

function operLinkRenderer(value) {
	return [ '<a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openActionDetails" name="openActionDetails">' + value + '</a>' ].join('');
}

function operMediaRenderer() {
	return [ '<a href="javascript:void(0);" class="btn" style="display:none;" id="media" name="media">添加多媒体</a>' ].join('');
}

function operRenderer() {
	return [ '<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="modify" name="modify">修改</a>',
		'<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="delete" name="delete">删除</a>' ]
		.join('');
}

function doImportAction() {
	if (cPublicId) {
		openImportPage();
	} else {
		fastDev.alert('请先选择通用标准节点！','信息提示','warning');
	}
}

function openImportPage() {
	dialog = fastDev.create('Dialog', {
		width : 500,
		height : 370,
		inside : false,
		showMaxBtn : false,
		title : '通用5W2H行为标准批量导入',
		allowResize : false,
		src : '../xwzcxt/standardlibrary/actionStandardImport.html?cPublicId=' + cPublicId + '&cPublicName=' + cPublicName + '&cIspublic=1',
		buttons : [ {
			text : '导入',
			align : 'center',
			iconCls : 'icon-save',
			onclick : function(event, win, cwin, fast) {
				this.disable();
				cwin.processBar(this);
				var form = fast.getInstance("checkForm");
				form.submit();
			}
		}, {
			text : '重置',
			align : 'center',
			iconCls : 'icon-reset',
			onclick : function(e, win, cwin) {
				cwin.doReset();
			}
		}, {
			text : '关闭',
			align : 'center',
			iconCls : 'icon-close',
			onclick : function(event, win) {
				win.close();
			}
		} ]
	});
}

function doAddAction() {
	fastDev.create("Dialog", {
		width : 250,
		inside : false,
		showMaxBtn : false,
		title : '请选择',
		content : '请选择创建岗位活动的类型',
		bodyStyle : 'text-align:center;padding:20px;font-size:15px;',
		allowResize : false,
		buttons : [ {
			text : "作业类",
			align : "center",
			onclick : function(event, that, win, fast) {
				actionAdd('添加岗位活动', 1);
				that.close();
			}
		}, {
			text : "管理类",
			align : "center",
			onclick : function(event, that, win, fast) {
				actionAdd('添加岗位活动', 2);
				that.close();
			}
		} ]
	});
}

function doOpenActionDetails() {
	actionAdd('标准岗位活动明细');
}

function doMedia() {
	actionAdd('添加多媒体');
}

function doModify() {
	actionAdd('修改岗位活动');
}

/**
 * 岗位活动查看、修改、新建共用接口
 */
function actionAdd(title, cActnodetype) {
	if (!cPublicId) {
		fastDev.alert('请先选择通用标准节点！','信息提示','warning');
	} else {
		var cModifyType = null;
		var button = null;
		var save = {
			id : 'save',
			text : '保存',
			align : 'center',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				var cActnodetype = fast.getInstance("cActnodetype").getValue();
				if ("1" == cActnodetype) {
					var actname = fast.getInstance("cActnodeName").getValue();
					var pdca = fast.getInstance("cPdca").getValue();
					var keyctrl = fast.getInstance("cIskeyctrl").getValue();
					var frequency = fast.getInstance("cFrequency").getValue();
					var sequence = fast.getInstance("cIssequence").getValue();
					var random = fast.getInstance("cIsrandom").getValue();
					var scan = fast.getInstance("cIsscan").getValue();
					var value = fast.getInstance("detailGrid1").getAllValue();
					if (actname == "") {
						window.alert("请设置“岗位活动名称”");
					} else if (pdca == "") {
						window.alert("请设置“PDCA属性”");
					} else if (keyctrl == "") {
						window.alert("请设置“是否为关键活动”");
					} else if (frequency == "") {
						window.alert("请设置“时态”");
					} else if (sequence == "") {
						window.alert("请设置“是否按顺序执行”");
					} else if (random == "") {
						window.alert("请设置“是否为随机任务”");
					} else if (scan == "") {
						window.alert("请设置“是否扫码”");
					} else if (0 == value.length) {
						window.alert("请设置“岗位活动项”");
					} else {
						this.disable();
						childWin.showWaitingDialog(this);
						var form = fast.getInstance("checkForm");
						form.submit();
					}
				} else if ("2" == cActnodetype) {
					var actname = fast.getInstance("cActnodeName").getValue();
					var manageattr = fast.getInstance("cManageattr").getValue();
					var pdca = fast.getInstance("cPdca").getValue();
					var keyctrl = fast.getInstance("cIskeyctrl").getValue();
					var frequency = fast.getInstance("cFrequency").getValue();
					var sequence = fast.getInstance("cIssequence").getValue();
					var random = fast.getInstance("cIsrandom").getValue();
					var scan = fast.getInstance("cIsscan").getValue();
					var value = fast.getInstance("detailGrid2").getAllValue();
					if (actname == "") {
						window.alert("请设置“岗位活动名称”");
					} else if (manageattr == "") {
						window.alert("请设置“管理属性”");
					} else if (pdca == "") {
						window.alert("请设置“PDCA属性”");
					} else if (keyctrl == "") {
						window.alert("请设置“是否为关键活动”");
					} else if (frequency == "") {
						window.alert("请设置“时态”");
					} else if (sequence == "") {
						window.alert("请设置“是否按顺序执行”");
					} else if (random == "") {
						window.alert("请设置“是否为随机任务”");
					} else if (scan == "") {
						window.alert("请设置“是否扫码”");
					} else if (0 == value.length) {
						window.alert("请设置“岗位活动项”");
					} else {
						this.disable();
						childWin.showWaitingDialog(this);
						var form = fast.getInstance("checkForm");
						form.submit();
					}
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

		if ('标准岗位活动明细' == title) {
			cModifyType = 0;
			button = new Array(close);
		} else if (('修改岗位活动' == title) || ('添加多媒体' == title)) {
			cModifyType = 1;
			button = new Array(save, close);
		} else if ('添加岗位活动' == title) {
			cModifyType = 2;
			cActnodeId = "";
			button = new Array(save, reset, close);
		} else {
			cModifyType = 0;
			button = new Array(close);
		}

		dialog = fastDev.create('Dialog', {
			width : '1000px',
			height : '500px',
			inside : false,
			showMaxBtn : false,
			title : title,
			allowResize : false,
			src : '../xwzcxt/standardlibrary/actionStandardAdd.html?cModifyType='
				+ cModifyType + '&cActnodetype=' + cActnodetype + '&cActnodeId=' + cActnodeId + '&cPublicId='
				+ cPublicId + '&cPublicName=' + cPublicName + '&cIspublic=1',
			buttons : button
		});
	}
}

function doDelete(data) {
	fastDev.confirm("确认是否删除选择的记录？<br/>注：如果删除选择的标准，则使用这些标准的模板也会被删除！", "信息提示", function(result) {
		if (result) {
			var ids = data.cActnodeId;
			deleteObject(ids);
		}
	});
}

function doBatchDeleteObject() {
	var datagrid = fastDev.getInstance("grid_actnode" + cActnodetype);
	var objects = datagrid.getValue();
	var ids = new Array();
	if (!!objects) {
		for (var i = 0; i < objects.length; i++)
			ids.push(objects[i].cActnodeId);
	}
	if (ids.length == 0) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm('是否删除所选择的' + ids.length + '条记录？<br/>注：如果删除所选择的标准，则使用这些标准的模板也会被删除！', '信息提示', function(result) {
			if (result) {
				deleteObject(ids.join(","));
			}
		});
	}
}

function deleteObject(idStr) {
	fastDev.create("Proxy", {
		action : "actnode_delActNode.action"
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

function getactnodeid() {
	var list = fastDev.getInstance("grid_actnode" + cActnodetype).getValue();
	if (list.length <= 0) {
		fastDev.alert("请选择一条记录进行操作", "信息提示", "warning");
		return;
	}
	window.alert("设置岗位活动 " + list[0].cActnodeName + " 成功");
	fastDev.Ui.Window.parent.getactnodeid(list[0]);
	fastDev.Ui.Window.parent.closeDialog();
}