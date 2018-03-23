var request = fastDev.Browser.getRequest();//获取请求对象
var orgId = request['orgid'];
var positionId = request['positionid'];

function onFormBeforeReady(){
	this.setOptions({
		dataSource : 'position_getPositionInfo.action?positionid=' + positionId
	});
};

function onParentOrgBeforeReady() {
	this.setOptions({
		value: orgId,
		initSource : 'org_initFirstGradeOrgTree.action?moduleId=M34&modeId=modify&orgID=' + orgId
	});
}

/**
 * 保存岗位信息
 */
function doSave() {
	fastDev.getInstance('positionForm').submit();
}

/**
 * 重置岗位表单
 */
function doReset() {
	fastDev.getInstance('positionForm').resetData();
}

function onFormSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function(){
		if(result.status == 'ok') {
			fastDev.Ui.Window.parent.refreshDatagrid();
			fastDev.Ui.Window.parent.closeDialog();
		}
	});
}
function formDataRender()
{
	var tree = fastDev.getInstance("position.orgId");
	var id = tree.getValue();
	fastDev.getInstance('position.manaPosi').initRefresh({
		url : 'user_initUserPos.action?orgid=' + id
	});
}

function showManaPosition(id, value) {
	fastDev.getInstance('position.manaPosi').clean(true);
	fastDev.getInstance('position.manaPosi').initRefresh({
		url : 'user_initUserPos.action?orgid=' + id
	});
}