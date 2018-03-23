var request = fastDev.Browser.getRequest();//获取请求对象
var logid = request['id'];
var actiontype = request['actionType'];

function initTemplate(data){
	var source = document.getElementById('tpl').innerHTML;
	template = fastDev.create("Template", {
		htmlList : source,
		params : data
	});
	var dom = template.createStaticHtml();
	fastDev("body").append(dom);
}


fastDev(function(){
	fastDev.create("Proxy", {
		url : "log_getLogInfo.action",
		urlParam : {
			id : logid,
			type : actiontype
		}
	}).load(initTemplate);
});