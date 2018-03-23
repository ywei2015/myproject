var request = fastDev.Browser.getRequest();//获取请求对象
var roleID = request['roleID'];			
var roleName = request['roleName'];
var currentId = null;

function onNodeClick(){
	var orgtree = fastDev.getInstance("orgtree"); 
	currentId = orgtree.getCurrentId();
}

function onBeforeReady() {
	this.setOptions({
		initSource : 'org_initFirstGradeOrgTree.action?modeId=view&moduleId=M5&roleID=' + roleID,
		asyncDataSource : 'org_initGradeOrgTree.action?modeId=view&moduleId=M5&roleID=' + roleID
	});
}
			
function doSave(){
	if(!currentId)
	{
		fastDev.alert("请选择要添加的组织", "信息提示", "warning");
        return;	
	}	
    var node = fastDev.getInstance("orgtree").getNodeByid(currentId);
    if(node.chk == 'true') {
    	fastDev.alert('灰色节点表示已经存在，请选择其他节点',"信息提示","warning");
    	return false;
    }
	fastDev.create("Proxy", {
		action : "role_addRoleMembers.action"
	}).save({
		id : currentId,
		roldId:roleID,
		type:"org"
	}, function(data) {
		fastDev.alert(data.msg, "信息提示", data.status, function() {
			if(data.status == 'ok') {
				fastDev.Ui.Window.parent.refreshData();
				fastDev.Ui.Window.parent.closeDialog();
			}
		});
	});      
}			