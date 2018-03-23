var request = fastDev.Browser.getRequest();
var Pid = request['Pid'] || '1';
var upTypeCode = request['upTypeCode']||"";
var tworkobjtypeform;
function fromReady()
{
	tworkobjtypeform = this;
}
function generateOtherData()
{
	var otherData= {
			"tworkobjtype.c_up_objtype_id":Pid
		};
		return otherData;
}
fastDev(function(){
	fastDev.getInstance('tworkobjtype.c_up_objtype_code').setValue(upTypeCode);
	//fastDev.getInstance('tworkobjtype.c_up_objtype_id').setValue(Pid);
});

function save() {
	tworkobjtypeform.setOptions({
		"otherSubmitData" : generateOtherData()
	});
	tworkobjtypeform.submit();
	
}

function doReset() {
	fastDev.getInstance('typeform').resetData();
}
function close(){
	
	parent.reqDialog.close();
}
function onSubmitSuccess(result) {
	fastDev.alert(result.msg, '信息提示', result.status, function(){
		if(result.status == 'ok') {
			parent.refreshTree();
			parent.reqDialog.close();
		}
	});
}