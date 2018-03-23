var request = fastDev.Browser.getRequest();//获取请求对象
var logid = request['id'];


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
		url : "mylog_getLogInfo.action",
		urlParam : {
			id : logid
		}
	}).load(initTemplate);
});