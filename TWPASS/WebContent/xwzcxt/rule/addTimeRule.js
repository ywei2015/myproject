var g_timerule_id="";

function FormReady(){
	form = this;
	g_timerule_id = getQueryString("timeruleid");
	console.info(g_timerule_id);
	var action = "basicAdd_addTimeRule.action";
	var dataSource = "";
	if (g_timerule_id != null && g_timerule_id != ""){
		action = "basicAdd_modifyTimeRuleById.action?c_timerule_id="+g_timerule_id;
		dataSource = "basicAdd_getTimeRuleById.action?c_timerule_id="+g_timerule_id;
	}
	
	form.setOptions({"action":action,"dataSource":dataSource});
}



var submitSuccess=function(data){
	console.info(data);
	fastDev.alert(data.msg, "信息提示", data.result,function(){
		if (data.result >= 0){//成功
			parent.appDialog.close();
			parent.refreshDatagrid();
		}
	});
};

function getQueryString(name) {
	//获取浏览器URL中参数，此处用于判断是否需要验证码。
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}