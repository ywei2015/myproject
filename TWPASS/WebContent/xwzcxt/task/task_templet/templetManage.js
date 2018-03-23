var request = fastDev.Browser.getRequest();
var type = request['type'];
var condition = null;
var childwindow = null;
var cPositionName = null;
var cPositionID = null;

function afterInitRender() {
	//设置所属部门和作业岗位
	fastDev.get("pbo/getPositionIdAction.action", {
		success : function (data) {
			var orgtree = fastDev.getInstance('orgtree');
			var id = data.positionid + '';
			orgtree.setValue(id);
			if (orgtree.getValue()) {
				cPositionName = orgtree.getCurrentTxt();
				cPositionID = id;
				condition = { cPositionID: id };
				fastDev.getInstance('grid_task_templet').refreshData(condition);
			}
		}
	});
}

function onBeforeReady() {
	if (("add" == type) || ("confirm" == type)) {
		this.setOptions({
			initSource : 'templet/getAllTaskTempletAction.action'
		});
	} else if ("search" == type) {
		this.setOptions({
			initSource : 'templet/getAllTaskTempletForSearchAction.action'
		});
		cPositionID = "";
	}
}

function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body - 90);
	if ("add" == type) {
		showResourceModes('rwmbzd');
	} else if ("confirm" == type) {
		showResourceModes('RWXFQR');
	} else if ("search" == type) {
		showResourceModes('RWMBCX');
	}
}

function operLinkRenderer(value) {
	return [ '<a href="javascript:void(0);" style="margin-left:5px;" class="btn" id="openTaskDetails" name="openTaskDetails">' + value + '</a>' ].join('');
}

function operStatusRenderer(value) {
	var id = (value == '启用') ? 'enable' : 'disable';
	return [ '<a href="javascript:void(0);" class="btn" style="display:none;" id="' + id + '" name="' + id + '">' + value + '</a>' ].join('');
}

function operMediaRenderer() {
	return [ '<a href="javascript:void(0);" class="btn" style="display:none;" id="media" name="media">添加多媒体</a>' ].join('');
}

function operRenderer() {
	if ("add" == type) {
		return [ '<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="modify" name="modify">修改</a>',
			'<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="delete" name="delete">删除</a>' ]
			.join('');
	} else if ("confirm" == type) {
		return [ '<a href="javascript:void(0);" class="btn" style="display:none;" id="confirm" name="confirm">评审</a>' ].join('');
	}
}

