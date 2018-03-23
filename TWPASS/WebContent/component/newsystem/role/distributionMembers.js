var request = fastDev.Browser.getRequest();//获取请求对象
var roleID = request['roleID'];
var roleName = request['roleName'];
var currentType = 'user';
var roleDialog, condition;
var roleDialogWidth = {
	position : 735,
	org : 300,
	user : 945
};

function showTypeBox(value){
	if(currentType) {
		fastDev("." + currentType + "-form").hide();
	}
	currentType = value;
	fastDev("." + value + "-form").show();
	initParams();
	var datagrid = fastDev.getInstance("datagrid");
	if(value == 'user') {
		datagrid.initRefresh({
			url: 'user_searchRoleUserInfos.action'
		});
	} else if(value == 'org') {
		datagrid.initRefresh({
			url: 'org_searchRoleOrg.action'
		});
	} else {
		datagrid.initRefresh({
			url: 'position_searchPositionRoleByRole.action'
		});
	}
}	

function onAfterInitRender() {
	if(currentType == 'user') {
		this.showColumn('displayName');
		this.showColumn('userCode');
		this.showColumn('orgName');
		this.hideColumn('orgCode');
		this.hideColumn('orgTypeName');
		this.hideColumn('posiName');
		this.hideColumn('posiCode');
	} else if(currentType == 'org') {
		this.hideColumn('displayName');
		this.hideColumn('userCode');
		this.showColumn('orgName');
		this.showColumn('orgCode');
		this.showColumn('orgTypeName');
		this.hideColumn('posiName');
		this.hideColumn('posiCode');
	} else {
		this.hideColumn('displayName');
		this.hideColumn('userCode');
		this.showColumn('orgName');
		this.hideColumn('orgCode');
		this.hideColumn('orgTypeName');
		this.showColumn('posiName');
		this.showColumn('posiCode');
	}
}
			
function deleteRel(){
	var datagridObj  =fastDev.getInstance("datagrid");	
	var list = datagridObj.getValue();
	if(!list||list.length==0)
	{
        fastDev.alert("请至少选择一条记录进行删除", "信息提示", "warning");
        return;	
	}	
	fastDev.confirm('是否删除所选的' + list.length + "条记录？", "信息提示", function(result) {
		if(result) {
			var ids = [];
		    for (var i = 0; i < list.length;i++) {
		        var obj = list[i];
		        ids.push(obj[currentType + 'Id']);
		    }
			fastDev.create("Proxy", {
				action : "role_deleteRoleMembers.action"
			}).save({
				roleId : roleID,
				id : ids.join(','),
				type : currentType
			}, function(data) {
				fastDev.alert(data.msg, "信息提示", data.status, function() {
					if(data.status == 'ok')
						refreshData();
				});
			});   	
		}
	});
}			
			
function addObject(){
	var type = fastDev.Util.StringUtil.capitalize(currentType);
	roleDialog = fastDev.create('Window', {
		title : '添加成员',
		width : roleDialogWidth[currentType],
		height : 427,
		inside : false,
		showMaxBtn : false,
		allowResize : false,
		src : 'newsystem/role/add' + type + 'Role.html?roleID=' + roleID + '&roleName=' + roleName,
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(e,win,cwin) {
				cwin.doSave();
			}
		}, {
			text : '关闭',
			iconCls : 'icon-close',
			onclick : function(e,win) {
				win.close();
			}
		}]
	});
}

function afterConstruct(){
	this.addInitReqParam({"roleID":roleID});
}

function valueChange(event, item){
	optype = item.value;
	showTypeBox(optype);
}

function addParam(items, id) {
	var value = items[id];
	if(value) {
		condition[id] = value;
	}
}

function initParams() {
	var form = fastDev.getInstance("roleform");
	var items = form.getItems();
	var param = fastDev.getInstance('datagrid').initProxy.getOptions().urlParam;
	condition = {
			pageSize: param.pageSize,
			page: param.page,
			roleID: roleID
	};
	if(currentType == 'user') {
		addParam(items, 'user.displayName');
		addParam(items, 'user.userCode');
	} else if(currentType == 'org') {
		addParam(items, 'org.orgName');
		addParam(items, 'org.orgCode');
	} else {
		addParam(items, 'position.posiName');
		addParam(items, 'position.posiCode');
	}
	fastDev.getInstance('datagrid').initProxy.getOptions().urlParam = condition;
}

function submit(){
	initParams();
	refreshData();
}

function reset(){
	var form = fastDev.getInstance("roleform");
	form.cleanData();		
}

function close() {
	parent.dialog.close();
}

function closeDialog() {
	roleDialog.close();
}

function refreshData() {
	fastDev.getInstance('datagrid').refreshData();
}

fastDev(function() {
	fastDev('#roleNameTextBox').setText(roleName);
});