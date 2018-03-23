var condition;

function onBeforeCompile() {
	var height = fastDev(window).height();
	fastDev('#datagrid').attr('height', (height - 66) + 'px');
}


var submit = function() {
	var form = fastDev.getInstance("searchForm");
	condition = form.getItems();
	refreshData();
};

var clear = function() {
	var form = fastDev.getInstance("searchForm");
	form.cleanData();
};

var reset = function() {
	var form = fastDev.getInstance("searchForm");
	form.cleanData();
};

var dialog = null;

function addObject() {
	createWindow({
		src : "addAccessMode.html",
		title : "添加访问方式",
		width : "624",
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin, fd) {
				var form=fd.getInstance("accessForm");
				form.submit();				
			}
		},{
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(e,win,cwin, fd) {
				var form=fd.getInstance("accessForm");
				form.cleanData();
			}
		}]		
	});
}

function createWindow(o) {
	var config = fastDev.apply({
		width : "640",
		height : "217",
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

function operRenderer() {
	var width = fastDev(this).width();
	return [
			'<div style="width:'
					+ width
					+ 'px;"><a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="modify">修改</a>',
			'<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="delete">删除</a>']
			.join('');
}

function onRowClick(event, rowindex, data) {
	var target = event.target.id;
	if (target) {
		switch (target) {
		case 'modify':
			createWindow({
				src : "modifyAccessMode.html?id=" + data.modeId,
				title : "新增访问方式",
				width : "624",
				buttons : [{
					text : '保存',
					iconCls : 'icon-save',
					onclick : function(e,win,cwin, fd) {
						var form=fd.getInstance("accessForm");
						form.submit();				
					}
				},{
					text : '重置',
					iconCls : 'icon-reset',
					onclick : function(e,win,cwin, fd) {
						var form=fd.getInstance("accessForm");
						form.resetData();
					}
				}]					
			});
			break;
		case 'delete':
			deleteObject(data.modeId);
			break;
		}
	}
}
function batchDeleteObject() {
	var datagrid = fastDev.getInstance("datagrid");
	var objects = datagrid.getValue();
	var ids = new Array();
	if (!!objects) {
		for ( var i = 0; i < objects.length; i++)
			ids.push(objects[i].modeId);
	}
	if(ids.length==0)
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	else
		deleteObject(ids.join(","));
}

function deleteObject(idStr) {
	fastDev.confirm("确定删除该条记录？", "确认删除", function(response) {
		if (response) {
			fastDev.create("Proxy", {
				action : "accessmode_deleteMode.action"
			}).save({
				id : idStr
			}, function(data) {
				fastDev.alert(data.msg, "信息提示", data.status, function() {
					fastDev.getInstance("datagrid").refreshData(true);
				});
			});
		}
	});
}

function refreshData() {
	fastDev.getInstance("datagrid").refreshData(condition);
}

function onAfterInitRender() {
	showResourceModes('accessmode');
}