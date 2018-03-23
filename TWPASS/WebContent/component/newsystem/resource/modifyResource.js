var request = fastDev.Browser.getRequest();
var resourceId = request['resourceid'];
var appId = request['appid'];
var resourceName = request['resourcename'];
var parentResourceCode = request['parentResourceCode'];

fastDev(function() {
	initValue();
});

function initValue() {
	fastDev('#oldCode').attr('value', resourceId);
	fastDev('#resource.appId').attr('value', resourceId);
}

function onParentCodeBeforeReady() {
	this.setOptions({
		value: parentResourceCode,
		initSource : 'resource_initResourceFormTree.action?appid=' + appId
	});
}

function onFormBeforeReady() {
	this.setOptions({
		dataSource : 'resource_getResourceById.action?id=' + resourceId
	});
}

function doSave() {
	fastDev.getInstance('resourceForm').submit();
}

function doReset() {
	fastDev.getInstance('resourceForm').resetData();
	initValue();
}

function onSubmitSuccess(result) {
	if(result['resource.resourceCode']) {
		parent.editNode(result['resource.resourceCode'], result['resource.resourceName']);
		parent.closeDialog();
	} else {
		fastDev.alert(result.msg, '信息提示', result.status);
	}
}