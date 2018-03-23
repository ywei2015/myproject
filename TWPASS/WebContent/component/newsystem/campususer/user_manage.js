var userDialog;
var condition = null, currentOrgId = -1,currentOrgId=null,currentOrgId2=-1;

function onBeforeCompile() {
	var height = fastDev(window).height();
	var width = fastDev(window).width();
	fastDev('#centerPanel').css('width', (width - 211) + 'px');
	fastDev('#usertree').attr('height',(height-37)+'px');
	fastDev('#userdatagrid').attr('height', (height-92)+'px');
}

/**
 * 渲染用户姓名列
 * @param value
 * @returns {String}
 */
function displayNameRenderer(value) {
	return '<a href="javascript:void(0);" class="btn" name="showInfo">' + value + '</a>';
}

/**
 * 查看组织结构
 */
function doViewByOrg() {
	var usertree = fastDev.getInstance('usertree');
	usertree.setOptions({
		asyncDataSource : 'org_initGradeOrgTree.action?moduleId=M4&modeId=view'
	});
	usertree.initRefresh({
		url : 'org_initFirstGradeOrgTree.action?moduleId=M4&modeId=view'
	});
}

/**
 * 查看业务结构
 */
function doViewByBusiness() {
	var usertree = fastDev.getInstance('usertree');
	usertree.setOptions({
		asyncDataSource : 'org_initBusinessOrgTree.action?moduleId=M4&modeId=view'
	});
	usertree.initRefresh({
		url : 'org_initFirstBusinessOrgTree.action?moduleId=M4&modeId=view'
	});
}

/**
 * 渲染操作列
 * @param value
 * @returns
 */
function operRenderer(value) {
	var width = fastDev(this).width();
	return ['<div style="width:'+(width - 2)+'px;">',
		        '<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="modify" name="modifyUser">修改</a>',
	        	'<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="modifypwd" name="resetPwd">重置密码</a>',
	        	'<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="assrole" name="assRole">关联角色</a>',
	        	'<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="grantauth" name="grantAuth">分配权限</a>',
	        	'<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="authview" name="authView">权限视图</a>',
	        	'<a href="javascript:void(0);" class="btn" style="margin-left:5px;display:none;" id="delete" name="deleteUser">删除</a>',
	        '</div>'].join('');
}

function onNodeClick(event, id, value) {

	currentOrgId = id;
	refreshDatagrid2();
}

function onRowClick(event,rowindex,data) {
	var target = event.target;
	var name = target['name'];
	if(name) {
		name = fastDev.Util.StringUtil.capitalize(name);
		if(fastDev.isFunction(window['do' + name])) {
			window['do' + name].call(window, data);
		}
	}
}

function onRowDblClick(event, rowindex, data) {
	doShowInfo(data);
}

/**
 * 过滤用户信息
 */
function doSearchUser() {
	condition = fastDev.getInstance('userform').getItems();
	refreshDatagrid();
}

/**
 * 重置用户信息表单
 */
function doResetUser() {
	fastDev.getInstance('userform').cleanData();
}

/**
 * 显示用户详细信息
 * @param data
 */
function doShowInfo(data) {
	userDialog = fastDev.create('Window', {
		width : 619,
		height : 464,
		showMaxBtn : false,
		allowResize : false,
		inside : false,
		title : '查看用户信息',
		src : 'newsystem/campususer/userinfo.html?userid=' + data.userId + '&staffid=' + data.staffId,
		buttons:[{
			text : '关闭',
			iconCls : "icon-close",
			onclick : function(event,win,childWin) {
				win.close();
			}
		}]
	});
}

/**
 * 关联角色
 * @param data
 */
function doAssRole(data) {
	userDialog = fastDev.create('Window', {
		width : 715,
		height : 340,
		inside : false,
		showMaxBtn : false,
		allowResize : false,
		title : '用户已经关联的角色',
		src : 'newsystem/campususer/addrole.html?userid=' + data.userId
	});
}

