var request = fastDev.Browser.getRequest();//获取请求对象
var roleID = request['roleID'];			
var roleName = request['roleName'];
var orgID;

function onNodeClick(){
	var orgtree = fastDev.getInstance("orgtree"); 
	var userdg = fastDev.getInstance("userdatagrid"); //获取datagrid对象   	
	orgID = orgtree.getValue();
	if (!orgID || orgID == "-1") {
		orgID = "";
	}
	userdg.refreshData({"user.orgId": orgID, "roleID" : roleID});
}

function afterConstruct(){
	this.addInitReqParam({"roleID":roleID});
}

function submit(){
	var form = fastDev.getInstance("searchform");
	var datagrid = fastDev.getInstance("userdatagrid");
	var items = form.getItems() || {};
	items.roleID = roleID;
	if (orgID) {
		items['user.orgId'] = orgID;
	}
	datagrid.refreshData(items);
}

function reset(){
	var form = fastDev.getInstance("searchform");
	form.cleanData();	
}
			
function doSave(){
	var list = fastDev.getInstance("userdatagrid").getValue();
	if(!list||list.length==0)
	{
		fastDev.alert("请选择要添加的用户", "信息提示", "warning");
        return;	
	}	
	
	var ids = [];
    for (var i = 0; i < list.length;i++) {
        var obj = list[i];
        ids.push(obj.userId);
    }
    
	fastDev.create("Proxy", {
		action : "role_addRoleMembers.action"
	}).save({
		id : ids.join(','),
		roldId:roleID,
		type:"user"
	}, function(data) {
		fastDev.alert(data.msg, "信息", data.status, function() {
			if(data.status == 'ok') {
				fastDev.Ui.Window.parent.refreshData();
				fastDev.Ui.Window.parent.closeDialog();
			}
		});
	});        
}			