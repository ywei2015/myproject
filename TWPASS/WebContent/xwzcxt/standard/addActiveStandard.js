var request = fastDev.Browser.getRequest();
var edit = request['edit'];
var g_nodeId = request['c_actnode_id'];
var g_itemId = request['c_actitem_id'];

var form;

var submitSuccess=function(result){
	fastDev.alert(result.msg, '信息提示', result.status, function() {
		if (result.status == 'ok'){//成功
			parent.closeDialog();
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

function FormReady(){
	form = this;
	var action = "";
	var dataSource = "";
	if (edit=="update"){//为修改功能，需要将携带过来的参数去后台查询到对应的数据
		action = "basicAdd_updateActiveStandard.action?cid="+g_itemId;
		dataSource = "basicAdd_getStandardById.action?cid="+g_itemId;
		
	}else{//新增功能
		action = "basicAdd_addActiveStandard.action";
	}
	form.setOptions({
		"action":action,
		"dataSource":dataSource
	});
}

function FormAfter() {
	fastDev.getInstance("basicMouldPojo.c_actnode_id").setValue(g_nodeId);
}
