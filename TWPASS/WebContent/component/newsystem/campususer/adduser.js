var orgId = fastDev.Browser.getRequest()['orgID'];

function onOrgBeforeReady() {
	this.setOptions({
		value: orgId,
		initSource: 'org_initFirstGradeOrgTree.action?modeId=add&moduleId=M32&orgID='+orgId
	});
}

function doSave() {
	fastDev.getInstance('jianzhiiDatagrid').writeBackCell();
	var datagridValues = fastDev.getInstance('jianzhiiDatagrid').getAllValue();
	var formValues = fastDev.getInstance('userForm').getItems();
	var loginIdInput=fastDev.getInstance("staff.userCode");
	var loginId=loginIdInput.getValue();
	var queue = fastDev.Queue.getInstance();
	//验证是否账号重复，否则弹窗
	fastDev.post("user_checkLoginIdExists.action",{
		success:function(dat){
			if(dat){
				fastDev.alert("该账号已存在","信息提示","warning");
			}
			else{
				for(var i = 0; i < datagridValues.length; i++) {
					var rowValue = datagridValues[i];
					if(!rowValue.orgId || rowValue.orgId == '-1') {
						fastDev.alert('第' + (i + 1) + '条兼职信息，兼职组织不能为空', '信息提示', 'warning');
						return;
					}
					if(!rowValue.positionId || rowValue.positionId == '-1') {
						fastDev.alert('第' + (i + 1) + '条兼职信息，兼职岗位不能为空', '信息提示', 'warning');
						return;
					}
					if(rowValue.orgId == formValues['user.orgId']) {
						fastDev.alert('第' + (i + 1) + '条兼职信息，兼职组织不能与本职所属组织相同', '信息提示', 'warning');
						return;
					}
					for(var j = 0; j < datagridValues.length; j++) {
						if(j != i) {
							if(rowValue.orgId == datagridValues[j].orgId) {
								fastDev.alert('第' + (i + 1) + '条兼职信息与第' + (j + 1) + '条兼职信息兼职组织重复', '信息提示', 'warning');
								return;
							}
						}
					}
				}
				fastDev.getInstance('userParts').setValue(fastDev.Util.JsonUtil.parseString(datagridValues));
				fastDev.getInstance('userForm').submit();
			}
		},
		data:{
			"loginId":loginId
		},
		queue:queue
	});
}

function doReset() {
	fastDev.getInstance('userForm').resetData();
	fastDev.getInstance('jianzhiiDatagrid').delRow();
}

/**
 * 渲染操作列
 * @param value
 * @returns
 */
function operRenderer(value) {
	var width = fastDev(this).width();
	return ['<div style="width:'+width+'px;">',
	        	'<a href="javascript:void(0);" class="btn" style="margin-left:5px;" name="deletePartUser">删除</a>',
	        '</div>'].join('');
}

function onSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function() {
		if(result.status == 'ok') {
			fastDev.Ui.Window.parent.refreshDatagrid();
			fastDev.Ui.Window.parent.closeDialog();
		}
	});
}

function onCellClick(event, cellIndex, value) {
	if(cellIndex == 7) {
		var name = event.target.name;
		var datagrid = this;
		var rowDatas = datagrid.getValue(event.target);
		var rowData = rowDatas[0];
		if(name == 'cleanUpUser') {
			var data = {
				id : rowData.id,
				upUserName : '',
				upUserId : ''
			};
			datagrid.updateRow(data);
		} else {
			selectUpUser(function(user){
				var data = {
					id : rowData.id,
					upUserName : user.displayName,
					upUserId : user.userId
				};
				datagrid.updateRow(data);
			});
		}
	}
}

function upUserRenderer(value) {
	if(value) {
		return value + '&nbsp;<img src="../../fastdev/images/ico/delete.gif" style="cursor:pointer;" name="cleanUpUser" title="清空上级用户"/>';
	}
	return value;
}

/**
 * 选择上级用户
 */
function doSelectUpUser() {
	selectUpUser(function(user){
		fastDev.getInstance('user.backup1').setValue(user.displayName);
		fastDev.getInstance('user.upUserId').setValue(user.userId);
	});
}

function doCleanUpUser() {
	fastDev.getInstance('user.backup1').setValue('');
	fastDev.getInstance('user.upUserId').setValue('');
}

function selectUpUser(fn) {
	fastDev.create('Window', {
		title : '选择上级用户',
		width : 845,
		height : 398,
		inside : false,
		showMaxBtn : false,
		allowResize : false,
		src : 'newsystem/campususer/selectUpUser.html',
		buttons : [{
			text : '确定',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin) {
				var upUsers = cwin.getUpUser();
				if(upUsers.length == 0) {
					fastDev.alert('请选择一条记录进行操作', '信息提示', 'warning');
				} else {
					if(fn(upUsers[0]) !== false) {
						win.close();
					}
				}
			}
		},{
			text : '关闭',
			iconCls : 'icon-close',
			onclick : function(e,win) {
				win.close();
			}
		}]
	});
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

function selectPos(id, value) {
	fastDev.getInstance('user.positionId').initRefresh({
		url: 'user_initUserPos.action?modeId=add&orgid=' + id
	});
}

function onPositionAfterConstruct() {
	var value = fastDev.getInstance("jianzhiiDatagrid").getValue(this);
	this.addInitReqParam({
		orgid : value[0]['orgId']
	});
}

/**
 * 删除用户兼职信息
 * @param data
 */
function doDeletePartUser(data) {
	fastDev.getInstance('jianzhiiDatagrid').delRow(data.id);
}

/**
 * 添加用户兼职信息
 */
function doAddPartUser() {
	fastDev.getInstance("jianzhiiDatagrid").addRow();
}