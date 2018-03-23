var request = fastDev.Browser.getRequest();
var Pid = request['Pid'] || '1';
//var upTypeCode = request['upTypeCode']||"";
var tworkobjtypeform;
function fromReady()
{
	tworkobjtypeform = this;
}
function generateOtherData()
{
	var otherData= {
			"tpostaction.c_UP_ACTION_ID":Pid
		};
		return otherData;
}

fastDev(function(){
	/*fastDev.get("activityType_getActionPostById.action", {
		data : {
			"id" : Pid
		},
		complete : function(rsp) {
			fastDev.getInstance('c_UP_ACTION_NAME').setValue(rsp['tpostaction.c_ACTION_NAME']);
		}
	});*/
	fastDev.getInstance('tpostaction.c_UP_ACTION_ID').setValue(Pid);
});

function doSave() {
	tworkobjtypeform.setOptions({
		"otherSubmitData" : generateOtherData()
	});
	tworkobjtypeform.submit();
}

function doReset() {
	fastDev.getInstance('typeform').resetData();
}

function onSubmitSuccess(result) {
	if(result['tpostaction.c_action_id']) {
		parent.addNode({
			id: result['tpostaction.c_action_id'],
			val: result['tpostaction.c_ACTION_NAME'],
			pid: result['tpostaction.c_UP_ACTION_ID']
		});
		parent.closeDialog();
	} else {
		fastDev.alert(result.msg, '信息提示', result.status);
	}
	 
	//不进行整棵树加载、刷新
	/*fastDev.alert(result.msg, '信息提示', result.status, function(){
		if(result.status == 'ok') {
			parent.refreshTree();
			parent.closeDialog();
		}
	});*/
}