function onNodeClick(event, id, value) {
	var isLeaf = this.isLeaf(id);
	if (isLeaf) {
		if (-1 < id.indexOf("1-")) {
			cPositionName = null;
			cPositionID = null;
		} else {
			cPositionName = value;
			cPositionID = id;
		}
	} else {
		cPositionName = null;
		cPositionID = null;
	}
	condition = { cPositionID: id };
	fastDev.getInstance('grid_task_templet').refreshData(condition);
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

function doAddTemplet() {
	templetAdd('添加任务模版', null, null);
}

function doOpenTaskDetails(data) {
	templetAdd('标准任务模版明细', data.C_TEMPLET_ID, data.C_ACTNODE_ID);
}

function doMedia(data) {
	templetAdd('添加多媒体', data.C_TEMPLET_ID, data.C_ACTNODE_ID);
}

function doModify(data) {
	templetAdd('修改任务模版', data.C_TEMPLET_ID, data.C_ACTNODE_ID);
}

function doConfirm(data) {
	templetAdd('评审任务模版', data.C_TEMPLET_ID, data.C_ACTNODE_ID);
}

/**
 * 任务模板查看、修改、评审、新建共用接口
 */
function templetAdd(title, cTempletId, cActnodeId) {
	if (cPositionID == null) {
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
				templetCheck(fast, childWin, this);
			}
		};
		var confirm = {
			id : 'confirm',
			text : '评审',
			align : 'center',
			iconCls : 'icon-down',
			onclick : function(event, win, childWin, fast) {
				templetCheck(fast, childWin, this);
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

		if ('标准任务模版明细' == title) {
			cModifyType = 0;
			button = new Array(close);
		} else if (('修改任务模版' == title) || ('添加多媒体' == title)) {
			cModifyType = 1;
			button = new Array(save, close);
		} else if ('评审任务模版' == title) {
			cModifyType = 2;
			button = new Array(confirm, close);
		} else if ('添加任务模版' == title) {
			cModifyType = 3;
			button = new Array(save, reset, close);
		} else {
			cModifyType = 0;
			button = new Array(close);
		}

		if (("add" == type) || ("confirm" == type)) {
			var orgtree = fastDev.getInstance('orgtree');
			var cOrgId = orgtree.getParentid(cPositionID).substring(2);
			var cDepartment = orgtree.getParentid("1-" + cOrgId).substring(2);
			while ("1-1000509" != orgtree.getParentid("1-" + cDepartment)) {
				cDepartment = orgtree.getParentid("1-" + cDepartment).substring(2);
			}
			var cDepartmentName = orgtree.getValByid("1-" + cDepartment);
		}

		childwindow = fastDev.create('Dialog', {
			width : 815,
			height : 510,
			inside : false,
			showMaxBtn : false,
			title : title,
			allowResize : false,
			src : '../xwzcxt/task/task_templet/templetAdd.html?cModifyType='
				+ cModifyType + '&cPositionName=' + cPositionName + '&cPositionID=' + cPositionID + '&cDepartmentName=' + cDepartmentName + '&cDepartment='
				+ cDepartment + '&cTempletId=' + cTempletId + '&cActnodeId=' + cActnodeId + '&cOrgId=' + cOrgId,
			buttons : button
		});
	}
}

/**
 * 任务模板修改、评审、新建提交前做的检查
 */
function templetCheck(fast, childWin, button) {
	var type = fast.getInstance("cActnodetype").getValue();
	var name = fast.getInstance("cTempletName").getValue();
	var aval = fast.getInstance("cActnodeId").getValue();
	var scan = fast.getInstance("cIsscan").getValue();
	var area = fast.getInstance("cExecAreaid").getValue();
	var begin = fast.getInstance("cTaskbeginTrid").getValue();
	var finish = fast.getInstance("cTaskfinishTrid").getValue();
	if (name == "") {
		window.alert("请设置“模版名称”");
		return;
	} else if (aval == "") {
		window.alert("请设置“岗位活动”");
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
	button.disable();
	childWin.showWaitingDialog(button);
	var form = fast.getInstance("checkForm");
	form.submit();
}

function doCopyAction() {
	fastDev.confirm("是否需要使用复制功能？<br/>注：<br/>如果目标岗位存在与被复制的模板使用的标准相同的标准，则新模板使用该标准<br/>如果目标岗位没有与被复制的模板使用的标准相同的标准，则该标准也会被复制", "信息提示", function(result) {
		if (result) {
			var datagrid = fastDev.getInstance("grid_task_templet");
			var objects = datagrid.getValue();
			var tids = "";
			var aids = "";
			if (!!objects) {
				for (var i = 0; i < objects.length; i++) {
					if (i == 0) {
						tids = objects[i].C_TEMPLET_ID;
						aids = objects[i].C_ACTNODE_ID;
					} else {
						tids = tids + "," + objects[i].C_TEMPLET_ID;
						aids = aids + "," + objects[i].C_ACTNODE_ID;
					}
				}
			}
		
			if ("" == tids) {
				fastDev.alert("请选择需要复制的模板！", "信息提示", "warning");
			} else {
				var orgtree = fastDev.getInstance('orgtree');
				var idArray = [];
				idArray.push(cPositionID);
				while ("1-1000509" != orgtree.getParentid(idArray[idArray.length - 1])) {
					idArray.push(orgtree.getParentid(idArray[idArray.length - 1]));
				}
				idArray.push("1-1000509");
		
				childwindow = fastDev.create('Dialog', {
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
								fastDev.post("templet/addTempletByCopyAction.action?pids=" + 
									pids + "&tids=" + tids + "&aids=" + aids, {
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

function doEnable(data) {
	fastDev.confirm("确认是否禁用该任务模板？", "信息提示", function(result) {
		if (result) {
			var proxy = fastDev.create('Proxy', {
				action : 'templet/setSTempletVaildByIDAction.action'
			});
			proxy.save({
				cTempletId : data.C_TEMPLET_ID,
				cIsvaild : '0'
			}, function(result) {
				fastDev.alert(result.msg, '信息提示', result.status, function() {
					if (result.status == 'ok') {
						refreshDatagrid();
					}
				});
			});
		}
	});
}

function doDisable(data) {
	fastDev.confirm("确认是否启用该任务模板？", "信息提示", function(result) {
		if (result) {
			var proxy = fastDev.create('Proxy', {
				action : 'templet/setSTempletVaildByIDAction.action'
			});
			proxy.save({
				cTempletId : data.C_TEMPLET_ID,
				cIsvaild : '1'
			}, function(result) {
				fastDev.alert(result.msg, '信息提示', result.status, function() {
					if (result.status == 'ok') {
						refreshDatagrid();
					}
				});
			});
		}
	});
}

function doDelete(data) {
	fastDev.confirm("确认是否删除选择的记录？", "信息提示", function(result) {
		if (result) {
			var ids = data.C_TEMPLET_ID;
			deleteTemplet(ids);
		}
	});
}

function batchDeleteObject() {
	var datagrid = fastDev.getInstance('grid_task_templet');
	var items = datagrid.getValue();
	if (items.length <= 0) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm("确认是否删除选择的" + items.length + "条记录？", "信息提示", function(result) {
			if (result) {
				var ids = [];
				for (var i = 0; i < items.length; i++) {
					ids.push(items[i].C_TEMPLET_ID);
				}
				deleteTemplet(ids.join(","));
			}
		});
	}
}

function deleteTemplet(id) {
	var proxy = fastDev.create('Proxy', {
		action : 'templet/deleteTempletByIdAction.action'
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
	fastDev.getInstance('grid_task_templet').refreshData(o);
}

function doSearch() {
	var form = fastDev.getInstance("checkform");
	condition = form.getItems();
	if (("add" == type) || ("confirm" == type)) {
		condition["cPositionID"] = cPositionID;
	}
	fastDev.getInstance('grid_task_templet').refreshData(condition);
}

function doReset() {
	fastDev.getInstance('checkform').cleanData();
	condition = null;
}

function onSelectArea() {
	fastDev.confirm("是否需要修改当前区域？", "信息提示", function(result) {
		if (result) {
			doSelectArea();
		}
	});
}

function doSelectArea() {
	var cAreaid = fastDev.getInstance("cExecAreaid").getValue();

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
		} ]
	});
}

function setArea(cAreaid, cAreaName) {
	fastDev.getInstance("cExecAreaid").setValue(cAreaid);
	fastDev.getInstance("cExecAreaName").setValue(cAreaName);
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
	var id = name.substring(0, name.indexOf("Name")) + "id" + name.substring(name.indexOf("Name") + 4);
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
				var cPidName = cPosition.getCurrentTxt();
				if (("" == cPid) || (0 > cPid.indexOf("U-"))) {
					window.alert("请选择岗位！");
				} else {
					cPid = cPosition.getParentid(cPid);
					setPosition(name, cPid, cPosition.getValByid(cPid) + "-" + cPidName);
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
	var id = name.substring(0, name.indexOf("Name")) + "id" + name.substring(name.indexOf("Name") + 4);
	var cPid = fastDev.getInstance(id).getValue();

	fastDev.create('Window', {
		width : 300,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : "选择岗位",
		allowResize : false,
		src : '../xwzcxt/task/task_templet/templetSelectPositionForSearch.html?orgtype=6&module=RWMBCX&cPid=' + cPid,
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
	var id = name.substring(0, name.indexOf("Name")) + "id" + name.substring(name.indexOf("Name") + 4);
	fastDev.getInstance(id).setValue(cPid);
	fastDev.getInstance(name).setValue(cPidName);
}