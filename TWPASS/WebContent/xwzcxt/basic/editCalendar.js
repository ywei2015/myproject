var g_seq = "";
var form;

function getQueryString(name) {
	//获取浏览器URL中参数，此处用于判断是否需要验证码。
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

function FormReady(){
	form = this;
	g_seq = getQueryString("seq");
	var action = "calendar_modifyCalData.action?seq="+g_seq;
	var initSource = "calendar_getCalDataBySeq.action?seq="+g_seq;
	form.setOptions({
		"action":action,
		"dataSource":initSource
		});
}

var submitSuccess=function(data){
	fastDev.alert(data.msg, "信息提示", data.result,function(){
		if (data.result >= 0){//成功
			parent.appDialog.close();
			parent.refreshDatagrid2();
		}
	});
};

