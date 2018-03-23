var request = fastDev.Browser.getRequest();
var dicttypeid = request['dicttypeid'] || 'ROOT';
var dictid = request['dictid'];
var dicttypename = request['dicttypename'] || '顶级节点';
var allowdelete = request['allowdelete'];
fastDev(function(){
	fastDev('#dicttypename').setText(dicttypename);
});

function onCheckBeforeReady() {
	if(allowdelete == '1'){
		this.setOptions({
			disabled : true
		});
	}
}

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

function onBeforeReady() {
	this.setOptions({
		dataSource : 'dict_getDictEntry.action?dictid='+dictid+'&dicttypeid='+dicttypeid
	});
}

function onTreeBeforeReady() {
	this.setOptions({
		initSource : 'dict_initDictEntryTree.action?dictEntryInfo.dicttypeid=' + dicttypeid
	});
}