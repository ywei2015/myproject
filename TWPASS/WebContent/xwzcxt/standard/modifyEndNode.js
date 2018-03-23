var request = fastDev.Browser.getRequest();
var id = request['id']||'';
var form;
function fromReady() {
	var action = "", dataSource;

	dataSource = "actNode_getTPostActnodeById.action?id="
			+ id;
	action = "actNode_modifyTPostActnode.action";

	this.setOptions({
		"action" : action,
		"dataSource" : dataSource
	});
	form = this;
}

function sectionSelReady() {
	var queue = "";
	queue = "nodeform";
	this.setOptions({
				"initSource" : "actNode_queryAllSections.action"
			});
	sectionSel = this;

}

fastDev(function() {
	fastDev.getInstance('tpostactnode.c_actnode_id').setValue(id);
});

function doSave() {
	fastDev.getInstance('nodeform').submit();
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