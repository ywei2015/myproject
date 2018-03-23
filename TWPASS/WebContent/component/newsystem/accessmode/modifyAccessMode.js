var request = fastDev.Browser.getRequest();//获取请求对象
var id = request['id'];

var submitSuccess=function(data){
	fastDev.alert(data.msg, "信息提示", data.status,function(){
		if(data.status == 'ok') {
			parent.refreshData();
			parent.dialog.close();	
		}
	});
};	

var afterConstruct=function(){
	this.addDataReqParam({"id":id});		
};

function beforeSubmit(){
	var o = fastDev.getInstance("oldid");
	o.setValue(id);
}