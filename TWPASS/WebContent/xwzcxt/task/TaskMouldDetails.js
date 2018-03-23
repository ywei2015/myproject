//
//function onAfterLoadData() {
//	fastDev.getInstance('modifycheckForm').setValue({
//		'org.basicID':1001619
//	});
//}
var request = fastDev.Browser.getRequest();
var taskId = request['taskId'];
var edit = request['edit'];

function onFormBeforeReady(){
	if (edit == "details") {
		this.setOptions({
			dataSource : 'taskEntry_queryTaskEntry.action?action=1&taskId='+taskId
		});
	}
}
function onBeforeGridReady(){	 
	this.setOptions({
		initSource : "task_queryTaskByPage.action?action=2"
	});	 
};
function onAfterInitRender() {
	var body = fastDev(window).height();
	this.setHeight(body-60);
}
function doSubmit() {
	var form = fastDev.getInstance('checkForm');
	form.setOptions({
		action : "taskEdit_editData.action?action=1&edit=" + edit
	});	 
	form.submit();
}
function doReset() {
	fastDev.getInstance('checkForm').resetData();
}
//表单提交后回调
function onSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function() {
		if (result.status == 'ok') {
			parent.refreshDatagrid();
			parent.closeDialog();
		}
	});
}