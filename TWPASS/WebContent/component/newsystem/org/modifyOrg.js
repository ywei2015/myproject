var request = fastDev.Browser.getRequest();//获取请求对象
var orgId = request['orgid'];
var parentOrgId = request['parentOrgId'];
var area = request['area'];

function onFormBeforeReady(){
	this.setOptions({
		dataSource : 'org_getOrgByID.action?orgID=' + orgId,
		initSource : 'org_initOrgForm.action?orgID=' + orgId
	});
};

function onParentOrgBeforeReady() {
	this.setOptions({
		value: parentOrgId,
		initSource : 'org_initFirstGradeOrgTree.action?moduleId=M31&modeId=modify&orgID=' + parentOrgId
	});
}

function onOrgTypeChange(event, id, value) {
	if(id == '2') {
		fastDev("#parentOrgRequired").show();
	} else {
		fastDev("#parentOrgRequired").hide();
	}
	doInitBizParentOrg();
}

function onBeforeSubmit() {
	var orgType = fastDev.getInstance('org.orgType').getValue();
	if(orgType == '2') {
		var orgParentId = fastDev.getInstance('org.parentOrgId').getValue();
		if(!orgParentId) {
			fastDev.getInstance('org.parentOrgId').initError('上级组织不能为空');
			return false;
		}
	}
	return true;
}

//初始化业务上级数据
function doInitBizParentOrg() {
	var orgType = fastDev.getInstance('org.orgType').getValue();
	var orgParentId = fastDev.getInstance('org.parentOrgId').getValue();
	if(orgType && orgParentId) {
		var bizSelect = fastDev.getInstance('org.bizParentId');
		bizSelect.clean(true);
		if(orgType == '2') {
			bizSelect.initRefresh({
				url : 'org_initBizParentOrg.action?parentId=' + orgParentId
			});
		}
	}
}

//保存组织信息
function doSave() {
	fastDev.getInstance('orgForm').submit();
}

function doReset() {
	fastDev.getInstance('orgForm').resetData();
}

//表单提交后回调
function onSubmitSuccess(result) {
	if(result['org.orgId']) {
		fastDev.Ui.Window.parent.editTreeNode(result['org.orgId'], result['org.orgName'],result['org.parentOrgId']);
		fastDev.Ui.Window.parent.refreshDatagrid();
		fastDev.Ui.Window.parent.closeDialog();
	} else {
		fastDev.alert(result.msg, '信息提示', result.status);
	}
}

function onBeforeAreaTreeReady() {
	var initSource = "area_initSubAreaTree.action"
			+ (area ? "?value=" + area : "");
	this.setOptions({
		initSource : initSource
	});
}

function onAreaAfterInitRender() {
	if(area) {
		this.setValue(area);
	}
}