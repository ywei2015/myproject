//取消数据列中按钮权限控制，根据code判断是否有权限
$(function(){
var ms_url;
/*if(parent.location.href.indexOf("/platform/Admin/main.html")!=-1){
	ms_url = location.href;
}else{
    ms_url = (window.dialogArguments) ? window.dialogArguments._url : parent.location.href;
}
if(!ms_url || ms_url===undefined){
	ms_url='';
}
*/
var projectName = location.pathname.substring(0,location.pathname.substr(1).indexOf('/')+1);
ms_url = encodeURIComponent(getStr(location.pathname,projectName));
var url = "../../../dynamic/Security/getOperationByUrl?url="+ms_url;   //默认的登录信息获取路径
$.getJSON(url,function(result){
	var jsonStr = result.dataset.jsonStr;
	var listButton = document.getElementsByTagName("button");
	if(result.dataset.jsonStr != "all"){
		var jsonStrs = result.dataset.jsonStr.split(",");
		for(var j=0;j<listButton.length;j++){
			var id = listButton[j].id;
			var authCode = $("#"+id).attr("authCode");
			if(authCode != "" && authCode != null && authCode != undefined){
				if($.inArray(authCode, jsonStrs) < 0)
				{
					$("#"+id).hide();
				}
			}
		}
	}
});
});

function validBarFunc(btName){
	if(jsonStr != "" && jsonStr != undefined && jsonStr != "all"){
		console.log(jsonStr);
		var jsonStrs = jsonStr.split(",");
		if($.inArray(btName, jsonStrs) < 0)
		{
			return false;
		}
	}
	return true;
}
function getStr(string,str){ 
    var str_before = string.split(str)[0]; 
    var str_after = string.split(str)[1]; 
    return str_after;
}