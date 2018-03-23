var condition;

function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body - 110);
}

var submit = function() {
	var form = fastDev.getInstance("pointSearchForm");
	condition = form.getItems();
	refreshData();
};

function refreshData() {
	fastDev.getInstance('stdfilegrid').refreshData(condition);
}

var reset = function() {
	var form = fastDev.getInstance("pointSearchForm");
	form.cleanData();
};

function createWindow(o) {
	var config = fastDev.apply({
		width : "500",
		height : "250",
		showMaxBtn : false,
		allowResize : false
	}, o || {});
	(config.buttons = config.buttons || []).push({
		text : "关闭",
		iconCls : "icon-close",
		onclick : function(e, win) {
			win.close();
		}
	});
	dialog = fastDev.create('Window', config);
}

function addPoint() {
	createWindow({
		src : "PointAdd.html",
		title : "新增",
		width : "500",
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(e, win, cwin, fd) {
				var form = fd.getInstance("pointAddForm");
				form.submit();
			}
		}, {
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(e, win, cwin, fd) {
				var form = fd.getInstance("pointAddForm");
				form.cleanData();
			}
		} ]
	});
}

function batchDeleteObject() {
	var datagrid = fastDev.getInstance("stdfilegrid");
	var objects = datagrid.getValue();
	var ids = new Array();
	if (!!objects) {
		for ( var i = 0; i < objects.length; i++)
			ids.push(objects[i].c_sfile_id);
	}
	if (ids.length == 0)
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	else {
		fastDev.confirm('是否删除所选择的' + ids.length + '条记录？', '信息提示', function(
				result) {
			if (result) {
				deleteObject(ids.join(","));
			}
		});
	}
}

function deleteObject(idStr) {
	fastDev.create("Proxy", {
		action : "standardlibrary_delManagementProcess.action"
	}).save({
		c_sfile_id : idStr
	}, function(data) {
		fastDev.alert(data.msg, "信息", data.status, function() {
			if (data.status == 'ok') {
				refreshData();
			}
		});
	});
}

function bznrhref() {
	var width = fastDev(this).width();
	return [ '<div style="width:'
			+ width
			+ 'px;"><a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="cknr">查看内容</a>' ]
			.join('');
}

function flhref() {
	var width = fastDev(this).width();
	return [ '<div style="width:'
			+ width
			+ 'px;"><a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="ckfl">查看附录</a>' ]
			.join('');
}

function operRenderer() {
	var width = fastDev(this).width();
	return [
			'<div style="width:'
					+ width
					+ 'px;"><a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="modify">修改</a>',
			'<a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="delete">删除</a>' ]
			.join('');
}

function onRowClick(event, rowindex, data) {
	var target = event.target.id;
	if (target) {
		switch (target) {
		case 'modify':
			window.open("standardFileUpdate.html?sfileid=" + data.c_sfile_id, "_blank");
			/*createWindow({
				src : "standardFileUpdate.html?id=" + data.identipointID
						+ "&realid=" + data.realIdentipointID,
				title : "修改标准文件",
				width : "1000",
				height : "650",
				buttons : [ {
					text : '保存',
					iconCls : 'icon-save',
					onclick : function(e, win, cwin, fd) {
						var form = fd.getInstance("pointModifyForm");
						form.submit();
					}
				}, {
					text : '重置',
					iconCls : 'icon-reset',
					onclick : function(e, win, cwin, fd) {
						var form = fd.getInstance("pointModifyForm");
						form.resetData();
					}
				} ]
			});*/
			break;
		case 'cknr':
			createWindow({
				src : "getStandardcontent.html?c_sfile_id=" + data.c_sfile_id,
				title : "文件内容",
				width : "600",
				height : "500"
			});
			break;
		case 'ckfl':
			createWindow({
				src : "getAppendix.html?c_sfile_id=" + data.c_sfile_id,
				title : "附录",
				width : "500",
				height : "250"
			});
			break;
		case 'delete':
			fastDev.confirm(('是否删除“' + data.c_sfile_name + '”？'), "信息提示",
					function(result) {
						if (result) {
							deleteObject(data.c_sfile_id);
						}
					});
			break;
		case 'viewInfo':
			doShow(data);
		}
	}
}

function appNameRenderer(value) {
	return '<a href="javascript:void(0);" class="btn" id="viewInfo">' + value
			+ '</a>';
}

/**
 * 查看识别点信息
 * 
 * @param data
 */
function doShow(data) {
	createWindow({
		src : "showPoint.html?realIdentipointID=" + data.realIdentipointID
				+ "&identipointID=" + data.identipointID + "&identipointName="
				+ data.identipointName + "&roadName=" + data.roadName
				+ "&directionName=" + data.directionName,
		title : "查看识别点信息",
		height : 283,
		width : 559
	});
}

/**
 * 流程节点树的操作
 */

var currentActionId, currentName;
var op = null;
var currentActionId, parentId, parentText;
var appDialog;

function getCurrentActionId() {
	var tree = fastDev.getInstance('actiontree');
	for ( var i = 0; i < tree.dataset.records.length; i++) {
		if (tree.dataset.records[i].id == currentActionId) {
			currentActionId = tree.dataset.records[i].id;
		}
	}
}

function createWindow(o) {
	var config = fastDev.apply({
		width : "640",
		height : "200",
		allowResize : false,
		showMaxBtn : false
	}, o || {});
	(config.buttons = config.buttons || []).push({
		text : "关闭",
		iconCls : "icon-close",
		onclick : function(e, win) {
			win.close();
		}
	});
	appDialog = fastDev.create('Window', config);
}

