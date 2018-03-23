var submitSuccess=function(data){
	fastDev.alert(data.msg, "信息", data.status,function(){
		if(data.status == 'ok') {
			parent.fastDev.getInstance("datagrid").refreshData(true);
			parent.dialog.close();	
			return false;
		}
	});
};