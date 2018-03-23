
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

function showManaPosition(id, value) {
	fastDev.getInstance('position.manaPosi').clean(true);
	fastDev.getInstance('position.manaPosi').initRefresh({
		url : 'user_initUserPos.action?orgid=' + id
	});
}