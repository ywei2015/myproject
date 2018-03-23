var condition,currentId;

function onBeforeCompile() {
	var height = fastDev(window).height();
	fastDev('#centerPanel').css('width',(fastDev(window).width() - 211) + 'px');
	fastDev('#orgtree').attr('height', (height - 35) + 'px');
	fastDev('#datagrid').attr('height', (height - 92) + 'px');
}

function submit(){
	var form = fastDev.getInstance("searchform");
	condition = form.getItems();
	refreshDatagrid();
}

function reset(){
	var form = fastDev.getInstance("searchform");
	form.cleanData();	
}

function addObject(){
	createWindow({
		src : "addrole.html?orgId="+currentId,
		title : "新增角色",
		width : "610",
		height : "218",
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(event,win,cwin, fd) {
				var form=fd.getInstance("roleForm");
				form.submit();				
			}
		},{
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(event,win,cwin, fd) {
				var form=fd.getInstance("roleForm");
				form.cleanData();
			}
		},{
			text : '关闭',
			iconCls : 'icon-close',
			onclick : function(e, win) {
				win.close();
			}
		}]		
	});	
}

function batchDeleteObject() {
	var datagrid = fastDev.getInstance("datagrid");
	var objects = datagrid.getValue();
	var ids = new Array();
	if (!!objects) {
		for ( var i = 0; i < objects.length; i++)
			ids.push(objects[i].roleId);
	}
	if(ids.length==0)
		fastDev.alert("请至少选择一条记录进行操作","信息提示","warning");
	else
		deleteObject(ids.join(","));
}

function deleteObject(idStr) {
	fastDev.confirm("确定删除该条记录？", "确认删除", function(response) {
		if (response) {
			fastDev.create("Proxy", {
				action : "role_delRoles.action"
			}).save({
				id : idStr
			}, function(data) {
				fastDev.alert(data.msg, "信息提示", data.status, function() {
					fastDev.getInstance("datagrid").refreshData();
				});
			});
		}
	});
}
function operRenderer() {
	var width = fastDev(this).width();
	var values = fastDev.getInstance('datagrid').getValue(this);
	var value = values.length > 0 ? values[0] : {};
	var ret = ['<div style="width:' + width + 'px;">'];
	if(value['roleType'] == 'GENERAL') {
		ret.push('<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="modify">修改</a>');
		ret.push('<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="delete">删除</a>');
	}
	ret.push('<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="maintenancemen">成员维护</a>');
	ret.push('<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="grantauth">分配权限</a>');
	ret.push('<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="authview">权限视图</a>');
	return ret.join('');
}

function onRowClick(event, rowindex, data) {
	var target = event.target.id;
	if (target) {
		switch (target) {
		case 'modify':
			createWindow({
				src : "modifyrole.html?roleid=" + data.roleId+"&appId="+data.appId,
				title : "修改角色",
				width : "610",
				height : "218",
				buttons : [{
					text : '保存',
					iconCls : 'icon-save',
					onclick : function(e,win,cwin, fd) {
						var form=fd.getInstance("roleForm");
						form.submit();				
					}
				},{
					text : '重置',
					iconCls : 'icon-reset',
					onclick : function(e,win,cwin, fd) {
						var form=fd.getInstance("roleForm");
						form.resetData();
					}
				},{
					text : '关闭',
					iconCls : 'icon-close',
					onclick : function(e, win) {
						win.close();
					}
				}]					
			});
			break;
		case 'delete':
			deleteObject(data.roleId);
			break;
		case 'maintenancemen':
			createWindow({
				src : "distributionMembers.html?roleID="+data.roleId+"&roleName="+data.roleName	,
				title : "成员维护",
				width : "715",
				height : "400"				
			});
			break;	
		case 'grantauth':
			createWindow({
				src : "distributionRights.html?roleID="+data.roleId+"&roleName="+data.roleName,
				title : "分配权限",
				width : "582",
				height : "376",
				buttons : [{
					text : '保存',
					iconCls : 'icon-save',
					onclick : function(e,win,cwin, fd) {
						cwin.doSave();			
					}
				},{
					text : '关闭',
					iconCls : 'icon-close',
					onclick : function(e,win,cwin, fd) {
						win.close();
					}
				}]					
			});
			break;
		case 'authview':
			createWindow({
				src : "roleRightView.html?roleid="+data.roleId+"&rolename="+data.roleName,
				title : "权限视图",
				width : "783",
				height : "378",
				buttons : [{
					text : '关闭',
					iconCls : 'icon-close',
					onclick : function(e,win,cwin, fd) {
						win.close();
					}
				}]					
			});
			break;				
		}
	}
}

function closeDialog() {
	dialog.close();
}

function onNodeClick(){
	var orgTree = fastDev.getInstance("orgtree");
	currentId = orgTree.getCurrentId();
	refreshDatagrid();   
}

function refreshDatagrid() {
	condition = condition || {};
	if (currentId !== undefined && currentId != -1) {
		condition['role.orgId'] = currentId;
	} else {
		delete condition['role.orgId'];
	}
	fastDev.getInstance("datagrid").refreshData(condition);
}

var dialog = null;

function createWindow(o) {
	var config = fastDev.apply({
		width : "720",
		height : "240",
		showMaxBtn : false,
		allowResize : false
	}, o || {});
	dialog = fastDev.create('Window', config);
}

function onAfterInitRender() {
	showResourceModes('role', fastDev.getInstance("datagrid"));
}