var request = fastDev.Browser.getRequest();//获取请求对象
var roleid = request['roleid'];
var rolename = request['rolename'];

fastDev(function(){
	fastDev("#roleNameTextBox").setText(rolename);
});

function onNodeClick() {
	var _currentId = this.getCurrentId();
	var node = this.getNodeByid(_currentId);
	var appid = node.appid;
	if(node.type == 'A') {
		_currentId = '';
	}
	datagrid = fastDev.getInstance('datagrid');
	datagrid.refreshData({
		resourceCode: _currentId,
		appId: appid,
		roleId: roleid,
		showType: 'role'
	});
}

function onAfterConstruct() {
	this.addInitReqParam({
		roleId: roleid,
		showType: 'role'
	});
}