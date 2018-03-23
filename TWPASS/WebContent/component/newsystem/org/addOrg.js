//
//function onAfterLoadData() {
//	fastDev.getInstance('modifyOrgForm').setValue({
//		'org.parentOrgId':1001619
//	});
//}
var request = fastDev.Browser.getRequest();
var parentOrgId = request['parentOrgId'];

function onFormBeforeReady(){
	this.setOptions({
		initSource : 'org_initOrgForm.action?parentOrgId=' + parentOrgId
	});
};

function onParentOrgBeforeReady() {
	this.setOptions({
		initSource : 'org_initFirstGradeOrgTree.action?moduleId=M31&modeId=add&orgID=' + parentOrgId,
		value : parentOrgId
	});
}

function onOrgTypeBeforeReady() {
	this.setOptions({
		value: parentOrgId=='-1'?1:2
	});
}

function onOrgTypeChange(id, value) {
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
		if(!orgParentId || orgParentId == '-1') {
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
function doSave(fn) {
	fastDev.getInstance('orgForm').submit();
}

function doReset() {
	fastDev.getInstance('orgForm').resetData();
}

//表单提交后回调
function onSubmitSuccess(result) {
	if(result['org.orgId']) {
		fastDev.Ui.Window.parent.addTreeNode({
			id: result['org.orgId'],
			val: result['org.orgName'],
			pid: parentOrgId
		});
		fastDev.Ui.Window.parent.refreshDatagrid();
		fastDev.Ui.Window.parent.closeDialog();
	} else {
		fastDev.alert(result.msg, '信息提示', result.status);
	}
}