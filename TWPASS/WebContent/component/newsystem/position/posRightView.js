var request = fastDev.Browser.getRequest();//获取请求对象
var positionid = request['positionid'];
var posname = request['posname'];

fastDev(function(){
	fastDev("#roleNameTextBox").setText(posname);
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
		positionId: positionid,
		showType: 'position'
	});
}

function onAfterConstruct() {
	this.addInitReqParam({
		positionId: positionid,
		showType: 'position'
	});
}