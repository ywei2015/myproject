var request = fastDev.Browser.getRequest();
var id = request['id']||'';
var form;
function fromReady() {
	var action = "", dataSource;

	dataSource = "activityType_getActionPostById.action?id="
			+ id;
	action = "activityType_updateActionPost.action";

	this.setOptions({
		"action" : action,
		"dataSource" : dataSource
	});
	form = this;
}
fastDev(function() {
	fastDev.getInstance('tpostaction.c_action_id').setValue(id);
	// fastDev('#tWorkObjtype.c_UP_OBJTYPE_CODE').setText(Pid);
});

function doSave() {
	fastDev.getInstance('typeform').submit();
}

function doReset() {
	fastDev.getInstance('typeform').resetData();
}

function onSubmitSuccess(result) {
	if(result['tpostaction.c_action_id']) {
		//parent.editNode(result['tpostaction.c_action_id'], result['tpostaction.c_ACTION_NAME']);
		parent.refreshTree();
		parent.closeDialog();
	} else {
		fastDev.alert(result.msg, '信息提示', result.status);
	}
}