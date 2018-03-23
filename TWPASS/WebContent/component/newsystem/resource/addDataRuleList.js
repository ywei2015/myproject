var request = fastDev.Browser.getRequest();
var id = request['id'];//权限ID

var afterConstruct=function(){
	this.addInitReqParam({"id":id});
};

function doSave(){
	var list = fastDev.getInstance("datagrid").getValue();
	if(!list||list.length==0)
	{
		fastDev.alert("请至少选择一条记录进行操作","信息提示","warning");
        return;	
	}			    			
	var ruleIds = new Array();
    for (var i = 0; i < list.length;i++) {
        var obj = list[i];
        ruleIds.push(obj.ruleId);
    }
	fastDev.create("Proxy", {
		action : "dataright_bindPermissions.action"
	}).save({
		pid : id,
		ruleId:ruleIds.join(",")
	}, function(data) {
		fastDev.alert(data.msg, "信息提示", data.status, function() {
			if(data.status=="ok") {
				fastDev.Ui.Window.parent.refreshDatagrid();
				fastDev.Ui.Window.parent.dialog.close();
			}
		});
	});
}

function closeDialog(){
	fastDev.Ui.Window.parent.dialog.close();
}