var request = fastDev.Browser.getRequest();//获取请求对象
var orgid = request['orgid'];
var orgname = request['orgname'];

fastDev(function(){
	fastDev("#roleNameTextBox").setText(orgname);
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
		orgId: orgid,
		showType: 'org'
	});
}

function onAfterConstruct() {
	this.addInitReqParam({
		orgId: orgid,
		showType: 'org'
	});
}