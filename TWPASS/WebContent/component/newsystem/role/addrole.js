var orgId = fastDev.Browser.getRequest()['orgId'];

function submitSuccess(data){
	fastDev.alert(data.msg, "信息", data.status,function(){
		if(data.status == 'ok') {
			parent.refreshDatagrid();
			parent.dialog.close();	
			return false;
		}
	});	
}

function onOrgBeforeReady() {
	this.setOptions({
		value: orgId,
		initSource: 'org_initFirstGradeOrgTree.action?modeId=add&moduleId=M44&orgID='+orgId
	});
}

function change(){
	var app=fastDev.getInstance("role.appId");
	if(!!app.getValue()){
		var selectTree=fastDev.getInstance("role.parentRoleId");	
	    selectTree.initRefresh({url:"role_showRoleTree.action?modeId=add&showApp=false&appid="+app.getValue()});
	}
}