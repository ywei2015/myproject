var request = fastDev.Browser.getRequest();//获取请求对象
var id = request['id'];

var afterConstruct=function(){
	this.addInitReqParam({"id":id});
};

/**
 * 增加访问模式纪录
 */
function addPermission(){
    var list = fastDev.getInstance("datagrid").getValue();
    if (list.length <= 0) {
        fastDev.alert("请至少选择一条记录进行操作","信息提示","warning");
        return;
    }                
    var ids = new Array();                
    for (var i = 0; i < list.length; i++) {
        var obj = list[i];
        ids.push(obj.modeId);
    }        
	fastDev.create("Proxy", {
		action : "permission_addPermission.action"
	}).save({
		resourceCode : id,
		modes:ids.join(",")
	},function(data) {
		fastDev.alert(data.msg, "信息提示", data.status, function() {
	           if(data.status=="ok"){        	   
	        	   parent.fastDev.getInstance("permissionDatagrid").refreshData();
	        	   close();
	           }
		});
	});           
}

function close() {
	parent.closeDialog();
}