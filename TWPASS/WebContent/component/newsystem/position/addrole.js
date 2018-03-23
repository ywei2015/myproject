var request = fastDev.Browser.getRequest();//获取请求对象
var positionid = request['positionid'];
var roleDialog;

function afterConstruct(){
	this.addInitReqParam({positionid:positionid});
}

function addObject(){
	//window.location.href = "addPositionRole.html?positionid=" + positionid;
	roleDialog = fastDev.create('Window', {
		title : '添加角色',
		src : 'newsystem/position/addPositionRole.html?positionid=' + positionid,
		width : 865,
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
	if(roleids.length <= 0){
		fastDev.alert("请至少选择一条记录进行操作", "信息提示", "warning");
        return;
	}	
	fastDev.confirm("确定删除所选择的" + roleids.length + "条记录？", "确认删除", function(response) {
		if (response) {
			var ids = [];
			for (var i = 0; i < roleids.length; i++) {
		        var obj = roleids[i];
		        ids.push(obj.roleId);
		    }			
			
			fastDev.create("Proxy", {
				action : "position_delHasRelRoles.action"
			}).save({
				positionid : positionid,
				id:ids.join(',')
			}, function(data) {
				fastDev.alert(data.msg, "信息", data.status, function() {
					if(data.status=="ok")
						fastDev.getInstance("datagrid").refreshData(true);
				});
			});
		}
	});		
}

function close(){
	fastDev.Ui.Window.parent.positionDialog.close();
}