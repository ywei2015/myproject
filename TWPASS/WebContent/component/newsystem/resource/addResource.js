var request = fastDev.Browser.getRequest();
var appid = request['appid'];
var resourceId = request['resourceId'];

function onChange(id, value) {
	if(id){
        var selectTree = fastDev.getInstance("resource.parentCode");
        selectTree.initRefresh({url:"resource_initResourceFormTree.action?appid="+id});
	}	
}

function onAppIdBeforeReady() {
	if(appid) {
		this.setOptions({
			value : appid
		});
	}
}

function onParentCodeBeforeReady() {
	if(appid) {
		this.setOptions({
			value: resourceId,
			initSource : 'resource_initResourceFormTree.action?appid=' + appid
		});
	}
}

function doSave() {
	fastDev.getInstance('resourceForm').submit();
}

function doReset() {
	fastDev.getInstance('resourceForm').resetData();
	if(appid) {
		fastDev.getInstance('resource.appId').setValue(appid);
	}
}

function onSubmitSuccess(result) {
	if(result['resource.resourceCode']) {
		parent.addNode({
			id: result['resource.resourceCode'],
			val: result['resource.resourceName'],
			pid: result['resource.parentCode'],
			type: 'R',
			appid: appid
		});
		parent.closeDialog();
	} else {
		fastDev.alert(result.msg, '信息提示', result.status);
	}
}