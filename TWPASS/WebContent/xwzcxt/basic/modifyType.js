var request = fastDev.Browser.getRequest();
var id = request['id']||'';
var form;
function fromReady() {
	var action = "", dataSource;

	dataSource = "ObjType_getTWorkObjtypeById.action?id="
			+ id;
	action = "ObjType_updateObjType.action";

	this.setOptions({
		"action" : action,
		"dataSource" : dataSource
	});
	form = this;
}
fastDev(function() {
	fastDev.getInstance('tworkobjtype.c_objtype_id').setValue(id);
	// fastDev('#tWorkObjtype.c_UP_OBJTYPE_CODE').setText(Pid);
});

function save() {
	fastDev.getInstance('typeform').submit();
}

function doReset() {
	fastDev.getInstance('typeform').resetData();
}
function close() {

	parent.modifyDialog.close();
}
function onSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function() {
		if (result.status == 'ok') {
			parent.refreshTree();
			parent.modifyDialog.close();
		}
	});
}