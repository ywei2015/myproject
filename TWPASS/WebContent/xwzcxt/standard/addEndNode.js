var request = fastDev.Browser.getRequest();
var Pid = request['Pid'] || '1';
var tworkobjtypeform;
var sectionSel;
function fromReady() {
	tworkobjtypeform = this;
}
function generateOtherData() {
	var otherData = {
		"tpostactnode.c_ACTION_ID" : Pid
	};
	return otherData;
}

function sectionSelReady() {
	var queue = "";
	queue = "nodeform";
	this.setOptions({
				"initSource" : "actNode_queryAllSections.action"
			});
	sectionSel = this;

}

function doSave() {
	tworkobjtypeform.setOptions({
		"otherSubmitData" : generateOtherData()
	});
	tworkobjtypeform.submit();

}

function doReset() {
	fastDev.getInstance('nodeform').resetData();
}

function onSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function() {
		if (result.status == 'ok') {
			parent.closeDialog();
			parent.refreshDatagrid();
		}
	});
}