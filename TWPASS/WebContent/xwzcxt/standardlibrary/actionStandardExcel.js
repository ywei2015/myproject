var request = fastDev.Browser.getRequest();
var type = request['type'];

var cActnodetype = 1;
var cActnodeId = "";
var cPositionId = "";
var cPositionName = "";
var dialog = null;
var condition = null;

function afterInitRender() {
	//设置所属部门和作业岗位
	fastDev.get("pbo/getPositionIdAction.action", {
		success : function (data) {
			var orgtree = fastDev.getInstance('orgtree');
			var id = data.positionid + '';
			orgtree.setValue(id);
			if (orgtree.getValue()) {
				cPositionId = id;
				cPositionName = orgtree.getCurrentTxt();
				refreshDatagrid();
			}
		}
	});
}

function exportOperation() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "actnodeSearchForm",
		text : "导出中..."
	});
	var formData = fastDev.getInstance('actnodeSearchForm').getItems();
	fastDev.create("Proxy", {
		action : 'tsdactnode/exportOperation.action'
	}).save(formData, function(data) {
		fastDev("#dc").prop("src", data.url);
		loadingWindow.close();
	});
}

function exportManage() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "actnodeSearchForm",
		text : "导出中..."
	});
	var formData = fastDev.getInstance('actnodeSearchForm').getItems();
	fastDev.create("Proxy", {
		action : 'tsdactnode/exportManage.action'
	}).save(formData, function(data) {
		fastDev("#dc").prop("src", data.url);
		loadingWindow.close();
	});
}

function operLinkRenderer(value) {
	return [ '<a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openActionDetails" name="openActionDetails">' + value + '</a>' ].join('');
}

function operMediaRenderer() {
	return [ '<a href="javascript:void(0);" class="btn" style="display:none;" id="media" name="media">添加多媒体</a>' ].join('');
}

function operRenderer() {
	if ("add" == type) {
		return [ '<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="modify" name="modify">修改</a>',
			'<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="delete" name="delete">删除</a>' ]
			.join('');
	} else if ("random" == type) {
		return [ '<a href="javascript:void(0);" class="btn" style="display:none;" id="create" name="create">发起临时任务</a>' ].join('');
	}
}

function closeDialog() {
	if (dialog) {
		dialog.close();
		dialog = null;
	}
}

function refreshDatagrid() {
	var o = condition || {};

	if ("add" == type) {
		o['actnode.c_position_id'] = cPositionId;
	}
	o['actnode.c_actnodetype'] = cActnodetype;

	fastDev.getInstance('grid_actnode' + cActnodetype).addInitReqParam(o);
	fastDev.getInstance('grid_actnode' + cActnodetype).refreshData();
}

function onNodeClick(event, id, value) {
	var isLeaf = this.isLeaf(id);
	if (isLeaf) {
		if (-1 < id.indexOf("1-")) {
			cPositionName = "";
			cPositionId = "";
		} else {
			cPositionName = value;
			cPositionId = id;
		}
	} else {
		cPositionName = "";
		cPositionId = "";
	}
	refreshDatagrid();
}

function doSearch() {
	condition = fastDev.getInstance('actnodeSearchForm').getItems();
	refreshDatagrid();
}

function doReset() {
	fastDev.getInstance('actnodeSearchForm').cleanData();
}

function onBeforeReady() {
	if ("add" == type) {
		this.setOptions({
			initSource : 'tsdactnode/getAllActNodesByConditionsAction.action'
		});
	} else if ("search" == type) {
		this.setOptions({
			initSource : 'tsdactnode/getAllActNodesByConditionsForSearchAction.action'
		});
		cPositionId = null;
	} else if ("random" == type) {
		this.setOptions({
			initSource : 'tsdactnode/getAllActNodesByConditionsForSearchAction.action?actnode.c_israndom=1'
		});
		cPositionId = null;
	}
}

function onAfterInitRender() {
	var body = fastDev(window).height();
	if ("add" == type) {
		showResourceModes('GW5W2H');
		this.setHeight(body - 165);
	} else if ("search" == type) {
		showResourceModes('GW5W2HCX');
		this.setHeight(body - 160);
	} else if ("random" == type) {
		showResourceModes('LSRWXF');
		this.setHeight(body - 160);
	}
}

