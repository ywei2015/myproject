var request = fastDev.Browser.getRequest();
var id = request['id'];//权限ID

var afterConstruct=function(){
	this.addInitReqParam({"id":id});
};

function addDataRule(){
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
		action : "dataright_batchBindPermissions.action"
	}).save({
		pids : id,
		ruleId:ruleIds.join(",")
	}, function(data) {
		fastDev.alert(data.msg, "信息", data.status, function() {
			if(data.status=="ok"){
				parent.dialog.close();
				return false;
			}
		});
	});
}

function back(){
	window.location.href = "dataRuleList.html?id="+id; 
}

function closeDialog(){
	parent.dialog.close();
}