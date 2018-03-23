var request = fastDev.Browser.getRequest();//获取请求对象
var id = request['id'];
var submitSuccess=function(data){
	fastDev.alert(data.msg, "信息", data.status,function(){
		if(data.status == 'ok') {
			parent.fastDev.getInstance("datagrid").refreshData(true);
			parent.dialog.close();	
			return false;
		}
	});
};	

var afterConstruct=function(){
	this.addDataReqParam({"ruleId":id});
};