function onBeforeDetailGridReady() {
	this.setOptions({
		initSource : "tsdactnode/getActNodeItemByActNodeIDAction.action?cActnodeId=" + cActnodeId
	});
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

function importAction() {
	if (cPositionId == "") {
		fastDev.alert('请先选择岗位节点！','信息提示','warning');
		return false;
	}
	openImportPage();
}

function openImportPage() {
	var orgtree = fastDev.getInstance('orgtree');
	var cOrgId = orgtree.getParentid(cPositionId).substring(2);
	var cDepartment = orgtree.getParentid("1-" + cOrgId).substring(2);
	var cDepartmentName = orgtree.getValByid("1-" + cDepartment) + "-" + orgtree.getValByid("1-" + cOrgId);
	while ("1-1000509" != orgtree.getParentid("1-" + cDepartment)) {
		cDepartment = orgtree.getParentid("1-" + cDepartment).substring(2);
		cDepartmentName = orgtree.getValByid("1-" + cDepartment) + "-" + cDepartmentName;
	}

	dialog = fastDev.create('Dialog', {
		width : 500,
		height : 400,
		inside : false,
		showMaxBtn : false,
		title : '5W2H行为标准批量导入',
		allowResize : false,
		src : '../xwzcxt/standardlibrary/actionStandardImport.html?cPositionId='
			+ cPositionId + '&cPositionName=' + cPositionName + '&cDepartment='
			+ cDepartment +'&cDepartmentName=' + cDepartmentName + '&cIspublic=0',
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
 * cActnodetype 2为管理类
 */
function actionAdd(title, cActnodetype) {
	if (cPositionId == "") {
		fastDev.alert("请选择岗位！", "信息提示", "warning");
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
					var areaname = fast.getInstance("cAreaName").getValue();
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
					} else if (areaname == "") {
						window.alert("请设置“作业区域”");
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
					var areaname = fast.getInstance("cAreaName").getValue();
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
					} else if (areaname == "") {
						window.alert("请设置“工作区域”");
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

		if ("add" == type) {
			var orgtree = fastDev.getInstance('orgtree');
			var cOrgId = orgtree.getParentid(cPositionId).substring(2);
			var cDepartment = orgtree.getParentid("1-" + cOrgId).substring(2);
			while ("1-1000509" != orgtree.getParentid("1-" + cDepartment)) {
				cDepartment = orgtree.getParentid("1-" + cDepartment).substring(2);
			}
		}
		console.info(cPositionName+'|'+cPositionId+'|'+cDepartment+'|'+cActnodeId+'|'+cOrgId);
		dialog = fastDev.create('Dialog', {
			width : '1000px',
			height : '500px',
			inside : false,
			showMaxBtn : false,
			title : title,
			allowResize : false,
			src : '../xwzcxt/standardlibrary/actionStandardAdd.html?cModifyType='
				+ cModifyType + '&cActnodetype=' + cActnodetype + '&cPositionName=' + cPositionName + '&cPositionId='
				+ cPositionId + '&cDepartment=' + cDepartment + '&cActnodeId=' + cActnodeId + '&cOrgId=' + cOrgId
				+ '&cIspublic=0',
				
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

function batchDeleteObject() {
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

function doCopyAction() {
	fastDev.confirm("是否需要使用复制功能？", "信息提示", function(result) {
		if (result) {
			var datagrid = fastDev.getInstance("grid_actnode" + cActnodetype);
			var objects = datagrid.getValue();
			var ids = "";
			if (!!objects) {
				for (var i = 0; i < objects.length; i++) {
					if (i == 0) {
						ids = objects[i].cActnodeId;
					} else {
						ids = ids + "," + objects[i].cActnodeId;
					}
				}
			}

			if ("" == ids) {
				fastDev.alert("请选择需要复制的标准！", "信息提示", "warning");
			} else {
				var orgtree = fastDev.getInstance('orgtree');
				var idArray = [];
				idArray.push(cPositionId);
				while ("1-1000509" != orgtree.getParentid(idArray[idArray.length - 1])) {
					idArray.push(orgtree.getParentid(idArray[idArray.length - 1]));
				}
				idArray.push("1-1000509");

				dialog = fastDev.create('Dialog', {
					width : 300,
					height : 500,
					inside : false,
					showMaxBtn : false,
					title : '选择岗位',
					allowResize : false,
					src : '../xwzcxt/task/task_templet/templetSelectPositionForCopy.html?',
					buttons : [ {
						id : 'save',
						text : '保存',
						align : 'center',
						iconCls : 'icon-save',
						onclick : function(event, win, childWin, fast) {
							var pids = fast.getInstance('cPosition').getChkedIds("onlyLeafValue");
							if (pids) {
								this.disable();
								childWin.showWaitingDialog();
								fastDev.post("struts-actnode/actnode_addActNodeByCopy.action?pids=" + 
									pids + "&ids=" + ids, {
									success : function(result) {
										fastDev.alert(result.msg, '信息提示', result.status, function() {
											if (result.status == 'ok') {
												closeDialog();
											}
										});
									}
								});
							} else {
								fastDev.alert("请选择岗位！", "信息提示", "warning");
							}
						}
					}, {
						id : 'close',
						text : '关闭',
						align : 'center',
						iconCls : 'icon-close',
						onclick : function(event, win) {
							win.close();
						}
					} ]
				}).setData("idArray", idArray);
			}
		}
	});
}

function doCreate(data) {
	randomTaskAdd(data.cActnodeId);
}

function doCreateAction() {
	var list = fastDev.getInstance('grid_actnode' + cActnodetype).getValue();
	if (list.length <= 0) {
		fastDev.alert("请选择一条记录进行操作", "信息提示", "warning");
		return;
	}
	randomTaskAdd(list[0].cActnodeId);
}

function randomTaskAdd(cActnodeId) {
	dialog = fastDev.create('Dialog', {
		width : 815,
		height : 490,
		inside : false,
		showMaxBtn : false,
		title : '发起临时任务',
		allowResize : false,
		src : '../xwzcxt/standardlibrary/actionStandardRandomTaskAdd.html?cActnodeId=' + cActnodeId,
		buttons : [ {
			id : 'save',
			text : '下发',
			align : 'center',
			iconCls : 'icon-down',
			onclick : function(event, win, childWin, fast) {
				var type = fast.getInstance("cActnodetype").getValue();
				var name = fast.getInstance("cTaskName").getValue();
				var exec = fast.getInstance("cPidExec").getValue();
				var scan = fast.getInstance("cIsscan").getValue();
				var area = fast.getInstance("cExecAreaid").getValue();
				var begin = fast.getInstance("cTaskbeginTrid").getValue();
				var finish = fast.getInstance("cTaskfinishTrid").getValue();
				if (name == "") {
					window.alert("请设置“临时任务名称”");
					return;
				} else if (exec == "") {
					if ("1" == type) {
						window.alert("请设置“执行岗位”");
					} else if ("2" == type) {
						window.alert("请设置“管理岗位”");
					}
					return;
				} else if (scan == "") {
					window.alert("请设置“是否扫码”");
					return;
				} else if (area == "") {
					if ("1" == type) {
						window.alert("请设置“作业区域”");
					} else if ("2" == type) {
						window.alert("请设置“工作区域”");
					}
					return;
				} else if (begin == "") {
					if ("1" == type) {
						window.alert("请设置“执行开始时间”");
					} else if ("2" == type) {
						window.alert("请设置“完工开始时间”");
					}
					return;
				} else if (finish == "") {
					if ("1" == type) {
						window.alert("请设置“执行完成时间”");
					} else if ("2" == type) {
						window.alert("请设置“完工完成时间”");
					}
					return;
				} else if (scan == 1) {
					var objects = fast.getInstance("cJobObjects").getValue();
					if (objects == "") {
						window.alert("请设置“扫码对象”");
						return;
					}
				}
				this.disable();
				childWin.showWaitingDialog(this);
				var form = fast.getInstance("checkForm");
				form.submit();
			}
		}, {
			id : 'reset',
			text : '重置',
			align : 'center',
			iconCls : 'icon-reset',
			onclick : function(e, win, cwin) {
				cwin.doReset();
			}
		}, {
			id : 'close',
			text : '关闭',
			align : 'center',
			iconCls : 'icon-close',
			onclick : function(event, win) {
				win.close();
			}
		} ]
	});
}

function onTabClick(Object, name) {
	if ("作业类" == name) {
		cActnodetype = 1;
		document.getElementById("c_area_name_title").innerHTML = "作业区域：";
		document.getElementById("c_position_name_review_title").innerHTML = "评价岗位：";
		document.getElementById("c_manageattr_title").style.display = "none";
		document.getElementById("c_manageattr").style.display = "none";
		if ("add" == type) {
			document.getElementById("actnodeSearchFormButton").colSpan = "4";
		} else if ("search" == type) {
			document.getElementById("actnodeSearchFormButton").colSpan = "8";
		}
	} else {
		cActnodetype = 2;
		document.getElementById("c_area_name_title").innerHTML = "工作区域：";
		document.getElementById("c_position_name_review_title").innerHTML = "监督岗位：";
		document.getElementById("c_manageattr_title").style.display = "table-cell";
		document.getElementById("c_manageattr").style.display = "table-cell";
		if ("add" == type) {
			document.getElementById("actnodeSearchFormButton").colSpan = "2";
		} else if ("search" == type) {
			document.getElementById("actnodeSearchFormButton").colSpan = "6";
		}
	}
	if ("" != cPositionId) {
		refreshDatagrid();
	}
}

function onSelectPosition(e) {
	var element = e.srcElement || e.target;
	fastDev.confirm("是否需要修改当前岗位？", "信息提示", function(result) {
		if (result) {
			doSelectPosition(element.id);
		}
	});
}

function doSelectPosition(name) {
	var id = name.substring(0, name.indexOf("name")) + "id" + name.substring(name.indexOf("name") + 4);
	var cPid = fastDev.getInstance(id).getValue();

	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "选择岗位",
		allowResize : false,
		src : '../xwzcxt/task/task_templet/templetSelectPosition.html?cPid=' + cPid,
		buttons : [ {
			id : 'ok',
			text : '确定',
			align : 'center',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				var cPosition = fast.getInstance("cPosition");
				var cPid = cPosition.getCurrentId();
				if (("" == cPid) || (0 > cPid.indexOf("U-"))) {
					window.alert("请选择岗位！");
				} else {
					cPid = cPosition.getParentid(cPid);
					setPosition(name, cPid, cPosition.getValByid(cPid));
					win.close();
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

function onSelectPositionForSearch(e) {
	var element = e.srcElement || e.target;
	fastDev.confirm("是否需要修改当前岗位？", "信息提示", function(result) {
		if (result) {
			doSelectPositionForSearch(element.id);
		}
	});
}

function doSelectPositionForSearch(name) {
	var id = name.substring(0, name.indexOf("name")) + "id" + name.substring(name.indexOf("name") + 4);
	var cPid = fastDev.getInstance(id).getValue();

	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "选择岗位",
		allowResize : false,
		src : '../xwzcxt/task/task_templet/templetSelectPositionForSearch.html?orgtype=6&module=GW5W2HCX&cPid=' + cPid,
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
					window.alert("请选择岗位！");
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
	var id = name.substring(0, name.indexOf("name")) + "id" + name.substring(name.indexOf("name") + 4);
	fastDev.getInstance(id).setValue(cPid);
	fastDev.getInstance(name).setValue(cPidName);
}

function onSelectArea() {
	fastDev.confirm("是否需要修改当前区域？", "信息提示", function(result) {
		if (result) {
			doSelectArea();
		}
	});
}

function doSelectArea() {
	var cAreaid = fastDev.getInstance("actnode.c_area_id").getValue();

	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "选择区域",
		allowResize : false,
		src : '../xwzcxt/task/task_templet/templetSelectArea.html?cAreaid=' + cAreaid,
		buttons : [ {
			id : 'ok',
			text : '确定',
			align : 'center',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				var cArea = fast.getInstance("cArea");
				var cAreaid = cArea.getCurrentId();
				if ("" == cAreaid) {
					window.alert("请选择区域！");
				} else {
					var cAreaName = cArea.getCurrentTxt();
					if ("1000100000001" != cAreaid) {
						var id = cArea.getParentid(cAreaid);
						while ("1000100000001" != id) {
							cAreaName = cArea.getValByid(id) + cAreaName;
							id = cArea.getParentid(id);
						}
					}
					setArea(cAreaid, cAreaName);
					win.close();
				}
			}
		}, {
			id : 'clear',
			text : '清空',
			align : 'center',
			iconCls : 'icon-reset',
			onclick : function(event, win) {
				setArea("", "");
				win.close();
			}
		} ]
	});
}

function setArea(cAreaid, cAreaName) {
	fastDev.getInstance("actnode.c_area_id").setValue(cAreaid);
	fastDev.getInstance("actnode.c_area_name").setValue(cAreaName);
}