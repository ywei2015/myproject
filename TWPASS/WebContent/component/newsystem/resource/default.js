var resourceDialog = null,resourceId=null;

function onBeforeCompile() {
	var width = fastDev(window).width();
	var height = fastDev(window).height();
	fastDev('#centerPanel').css('width', (width - 211) + 'px');
	var datagrid = fastDev('#permissionDatagrid');
	datagrid.attr('height', (height - 20) + 'px');
	fastDev('#resourceTree').attr('height', (height - 63) + 'px');
}

var currentResourceOP=null;

var getAppId = function() {
	var tree = fastDev.getInstance("resourceTree");
	resourceId = tree.getCurrentId();
	if(resourceId) {
		var node = tree.getNodeByid(resourceId);
		if(node.type == 'A') {
			return resourceId.replace('app_', '');
		} else {
			return node.appid;
		}
	}
	return '';
};

var addResource = function(){
	var appid = getAppId() || '';
	resourceDialog = fastDev.create('Window', {
		title : '新增资源信息',
		width : 604,
		height : 242,
		showMaxBtn : false,
		allowResize : false,
		src : 'addResource.html?appid=' + appid+"&resourceId="+resourceId,
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

var modifyResource = function(){
    var resourceTree = fastDev.getInstance("resourceTree");           
    var appid = null;
    var resourceTreeId = resourceTree.getCurrentId();//得到一行记录
    var resourcename = null;
    var parentResourceCode = null;
    if(resourceTreeId){                	
        var node = resourceTree.getNodeByid(resourceTreeId);   
        parentResourceCode = node.pid.replace('app_', '');
        resourcename = node.val;
        if(node.type=="A"){
        	fastDev.alert('应用节点不能修改','信息提示','warning');
        	return false;
        }else{
        	appid = node.appid;
        }
    }                
    if(!appid) {
    	fastDev.alert('请选择需要修改的资源','信息提示','warning');
    	return false;
    }
    resourceDialog = fastDev.create('Window', {
		title : '修改资源信息',
		width : 604,
		height : 242,
		showMaxBtn : false,
		allowResize : false,
		src : 'modifyResource.html?appid=' + appid + '&resourceid=' + resourceTreeId + '&resourcename=' + resourcename + '&parentResourceCode=' + parentResourceCode,
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
		var resourceTree = fastDev.getInstance("resourceTree");
		resourceTree.addNode(node);
	}
}

function editNode(id, val) {
	if(id) {
		var resourceTree = fastDev.getInstance("resourceTree");
		var node = resourceTree.getNodeByid(id);
		if(node) {
			node.val = val;
			resourceTree.editNode(node);
		}
	}
}

var deleteResource = function(){
    var tree = fastDev.getInstance("resourceTree");
    var value = tree.getCurrentId();//得到一行记录
    if (value == 'undefined' || value == '') {       
        fastDev.alert("请选择要删除的资源！","信息提示","warning");
        return;
    }
    var node = tree.getNodeByid(value);
    if(node.type!="R")
    {
    	fastDev.alert("你选择的不是资源节点，请重新选择","信息提示","warning");
    	return;
    }    
	fastDev.confirm("确定删除该条记录？", "确认删除", function(response) {
		if (response) {
			fastDev.create("Proxy", {
				action : "resource_deleteResource.action"
			}).save({
				id : tree.getCurrentId()
			},function(data) {
				fastDev.alert(data.msg, "提示信息", data.status, function() {
		           if(data.status=="ok"){
		        	    tree.delNode(value);
		           	    fastDev.getInstance('permissionDatagrid').refreshData();
		           }
				});
			});    
		}
	});
};


var reloadResource = function(){
	//只在没有做资源的增加，修改操作才做datagrid的刷新操作
	if(!currentResourceOP){
	    var resourceTree = fastDev.getInstance("resourceTree");
	    var permissionDatagrid = fastDev.getInstance("permissionDatagrid");
	    permissionDatagrid.addInitReqParam({"permission.resourceCode": resourceTree.getCurrentId()});
	    permissionDatagrid.refreshData(true);
	}	
};

function operRenderer() {
	var width = fastDev(this).width();
	return [
			'<div style="width:'
					+ width
					+ 'px;"><a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="delete">移除</a>',
			'<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="bindrule">绑定规则</a>']
			.join('');
}

function onRowClick(event, rowindex, data) {
	var target = event.target.id;
	if (target) {
		switch (target) {
		case 'bindrule':
			createWindow({
				src : "dataRuleList.html?id=" + data.permissionId,
				title : "已绑定数据规则",
				width : "720",
				height : "339"
			});
			break;
		case 'delete':
			deletePermission(data.permissionId);
			break;
		}
	}
}

function addPermission(){
    var resourceTree = fastDev.getInstance("resourceTree");
    var id = resourceTree.getCurrentId();
    if(id=="undefined"||id.length==0){
    	fastDev.alert("请选择资源","信息提示","warning");
    	return;
    }
    var node = resourceTree.getNodeByid(id);
    if(node.type!="R")
    {
    	fastDev.alert("你选择的不是资源节点，请重新选择","信息提示","warning");
    	return;
    }
    
    resourceDialog = fastDev.create('Window', {
		src : "accessModeList.html?id=" + id,
		title : "新增权限",
		width : "750",
		height : "340",
		showMaxBtn : false,
		allowResize : false
	});        
}

function batchDeletePermission(){
    var list = fastDev.getInstance("permissionDatagrid").getValue();
    if (list.length <= 0) {
        fastDev.alert("请至少选择一条记录进行操作","信息提示","warning");
        return;
    }                
    var ids = new Array();                
    for (var i = 0; i < list.length; i++) {
        var obj = list[i];
        ids.push(obj.permissionId);
    }
    deletePermission(ids.join(","));	
}

function deletePermission(ids){	
	fastDev.confirm("确定删除记录？", "确认删除", function(response) {
		if (response) {
			fastDev.create("Proxy", {
				action : "permission_deletePermission.action"
			}).save({
				id : ids
			},function(data) {
				fastDev.alert(data.msg, "信息提示", data.status, function() {
					if(data.status=="ok")
						fastDev.getInstance("permissionDatagrid").refreshData(true);
				});
			});
		}
	});		
}

var dialog = null;

function createWindow(o) {
	var config = fastDev.apply({
		width : "720",
		height : "370",
		showMaxBtn : false,
		allowResize : false
	}, o || {});
	dialog = fastDev.create('Window', config);
}

function batchBindRule(){
    var list = fastDev.getInstance("permissionDatagrid").getValue();
    if (list.length <= 0) {
        fastDev.alert("请至少选择一条记录进行操作","信息提示","warning");
        return;
    }                
    var ids = new Array();                
    for (var i = 0; i < list.length; i++) {
        var obj = list[i];
        ids.push(obj.permissionId);
    }		
	createWindow({
		src : "batchAddDataRuleList.html?id=" + ids.join(","),
		title : "批量绑定数据规则",
		width : "720",
		height : "340"
	});    
    
}

function closeDialog() {
	if(resourceDialog) {
		resourceDialog.close();
		resourceDialog = null;
	}
}

function refreshTree() {
	fastDev.getInstance('resourceTree').reLoad();
}

function onAfterInitRender() {
	showResourceModes('resource');
}