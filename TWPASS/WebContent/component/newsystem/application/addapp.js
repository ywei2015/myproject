var submitSuccess=function(data){
	fastDev.alert(data.msg, "信息提示", data.status,function(){
		if(data.status == 'ok') {
			parent.refreshData();
			parent.appDialog.close();	
		}
	});
};