/**
 * 分配权限
 * @param data
 */
function doGrantAuth(data) {
	searchUserRole(function(result){
		userDialog = fastDev.create('Window', {
			showMaxBtn : false,
			allowResize : false,
			width: "582",
			height: "376",
			title :"分配权限",
			src : "distributionRights.html?userID="+data.userId+"&userName="+data.displayName + "&roleID=" + result.roleid,
			buttons : [{
				text: '还原权限',
				iconCls : 'icon-undo',
				onclick : function(e, win, cwin) {
					cwin.doResetRights();
				}
			},{
				text: '查看限制权限',
				iconCls: 'icon-search',
				onclick: function(e, win, cwin) {
					cwin.viewLimitRights();
				}
			},{
				text : '保存',
				iconCls : 'icon-save',
				onclick : function(event, win, cwin) {
					cwin.doSave();
				}
			},{
				text : '关闭',
				iconCls : 'icon-close',
				onclick : function(event,win) {
					win.close();
				}
			}]
		});
	}, data);
}

function searchUserRole(fn, data) {
	fastDev.create('Proxy',{
		action : 'user_searUserRole.action'
	}).save({
		userid : data.userId
	}, function(result) {
		fn(result);
	});
}

/**
 * 权限视图
 * @param data
 */
function doAuthView(data) {
	searchUserRole(function(result){
		userDialog = fastDev.create('Window', {
			width: "783",
			title : "用户权限视图",
			height: "378",
			src : "userRightView.html?userid="+data.userId +"&username=" + data.displayName,
			showMaxBtn : false,
			allowResize : false,
			buttons : [{
				text : '关闭',
				iconCls : 'icon-close',
				onclick : function(event, win) {
					win.close();
				}
			}]
		});
	}, data);
}

/**
 * 修改用户信息
 * @param data
 */
function doModifyUser(data) {
	userDialog = fastDev.create('Window', {
		width : 640,
		height : 480,
		inside : false,
		showMaxBtn : false,
		allowResize : false,
		title : '修改用户信息',
		src : 'newsystem/campususer/modifyuser.html?userId=' + data.userId + '&staffId=' + data.staffId + '&orgId=' + data.majorOrgId + '&positionId='+data.positionId,
		buttons:[{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin) {
				childWin.doSave();
			}
		},{
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(e, win, cwin) {
				cwin.doReset();
			}
		},{
			text : '关闭',
			iconCls : 'icon-close',
			onclick : function(event, win) {
				win.close();
			}
		}]
	});
}

/**
 * 删除用户信息
 * @param data
 */
function doDeleteUser(data) {
	fastDev.confirm('是否确认删除用户“' + data.displayName + '”?','信息提示', function(result){
		if(result) {
			deleteUserById(data.userId);
		}
	});
}

function deleteUserById(id) {
	var proxy = fastDev.create('Proxy', {
		action : 'user_delUser.action'
	});
	proxy.save({id:id}, function(result){
		fastDev.alert(result.msg, '信息提示', result.status, function(){
			if(result.status == 'ok') {
				refreshDatagrid();
			}
		});
	});
}



function refreshDatagrid2(){
	var o = condition || {};
	var displayName=fastDev.getInstance('user.displayName').getValue();
	//alert(displayName);
	if(displayName==null || ''==displayName){
		o['user.displayName']='';
		o['user.orgId'] = currentOrgId;
		//alert(fastDev.Util.JsonUtil.parseString(o));
	}else{
		fastDev.getInstance('userform').cleanData();	
		o['user.orgId'] = currentOrgId;
		//alert(fastDev.Util.JsonUtil.parseString(o));
		
	}
	fastDev.getInstance('userdatagrid').refreshData(o);

}

function onOrgChange(value){
	currentOrgId2=value;
}

