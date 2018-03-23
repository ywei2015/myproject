var condition;

var onBeforeCompile = function() {
	var height = fastDev(window).height();
	fastDev('#datagrid').attr('height', (height - 65) + 'px');
};

function onAfterInitRender() {
	showResourceModes('app');
}

var submit = function() {
	var form = fastDev.getInstance("appSearchForm");
	condition = form.getItems();
	refreshData();
};
var clear = function() {
	var form = fastDev.getInstance("appSearchForm");
	form.cleanData();
};

var reset = function() {
	var form = fastDev.getInstance("appSearchForm");
	form.cleanData();
};

var appDialog = null;

function addApp() {
	createWindow({
		src : "addapp.html",
		title : "新增应用",
		width : "625",
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin, fd) {
				var form=fd.getInstance("addAppForm");
				form.submit();		
			}
		},{
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(e,win,cwin, fd) {
				var form=fd.getInstance("addAppForm");
				form.cleanData();
			}
		}]		
	});
}

function createWindow(o) {
	var config = fastDev.apply({
		width : "640",
		height : "300",
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

function appNameRenderer(value) {
	return '<a href="javascript:void(0);" class="btn" id="view">' + value
			+ '</a>';
}

function operRenderer() {
	var width = fastDev(this).width();
	return [
			'<div style="width:'
					+ width
					+ 'px;"><a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="modify">修改</a>',
			'<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="delete">删除</a>',
			'<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="resourcemanage">资源管理</a>',
			'<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="rolemanage">角色管理</a></div>' ]
			.join('');
}

function onRowClick(event, rowindex, data) {
	var target = event.target.id;
	if (target) {
		switch (target) {
		case 'modify':
			createWindow({
				src : "modifyapp.html?appid=" + data.appId,
				title : "修改应用",
				width : "648",
				buttons : [{
					text : '保存',
					iconCls : 'icon-save',
					onclick : function(e,win,cwin, fd) {
						var form=fd.getInstance("modifyAppForm");
						form.submit();
					}
				},{
					text : '重置',
					iconCls : 'icon-reset',
					onclick : function(e,win,cwin, fd) {
						var form=fd.getInstance("modifyAppForm");
						form.resetData();
					}
				}]					
			});
			break;
		case 'delete':
			deleteApp(data.appId);
			break;
		case 'resourcemanage':
			createWindow({
				src : "newsystem/resource/default.html?appid=" + data.appId,
				title : "资源管理",
				width : 1000,
				height : 480,
				inside : false
			});
			break;
		case 'rolemanage':
			createWindow({
				src : "newsystem/role/default.html?appid=" + data.appId,
				title : "角色管理",
				width : 1100,
				height : 480,
				inside : false
			});
			break;
		case 'view':
			doView(data);
			break;
		}
	}
}

function onRowDblClick(event, rowindex, data) {
	doView(data);
}

function doView(data) {
	createWindow({
		src : "appInfo.html?appid=" + data.appId,
		title : "查看应用",
		width : "670",
		height : "296"
	});
}

function batchDeleteApp() {
	var datagrid = fastDev.getInstance("datagrid");
	var objects = datagrid.getValue();
	var ids = new Array();
	if (!!objects) {
		for ( var i = 0; i < objects.length; i++)
			ids.push(objects[i].appId);
	}
	if(ids.length==0)
		fastDev.alert("请至少选择一条记录进行操作", "提示信息", 'warning');
	else	
	deleteApp(ids.join(","));
}

function deleteApp(idStr) {
	fastDev.confirm("确定删除该条记录？", "确认删除", function(response) {
		if (response) {
			fastDev.create("Proxy", {
				action : "app_removeApps.action"
			}).save({
				ids : idStr
			}, function(data) {
				fastDev.alert(data.msg, "信息", data.status, function() {
					if(data.status == 'ok') {
						refreshData();
					}
				});
			});
		}
	});
}

function refreshData() {
	fastDev.getInstance('datagrid').refreshData(condition);
}
