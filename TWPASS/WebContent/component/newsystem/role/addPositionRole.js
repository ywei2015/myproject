var request = fastDev.Browser.getRequest();//获取请求对象
var roleID = request['roleID'];			
var roleName = request['roleName'];
function submit(){
	var form = fastDev.getInstance("searchForm");
	var datagrid = fastDev.getInstance("datagrid");
	var items = form.getItems() || {};
	items.roleID = roleID;
	datagrid.refreshData(items);
}

function reset(){
	var form = fastDev.getInstance("searchForm");
	form.cleanData();		
}

function afterConstruct(){
	this.addInitReqParam({"roleID":roleID});
}

function doSave(){
	var list = fastDev.getInstance("datagrid").getValue();
	if(!list||list.length==0)
	{
		fastDev.alert("请选择要添加的组织", "信息提示", "warning");
        return;	
	}	
	
	var ids = [];
    for (var i = 0; i < list.length;i++) {
        var obj = list[i];
        ids.push(obj.positionId);
    }
    
	fastDev.create("Proxy", {
		action : "role_addRoleMembers.action"
	}).save({
		id : ids.join(','),
		roldId:roleID,
		type:"position"
	}, function(data) {
		fastDev.alert(data.msg, "信息提示", data.status, function() {
			if(data.status == 'ok') {
				fastDev.Ui.Window.parent.refreshData();
				fastDev.Ui.Window.parent.closeDialog();
			}
				
		});
	});    
}