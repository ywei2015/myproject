var g_actitem_id="";
var g_cid="";

function FormReady(){
	form = this;
	g_actitem_id = getQueryString("item");
	g_cid = getQueryString("stepid");
	
	var action = "";
	var dataSource = "";
	if (g_actitem_id != null && g_actitem_id != ""){
		action = "basicAdd_addStepData.action?c_actitem_id="+g_actitem_id;
		form.setOptions({"action":action});
	}
	
	if (g_cid != null && g_cid != ""){
		//console.info(g_cid);
		dataSource = "basicAdd_getStepDataByCid.action?c_id="+g_cid;
		action = "basicAdd_modifyStepDataByCid.action?c_id="+g_cid;
		form.setOptions({"action":action,"dataSource":dataSource});
	}
}
function BeforeInit(){
	if (g_actitem_id != null && g_actitem_id != ""){
		fastDev.getInstance('basicMouldPojo.c_actitem_id').setValue(g_actitem_id);
	}
	if(g_cid != null && g_cid != ""){
		fastDev.getInstance('basicMouldPojo.c_id').setValue(g_cid);
		fastDev.getInstance('basicMouldPojo.c_actstep_index').setReadonly(true);
	}
}

var submitSuccess=function(result){
	//console.info(result);
	fastDev.alert(result.msg, '信息提示', result.status, function() {
		if (result.status == 'ok'){//成功
			parent.appDialog.close();
			//parent.refreshDetailgrid();
			parent.refreshDatagrid()
		}
	});
};

function getQueryString(name) {
	//获取浏览器URL中参数，此处用于判断是否需要验证码。
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}