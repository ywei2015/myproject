var condition;

function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body-110);
	
}

var submit = function() {
	var form = fastDev.getInstance("pointSearchForm");
	condition = form.getItems();
	refreshData();
};

function refreshData() {
	fastDev.getInstance('actiongrid').refreshData(condition);
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
		onclick : function(e,win) {
			win.close();
		}
	});		
	dialog = fastDev.create('Window', config);
}

function addPoint() {
	createWindow({
		src : "filepointadd.html",
		title : "新增",
		width : "500",
		height : "320",
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin, fd) {
				var form=fd.getInstance("pointAddForm");
				form.submit();		
			}
		},{
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(e,win,cwin, fd) {
				var form=fd.getInstance("pointAddForm");
				form.cleanData();
			}
		}]		
	});
}

function batchDeleteObject() {
	var datagrid = fastDev.getInstance("actiongrid");
	var objects = datagrid.getValue();
	var ids = new Array();
	if (!!objects) {
		for ( var i = 0; i < objects.length; i++)
			ids.push(objects[i].c_sfile_id);
	}
	if(ids.length==0)
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	else{
		fastDev.confirm('是否删除所选择的' + ids.length + '条记录？', '信息提示', function(result) {
			if(result) {
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
	},function(data) {
		fastDev.alert(data.msg, "信息", data.status, function() {
			if(data.status == 'ok') {
				refreshData();
			}
		});
	});
}

function bznrhref() {
	var width = fastDev(this).width();
	return [
			'<div style="width:'
					+ width
					+ 'px;"><a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="cknr">查看内容</a>']
			.join('');
}

function flhref() {
	var width = fastDev(this).width();
	return [
			'<div style="width:'
					+ width
					+ 'px;"><a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="ckfl">查看附录</a>']
			.join('');
}

function operRenderer() {
	var width = fastDev(this).width();
	return [
			'<div style="width:'
					+ width
					+ 'px;"><a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="modify">修改</a>',
			'<a href="javascript:void(0);" class="btn" style="margin-left:5px;" id="delete">删除</a>']
			.join('');
}

function onRowClick(event, rowindex, data) {
	var target = event.target.id;
	if (target) {
		switch (target) {
		case 'modify':
			createWindow({
				src : "filepointadd.html?id=" + data.identipointID+"&realid="+data.realIdentipointID,
				title : "修改收费站信息",
				width : "500",
				height : "320",
				buttons : [{
					text : '保存',
					iconCls : 'icon-save',
					onclick : function(e,win,cwin, fd) {
						var form=fd.getInstance("pointModifyForm");
						form.submit();				
					}
				},{
					text : '重置',
					iconCls : 'icon-reset',
					onclick : function(e,win,cwin, fd) {
						var form=fd.getInstance("pointModifyForm");
						form.resetData();
					}
				}]					
			});
			break;
		case 'cknr':
			createWindow({
				src : "getStdContentByjd.html?c_id=" + data.c_id,
				title : "标准章节内容",
				width : "800",
				height : "320"	
							
			});
			break;
		case 'ckfl':
			createWindow({
				src : "getStdAppendixByjd.html?c_id=" + data.c_id,
				title : "附录列表",
				width : "500",
				height : "320"	
							
			});
			break;
		case 'delete':
			fastDev.confirm(('是否删除“' + data.c_sfile_name + '”？'), "信息提示", function(result){
				if(result){
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
	return '<a href="javascript:void(0);" class="btn" id="viewInfo">' + value + '</a>';
}

/**
 * 查看识别点信息
 * @param data
 */
function doShow(data) {
	createWindow({
		src : "showPoint.html?realIdentipointID="+data.realIdentipointID + "&identipointID=" + data.identipointID + "&identipointName=" + data.identipointName
		+ "&roadName=" + data.roadName + "&directionName=" + data.directionName,
		title : "查看识别点信息",
		height : 283,
		width:559
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
		onclick : function(e,win) {
			win.close();
		}
	});		
	appDialog = fastDev.create('Window', config);
}

function openActionDetailsPage(event){
	var src = "actionDetails.html?event="+event+"&id="+currentActionId;
	var title = "新增流程节点";
	if(event == "modify"){
		title = "修改流程节点";
	}
		
	createWindow({
		src : src,
		title : title,
		width : "625",
		height: "390",
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				childWin.doSave();
				/*var form=fd.getInstance("addActionForm");
				var data = appDialog.getAllGridValue();
				console.info(data);
				form.setOptions({"otherSubmitData" : fastDev.Util.JsonUtil.parseJson(data)});
				form.submit();	*/	
			}
		}]		
	});
}

function refreshTree(){
	var tree = fastDev.getInstance('actiontree');
	tree.reLoad();
}

function doAddAction(){
	op = 'add';
	openActionDetailsPage(op);
}

function doModifyAction(){
	if (!currentActionId || currentActionId == -1) {
		fastDev.alert('请选择需要修改的区域节点', '信息提示', 'warning');
	} else {
		// refreshUppositiontree();
		// refreshForm(currentActionId);
		op = 'modify';
		//setReadOnly(false);
		// fastDev.getInstance('area.area').setReadonly(true);
		openActionDetailsPage(op);
	}
}

function doDeleteAction(){
	if (!currentActionId || currentActionId == -1) {
		fastDev.alert('请选择需要删除的区域节点', '信息提示', 'warning');
	} else {
		var alertmsg = "是否删除所选记录？";
		var ids = [];
		var actiontree = fastDev.getInstance('actiontree');
		if (actiontree.isLeaf(currentActionId) == false){
			alertmsg = "该节点有下属子节点，请确认是否删除？";
			var nodes = actiontree.findNodesByPid(currentActionId);
			for (var i=0 ;i<nodes.length; i++){
				ids.push(nodes[i].id);
			}
		}
		ids.push(currentActionId);
		fastDev.confirm(alertmsg, '信息提示', function(result) {
			if (result) {
				//setReadOnly(true);
				op = 'delete';
				var proxy = fastDev.create('Proxy', {
					action : 'sdaction_deleteActionById.action'
				});
				proxy.save({
					id : ids.join(',')
				}, function(result) {
					fastDev.alert(result.msg, '信息提示', result.status, function() {
						if (result.status == 'ok') {
							if (actiontree.getNodeByid(currentActionId)) {
								actiontree.delNode(currentActionId);
							}
							//fastDev.getInstance('positionForm').cleanData();
							currentActionId = null;
							refreshDataGrid();
						}
					});
				});
			}
		});
	}
}

function onActionTreeNodeClick(event, id, value){
	currentActionId = id;
	getCurrentActionId();
	parentId = this.getParentid(id);
	parentText = this.getValByid(parentId);
	currentName = this.getValByid(id);
	//refreshUppositiontree();
	// fastDev.getInstance("position.posiName").getGlobal().box.prop("value",
	// parentText);
	op = 'view';
	refreshDataGrid();
}

function refreshDataGrid(){
	var o = condition || {};
	o['standardfile.c_action_id'] = currentActionId;
	fastDev.getInstance('actiongrid').refreshData(o);
	console.info(o);
}

function onUploadSuccess(file, response){
	fastDev.alert(response.msg, '信息提示', response.result, function() {
		if (response.result == true) {
			//导入文件成功后，刷新树，设置最上层节点为当前节点，刷新grid
			refreshTree();
			currentActionId = null;
			refreshDataGrid();
		}
	});
}


function openImportPage(){
	var src = "processnodeImport.html";
	var title = "导入流程节点";
		
	createWindow({
		src : src,
		title : title,
		width : "625",
		height: "200",
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				childWin.doSave();
				/*var form=fd.getInstance("addActionForm");
				var data = appDialog.getAllGridValue();
				console.info(data);
				form.setOptions({"otherSubmitData" : fastDev.Util.JsonUtil.parseJson(data)});
				form.submit();	*/	
			}
		}]		
	});
}