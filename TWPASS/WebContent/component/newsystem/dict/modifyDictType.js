var request = fastDev.Browser.getRequest();
function onBeforeReady() {
	this.setOptions({
		dataSource : 'dict_initModifyDictType.action?id=' + request['id']
	});
}

function onParentBeforeReady() {
	this.setOptions({
		value: request['pid']
	});
}

function onSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function(){
		if(result.status == 'ok') {
			parent.editDictTypeTreeNode({
				pid : result.parentid,
				id : result.dicttypeid,
				val : result.dicttypename,
				allowmodify : result.allowmodify,
				type : result.type
			});
			parent.closeDialog();
		}
	});
}

function doSave() {
	fastDev.getInstance('dictTypeForm').submit();
}

function doReset() {
	fastDev.getInstance('dictTypeForm').resetData();
}