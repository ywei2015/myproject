var request = fastDev.Browser.getRequest();//获取请求对象
var userid = request['userid'];
var username = request['username'];

fastDev(function(){
	fastDev("#roleNameTextBox").setText(username);
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
		userId: userid,
		showType: 'user'
	});
}

function onAfterConstruct() {
	this.addInitReqParam({
		userId: userid,
		showType: 'user'
	});
}