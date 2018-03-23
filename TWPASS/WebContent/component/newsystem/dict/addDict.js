var request = fastDev.Browser.getRequest();
var dicttypeid = request['dicttypeid'] || 'ROOT';
var dicttypename = request['dicttypename'] || '顶级节点';
fastDev(function(){
	fastDev.getInstance('dictEntryInfo.dicttypeid').setValue(dicttypeid);
	fastDev('#dicttypename').setText(dicttypename);
});

function doSave() {
	fastDev.getInstance('dictform').submit();
}

function doReset() {
	fastDev.getInstance('dictform').resetData();
}

function onSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function(){
		if(result.status == 'ok') {
			parent.refreshData();
			parent.closeDialog();
		}
	});
}