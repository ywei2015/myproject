var request = fastDev.Browser.getRequest();//获取请求对象
var appid = request['appid'];
var submitSuccess=function(data){
	data = fastDev.Util.JsonUtil.parseJson(data);
	fastDev.alert(data.msg, "信息提示", data.status,function(){
		if(data.status == 'ok') {
			parent.refreshData();
			parent.appDialog.close();	
		}
	});
};

var afterConstruct=function(){
	this.addDataReqParam({"id":appid});
};