function refreshDatagrid() {
	
	var o = condition || {};
	if(currentOrgId2==null || ''==currentOrgId2 || currentOrgId2==-1){
	   //o['user.orgId'] = currentOrgId;
	  fastDev.getInstance('userdatagrid').refreshData(o);
	}else{
		o['user.orgId'] = currentOrgId2;
	}
	fastDev.getInstance('userdatagrid').refreshData(o);
}

function closeDialog() {
	if(userDialog) {
		userDialog.close();
		userDialog = null;
	}
}

/**
 * 重置密码
 * @param data
 */
function doResetPwd(data) {
	fastDev.confirm('是否重置用户“' + data.displayName + '的密码？”', '信息提示', function(result){
		if(result) {
			fastDev.create('Proxy',{
				action : 'user_resetPassWord.action'
			}).save({
				userId : data.userId
			},function(result) {
				fastDev.alert(result.msg, '信息提示', result.status);
			});
		}
	});
}

/**
 * 添加用户信息
 */
function doAddUser() {
	userDialog = fastDev.create('Window', {
		width : 640,
		height : 500,
		inside : false,
		showMaxBtn : false,
		title : '新增用户信息',
		allowResize : false,
		src : 'newsystem/campususer/adduser.html?orgID=' + currentOrgId,
		buttons:[{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin, fast) {
				childWin.doSave();
			}
		},{
			text : '重置',
			iconCls : 'icon-reset',
			onclick : function(e, win, cwin) {
				cwin.doReset();
			}
		},{
			text : '关闭',
			iconCls : 'icon-close',
			onclick : function(event, win) {
				win.close();
			}
		}]
	});
}

/**
 * 调动组织
 */
function doChangeOrg() {
	var datagrid = fastDev.getInstance('userdatagrid');
	var items = datagrid.getValue();
	if(items.length <=0 ) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		var ids = [];
		for(var i=0; i< items.length; i++) {
			ids.push(items[i].userId);
		}
		userDialog = fastDev.create("Window", {
			width: "345",
			title : "调动组织",
			height: "447",
			src : "changeuser_org.html?empids="+ids.join(','),
			showMaxBtn : false,
			allowResize : false,
			buttons:[{
				text : '保存',
				iconCls : 'icon-save',
				onclick : function(event, win, childWin) {
					childWin.doSave();
				}
			},{
				text : '关闭',
				iconCls : 'icon-close',
				onclick : function(event, win) {
					win.close();
				}
			}]
		});
	}
}

/**
 * 用户排序
 */
function doOrderUser() {
	var orgId = getOrgId();
	if(!orgId || orgId == '-1') {
		fastDev.alert('请从左边树中选中用户的上级组织后再排序！', '信息提示', 'warning');
		return false;
	}
	userDialog = fastDev.create('Window', {
		width: "513",
		title : "用户排序",
		height: "383",
		showMaxBtn : false,
		allowResize : false,
		src : "userOrder.html?orgid="+orgId,
		buttons:[{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(event, win, childWin) {
				childWin.doSave();
			}
		},{
			text : '关闭',
			iconCls : 'icon-close',
			onclick : function(event, win) {
				win.close();
			}
		}]
	});
}

function getOrgId() {
	var orgTree = fastDev.getInstance('usertree');
	return orgTree.getCurrentId();
}

/**
 * 批量删除用户
 */
function doDelManyUsers() {
	var datagrid = fastDev.getInstance('userdatagrid');
	var items = datagrid.getValue();
	if(items.length <=0 ) {
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
	} else {
		fastDev.confirm("确认是否删除选择的" + items.length + "条记录？", "信息提示", function(result){
			if(result) {
				var ids = [];
				for(var i = 0; i < items.length; i++) {
					ids.push(items[i].userId);
				}
				deleteUserById(ids.join(","));
			}
		});
	}
}

function onAfterInitRender() {
	showResourceModes('user', fastDev.getInstance('userdatagrid'));
}