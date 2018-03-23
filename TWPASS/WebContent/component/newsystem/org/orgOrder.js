function onBeforeReady() {
	var request = fastDev.Browser.getRequest();
	this.setOptions({
		initSource:'org_searchOrg.action?org.parentOrgId=' + request['orgid']
	});
}

function doSave() {
	var items = fastDev.getInstance('orgdatagrid').getAllValue();
	var ids = [];
	for(var i=0;i<items.length;i++) {
		ids.push(items[i]['orgId']);
	}
	if(ids.length > 0) {
		fastDev.create('Proxy', {
			action : 'org_changeOrgOrder.action'
		}).save({
			data : ids.join(',')
		}, function(result) {
			fastDev.alert(result.msg, '信息提示', result.status, function(){
				if(result.status == 'ok') {
					fastDev.Ui.Window.parent.refreshDatagrid();
					fastDev.Ui.Window.parent.closeDialog();
				}
			});
		});
	}
}

function doMoveUp() {
	fastDev.getInstance('orgdatagrid').moveUp();
}

function doMoveDown() {
	fastDev.getInstance('orgdatagrid').moveDown();
}