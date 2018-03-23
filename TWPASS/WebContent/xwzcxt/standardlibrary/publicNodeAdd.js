var request = fastDev.Browser.getRequest();
var cModifyType = request['cModifyType'];
var cPublicId = request['cPublicId'];
var cPublicName = request['cPublicName'];

var submitButton = null;
var loadingWindow = null;

function onBeforeReady() {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkForm",
		text : "加载中..."
	});

	var action = "";
	var dataSource = "";
	if (1 == cModifyType) {
		action = "tSdPublic/addPublicNodeAction.action";
		dataSource = "tSdPublic/getPublicNodeByIdAction.action?cPublicId=" + cPublicId;
	} else {
		action = "tSdPublic/addPublicNodeAction.action";
	}
	this.setOptions({
		action : action,
		dataSource: dataSource
	});
}

function onAfterDataRender() {
	if (2 == cModifyType) {
		fastDev("#cPublicPid").prop("value", cPublicId);
		fastDev("#cPublicPname").prop("value", cPublicName);
	}
	if (!fastDev("#cPublicPname").prop("value")) {
		fastDev("#cPublicPname").prop("value", "所有节点");
	}
	fastDev.getInstance("cModifyType").setValue(cModifyType);

	loadingWindow.close();
}

function showWaitingDialog(button) {
	loadingWindow = fastDev.create("ProgressBar", {
		container : "checkForm",
		text : "保存中..."
	});
	submitButton = button;
}

function onSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function() {
		if (result.status == 'ok') {
			fastDev.Ui.Window.parent.refreshPublicTree();
			fastDev.Ui.Window.parent.refreshDatagrid();
			fastDev.Ui.Window.parent.closeDialogPublicTree();
		}
	});
}

function doReset() {
	fastDev.getInstance('checkForm').resetData();
}