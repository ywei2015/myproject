var request = fastDev.Browser.getRequest();//获取请求对象
var userid = request['userid'];
var dialog = fastDev.Ui.Window.getData("parent").userDialog;
var roleDialog;
function afterConstruct(){
	this.addInitReqParam({userid:userid});
}

function addObject(){
	//window.location.href = "addUserRole.html?userid="+userid;
	roleDialog = fastDev.create('Window', {
		title : '添加角色',
		src : 'newsystem/campususer/addUserRole.html?userid='+userid,
		width : 875,
		height : 505,
		inside : false,
		showMaxBtn : false ,
		allowResize : false,
		buttons : [{
			text : '保存',
			iconCls : 'icon-save',
			onclick : function(e, win, cwin) {
				cwin.doSave();
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

function deleteObject(){
	//获取datagrid对象
	var datagrid = fastDev.getInstance("datagrid");
	var roleids = datagrid.getValue();
	if(roleids <= 0){
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
        return;
	}
	fastDev.confirm("确定删除该条记录？", "确认删除", function(response) {
		if (response) {
			var ids = [];
			for (var i = 0; i < roleids.length; i++) {
		        var obj = roleids[i];
		        ids.push(obj.roleId);
		    }			
			
			fastDev.create("Proxy", {
				action : "user_delHasRelRoles.action"
			}).save({
				userid : userid,
				id:ids.join(',')
			}, function(data) {
				fastDev.alert(data.msg, "信息提示", data.status, function() {
					if(data.status=="ok")
						refreshDatagrid();
				});
			});
		}
	});		
}

function refreshDatagrid() {
	fastDev.getInstance("datagrid").refreshData();
}

function close(){
	fastDev.Ui.Window.parent.userDialog.close();
}
