var activityDialog = null;
//刷新条件
var condition = null;

function onBeforeCompile() {
	var width = fastDev(window).width();
	var height = fastDev(window).height();
	fastDev('#centerPanel').css('width', (width - 211) + 'px');
	var datagrid = fastDev('#permissionDatagrid');
	datagrid.attr('height', (height - 20) + 'px');
	fastDev('#resourceTree').attr('height', (height - 63) + 'px');
}

var currentActivityOP=null;

var getTypeId = function() {
	var tree = fastDev.getInstance("activityTree");
	typeId = tree.getCurrentId();
	if(typeId) {
		return typeId;
	}
	return '';
};

var addActiveType = function(){
	var currentTypeId = getTypeId() || '';
	activityDialog = fastDev.create('Window', {
		title : '新增活动类别信息',
		width : 604,
		height : 212,
		showMaxBtn : false,
		allowResize : false,
		src : 'addType.html?Pid=' + currentTypeId,
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(event,win,cwin) {
				cwin.doSave();
			}
		}, {
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(event,win,cwin) {
				cwin.doReset();
			}
		}, {
			text : '关闭',
			iconCls : 'icon-close',
			onclick : function(event,win) {
				win.close();
			}
		}]
	});
};

var modifyActiveType = function(){
    var activityTree = fastDev.getInstance("activityTree");           
    var activityTreeId = activityTree.getCurrentId();//得到一行记录
    if(!activityTreeId) {
    	fastDev.alert('请选择需要修改的活动类别！','信息提示','warning');
    	return false;
    }
    activityDialog = fastDev.create('Window', {
		title : '修改活动类别信息',
		width : 604,
		height : 212,
		showMaxBtn : false,
		allowResize : false,
		src : 'modifyType.html?id=' + activityTreeId,
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(event,win,cwin) {
				cwin.doSave();
			}
		}, {
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(event,win,cwin) {
				cwin.doReset();
			}
		}, {
			text : '关闭',
			iconCls : 'icon-close',
			onclick : function(event,win) {
				win.close();
			}
		}]
	});
};

function addNode(node) {
	if(node) {
		var activityTree = fastDev.getInstance("activityTree");
		activityTree.addNode(node);
	}
}

function editNode(id, val) {
	if(id) {
		var activityTree = fastDev.getInstance("activityTree");
		var node = activityTree.getNodeByid(id);
		if(node) {
			node.val = val;
			activityTree.editNode(node);
		}
	}
}

var deleteActiveType = function(){
    var tree = fastDev.getInstance("activityTree");
    var value = tree.getCurrentId();//得到一行记录
    if (value == 'undefined' || value == '') {       
        fastDev.alert("请选择要删除的活动类别！","信息提示","warning");
        return;
    }
	fastDev.confirm("确定删除该条记录？", "确认删除", function(response) {
		if (response) {
			fastDev.create("Proxy", {
				action : "activityType_deleteNodesById.action"
			}).save({
				id : tree.getCurrentId()
			},function(data) {
				fastDev.alert(data.msg, "提示信息", data.status, function() {
		           if(data.status=="ok"){
		        	    tree.delNode(value);
		           	    fastDev.getInstance('activityDatagrid').refreshData();
		           }
				});
			});    
		}
	});
};


var reloadActivity = function(){
	//只在没有做资源的增加，修改操作才做datagrid的刷新操作
	if(!currentActivityOP){
	    var activityTree = fastDev.getInstance("activityTree");
	    var activityDatagrid = fastDev.getInstance("activityDatagrid");
	    activityDatagrid.addInitReqParam({"tpostactnode.c_ACTION_ID": activityTree.getCurrentId()});
	    activityDatagrid.refreshData(true);
	}	
};

//操作列渲染函数
function operRenderer() {
	var oper = [];
	oper.push('<a href="#" name="modify">修改</a>&nbsp;');
	oper.push('<a href="#" name="del">删除</a>');

	return oper.join('');
}

function actRenderer(value)
{
	switch (value) {
	case "0":
		return "否";
	case "1":
		return "是";
	}
}

function pdcaRenderer(value)
{
	switch (value) {
	case "0":
		return "P";
	case "1":
		return "D";
	case "2":
		return "C";
	case "3":
		return "A";
	}
}

function onRowClick(event,rowindex,rowData) {		
	var oper = event.target.name;
	switch (oper) {
	case "modify":
		modifyEndNode(rowData.c_actnode_id);
		return;
	case "del":
		deleteEndNode(rowData.c_actnode_id);
		return;
	default:
		return;
	}
}

function doAddEndNode() {
	var activityTree = fastDev.getInstance("activityTree");           
    var activityTreeId = activityTree.getCurrentId();//得到一行记录
    if(!activityTreeId) {
    	fastDev.alert('请选择活动类别！','信息提示','warning');
    	return false;
    }
	var url = "addEndNode.html?Pid=" + activityTreeId;

	activityDialog = fastDev.create("Dialog", {
		title : "新增活动末节点",
		width : "900px",
		height : "301px",
		top : "100px",
		//showCollapseBtn : false,
		showMaxBtn : false,
		src : url,
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(event,win,cwin) {
				cwin.doSave();
			}
		}, {
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(event,win,cwin) {
				cwin.doReset();
			}
		}, {
			text : '关闭',
			iconCls : 'icon-close',
			onclick : function(event,win) {
				win.close();
			}
		}]
	});
}

function modifyEndNode(id) {
	var url = "modifyEndNode.html?id=" + id;

	activityDialog = fastDev.create("Dialog", {
		title : "修改活动末节点",
		width : "900px",
		height : "301px",
		top : "100px",
		//showCollapseBtn : false,
		showMaxBtn : false,
		src : url,
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(event,win,cwin) {
				cwin.doSave();
			}
		}, {
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(event,win,cwin) {
				cwin.doReset();
			}
		}, {
			text : '关闭',
			iconCls : 'icon-close',
			onclick : function(event,win) {
				win.close();
			}
		}]
	});
}

function doBatchDelete(){
    var list = fastDev.getInstance("activityDatagrid").getValue();
    if (list.length <= 0) {
        fastDev.alert("请至少选择一条记录进行操作","信息提示","warning");
        return;
    }                
    var ids = new Array();                
    for (var i = 0; i < list.length; i++) {
        var obj = list[i];
        ids.push(obj.c_actnode_id);
    }
    deleteEndNode(ids.join(","));	
}

function deleteEndNode(ids){	
	fastDev.confirm("确定删除记录？", "确认删除", function(response) {
		if (response) {
			fastDev.create("Proxy", {
				action : "actNode_deleteNodesByIds.action"
			}).save({
				ids : ids
			},function(data) {
				fastDev.alert(data.msg, "信息提示", data.status, function() {
					if(data.status=="ok")
						fastDev.getInstance("activityDatagrid").refreshData(true);
				});
			});
		}
	});		
}

function closeDialog() {
	if(activityDialog) {
		activityDialog.close();
		activityDialog = null;
	}
}

function refreshTree() {
	fastDev.getInstance('activityTree').reLoad();
}

/**
 * 过滤信息
 */
function doSearch() {
	var fm = fastDev.getInstance('checkform');
	condition = fm.getItems();
	
	refreshDatagrid();
}
/**
 * 重置表单数据
 */
function doReset() {
	fastDev.getInstance('checkform').cleanData();
}

function refreshDatagrid() {
	var currentTypeId = getTypeId() || '';
	var o = fastDev.apply(o || {}, condition || {});
	o['tpostactnode.c_ACTION_ID'] = currentTypeId;
	fastDev.getInstance('activityDatagrid').refreshData(o);
}
