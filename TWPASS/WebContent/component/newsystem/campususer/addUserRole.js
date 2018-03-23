var request = fastDev.Browser.getRequest();//获取请求对象
var userid = request['userid'];
var dialog = fastDev.Ui.Window.getData("parent").userDialog;
var condition, currentId;
function afterConstruct(){
	this.addInitReqParam({userid:userid});
}

function doSave(){
	var datagrid = fastDev.getInstance("datagrid");
	var objvalue = datagrid.getValue();
	if(objvalue.length <= 0){
		fastDev.alert("没有选择添加的角色!", "信息提示", "warning");
	    return false;
	}
	var ids = [];
	for (var i = 0; i < objvalue.length; i++) {
        var obj = objvalue[i];
        ids.push(obj.roleId);
    }
	fastDev.create("Proxy", {
		action : "user_addRoleWithuser.action"
	}).save({
		userid : userid,
		roleids:ids.join(',')
	}, function(data) {
		fastDev.alert(data.msg, "信息", data.status, function() {
			if(data.status=="ok"){
				fastDev.Ui.Window.parent.refreshDatagrid();
				close();
			}
		});
	});	
}

function submit(){
	var form = fastDev.getInstance("searchform");
	refreshData(form.getItems());	
}

function reset(){
	var form = fastDev.getInstance("searchform");
	form.cleanData();	
}

function onNodeClick(event, id, value){
	currentId = id;							    
	refreshData();    
}

function formAfterConstruct(){
	this.addInitReqParam({userid:userid});
}

function refreshData(condition) {
	condition = condition || {};
	condition.userid = userid;
	if(currentId !== undefined && currentId!=-1){
		condition['orgid'] = currentId;
	} else {
		condition['orgid'] = '';
	}
	fastDev.getInstance('datagrid').refreshData(condition);
}

function close() {
	fastDev.Ui.Window.parent.roleDialog.close();
}
