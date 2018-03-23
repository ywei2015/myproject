var request = fastDev.Browser.getRequest();//获取请求对象
var appid = request['appid'];
function initTemplate(data){
	var source = document.getElementById('tpl').innerHTML;
	template = fastDev.create("Template", {
		htmlList : source,
		params : data
	});
	var dom = template.createStaticHtml();
	fastDev("body").append(dom);
}


fastDev.ready(function(){
	var proxy = fastDev.create("Proxy", {
		url : "app_getAppById.action",
		urlParam :{id:appid}
	}).load(initTemplate);
});