var request = fastDev.Browser.getRequest();//获取请求对象
var positionid = request['positionid'];
var condition,currentId;

function afterConstruct(){
	this.addInitReqParam({realpositionid:positionid});
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
		action : "position_addRoleWithposition.action"
	}).save({
		positionid : positionid,
		roleids:ids.join(',')
	}, function(data) {
		fastDev.alert(data.msg, "信息", data.status, function() {
			if(data.status=="ok") {
				fastDev.Ui.Window.parent.fastDev.getInstance('datagrid').refreshData();
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

function refreshData(condition) {
	condition = condition || {};
	condition.realpositionid = positionid;
	if(currentId != undefined && currentId!=-1){
		condition['orgid'] = currentId;
	} else {
		condition['orgid'] = '';
	}
	fastDev.getInstance('datagrid').refreshData(condition);
}

function formAfterConstruct(){
//	this.setInitReqParam({ownorgid:userid});
}

function close() {
	fastDev.Ui.Window.parent.roleDialog.close();
}