function openActionDetailsPage(event) {
	var src = "sortAdd.html";
	var title = "新增标准文件类别";
	if (event == "modify") {
		title = "修改标准文件类别";
	}

	createWindow({
		src : src,
		title : title,
		width : "400",
		height : "130",
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				var input=childWin.document.getElementById("sdActionPojo.c_action_fname");
				var selectTree=childWin.document.getElementById("sdActionPojo.c_action_pid");
				var inputValue=input.value; //类别名称
				var selectValue=selectTree.childNodes[1].value; //父节点的ID
				fast.post("standardlibrary_addStandardFileTreeNode.action",{
					success : function(data){
						win.close();
						fast.tips("添加成功");
						refreshTree();
					},
					data : {
						"name": inputValue,
						"pid": selectValue
					}
				});

				/*
				 * var form=fd.getInstance("addActionForm"); var data =
				 * appDialog.getAllGridValue(); console.info(data);
				 * form.setOptions({"otherSubmitData" :
				 * fastDev.Util.JsonUtil.parseJson(data)}); form.submit();
				 */
			}
		} ]
	});
}

function refreshTree() {
	var tree = fastDev.getInstance('actiontree');
	tree.reLoad();
}

function doAddAction() {
	if (!currentActionId || currentActionId == -1) {
		currentActionId = 1;
	}
	op = 'add';
	openActionDetailsPage(op);
}

function doModifyAction() {
	if (!currentActionId || currentActionId == -1) {
		fastDev.alert('请选择需要修改的区域节点', '信息提示', 'warning');
	} else {
		var src = "sortAdd.html";
		var tree = fastDev.getInstance('actiontree');
		var currentText=tree.getCurrentTxt();
		var currentId=tree.getCurrentId();
		var parentId=tree.getParentid(currentId);
		var parentText=tree.getValByid(parentId);
		createWindow({
			src : encodeURI(src+"?name="+currentText+"&pName="+parentText),
			title : "修改标准文件类别",
			width : "400",
			height : "130",
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				onclick : function(event, win, childWin, fast) {
					var input=childWin.document.getElementById("sdActionPojo.c_action_fname");
					var selectTree=childWin.document.getElementById("sdActionPojo.c_action_pid");
					var selectValue=selectTree.childNodes[1].value; //父节点的ID
					fast.post("standardlibrary_updateStandardFileTreeNode.action",{
						success : function(data){
							win.close();
							fast.tips("修改成功");
							refreshTree();
						},
						data : {
							"name": input.value,
							"pid": selectValue,
							"id": currentId
						}
					});
				}
			} ]
		});
	}
}

function doDeleteAction() {
	if (!currentActionId || currentActionId == -1) {
		fastDev.alert('请选择需要删除的区域节点', '信息提示', 'warning');
	} 
	else {
		var alertmsg = "是否删除所选记录？";
		var ids = [];
		var actiontree = fastDev.getInstance('actiontree');
		if (actiontree.isLeaf(currentActionId) == false) {
			alertmsg = "该节点有下属子节点，请确认是否删除？";
			var nodes = actiontree.findNodesByPid(currentActionId);
			for ( var i = 0; i < nodes.length; i++) {
				ids.push(nodes[i].id);
			}
		}
		ids.push(currentActionId);
		fastDev.post("standardlibrary_delStandardFileTreeNode.action",{
			success : function(data){
				fastDev.tips("删除成功");
				refreshTree();
			},
			data : {
				"ids": ids.join(",")
			}
		});							
	}
}

function onActionTreeNodeClick(event, id, value) {
	currentActionId = id;
	getCurrentActionId();
	parentId = this.getParentid(id);
	parentText = this.getValByid(parentId);
	currentName = this.getValByid(id);
	// refreshUppositiontree();
	// fastDev.getInstance("position.posiName").getGlobal().box.prop("value",
	// parentText);
	op = 'view';
	refreshDataGrid();
}

function refreshDataGrid() {
	//fastDev.getInstance('stdfilegrid').initSource="stdfile_getStdFilesPageBySortId.action";
	var o = condition || {};
	/*o['standardfile.c_action_id'] = currentActionId;
	fastDev.getInstance('stdfilegrid').refreshData(o);*/
	//alert(currentActionId);
	//o['c_sort_id'] = currentActionId;
	o["standardfile.c_sort_id"] = currentActionId;
	fastDev.getInstance('stdfilegrid').refreshData(o);
	//console.info(o);
}

function onquery_refreshdatagrid()
{
	//fastDev.getInstance('stdfilegrid').initSource="stdfile_getStdFilesPageByParams.action";
	var form = fastDev.getInstance("pointSearchForm");
	condition = form.getItems();
	//var o = condition || {};
	/*var o = {};
	o['c_sort_id'] = condition["standardfile.c_sort_id"];
	o['c_sfile_name'] = condition["standardfile.c_sfile_name"];
	o['c_releaseunit'] = condition["standardfile.c_releaseunit"];
	o['c_fw_qcbm'] = condition["standardfile.c_fw_qcbm"];
	o['c_fw_gkbm'] = condition["standardfile.c_fw_gkbm"];
	o['c_releasetime'] = condition["standardfile.c_releasetime"];
	o['c_impletime'] = condition["standardfile.c_impletime"];*/ 
	//alert(condition["standardfile.c_sfile_name"]);
	fastDev.getInstance('stdfilegrid').refreshData(condition);
	console.info(condition);
}

function openExcelWindow()
{
	window.open('../mytask/testExcel.html', '_blank');
}

