var request = fastDev.Browser.getRequest();//获取请求对象
var orgid = request['orgid'];
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
		url : "org_getOrgByID.action",
		urlParam : {
			orgID : orgid
		}
	}).load(initTemplate);
});