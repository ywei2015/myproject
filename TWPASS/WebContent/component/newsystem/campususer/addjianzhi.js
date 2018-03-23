var request = fastDev.Browser.getRequest();
var orgId = request['orgId'];
var positionId = request['positionId'];
var id = request['id'];

//function onBeforeCompile() {
//	fastDev('#orgId').attr('value', orgId);
//	fastDev('#positionId').attr('value', positionId);
//}

//变更组织信息后，更新岗位信息
function doSelectPosition(id, value) {
	fastDev.getInstance('positionId').initRefresh({
		url : 'user_initUserPos.action?modeId=add&orgid=' + id
	});
}

fastDev(function(){
	doSelectPosition(orgId);
});

function getValues() {
	var values = fastDev.getInstance('jianzhiForm').getItems();
	var positionName = fastDev.getInstance('positionId').getText();
	//if
	fastDev.apply(values, {
		orgName : fastDev.getInstance('orgId').getText(),
		positionName : fastDev.getInstance('positionId').getText()
	});
	return values;
}

function onOrgBeforeReady() {
	//doSelectPosition(orgId);
	this.setOptions({
		value : orgId
	});
}

function onPositionBeforeReady() {
//	alert(positionId)
	this.setOptions({
		value : positionId
	});
}