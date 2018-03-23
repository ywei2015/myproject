var request = fastDev.Browser.getRequest();//获取请求对象
var roleid = request['roleid'];
var appId = request['appId'];
function submitSuccess(data){
	fastDev.alert(data.msg, "信息提示", data.status,function(){
		if(data.status == 'ok') {
			parent.refreshDatagrid();
			parent.dialog.close();	
			return false;
		}
	});	
}

function change(){
	var app=fastDev.getInstance("role.appId");
	if(!!app.getValue()){
		var selectTree=fastDev.getInstance("role.parentRoleId");	
	    selectTree.initRefresh({url:"role_showRoleTree.action?modeId=add&showApp=false&appid="+app.getValue()});
	}
}

var afterConstruct=function(){
	this.addInitReqParam({"appId":appId,"ROLEID":roleid});
	this.addDataReqParam({"ROLEID":roleid});
};