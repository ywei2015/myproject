var request = fastDev.Browser.getRequest();//获取请求对象
var orgid = request['orgid'];

function onBeforeReady() {
	this.setOptions({
		initSource : 'user_queryOrderusers.action?orgid=' + orgid
	});
}

function doMoveUp() {
	fastDev.getInstance('userdatagrid').moveUp();
}

function doMoveDown() {
	fastDev.getInstance('userdatagrid').moveDown();
}

function doSave() {
	var items = fastDev.getInstance('userdatagrid').getAllValue();
	var names = [];
	for(var i=0;i<items.length;i++) {
		names.push(items[i]['userId']);
	}
	if(names.length > 0) {
		fastDev.create('Proxy', {
			action : 'user_changeUserOrder.action'
		}).save({
			data : names.join(',')
		}, function(result) {
			fastDev.alert(result.msg, '信息提示', result.status, function(){
				if(result.status == 'ok') {
					parent.refreshDatagrid();
					parent.closeDialog();
				}
			});
		});
	}
}